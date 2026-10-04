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

    /**
     * Constructs a new Menu with the specified title and options.
     *
     * @param title   The title of the menu.
     * @param options An array of MenuOption objects representing the menu options.
     */
    public Menu(String title, MenuOption[] options) {
        this.title = title;
        this.options = new ArrayList<MenuOption>(List.of(options));
        this.isMainMenu = false;

        addLastOption();
    }

    /**
     * Constructs a new Menu with the specified title, options, and a flag
     * indicating
     * whether it is the main menu.
     *
     * @param title      The title of the menu.
     * @param options    An array of MenuOption objects representing the menu
     *                   options.
     * @param isMainMenu A boolean to indicate whether this menu is the main
     *                   menu. If true, there will be a confirmation prompt before
     *                   ending the menu loop
     */
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

            int optionsCount = options.size();

            if (choice >= 1 && choice <= optionsCount) {
                options.get(choice - 1).action().run();
            } else {
                var optionsRange = (optionsCount == 1) ? "1"
                        : ("1-" + String.valueOf((optionsCount + 1)));

                IOHelper.println("Pilihan tidak valid! Mohon pilih opsi "
                        + optionsRange);
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