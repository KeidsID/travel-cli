package data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import models.flights.AirportCode;
import models.flights.Flight;
import models.flights.FlightStop;

public final class DummyFlights {
    private DummyFlights() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static final Flight[] data;

    static {
        var jktZone = ZoneId.of("Asia/Jakarta");
        var mksZone = ZoneId.of("Asia/Makassar");
        var jypZone = ZoneId.of("Asia/Jayapura");

        var date = LocalDate.now();
        var wib = ZonedDateTime.of(date, LocalTime.MIDNIGHT, jktZone);
        var wita = ZonedDateTime.of(date, LocalTime.MIDNIGHT, mksZone);

        var jktToMksGaruda = new Flight("GA401", "Garuda Indonesia",
                new FlightStop(AirportCode.CGK, wib.withHour(8).withMinute(0)),
                new FlightStop(AirportCode.UPG, wib.withHour(8).withMinute(0)
                        .plusHours(2).plusMinutes(30).withZoneSameInstant(mksZone)),
                180, 3000000);

        var jktToMksCitilink = new Flight("QG322", "Citilink",
                new FlightStop(AirportCode.CGK, wib.withHour(13).withMinute(45)),
                new FlightStop(AirportCode.UPG, wib.withHour(13).withMinute(45)
                        .plusHours(2).plusMinutes(30).withZoneSameInstant(mksZone)),
                180, 2800000);

        var mksToJypBatikAir = new Flight("ID6185", "Batik Air",
                new FlightStop(AirportCode.UPG, wita.withHour(10).withMinute(15)),
                new FlightStop(AirportCode.DJJ, wita.withHour(10).withMinute(15)
                        .plusHours(3).plusMinutes(30).withZoneSameInstant(jypZone)),
                150, 4400000);

        var mksToJypGaruda = new Flight("GA652", "Garuda Indonesia",
                new FlightStop(AirportCode.UPG, wita.withHour(16).withMinute(40)),
                new FlightStop(AirportCode.DJJ, wita.withHour(16).withMinute(40)
                        .plusHours(3).plusMinutes(30).withZoneSameInstant(jypZone)),
                180, 4600000);

        var jktToJypCitilink = new Flight("QG330", "Citilink",
                new FlightStop(AirportCode.CGK, wib.withHour(7).withMinute(0)),
                new FlightStop(AirportCode.DJJ, wib.withHour(7).withMinute(0)
                        .plusHours(5).plusMinutes(20).withZoneSameInstant(jypZone)),
                165, 6800000);

        var jktToJypBatikAir = new Flight("ID6188", "Batik Air",
                new FlightStop(AirportCode.CGK, wib.withHour(11).withMinute(35)),
                new FlightStop(AirportCode.DJJ, wib.withHour(11).withMinute(35)
                        .plusHours(5).plusMinutes(20).withZoneSameInstant(jypZone)),
                150, 6500000);

        data = new Flight[] {
                jktToMksGaruda,
                scheduledOffer(jktToMksGaruda, 1),
                scheduledOffer(jktToMksGaruda, 2),
                jktToMksCitilink,
                scheduledOffer(jktToMksCitilink, 1),
                scheduledOffer(jktToMksCitilink, 2),

                mksToJypBatikAir,
                scheduledOffer(mksToJypBatikAir, 1),
                scheduledOffer(mksToJypBatikAir, 2),
                mksToJypGaruda,
                scheduledOffer(mksToJypGaruda, 1),
                scheduledOffer(mksToJypGaruda, 2),

                jktToJypCitilink,
                scheduledOffer(jktToJypCitilink, 1),
                scheduledOffer(jktToJypCitilink, 2),
                jktToJypBatikAir,
                scheduledOffer(jktToJypBatikAir, 1),
                scheduledOffer(jktToJypBatikAir, 2)
        };
    }

    private static Flight scheduledOffer(Flight flight, int daysLater) {
        var discount = daysLater > 3 ? 0.3 : 0.1 * daysLater;

        return flight.copyWithSchedule(
                flight.getDeparture().dateTime().plusDays(daysLater),
                flight.getArrival().dateTime().plusDays(daysLater))
                .copyWithTicketPrice(flight.getTicketPrice() * (1 - discount));
    }
}
