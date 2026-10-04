import java.time.LocalDate;
import java.util.List;

import data.DummyFlights;
import data.DummyStays;
import models.booking.Customer;
import models.booking.FlightBooking;
import models.booking.StayBooking;
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

        Menu mainMenu = new Menu("MENU UTAMA", new MenuOption[] {
                new MenuOption("Menu Penerbangan", flightsMenu::show),
                new MenuOption("Cari Penginapan", Main::searchStays)
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
                throw new IllegalArgumentException("Jumlah penumpang harus lebih dari 0.");
            }
        });

        List<Flight> flights = app.searchFlights(origin, destination, date, passengerCount);

        IOHelper.printDivider();
        if (flights.isEmpty()) {
            IOHelper.println("Tidak ada penerbangan yang tersedia");
            return;
        }
        IOHelper.println("Hasil pencarian penerbangan: ");

        int flightsCount = flights.size();

        for (int i = 0; i < flightsCount; i++) {
            IOHelper.printDivider("-");
            IOHelper.println("No. " + (i + 1));
            IOHelper.println("-".repeat(8));
            printFlightDetails(flights.get(i));
        }
        IOHelper.printDivider();

        int choice = IOHelper.readInt("Pilih hasil penerbangan untuk dibooking (0 untuk batal): ", (input) -> {
            if (input < 0 || input > flightsCount) {
                throw new IllegalArgumentException("Pilihan tidak valid. Pilihan tersedia cuma penerbangan "
                        + (flightsCount == 1 ? "1" : ("1-" + flightsCount)));
            }
        });

        if (choice == 0) {
            return;
        }

        Flight selectedFlight = flights.get(choice - 1);
        Customer[] customers = new Customer[passengerCount];

        for (int i = 0; i < passengerCount; i++) {
            IOHelper.printDivider("-");
            IOHelper.println("Data Penumpang ke-" + (i + 1));
            String customerName = readNonBlankString("Nama: ");
            String contact = IOHelper.readString("Kontak: ");
            customers[i] = new Customer(customerName, contact);
        }

        FlightBooking booking = app.bookFlight(selectedFlight, passengerCount, customers);

        IOHelper.printDivider();
        IOHelper.println("BOOKING BERHASIL");
        IOHelper.printDivider();
        booking.printDetails();
    }

    private static void searchStays() {
        IOHelper.printDivider();
        IOHelper.println("PENCARIAN PENGINAPAN");
        IOHelper.printDivider();

        String city = readNonBlankString("Dimana anda akan menginap? ");
        LocalDate checkIn = readNonPastDate("Kapan anda check-in? (dd-MM-yyyy) ", "dd-MM-yyyy");
        LocalDate checkOut = IOHelper.readDate("Kapan anda check-out? (dd-MM-yyyy) ", "dd-MM-yyyy", (dateInput) -> {
            if (checkIn.isEqual(dateInput)) {
                throw new IllegalArgumentException("Tidak dapat check-out di hari yang sama dengan check in.");
            }

            if (checkIn.isAfter(dateInput)) {
                throw new IllegalArgumentException("Tanggal check-out harus setelah tanggal check-in.");
            }
        });

        int roomCount = IOHelper.readInt("Butuh berapa kamar? ", (intInput) -> {
            if (intInput <= 0) {
                throw new IllegalArgumentException("Jumlah kamar harus lebih dari 0.");
            }
        });

        List<Stay> stays = app.searchStays(city, checkIn, checkOut, roomCount);

        IOHelper.printDivider();
        if (stays.isEmpty()) {
            IOHelper.println("Tidak ada penginapan yang tersedia");
            return;
        }
        IOHelper.println("Hasil pencarian penginapan:");

        int staysCount = stays.size();

        for (int i = 0; i < staysCount; i++) {
            IOHelper.printDivider("-");
            IOHelper.println((i + 1) + ".");
            printStayDetails(stays.get(i));
        }
        IOHelper.printDivider("-");

        int choice = IOHelper.readInt("Pilih nomor penginapan untuk dibooking (0 untuk batal): ", (input) -> {
            if (input < 0 || input > staysCount) {
                throw new IllegalArgumentException("Pilihan tidak valid. Pilihan tersedia cuma penginapan "
                        + (staysCount == 1 ? "1" : ("1-" + staysCount)));
            }
        });

        if (choice == 0) {
            return;
        }

        Stay selectedStay = stays.get(choice - 1);
        Customer[] customers = new Customer[1];

        IOHelper.printDivider("-");
        IOHelper.println("Data Pemesan");
        String customerName = readNonBlankString("Nama: ");
        String contact = IOHelper.readString("Kontak: ");
        customers[0] = new Customer(customerName, contact);

        StayBooking booking = app.bookStay(selectedStay, roomCount, checkIn, checkOut, customers);

        IOHelper.printDivider();
        IOHelper.println("BOOKING PENGINAPAN BERHASIL");
        IOHelper.printDivider();
        booking.printDetails();

    }

    // ========================================
    // IO Methods
    // ========================================

    private static String readNonBlankString(String prompt) {
        return IOHelper.readString(prompt, (input) -> {
            if (input.isBlank()) {
                throw new IllegalArgumentException("tidak boleh kosong");
            }
        });
    }

    private static LocalDate readNonPastDate(String prompt, String dateFormat) {
        return IOHelper.readDate(prompt, dateFormat, (date) -> {
            if (date.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("Tanggal tidak boleh sebelum hari ini.");
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
