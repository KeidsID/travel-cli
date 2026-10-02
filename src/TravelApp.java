import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import models.flights.Flight;

public final class TravelApp {
    private final List<Flight> flights;

    public TravelApp(Flight[] flights) {
        this.flights = new ArrayList<Flight>(List.of(flights));
    }

    /**
     * Searches for flights based on the provided criteria.
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
}
