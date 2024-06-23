import com.sun.security.jgss.GSSUtil;

import java.util.ArrayList;
import java.util.Scanner;

public class View {

    public static final char CREATE_HOTEL_OPTION = 'C',
                             VIEW_HOTEL_OPTION = 'V',
                             MANAGE_HOTEL_OPTION = 'M',
                             BOOK_RESERVATION_OPTION = 'B',
                             QUIT_OPTION = 'Q';

    public static final char HIGH_LEVEL_OPTION = 'H',
                             LOW_LEVEL_OPTION = 'L';

    public static final char SELECTED_DATE_OPTION = '1',
                             SELECTED_ROOM_OPTION = '2',
                             SELECTED_RESERVATION_OPTION = '3';

    private final Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }

    public void displayDivider() {
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


    public String getInputString(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine();
    }


    public int getInputInt(String prompt) {
        System.out.println(prompt + " ");
        return scanner.nextInt();
    }

    public double getInputDouble(String prompt) {
        System.out.println(prompt + " ");
        return scanner.nextDouble();
    }

    /**
     * Prompts the user to confirm their input.
     * @return true if the user inputs 'Y'; false if the user inputs 'N'
     */
    public boolean confirmInput() {
        displayDivider();
        System.out.print("Do you want to confirm your action (Y/N)? "); // placeholder and tentative
        String userInput = scanner.nextLine();
        if (userInput.equalsIgnoreCase("Y"))
            return true;
        else if (userInput.equalsIgnoreCase("N"))
            return false;
        return confirmInput(); // may cause some errors, idk
    }


    public void clearConsole() {
        if (Debug.DONT_CLEAR_CONSOLE)
            for (int i = 0; i < 5; i++)
                System.out.print("\n");
        else
            System.out.print("\033\143");
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


    public void displayHotel(Hotel hotel) {
        displayDivider();

        System.out.println("Name: " + hotel.getName());
        System.out.println("List of rooms:");

        int i = 1;
        for (Room room : hotel.getRoomList()) {
            System.out.println(i + ".) " + room.getName());
            ++i;
        }
    }


    public void displayInvalidInputWarning() {
        displayDivider();
        System.out.println("Invalid input!");
    }


    // TODO: Continue this
    public Hotel selectHotel(ArrayList<Hotel> hotelList) {
        displayDivider();

    }


    public void displayHighLevelHotelInfoPrompt(boolean hasDivider) {
        if (hasDivider) displayDivider();
        System.out.println("[H]igh-level information");
        System.out.println("\t- Name");
        System.out.println("\t- Total number of rooms");
        System.out.println("\t- Estimated earnings for the month");
    }


    public void displayLowLevelHotelInfoPrompt(boolean hasDivider) {
        if (hasDivider) displayDivider();
        System.out.println("[L]ow-level information");
        System.out.println("\t[1] Selected date hotel availability");
        System.out.println("\t\t- Total number of available rooms");
        System.out.println("\t\t- Total number of booked rooms");
        System.out.println("\t[2] Selected room information");
        System.out.println("\t\t- Name");
        System.out.println("\t\t- Price per night");
        System.out.println("\t\t- Availability across the entire month");
        System.out.println("\t[3] Selected reservation information");
        System.out.println("\t\t- Guest information");
        System.out.println("\t\t- Check-in date");
        System.out.println("\t\t- Check-out date");
        System.out.println("\t\t- Total price");
        System.out.println("\t\t- Breakdown");
    }


    public void displayHotelInfoPrompt() {
        displayHighLevelHotelInfoPrompt(true);
        displayLowLevelHotelInfoPrompt(false);
    }
}