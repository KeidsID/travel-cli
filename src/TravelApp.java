import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import models.flights.Flight;
import models.stays.Stay;

public final class TravelApp {
    private final List<Flight> flights;
    private final List<Stay> stays;

    public TravelApp(Flight[] flights, Stay[] stays) {
        this.flights = new ArrayList<Flight>(List.of(flights));
        this.stays = new ArrayList<Stay>(List.of(stays));
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
        if (!checkOut.isAfter(checkIn)) {
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
}
