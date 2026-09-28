package models.flights;

import java.time.ZonedDateTime;

public record FlightStop(AirportCode airport, ZonedDateTime time) {
}
