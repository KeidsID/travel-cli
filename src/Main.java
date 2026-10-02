import java.time.LocalDate;
import java.util.List;

import data.DummyFlights;
import models.flights.Flight;
import utils.IOHelper;
import utils.Menu;
import utils.MenuOption;

public class Main {
    static TravelApp app = new TravelApp(DummyFlights.data);
    static String user = "Anonim";

    public static void main(String[] args) throws Exception {
        intro();
        runApp();
    }

    private static void intro() {
        IOHelper.printDivider();
        IOHelper.println(" ".repeat(30) + "TRAVEL APP");
        IOHelper.println(" ".repeat(30) + "  v1.0");
        IOHelper.printDivider();

        IOHelper.print("Masukkan nama anda: ");
        String name = IOHelper.readString("");
        user = name.isBlank() ? user : name;

        IOHelper.println("Selamat datang, " + user + "!");
    }

    private static void runApp() {
        Menu flightsMenu = new Menu("MENU PENERBANGAN", new MenuOption[] {
                new MenuOption("Penerbangan Hari Ini", () -> searchFlights(true)),
                new MenuOption("Cari Jadwal Penerbangan Lain", () -> searchFlights(false))
        });

        Menu mainMenu = new Menu("MENU UTAMA", new MenuOption[] {
                new MenuOption("Menu Penerbangan", () -> flightsMenu.show())
        }, true);

        mainMenu.show();
    }

    private static void searchFlights(boolean isTodayFlight) {
        IOHelper.printDivider();
        IOHelper.println("PENCARIAN PENERBANGAN");
        IOHelper.printDivider();

        String origin = IOHelper.readString("Masukkan lokasi asal: ");
        String destination = IOHelper.readString("Masukkan lokasi tujuan: ");
        LocalDate date = isTodayFlight ? LocalDate.now()
                : IOHelper.readDate("Masukkan tanggal penerbangan (dd-MM-yyyy): ", "dd-MM-yyyy");
        int passengerCount = IOHelper.readInt("Masukkan jumlah penumpang: ");

        List<Flight> flights = app.searchFlights(origin, destination, date, passengerCount);

        IOHelper.printDivider();
        if (flights.isEmpty()) {
            IOHelper.println("Tidak ada penerbangan yang tersedia");
            return;
        }
        IOHelper.println("Hasil pencarian penerbangan: ");

        flights.forEach((flight) -> {
            IOHelper.printDivider("-");
            printFlightDetails(flight);
        });
    }

    private static void printFlightDetails(Flight flight) {
        var departure = flight.getDeparture();
        var arrival = flight.getArrival();

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

        IOHelper.print("Harga Tiket    : ");
        IOHelper.printCurrency(flight.getTicketPrice());
        IOHelper.println("");
    }

}
