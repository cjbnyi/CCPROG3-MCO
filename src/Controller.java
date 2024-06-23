import java.time.LocalDate;

public class Controller {

    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void createHotel() {

        view.displayCreateHotelPrompt();

        String hotelName;
        Hotel hotel;
        do {
            hotelName = view.getInputString("Name of hotel: ");
            hotel = model.addHotel(hotelName);
        } while(hotel == null);

        double roomBasePrice;
        do {
            roomBasePrice = view.getInputDouble("Base price per night per room: ");
        } while(!hotel.setRoomBasePrice(roomBasePrice));


        view.displayMessage("Hotel successfully initialized! :>");
    }

    public void viewHighLevelInfo(Hotel hotel) {

    }

    public void viewLowLevelInfo(Hotel hotel) {
        char userInput;
        do {
            view.displayLowLevelHotelInfoPrompt(true);
            userInput = view.getInputString("Please provide a response (1/2/3): ").toUpperCase().charAt(0);
        } while (!(userInput >= '1' && userInput <= '3'));

        view.displayDivider();
        switch(userInput) {
            case View.SELECTED_DATE_OPTION: // selected date hotel availability
                int month = view.getInputInt("Enter month (1-12): ");
                int day = view.getInputInt("Enter day (1-31): ");
                LocalDate date = LocalDate.of(2024, month, day);
                int availableRooms = model.getTotalAvailableRoomsByDate(hotel, date);
                // TODO: Create view function that will print this
                break;
            case View.SELECTED_ROOM_OPTION: // selected room information
                String roomName;
                Room room;

                do {
                    roomName = view.getInputString("Enter the room name: ");
                    room = hotel.getRoom(roomName);
                    if (room == null) view.displayInvalidInputWarning();
                } while (room == null);

                view.displayMessage(hotel.getRoomInfo(room));

                break;
            case View.SELECTED_RESERVATION_OPTION: // selected reservation information

                break;
        }
    }

    // TODO: Continue this
    public void viewHotel() {

        view.displayViewHotelPrompt();

        Hotel hotel = view.selectHotel(model.getHotelList());
        if (hotel == null)
            return;

        char userInput;
        do {
            view.displayHotelInfoPrompt();
            userInput = view.getInputString("Please provide a response (H/L): ").toUpperCase().charAt(0);
        } while(!(userInput == 'H' || userInput == 'L'));

        switch(userInput) {
            case View.HIGH_LEVEL_OPTION:
                viewHighLevelInfo(hotel);
                break;
            case View.LOW_LEVEL_OPTION:
                viewLowLevelInfo(hotel);
                break;
        }
    }

    public void manageHotel() {

        view.displayManageHotelPrompt();
        String userInput = view.getInputString("Please provide a response: ");

        switch(userInput) {
            
        }
    }

    public void bookReservation() {

        view.displayBookReservationPrompt();
        String userInput = view.getInputString("Please provide a response: ");

        switch(userInput) {

        }
    }

    public void start() {

        boolean isProgramRunning = true;

        /* program flow */
        while (isProgramRunning) {

            view.displayActionPrompt();

            char userInput = view.getInputString("\nPlease provide a response: ").toUpperCase().charAt(0);

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
                    view.displayInvalidInputWarning();
                    break;
            }

            view.clearConsole();
        }

        /* program termination sequence */
        view.displayProgramTerminationMessage();
    }
}