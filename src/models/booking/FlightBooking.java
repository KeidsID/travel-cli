package models.booking;

import models.flights.Flight;
import models.flights.FlightStop;
import utils.IOHelper;

public final class FlightBooking extends Booking<Flight> {

    public FlightBooking(Flight bookedItem, int paidCount, Customer[] customers) {
        super(bookedItem, paidCount, customers);
    }

    @Override
    public void printDetails() {
        Flight flight = getBookedItem();
        FlightStop departure = flight.getDeparture();
        FlightStop arrival = flight.getArrival();

        IOHelper.print("No. Penerbangan: ");
        IOHelper.println(flight.getFlightNumber());

        IOHelper.print("Maskapai       : ");
        IOHelper.println(flight.getAirline());

        IOHelper.print("Rute           : ");
        IOHelper.print(departure.airport().getLocation() + " (" + departure.airport().getCode() + ")");
        IOHelper.print(" ---> ");
        IOHelper.println(arrival.airport().getLocation() + " (" + arrival.airport().getCode() + ")");

        IOHelper.print("Jadwal         : ");

        var departureDateTime = departure.dateTime();
        var arrivalDateTime = arrival.dateTime();

        if (departureDateTime.toLocalDate().equals(arrivalDateTime.toLocalDate())) {
            IOHelper.printZonedDateTime(arrivalDateTime, "dd MMM yyyy");
            IOHelper.print(", ");
            IOHelper.printZonedDateTime(departureDateTime, "HH:mm (z)");
            IOHelper.print(" --[");
            IOHelper.printDuration(flight.getEstimatedDuration());
            IOHelper.print("]--> ");
            IOHelper.printZonedDateTime(arrivalDateTime, "HH:mm (z)");
        } else {
            IOHelper.printZonedDateTime(departureDateTime);
            IOHelper.print(" --[");
            IOHelper.printDuration(flight.getEstimatedDuration());
            IOHelper.print("]--> ");
            IOHelper.printZonedDateTime(arrivalDateTime);
        }
        IOHelper.println("");

        IOHelper.print("Penumpang      : ");
        printCustomers();

        IOHelper.print("Total Bayar    : ");

        int bookedCount = getBookedCount();
        double ticketPrice = flight.getTicketPrice();

        IOHelper.print(String.valueOf(bookedCount));
        IOHelper.print(" x ");
        IOHelper.printCurrency(ticketPrice);
        IOHelper.print(" = ");
        IOHelper.printCurrency(bookedCount * ticketPrice);
        IOHelper.println("");
    }
}
