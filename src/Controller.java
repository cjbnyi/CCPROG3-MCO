import java.time.LocalDate;
import java.util.ArrayList;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void createHotel() {

        view.clearConsole();
        view.displayCreateHotelPrompt();
        view.displayHotelSelection(model.getHotelList());

        String hotelName;
        Hotel hotel;
        boolean isValidHotel;
        do {
            hotelName = view.getInputStr("\nName of hotel: ");
            hotel = model.addHotel(hotelName);
            isValidHotel = hotel != null;

            view.displayInvalidInputWarning(isValidHotel, "Please provide a unique hotel name!");
        } while(!isValidHotel);

        double roomBasePrice;
        boolean isValidBasePrice;
        do {
            roomBasePrice = view.getInputDouble("\nBase price per night per room: ");
            isValidBasePrice = hotel.setRoomBasePrice(roomBasePrice);
            view.displayInvalidInputWarning(isValidBasePrice, "Please provide a base price greater than 0!");
        } while(!isValidBasePrice);

        view.displayMessage("\nHotel successfully initialized! :>");
    }


    public void viewLowLevelInfo(Hotel hotel) {

        char userInput;
        boolean isValidInput;
        do {
            view.displayLowLevelHotelInfoPrompt(true, hotel.getName());
            userInput = view.getInputChar("\nEnter a response (1/2/3): ");
            isValidInput = userInput >= '1' && userInput <= '3';
            view.displayInvalidInputWarning(isValidInput, "Please provide a valid response (1/2/3)!");
        } while (!isValidInput);

        view.clearConsole();
        view.displayDivider();

        String roomName;
        Room room;
        boolean isValidRoom;

        int day;
        LocalDate date;

        switch(userInput) {
            case View.SELECTED_DATE_OPTION: // selected date hotel availability

                view.displayMessage("The only month in the system is July.");
                day = view.getInputInt("\nEnter a day in July (1-31): ");
                date = LocalDate.of(2024, View.SYSTEM_MONTH, day);

                int availableRooms = model.getTotalAvailableRoomsByDate(hotel, date);
                view.displaySelectedDateInfo(availableRooms, hotel.getTotalRooms() - availableRooms);
                break;
            case View.SELECTED_ROOM_OPTION: // selected room information

                view.displayRoomSelection(hotel.getRoomList());
                do {
                    roomName = view.getInputStr("\nEnter the room name: ").toUpperCase();
                    room = hotel.getRoom(roomName);
                    isValidRoom = room != null;
                    view.displayInvalidInputWarning(isValidRoom, "Please provide a valid room name!");
                } while (!isValidRoom);

                view.displaySelectedRoomInfo(hotel.getRoomInfo(room));
                break;
            case View.SELECTED_RESERVATION_OPTION: // selected reservation information

                // TODO: Should I display the current hotel?
                view.displayRoomSelection(hotel.getRoomList());
                do {
                    roomName = view.getInputStr("\nEnter the room name: ").toUpperCase();
                    room = hotel.getRoom(roomName);
                    isValidRoom = room != null;
                    view.displayInvalidInputWarning(isValidRoom, "Please provide a valid room name!");
                } while (!isValidRoom);

                view.displayReservationSelection(hotel.filterReservationsByRoom(room));
                day = view.getInputInt("\nEnter the check-in day of the reservation (1-31): ");
                date = LocalDate.of(2024, View.SYSTEM_MONTH, day);

                Reservation reservation = hotel.getReservation(room, date);
                view.displaySelectedReservationInfo(reservation);
                break;
            default:
                view.displayMessage("This can't be reached! :<");
        }
    }


    public void viewHotel() {

        view.displayViewHotelPrompt();
        view.displayHotelSelection(model.getHotelList());

        String hotelName;
        Hotel hotel;
        boolean isValidHotel;
        do {
            hotelName = view.getInputStr("\nEnter the hotel name: ");
            hotel = model.getHotel(hotelName);
            isValidHotel = hotel != null;
            view.displayInvalidInputWarning(isValidHotel, "Please provide a valid hotel name!");
        } while (!isValidHotel);

        view.clearConsole();
        view.displayHotelInfoPrompt(hotelName);

        char userInput;
        boolean isValidInput;
        do {
            userInput = view.getInputStr("\nEnter a response (H/L): ").toUpperCase().charAt(0);
            isValidInput = userInput == View.HIGH_LEVEL_OPTION || userInput == View.LOW_LEVEL_OPTION;
            view.displayInvalidInputWarning(isValidInput, "Please provide a valid response (H/L)!");
        } while(!isValidInput);

        view.clearConsole();
        switch(userInput) {
            case View.HIGH_LEVEL_OPTION:
                double estimatedEarnings = model.getHotelEstimatedEarnings(hotel);
                view.displayHighLevelHotelInfo(hotel, estimatedEarnings);
                break;
            case View.LOW_LEVEL_OPTION:
                viewLowLevelInfo(hotel);
                break;
        }
    }

    public void manageHotel() {

        /*
        view.displayManageHotelPrompt();
        String userInput = view.getInputStr("Please provide a response: ");

        switch(userInput) {

        }
         */
    }

    public void bookReservation() {

        /*
        view.displayBookReservationPrompt();
        String userInput = view.getInputStr("Please provide a response: ");
         */
    }

    public void start() {

        boolean isProgramRunning = true;

        /* program flow */
        while (isProgramRunning) {

            view.displayMainActionPrompt();
            char userInput = view.getInputChar("\nEnter a response: ");
            view.clearConsole();

            switch(userInput) {
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
                    view.displayInvalidInputWarning(true, "Please provide a valid response!");
                    break;
            }

            view.clearConsole();
        }

        /* program termination sequence */
        view.displayProgramTerminationMessage();
    }
}