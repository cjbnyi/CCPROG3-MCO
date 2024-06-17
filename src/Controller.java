import java.util.Scanner;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void start() {

        boolean isProgramRunning = true;

        while (isProgramRunning) {

            String input = view.getUserInput("Please provide a response: ");

            switch(input) {
                case "C":

                    break;
                case "V":

                    break;
                case "M":

                    break;
                case "B":

                    break;
                case "quit":
                    isProgramRunning = false;
                    break;
                default:
                    System.out.println("\nPlease provide a valid response!"); // temporary
                    break;
            }

            // insert code that clears screen
        }

        view.displayProgramTerminationMessage();
    }

    /*
    public static void main(String[] Args) {

        // main program
        while (isProgramRunning) {
            System.out.println("\nWelcome to the Hotel Reservation System!\n");

            System.out.println("[C]reate Hotel");
            System.out.println("[V]iew Hotel");
            System.out.println("[M]anage Hotel");
            System.out.println("[B]ook Reservation");

            System.out.print("\nPlease provide a response: ");
            userResponse = sc.nextLine();


        }
     */
}