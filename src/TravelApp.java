import java.util.ArrayList;
import java.util.List;

import models.flights.Flight;

public final class TravelApp {
    private final List<Flight> flights;

    public TravelApp(Flight[] flights) {
        this.flights = new ArrayList<>(List.of(flights));
    }
}
