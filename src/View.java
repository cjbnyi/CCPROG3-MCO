import java.util.Scanner;

public class View {

    private final Scanner scanner;

    public View() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMessage(String message) {
        System.out.println(message);
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

    public String displayCreateHotelPrompt() {
        System.out.println("<insert Create Hotel prompt>");
        return getUserInput("Please provide a response: ");
    }

    public String displayViewHotelPrompt() {
        System.out.println("<insert View Hotel prompt>");
        return getUserInput("Please provide a response: ");
    }

    public String displayManageHotelPrompt() {
        System.out.println("<insert Manage Hotel prompt>");
        return getUserInput("Please provide a response: ");
    }

    public String displayBookReservationPrompt() {
        System.out.println("<insert Book Reservation prompt>");
        return getUserInput("Please provide a response: ");
    }

    public void displayProgramTerminationMessage() {
        System.out.println("Thank you for trying out our Hotel Reservation System!");
        System.out.println("Authors:");
        System.out.println("Christian Joseph C. Bunyi - @cjbnyi");
        System.out.println("Roan Cedric V. Campo - @ImaginaryLogs");
    }
}