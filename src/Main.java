import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;

import data.DummyFlights;
import data.DummyStays;
import models.flights.Flight;
import models.stays.Stay;
import utils.IOHelper;
import utils.Menu;
import utils.MenuOption;

public class Main {
    static TravelApp app = new TravelApp(DummyFlights.data, DummyStays.data);
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

        Menu staysMenu = new Menu("MENU PENGINAPAN", new MenuOption[] {
                new MenuOption("Cari Penginapan", Main::searchStays)
        });

        Menu mainMenu = new Menu("MENU UTAMA", new MenuOption[] {
                new MenuOption("Menu Penerbangan", () -> flightsMenu.show()),
                new MenuOption("Menu Penginapan", () -> staysMenu.show())
        }, true);

        mainMenu.show();
    }

    // ========================================
    // Travel App Methods
    // ========================================

    private static void searchFlights(boolean isTodayFlight) {
        IOHelper.printDivider();
        IOHelper.println("PENCARIAN PENERBANGAN");
        IOHelper.printDivider();

        String origin = readNonBlankString("Darimana anda berangkat? ");
        String destination = readNonBlankString("Kemana anda pergi? ");
        LocalDate currentDate = LocalDate.now();
        LocalDate date = isTodayFlight ? currentDate
                : readNonPastDate("Kapan anda berangkat? (dd-MM-yyyy) ", "dd-MM-yyyy");
        int passengerCount = IOHelper.readInt("Berapa orang yang akan pergi? ", (input) -> {
            if (input <= 0) {
                throw new InputMismatchException("Jumlah penumpang harus lebih dari 0.");
            }
        });

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

    private static void searchStays() {
        IOHelper.printDivider();
        IOHelper.println("PENCARIAN PENGINAPAN");
        IOHelper.printDivider();

        String city = readNonBlankString("Dimana anda akan menginap? ");
        LocalDate checkIn = readNonPastDate("Kapan anda check-in? (dd-MM-yyyy) ", "dd-MM-yyyy");
        LocalDate checkOut = IOHelper.readDate("Kapan anda check-out? (dd-MM-yyyy) ", "dd-MM-yyyy", (dateInput) -> {
            if (checkIn.isAfter(dateInput)) {
                throw new InputMismatchException("Tanggal check-out harus setelah tanggal check-in.");
            }
        });

        int roomCount = IOHelper.readInt("Butuh berapa kamar? ", (intInput) -> {
            if (intInput <= 0) {
                throw new InputMismatchException("Jumlah kamar harus lebih dari 0.");
            }
        });

        List<Stay> stays = app.searchStays(city, checkIn, checkOut, roomCount);

        IOHelper.printDivider();
        if (stays.isEmpty()) {
            IOHelper.println("Tidak ada penginapan yang tersedia");
            return;
        }
        IOHelper.println("Hasil pencarian penginapan:");

        stays.forEach((stay) -> {
            IOHelper.printDivider("-");
            printStayDetails(stay);
        });
    }

    // ========================================
    // IO Methods
    // ========================================

    private static String readNonBlankString(String prompt) {
        return IOHelper.readString(prompt, (input) -> {
            if (input.isBlank()) {
                throw new InputMismatchException("tidak boleh kosong");
            }
        });
    }

    private static LocalDate readNonPastDate(String prompt, String dateFormat) {
        return IOHelper.readDate(prompt, dateFormat, (date) -> {
            if (date.isBefore(LocalDate.now())) {
                throw new InputMismatchException("Tanggal tidak boleh sebelum hari ini.");
            }
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

    private static void printStayDetails(Stay stay) {
        IOHelper.print("Nama           : ");
        IOHelper.println(stay.getName());

        IOHelper.print("Lokasi         : ");
        IOHelper.println(stay.getLocation());

        IOHelper.print("Harga per malam: ");
        IOHelper.printCurrency(stay.getRoomPricePerNight());
        IOHelper.println("");
    }

}
