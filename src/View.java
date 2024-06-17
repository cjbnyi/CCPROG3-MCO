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
        System.out.print(prompt);
        return scanner.nextLine();
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