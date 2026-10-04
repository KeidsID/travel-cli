package models.stays;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import utils.IdGenerator;

public final class Stay {
    private final String id;
    private final String name;
    private final ZoneId zoneId;
    private final String location;

    /**
     * The number of rooms built at the stay. Not the number of available rooms.
     * 
     * If you're looking for available rooms, refer to
     * {@link #getAvailableRoomCount} method.
     */
    private final int roomCount;
    private final double roomPricePerNight;

    private List<StayGuest> guests;

    public Stay(String name, String location, ZoneId zoneId, int roomCount, double roomPricePerNight) {

        this.id = "stay-" + IdGenerator.generateId();
        this.name = name;
        this.location = location;
        this.zoneId = zoneId;

        if (roomCount < 0) {
            throw new IllegalArgumentException("Stay.roomCount cannot be negative");
        }
        this.roomCount = roomCount;

        if (roomPricePerNight < 0) {
            throw new IllegalArgumentException("Stay.roomPricePerNight cannot be negative");
        }
        this.roomPricePerNight = roomPricePerNight;
        this.guests = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public String getLocation() {
        return location;
    }

    public double getRoomPricePerNight() {
        return roomPricePerNight;
    }

    public void addGuest(StayGuest guest) {
        int availableRoomCount = getAvailableRoomCount(guest.checkIn(), guest.checkOut());

        if (guest.roomCount() > availableRoomCount) {
            throw new IllegalArgumentException(
                    "No rooms are available. The Stay cannot accommodate guests.");
        }

        guests.add(guest);
    }

    /**
     * Returns the rooms that remain free for the entire requested stay.
     */
    public int getAvailableRoomCount(ZonedDateTime checkIn, ZonedDateTime checkOut) {
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }

        Instant start = checkIn.toInstant();
        Instant end = checkOut.toInstant();
        TreeSet<Instant> occupancyChangePoints = new TreeSet<>();
        occupancyChangePoints.add(start);

        for (StayGuest guest : guests) {
            Instant guestStart = guest.checkIn().toInstant();
            if (!guestStart.isBefore(start) && guestStart.isBefore(end)) {
                occupancyChangePoints.add(guestStart);
            }
        }

        int maximumOccupiedRooms = 0;
        for (Instant point : occupancyChangePoints) {
            int occupiedRooms = getOccupiedRoomCountAt(guests, point);
            maximumOccupiedRooms = Math.max(maximumOccupiedRooms, occupiedRooms);
        }

        return (int) (roomCount - maximumOccupiedRooms);
    }

    private static int getOccupiedRoomCountAt(List<StayGuest> guests, Instant point) {
        return guests.stream()
                .filter((guest) -> {
                    return !guest.checkIn().toInstant().isAfter(point)
                            && guest.checkOut().toInstant().isAfter(point);
                })
                .mapToInt(StayGuest::roomCount)
                .sum();
    }
}
