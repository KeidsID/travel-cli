import data.DummyFlights;
import utils.*;

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
        Menu mainMenu = new Menu("MENU UTAMA", new MenuOption[0], true);

        mainMenu.show();
    }

}
