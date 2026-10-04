package models.booking;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import models.stays.Stay;
import utils.IOHelper;

public final class StayBooking extends Booking<Stay> {
    private final ZonedDateTime checkInDateTime;
    private final ZonedDateTime checkOutDateTime;

    public StayBooking(
            Stay bookedItem,
            int paidCount,
            ZonedDateTime checkInDateTime,
            ZonedDateTime checkOutDateTime,
            Customer[] customers) {
        super(bookedItem, paidCount, customers);

        this.checkInDateTime = checkInDateTime;
        this.checkOutDateTime = checkOutDateTime;
    }

    @Override
    public void printDetails() {
        Stay stay = getBookedItem();

        IOHelper.print("Nama Penginapan: ");
        IOHelper.println(stay.getName());

        IOHelper.print("Lokasi         : ");
        IOHelper.println(stay.getLocation());

        IOHelper.print("Check-in       : ");
        IOHelper.printZonedDateTime(checkInDateTime);
        IOHelper.println("");

        IOHelper.print("Check-out      : ");
        IOHelper.printZonedDateTime(checkOutDateTime);
        IOHelper.println("");

        IOHelper.print("Jumlah kamar   : ");
        IOHelper.println(String.valueOf(getBookedCount()));

        IOHelper.print("Tamu           : ");
        printCustomers();
        IOHelper.println("");

        IOHelper.print("Total Bayar    : ");

        int bookedCount = getBookedCount();
        double roomPrice = stay.getRoomPricePerNight();
        long nightsCount = ChronoUnit.DAYS.between(checkInDateTime.toLocalDate(), checkOutDateTime.toLocalDate());

        IOHelper.printCurrency(bookedCount * nightsCount * roomPrice);
        IOHelper.print(" (");
        IOHelper.print(String.valueOf(bookedCount));
        IOHelper.print(" kamar x ");
        IOHelper.print(String.valueOf(nightsCount));
        IOHelper.print(" malam x ");
        IOHelper.printCurrency(roomPrice);
        IOHelper.println(")");
    }

}
