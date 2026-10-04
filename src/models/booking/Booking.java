package models.booking;

import utils.IOHelper;
import utils.IdGenerator;

public sealed abstract class Booking<T> permits FlightBooking, StayBooking {
    private final String id;

    /**
     * Referenced booked item.
     */
    private final T bookedItem;

    /**
     * The number of tickets/rooms paid for in this booking.
     */
    private final int bookedCount;

    private final Customer[] customers;

    public Booking(T bookedItem, int bookedCount, Customer[] customers) {
        this.id = "booking-" + IdGenerator.generateId();
        this.bookedItem = bookedItem;
        this.bookedCount = bookedCount;
        this.customers = customers;
    }

    public String getBookedId() {
        return id;
    }

    public T getBookedItem() {
        return bookedItem;
    }

    public int getBookedCount() {
        return bookedCount;
    }

    public Customer[] getCustomers() {
        return customers;
    }

    /**
     * Print booking details to the user console.
     */
    public abstract void printDetails();

    public final void printCustomers() {
        for (int i = 0; i < customers.length; i++) {
            Customer customer = customers[i];

            String contact = customer.contact();
            String contactToPrint = contact.isBlank() ? "" : " <" + contact + ">";

            IOHelper.print(((i == 0) ? "" : ", ") + customer.name() + contactToPrint);
        }
        IOHelper.println("");
    }
}
