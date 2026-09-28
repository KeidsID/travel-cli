package data;

import java.time.*;

import models.flights.*;

public final class DummyFlights {
    private DummyFlights() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final Flight[] data;

    static {
        var wib = ZonedDateTime.of(2026, 10, 1, 0, 0, 0, 0, ZoneId.of("Asia/Jakarta"));
        var wita = ZonedDateTime.of(2026, 10, 1, 0, 0, 0, 0, ZoneId.of("Asia/Makassar"));
        var wit = ZonedDateTime.of(2026, 10, 1, 0, 0, 0, 0, ZoneId.of("Asia/Jayapura"));

        var mksToJktLion = new Flight(
                "JT793",
                "Lion Airlines",
                new FlightStop(AirportCode.UPG, wita.withHour(16).withMinute(55)),
                new FlightStop(AirportCode.CGK, wib.withHour(18).withMinute(10)),
                150,
                2088193.20);
        mksToJktLion.setPassengerCount(149);

        var jktToMksCitilink = new Flight(
                "QG340",
                "Citilink",
                new FlightStop(AirportCode.CGK, wib.withDayOfMonth(3).withHour(14).withMinute(15)),
                new FlightStop(AirportCode.UPG, wita.withDayOfMonth(3).withHour(17).withMinute(45)),
                150,
                2433094.81);

        var jktToJayapuraGaruda = new Flight(
                "GA656",
                "Garuda Indonesia",
                new FlightStop(AirportCode.CGK,
                        wib.withDayOfMonth(3).withHour(23).withMinute(15)),
                new FlightStop(AirportCode.DJJ,
                        wit.withDayOfMonth(3).withHour(6).withMinute(35)),
                150,
                9641681.57);

        data = new Flight[] { mksToJktLion, jktToMksCitilink, jktToJayapuraGaruda };
    }
}
