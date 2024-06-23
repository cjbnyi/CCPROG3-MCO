import java.util.Scanner;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void createHotel() {

        view.displayCreateHotelPrompt();
        String userInput = view.getUserInput("Please provide a response: ");

        switch(userInput) {

        }
    }

    public void viewHotel() {

        view.displayViewHotelPrompt();
        String userInput = view.getUserInput("Please provide a response: ");

        switch(userInput) {

        }
    }

    public void manageHotel() {

        view.displayManageHotelPrompt();
        String userInput = view.getUserInput("Please provide a response: ");

        switch(userInput) {

        }
    }

    public void bookReservation() {

        view.displayBookReservationPrompt();
        String userInput = view.getUserInput("Please provide a response: ");

        switch(userInput) {

        }
    }

    public void start() {

        boolean isProgramRunning = true;

        /* program flow */
        while (isProgramRunning) {

            view.displayActionPrompt();

            String userInput = view.getUserInput("\nPlease provide a response: ");

            switch(userInput.charAt(0)) {
                case View.CREATE_HOTEL_OPTION:
                    this.createHotel();
                    break;
                case View.VIEW_HOTEL_OPTION:
                    this.viewHotel();
                    break;
                case View.MANAGE_HOTEL_OPTION:
                    this.manageHotel();
                    break;
                case View.BOOK_RESERVATION_OPTION:
                    this.bookReservation();
                    break;
                case View.QUIT_OPTION:
                    isProgramRunning = false;
                    break;
                default:
                    view.displayInvalidInputWarning();
                    break;
            }

            view.clearScreen();
        }

        /* program termination sequence */
        view.displayProgramTerminationMessage();
    }
}