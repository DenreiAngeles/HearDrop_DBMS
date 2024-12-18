package menus;

import java.util.Scanner;

public abstract class BaseMenu {
    protected static Scanner scanner = new Scanner(System.in);

    public abstract void displayMenu();

    public abstract void handleChoice(String choice);

    public void show() {
        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            if (choice.equals(getExitChoice())) {
                return;
            }

            handleChoice(choice);
        }
    }

    protected abstract String getExitChoice();
}
