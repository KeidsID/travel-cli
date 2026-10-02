package models.flights;

import java.time.Duration;
import java.time.ZonedDateTime;

import utils.IDGenerator;

public final class Flight {
    private final String id;
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

        this.id = flightNumber + "-" + IDGenerator.generateID();
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.departure = departure;
        this.arrival = arrival;
        this.maxPassangers = maxPassanger;
        this.ticketPrice = ticketPrice;
    }

    public String getId() {
        return id;
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

    public int getMaxPassengerCount() {
        return maxPassangers;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    /**
     * @return The number of available seats on this flight.
     */
    public int getAvailableSeats() {
        return maxPassangers - passengerCount;
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

    public Duration getEstimatedDuration() {
        return Duration.between(departure.dateTime(), arrival.dateTime());
    }

    public Flight copyWithSchedule(ZonedDateTime departureDateTime, ZonedDateTime arrivalDateTime) {
        return new Flight(
                flightNumber,
                airline,
                new FlightStop(departure.airport(), departureDateTime),
                new FlightStop(arrival.airport(), arrivalDateTime),
                maxPassangers, ticketPrice);
    }

    public Flight copyWithTicketPrice(double ticketPrice) {
        return new Flight(
                flightNumber,
                airline,
                departure,
                arrival,
                maxPassangers, ticketPrice);
    }
}
