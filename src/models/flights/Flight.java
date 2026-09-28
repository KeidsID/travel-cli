package models.flights;

public final class Flight {
    private final String flightNumber;
    private final String airline;
    private final FlightStop departure;
    private final FlightStop arrival;

    /**
     * The maximum number of {@link #passengerCount} that can be
     * booked on this flight.
     */
    private final int maxPassangers;
    private final double ticketPrice;
    private int passengerCount = 0;

    public Flight(String flightNumber, String airline, FlightStop departure, FlightStop arrival, int maxPassanger,
            double ticketPrice) {
        if (maxPassanger < 0) {
            throw new IllegalArgumentException("Flight.maxPassangers cannot be negative");
        }

        if (ticketPrice < 0) {
            throw new IllegalArgumentException("Flight.ticketPrice cannot be negative");
        }

        this.flightNumber = flightNumber;
        this.airline = airline;
        this.departure = departure;
        this.arrival = arrival;
        this.maxPassangers = maxPassanger;
        this.ticketPrice = ticketPrice;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public FlightStop getDeparture() {
        return departure;
    }

    public FlightStop getArrival() {
        return arrival;
    }

    public int getMaxPassangers() {
        return maxPassangers;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public int setPassengerCount(int passengerCount) {
        if (passengerCount < 0) {
            throw new IllegalArgumentException("Flight.passengerCount cannot be negative");
        }

        if (passengerCount > this.maxPassangers) {
            throw new IllegalArgumentException(
                    "Flight.passengerCount cannot be greater than Flight.maxPassangers");
        }

        return this.passengerCount = passengerCount;
    }
}
