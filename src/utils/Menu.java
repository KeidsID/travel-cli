package utils;

import java.util.ArrayList;
import java.util.List;

public final class Menu {
    private final String title;
    private List<MenuOption> options;

    /**
     * Indicates whether this menu is the main menu or a sub-menu. If it is the main
     * menu, there will be a confirmation prompt before ending the menu loop.
     */
    private final boolean isMainMenu;
    private boolean isRunning;

    public Menu(String title, MenuOption[] options) {
        this.title = title;
        this.options = new ArrayList<MenuOption>(List.of(options));
        this.isMainMenu = false;

        addLastOption();
    }

    public Menu(String title, MenuOption[] options, boolean isMainMenu) {
        this.title = title;
        this.options = new ArrayList<MenuOption>(List.of(options));
        this.isMainMenu = isMainMenu;

        addLastOption();
    }

    /**
     * Starts the menu loop and handles user input until the user chooses to exit
     * the menu.
     */
    public void show() {
        this.isRunning = true;

        while (isRunning) {
            IOHelper.printDivider();
            IOHelper.println(title);
            IOHelper.printDivider();
            for (int i = 0; i < options.size(); i++) {
                IOHelper.println((i + 1) + ". " + options.get(i).name());
            }
            IOHelper.printDivider();

            int choice = IOHelper.readInt("Pilih menu: ");
            if (choice >= 1 && choice <= options.size()) {
                options.get(choice - 1).action().run();
            } else {
                IOHelper.println("Pilihan tidak valid!");
            }
        }
    }

    private void addLastOption() {
        String lastOptionTitle = isMainMenu ? "Keluar" : "Kembali";

        this.options.add(new MenuOption(lastOptionTitle, () -> {
            if (isMainMenu) {
                String confirmation = IOHelper.readString("Apakah anda yakin ingin keluar? (Y/n): ");
                if (confirmation.equalsIgnoreCase("y")) {
                    isRunning = false;
                }
                return;
            }
            isRunning = false;
        }));
    }
}