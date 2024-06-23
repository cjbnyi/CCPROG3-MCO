import java.util.ArrayList;
import java.util.Scanner;

public class View {

    public static final char CREATE_HOTEL_OPTION = 'C',
                             VIEW_HOTEL_OPTION = 'V',
                             MANAGE_HOTEL_OPTION = 'M',
                             BOOK_RESERVATION_OPTION = 'B',
                             QUIT_OPTION = 'Q';

    private final Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }


    // TODO: Finalize the divider
    private void displayDivider() {
        System.out.println("========");
    }


    public void displayMessage(String message) {
        displayDivider();
        System.out.println(message);
    }


    public void displayActionPrompt() {
        displayDivider();
        System.out.println("Welcome to the Hotel Reservation System!\n");
        System.out.println("[C]reate Hotel");
        System.out.println("[V]iew Hotel");
        System.out.println("[M]anage Hotel");
        System.out.println("[B]ook Reservation");
    }


    public String getUserInput(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }


    /**
     * Prompts the user to confirm their input.
     * @return true if the user inputs 'Y'; false if the user inputs 'N'
     */
    public boolean confirmUserInput() {
        displayDivider();
        System.out.print("Do you want to confirm your action (Y/N)? "); // placeholder and tentative
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("Y"))
            return true;
        else if (userInput.equalsIgnoreCase("N"))
            return false;
        return confirmUserInput(); // may cause some errors, idk
    }

    // TODO: Implement clearScreen()
    public void clearScreen() {

    }

    public void displayCreateHotelPrompt() {
        displayDivider();
        System.out.println("<insert Create Hotel prompt>");
    }

    public void displayViewHotelPrompt() {
        displayDivider();
        System.out.println("<insert View Hotel prompt>");
    }

    public void displayManageHotelPrompt() {
        displayDivider();
        System.out.println("<insert Manage Hotel prompt>");
    }

    public void displayBookReservationPrompt() {
        displayDivider();
        System.out.println("<insert Book Reservation prompt>");
    }

    public void displayProgramTerminationMessage() {
        displayDivider();
        System.out.println("Thank you for trying out our Hotel Reservation System!");
        System.out.println("Authors:");
        System.out.println("Christian Joseph C. Bunyi - @cjbnyi");
        System.out.println("Roan Cedric V. Campo - @ImaginaryLogs");
    }

    public void displayHotels(ArrayList<Hotel> hotelList) {
        displayDivider();

        if (hotelList.isEmpty())
            System.out.println("No hotels currently exist.");
        else {
            int i = 0;
            for (Hotel hotel : hotelList) {
                displayDivider();
                System.out.println((i + 1) + ".)");
                System.out.println("Name: " + hotel.getName());
                System.out.println("List of rooms:");

                ++i;
                // will continue this - cj
            }
        }
    }

    public void displayInvalidInputWarning() {
        displayDivider();
        System.out.println("Please provide a valid response!");
    }
}