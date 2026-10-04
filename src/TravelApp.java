import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import models.booking.Booking;
import models.booking.Customer;
import models.booking.FlightBooking;
import models.booking.StayBooking;
import models.flights.Flight;
import models.stays.Stay;
import models.stays.StayGuest;

public final class TravelApp {
    private final List<Flight> flights;
    private final List<Stay> stays;
    private final List<Booking<?>> bookings;

    public TravelApp(Flight[] flights, Stay[] stays) {
        this.flights = new ArrayList<Flight>(List.of(flights));
        this.stays = new ArrayList<Stay>(List.of(stays));
        this.bookings = new ArrayList<Booking<?>>();
    }

    /**
     * Search for flights based on the provided criteria.
     *
     * @param origin         The origin airport location.
     * @param destination    The destination airport location.
     * @param date           The date of the flight (YYYY-MM-DD).
     * @param passengerCount The number of passengers.
     * @return A list of matching flights.
     */
    public List<Flight> searchFlights(String origin, String destination, LocalDate date, int passengerCount) {
        return flights.stream()
                .filter((flight) -> {
                    String flightOrigin = flight.getDeparture().airport().getLocation().toLowerCase();
                    String flightDestination = flight.getArrival().airport().getLocation().toLowerCase();
                    LocalDate flightDate = flight.getDeparture().dateTime().toLocalDate();
                    int flightAvailableSeats = flight.getAvailableSeats();

                    return flightOrigin.contains(origin.toLowerCase()) &&
                            flightDestination.contains(destination.toLowerCase()) &&
                            flightDate.isEqual(date) &&
                            flightAvailableSeats >= passengerCount;
                })
                .toList();
    }

    /**
     * Search for stays based on provided criteria.
     *
     * @param city               the city or location to search for
     * @param checkIn            the check-in date
     * @param checkOut           the check-out date
     * @param availableRoomCount the number of rooms required
     * @return stays matching the location and room availability criteria
     */
    public List<Stay> searchStays(String city, LocalDate checkIn, LocalDate checkOut, int availableRoomCount) {
        if (checkIn.isEqual(checkOut)) {
            throw new IllegalArgumentException("Cannot check-out on check-in day");
        }

        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException("Check-out must be after check-in");
        }

        if (availableRoomCount <= 0) {
            throw new IllegalArgumentException("The required room count must be positive");
        }

        String normalizedCity = city.toLowerCase(Locale.ROOT);
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime checkInDateTime = checkIn.atStartOfDay(zone);
        ZonedDateTime checkOutDateTime = checkOut.atStartOfDay(zone);

        return stays.stream()
                .filter((stay) -> {
                    return (stay.getName().toLowerCase(Locale.ROOT).contains(normalizedCity)
                            || stay.getLocation().toLowerCase(Locale.ROOT).contains(normalizedCity))
                            && stay.getAvailableRoomCount(checkInDateTime, checkOutDateTime) >= availableRoomCount;
                })
                .toList();
    }

    public FlightBooking bookFlight(Flight flight, int bookedCount, Customer[] customers) {
        if (flight.getAvailableSeats() == 0) {
            throw new IllegalStateException("No more seats available for this flight.");
        }

        FlightBooking booking = new FlightBooking(flight, bookedCount, customers);
        flight.setPassengerCount(flight.getPassengerCount() + bookedCount);
        bookings.add(booking);
        return booking;
    }

    public StayBooking bookStay(Stay stay, int roomCount, LocalDate checkIn, LocalDate checkOut, Customer[] customers) {
        ZoneId stayZone = stay.getZoneId();
        ZonedDateTime checkInDateTime = checkIn.atStartOfDay(stayZone).withHour(15).withMinute(0);
        ZonedDateTime checkOutDateTime = checkOut.atStartOfDay(stayZone).withHour(12).withMinute(0);

        if (stay.getAvailableRoomCount(checkInDateTime, checkOutDateTime) < roomCount) {
            throw new IllegalStateException("Not enough rooms available for this stay.");
        }

        StayBooking booking = new StayBooking(stay, roomCount, checkInDateTime, checkOutDateTime, customers);

        StayGuest guest = new StayGuest(booking.getBookedId(), roomCount, checkInDateTime,
                checkOutDateTime);
        stay.addGuest(guest);

        bookings.add(booking);
        return booking;
    }
}
