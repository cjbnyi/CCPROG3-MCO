import java.util.Scanner;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void createHotel() {

        String userInput = view.displayCreateHotelPrompt();


    }

    public void viewHotel() {

        String userInput = view.displayViewHotelPrompt();


    }

    public void manageHotel() {

        String userInput = view.displayManageHotelPrompt();


    }

    public void bookReservation() {

        String userInput = view.displayBookReservationPrompt();


    }

    public void start() {

        boolean isProgramRunning = true;

        while (isProgramRunning) {

            view.displayMessage("\nWelcome to the Hotel Reservation System!\n");

            view.displayMessage("[C]reate Hotel");
            view.displayMessage("[V]iew Hotel");
            view.displayMessage("[M]anage Hotel");
            view.displayMessage("[B]ook Reservation");

            String userInput = view.getUserInput("\nPlease provide a response: ");

            switch(userInput) {
                case "C":
                    this.createHotel();
                    break;
                case "V":
                    this.viewHotel();
                    break;
                case "M":
                    this.manageHotel();
                    break;
                case "B":
                    this.bookReservation();
                    break;
                case "quit":
                    isProgramRunning = false;
                    break;
                default:
                    view.displayMessage("\nPlease provide a valid response!"); // temporary
                    break;
            }

            view.clearScreen();
        }

        /* program termination sequence */
        view.displayProgramTerminationMessage();
    }
}