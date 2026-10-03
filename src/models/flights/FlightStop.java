package models.flights;

import java.time.ZonedDateTime;
import java.util.Objects;

public record FlightStop(AirportCode airport, ZonedDateTime dateTime) {
}
