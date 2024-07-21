package Hotel;

import static Hotel.Result.COMMON_ERRORS.ER_MAX_CAPACITY;
import static Hotel.Result.COMMON_ERRORS.ER_NOT_UNIQUE_GIVENNAME;
import static Hotel.Result.COMMON_ERRORS.ER_NO_HOTEL;
import static Hotel.Result.COMMON_ERRORS.ER_NO_RESERVATION;
import static Hotel.View.MANAGER_STATE.MS_ADD_ROOMS;
import static Hotel.View.MANAGER_STATE.MS_CHANGE_NAME;
import static Hotel.View.MANAGER_STATE.MS_CHOSE_HOTEL;
import static Hotel.View.MANAGER_STATE.MS_OVERVIEW;
import static Hotel.View.MANAGER_STATE.MS_REMOVE_HOTEL;
import static Hotel.View.MANAGER_STATE.MS_REMOVE_RESERVATIONS;
import static Hotel.View.MANAGER_STATE.MS_REMOVE_ROOMS;
import static Hotel.View.MANAGER_STATE.MS_UPDATE_PRICE;
import static Hotel.View.SIMULATE_BOOKING.SB_DATE_SELECTION;
import static Hotel.View.SIMULATE_BOOKING.SB_GUEST_SELECTION;
import static Hotel.View.SIMULATE_BOOKING.SB_HOTEL_SELECTION;
import static Hotel.View.SIMULATE_BOOKING.SB_OVERVIEW;
import static Hotel.View.SIMULATE_BOOKING.SB_RESERVATION_CONFIRMATION;

import java.time.LocalDate;
import java.util.ArrayList;

import Hotel.View.MANAGER_STATE;


/**
 * The {@code Controller} class acts as the intermediary between the {@code Model} and {@code View} in the
 * Model-View-Controller (MVC) design pattern. It handles user input and interacts
 * with the model and view to perform the necessary operations.
 */
public class Controller {

    private Model model;
    private View view;


    /**
     * Constructs a Controller with the specified Model and View.
     *
     * @param model the model to be used by the controller
     * @param view the view to be used by the controller
     */
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }


    /**
     * Creates a new hotel with a unique name and a base price for its rooms.
     * Prompts the user for input and handles the creation process.
     */
    public void createHotel() {

        view.displayCreateHotelPrompt();
        view.displayHotelSelection(model.getHotelList());

        String hotelName;
        Hotel hotel;
        boolean isValidHotelName;
        do {
            hotelName = view.getInputStr("\nEnter the name of the hotel: ");
            if (hotelName.equalsIgnoreCase("quit")) {
                view.displayResultMessage("Hotel creation cancelled.");
                return;
            }
            hotel = model.getHotel(hotelName);
            isValidHotelName = hotel == null;
            view.displayInvalidInputWarning(isValidHotelName, "Please provide a unique hotel name!");
        } while(!isValidHotelName);

        String buffer;
        double roomBasePrice;
        boolean isValidBasePrice;
        do {
            buffer = view.getInputStr("\nEnter the base price per night for each room: ");
            if (buffer.equalsIgnoreCase("quit")) {
                view.displayResultMessage("Hotel creation cancelled.");
                return;
            }
            try {
                roomBasePrice = Double.parseDouble(buffer);
                isValidBasePrice = roomBasePrice >= 100;
                view.displayInvalidInputWarning(isValidBasePrice, "Please provide a base price greater than or equal to 100!");
            } catch(NumberFormatException e) {
                roomBasePrice = 0;
                isValidBasePrice = false;
                view.displayInvalidInputWarning(isValidBasePrice, "Please provide a numeric response!");
            }
        } while(!isValidBasePrice);

        if (view.confirmUserAction("creating a new hotel")) {
            model.addHotel(hotel);
            hotel.setRoomBasePrice(roomBasePrice);
            view.displayResultMessage("Hotel creation successful! :>");
        } else {
            view.displayResultMessage("Hotel creation cancelled.");
        }
    }


    /**
     * Displays detailed information about a specified hotel, including room and reservation details.
     * Prompts the user for input and handles the information viewing process.
     *
     * @param hotel the hotel for which to display information
     */
    public void viewLowLevelInfo(Hotel hotel) {

        view.displayViewHotelPrompt();
        view.displayLowLevelHotelInfoPrompt(true, hotel.getName());

        String buffer;
        char userInput;
        boolean isValidInput;
        do {
            buffer = view.getInputStr("\nEnter a response (1/2/3): ");
            if (buffer.equalsIgnoreCase("quit")) {
                view.displayResultMessage("Hotel information viewing cancelled.");
                return;
            }
            userInput = buffer.charAt(0);
            isValidInput = userInput >= '1' && userInput <= '3';
            view.displayInvalidInputWarning(isValidInput, "Please provide a valid response!");
        } while (!isValidInput);

        view.clearScreen();
        view.displayViewHotelPrompt();

        String roomName;
        Room room;
        boolean isValidRoom;

        int day;
        boolean isValidDay;
        LocalDate date;

        switch(userInput) {
            case View.SELECTED_DATE_OPTION: // selected date hotel availability

                view.displayMessage("\nThe system month is July.");
                do {
                    buffer = view.getInputStr("\nEnter a day in July (1-31): ");
                    if (buffer.equalsIgnoreCase("quit")) {
                        view.displayResultMessage("Hotel information viewing cancelled.");
                        return;
                    }
                    try {
                        day = Integer.parseInt(buffer);
                        isValidDay = day >= 1 && day <= 31;
                        view.displayInvalidInputWarning(isValidDay, "Please provide a valid day!");
                    } catch(NumberFormatException e) {
                        day = 0;
                        isValidDay = false;
                        view.displayInvalidInputWarning(isValidDay, "Please provide an integer response!");
                    }
                } while (!isValidDay);

                date = LocalDate.of(2024, View.SYSTEM_MONTH, day);
                int availableRooms = model.getTotalAvailableRoomsByDate(hotel, date);

                view.clearScreen();
                view.displaySelectedDateInfo(hotel.getName(), availableRooms, hotel.getTotalRooms() - availableRooms);
                view.displayResultMessage("Hotel information viewing successful! :>");
                break;
            case View.SELECTED_ROOM_OPTION: // selected room information

                view.displayRoomSelection(hotel.getName(), hotel.getRoomList());
                view.displayDivider();
                do {
                    roomName = view.getInputStr("\nEnter the room name: ").toUpperCase();
                    if (roomName.equalsIgnoreCase("quit")) {
                        view.displayResultMessage("Hotel information viewing cancelled.");
                        return;
                    }
                    room = hotel.getRoom(roomName);
                    isValidRoom = room != null;
                    view.displayInvalidInputWarning(isValidRoom, "Please provide a valid room name!");
                } while (!isValidRoom);

                view.clearScreen();
                view.displaySelectedRoomInfo(hotel.getRoomInfo(room));
                view.displayResultMessage("Hotel information viewing successful! :>");
                break;
            case View.SELECTED_RESERVATION_OPTION: // selected reservation information

                view.displayRoomSelection(hotel.getName(), hotel.getRoomList());
                view.displayDivider();
                do {
                    roomName = view.getInputStr("\nEnter the room name: ").toUpperCase();
                    if (roomName.equalsIgnoreCase("quit")) {
                        view.displayResultMessage("Hotel information viewing cancelled.");
                        return;
                    }
                    room = hotel.getRoom(roomName);
                    isValidRoom = room != null;
                    view.displayInvalidInputWarning(isValidRoom, "Please provide a valid room name!");
                } while (!isValidRoom);

                view.clearScreen();
                view.displayViewHotelPrompt();
                view.displayReservationSelection(hotel.getName(), room.getName(), hotel.filterReservationsByRoom(room));

                do {
                    buffer = view.getInputStr("\nEnter the check-in day of the reservation (1-31): ");
                    if (buffer.equalsIgnoreCase("quit")) {
                        view.displayResultMessage("Hotel information viewing cancelled.");
                        return;
                    }
                    try {
                        day = Integer.parseInt(buffer);
                        isValidDay = day >= 0 && day <= 31;
                        view.displayInvalidInputWarning(isValidDay, "Please provide a valid day!");
                    } catch(NumberFormatException e) {
                        day = 0;
                        isValidDay = false;
                        view.displayInvalidInputWarning(isValidDay, "Please provide a numeric response!");
                    }
                } while (!isValidDay);

                date = LocalDate.of(2024, View.SYSTEM_MONTH, day);
                Reservation reservation = model.getReservation(hotel.getName(), roomName, date);

                view.clearScreen();
                view.displaySelectedReservationInfo(reservation);
                view.displayResultMessage("Hotel information viewing successful! :>");
                break;
        }
    }


    /**
     * Allows the user to view high-level or low-level information about a selected hotel.
     * Prompts the user for input and handles the information viewing process.
     */
    public void viewHotel() {

        view.displayViewHotelPrompt();
        view.displayHotelSelection(model.getHotelList());

        String hotelName;
        Hotel hotel;
        boolean isValidHotel;
        do {
            hotelName = view.getInputStr("\nEnter the name of the hotel you want to view: ");
            if (hotelName.equalsIgnoreCase("quit")) {
                view.displayResultMessage("Hotel information viewing cancelled.");
                return;
            }
            hotel = model.getHotel(hotelName);
            isValidHotel = hotel != null;
            view.displayInvalidInputWarning(isValidHotel, "Please provide a valid hotel name!");
        } while (!isValidHotel);

        view.clearScreen();
        view.displayViewHotelPrompt();
        view.displayHotelInfoPrompt(hotelName);

        String buffer;
        char userInput;
        boolean isValidInput;
        do {
            buffer = view.getInputStr("\nEnter a response (H/L): ");
            if (buffer.equalsIgnoreCase("quit")) {
                view.displayResultMessage("Hotel information viewing cancelled.");
                return;
            }
            userInput = buffer.toUpperCase().charAt(0);
            isValidInput = userInput == View.HIGH_LEVEL_OPTION || userInput == View.LOW_LEVEL_OPTION;
            view.displayInvalidInputWarning(isValidInput, "Please provide a valid response!");
        } while(!isValidInput);
        view.clearScreen();
        switch(userInput) {
            case View.HIGH_LEVEL_OPTION:
                double estimatedEarnings = model.getHotelEstimatedEarnings(hotel);
                view.displayHighLevelHotelInfo(hotel, estimatedEarnings);
                view.displayResultMessage("Hotel information viewing successful.");
                break;
            case View.LOW_LEVEL_OPTION:
                viewLowLevelInfo(hotel);
                break;
        }
        view.clearScreen();
    }


    /**
     * Changes the name of a hotel.
     * Displays a list of current hotels and prompts the user for the old and new names.
     * Confirms the action and updates the hotel's name if valid.
     */
    private void changeHotelName(Hotel oldHotelName) {
        ArrayList<Hotel> currentList;
        String newHotelName = "";
        Result resSetHotelName;

        view.clearScreen();
        currentList = model.getHotelList();
        view.displayHotelSelection(currentList);

        newHotelName = view.getInputStr("Please input a new name: ");
        
        if (!view.confirmUserAction("changing the hotel name")) {
            return;
        }

        view.displayMessage("\n");
        resSetHotelName = model.setHotelName(oldHotelName.getName(), newHotelName);

        if (resSetHotelName.isSuccesful()){
            view.displayResultMessage("Changed Hotel name.");
            oldHotelName = model.getHotel(newHotelName);
        } else {
            switch (resSetHotelName.getCommonError()){
                case ER_EXISTING_OLD_NAME:
                    view.displayResultMessage("New give name is same as the previous old one.");
                    break;
                case ER_NO_HOTEL:
                    view.displayResultMessage("Hotel does not exist.");
                    break;
                case ER_NOT_UNIQUE_GIVENNAME:
                    view.displayResultMessage("Name is not unique, please provide a new one.");
                    break;      
                default:
                    view.displayResultMessage("Unknown Error: " + resSetHotelName.getMessage());
                    break;
            }
        }
        view.clearScreen();
    }
    

        

    /**
     * Adds a room to a selected hotel.
     * Displays a list of current hotels and rooms, prompts the user for the hotel and room names.
     * Confirms the action and adds the room if valid.
     */
    private void addRooms(Hotel hotel){
        ArrayList<Hotel> currentHotelList;
        ArrayList<Room> currentRoomList;

        String nameOfRoomToAdd = "";
        Result resAddRoom;

        currentHotelList = model.getHotelList();
        view.displayHotelSelection(currentHotelList);

        if (!model.doesHotelExist(hotel.getName())) {
            view.displayMessage(hotel.getName() + " does not exist.");
            return;
        }

        currentRoomList = model.getRoomListOfAHotel(hotel.getName());
        view.displayRoomList(currentRoomList);

        nameOfRoomToAdd = view.getInputStr("Please provide the name of the room you want to add:");

        if (!view.confirmUserAction("adding a room to the selected hotel")) {
            view.pressEnterToContinue();
            view.clearScreen();
            return;
        }

        resAddRoom = model.addRoomToAHotel(hotel.getName(), nameOfRoomToAdd);

        if (resAddRoom.isSuccesful()){
            view.displayMessage("Succesfully added a Room.");
            view.pressEnterToContinue();
            view.clearScreen();
            return;
        }

        switch (resAddRoom.getCommonError()){
            case ER_MAX_CAPACITY:
                view.displayMessage("Hotel already exists.");
                break; 
            case ER_NO_HOTEL:
                view.displayMessage("Name of hotel does not exist.");    
                break;
            case ER_NOT_UNIQUE_GIVENNAME:
                view.displayMessage("Room name not unique.");
                break;
            default:
                view.displayMessage("Unknown Error: " + resAddRoom.getMessage());
                break;
        }
    }
    view.clearScreen();
}
  

    

/**
 * Adds a room to a selected hotel.
 * Displays a list of current hotels and rooms, prompts the user for the hotel and room names.
 * Confirms the action and adds the room if valid.
 */
private void addRooms(Hotel hotel) {
    ArrayList<Hotel> currentHotelList;
    ArrayList<Room> currentRoomList;

    String nameOfRoomToAdd = "";
    Result resAddRoom;

    currentHotelList = model.getHotelList();
    view.displayHotelSelection(currentHotelList);

    if (!model.doesHotelExist(hotel.getName())) {
        view.displayMessage(hotel.getName() + " does not exist.");
        return;
    }

    // TODO: Test out
    currentRoomList = model.getRoomListOfAHotel(hotel.getName());
    view.displayRoomList(currentRoomList);

    nameOfRoomToAdd = view.getInputStr("Please provide the name of the room you want to add:");

    if (!view.confirmUserAction("adding a room to the selected hotel")) {
        view.pressEnterToContinue();
        view.clearScreen();
    }

    /**
     * Removes a room from a selected hotel.
     * Displays a list of current hotels and rooms, prompts the user for the hotel and room names.
     * Confirms the action and removes the room if valid.
     */
    private void removeRooms(Hotel hotel){
        ArrayList<Room> currentRoomList;

    if (resAddRoom.isSuccesful()){
        view.displayMessage("Succesfully added a Room.");
        view.pressEnterToContinue();
        view.clearScreen();
        return;
    }

    switch (resAddRoom.getCommonError()){
        case ER_MAX_CAPACITY:
            view.displayMessage("Hotel already exists.");
            break; 
        case ER_NO_HOTEL:
            view.displayMessage("Name of hotel does not exist.");    
            break;
        case ER_NOT_UNIQUE_GIVENNAME:
            view.displayMessage("Room name not unique.");
            break;
        default:
            view.displayMessage("Unknown Error: " + resAddRoom.getMessage());
            break;
    }
    view.pressEnterToContinue();
    view.clearScreen();
}

/**
 * Removes a room from a selected hotel.
 * Displays a list of current hotels and rooms, prompts the user for the hotel and room names.
 * Confirms the action and removes the room if valid.
 */
private void removeRooms(Hotel hotel){
    ArrayList<Room> currentRoomList;

    String nameOfRoomToDelete = "";
    Result resRemoveRoom;
  
  
    if (!model.doesHotelExist(hotel.getName())) {
        view.displayMessage("Hotel does not exist.");
        return; 
    }

    currentRoomList = model.getRoomListOfAHotel(hotel.getName());
    view.displayRoomList(currentRoomList);

    nameOfRoomToDelete = view.getInputStr("Please provide the name of the room you want to DELETE:");

    if (!view.confirmUserAction("deleting a room?")){
        view.clearScreen();
        return;
    }

    resRemoveRoom = model.removeRoomOfHotel(hotel.getName(), nameOfRoomToDelete);
    
    
        if (!model.doesHotelExist(hotel.getName())) {
            view.displayMessage("Hotel does not exist.");
            return; 
        }

        currentRoomList = model.getRoomListOfAHotel(hotel.getName());
        view.displayRoomList(currentRoomList);

        nameOfRoomToDelete = view.getInputStr("Please provide the name of the room you want to DELETE:");

        if (!view.confirmUserAction("deleting a room?")){
            view.clearScreen();
            return;
        }

        resRemoveRoom = model.removeRoomToHotel(hotel.getName(), nameOfRoomToDelete);
        
        if (resRemoveRoom.isSuccesful()){
            view.displayResultMessage("Room succesfully deleted.");
            return;
        }

        switch (resRemoveRoom.getCommonError()) {
            case ER_NO_ROOM:
                view.displayResultMessage("Room not found.");
                break;
            case ER_ROOM_HAS_RESERVATION:
                view.displayResultMessage("Cannot delete, has a reservation.");
                break;
            default:
                view.displayMessage("Unknown Error: " + resRemoveRoom.getMessage());
                break;
        }

        view.clearScreen();
    }


    /**
     * Changes the name of a hotel.
     * Displays a list of current hotels and prompts the user for the old and new names.
     * Confirms the action and updates the hotel's name if valid.
     */
    private void changeHotelName() {
        ArrayList<Hotel> currentList;
        String oldHotelName = "";
        String newHotelName = "";
        Result setHotelState;

        currentList = model.getHotelList();
        view.displayHotelSelection(currentList);

        oldHotelName = view.getInputStr("Please provide the name of the hotel you want to change:");
        newHotelName = view.getInputStr("Please input a new name: ");

        if (!view.confirmUserAction("changing the hotel name")) {
            return;
        }

        setHotelState = model.setHotelName(oldHotelName, newHotelName);

        if (setHotelState.isSuccesful()){
            view.displayMessage("Changed Hotel name.");
            return;
        }

        switch(setHotelState.getMessage()) {
            case "Old hotel name exists.":
                view.displayMessage("Old hotel name exists.");
                break;
            case "Given name is not unique within list.":
                view.displayMessage("Name is not unique, please provide a new one.");
                break;
        }
    }




    

/**
 * Updates the price of rooms in a selected hotel.
 * Displays a list of current hotels, prompts the user for the hotel name and new price.
 * Confirms the action and updates the price if valid.
 */
private void updatePrice(Hotel nameOfHotel) {
    double price = 0.0;
    Result errorState;

    Boolean hasNoExistingHotel = !model.doesHotelExist(nameOfHotel.getName());
    if (hasNoExistingHotel) {
        view.displayMessage("Hotel does not exist.");
        return; 
    }

    price = view.getInputDouble("What is the new price? Price should be greater or equal 100.");
    
    Boolean isBelowBasePrice = price < 100;
    if (isBelowBasePrice) {
        view.displayResultMessage("Error, Price should be greater or equal 100.");
        view.clearScreen();
        return;
    }

    Boolean userWantsToStopUpdatingPrice = !view.confirmUserAction("updating the selected hotel's price per room>");
    if (userWantsToStopUpdatingPrice){
        return;
    }

    errorState = model.updatePriceOfAHotel(nameOfHotel.getName(), price);
    
    if (errorState.isSuccesful()) {
        view.displayResultMessage("Price succesfully changed");
        return;
    }

    switch (errorState.getCommonError()) {
        case ER_NO_HOTEL:
            view.displayResultMessage("Hotel not found.");
            break;
        case ER_ROOM_HAS_RESERVATION:
            view.displayResultMessage("At least one reservation is made for this room; modification of price is not possible.");
            break;
        case ER_LOWER_THAN_BASEPRICE:
            view.displayResultMessage("Price is lower than 100. Please Change to a higher than the minimum price.");
            break;
        default:
            view.displayMessage("Unknown Error: " + errorState.getMessage());
            break;
    }
}


    /**
     * Removes a room from a selected hotel.
     * Displays a list of current hotels and rooms, prompts the user for the hotel and room names.
     * Confirms the action and removes the room if valid.
     */
    private void removeRooms(){
        ArrayList<Hotel> currentHotelList;
        ArrayList<Room> currentRoomList;
        String nameOfHotel = "";
        String nameOfRoomToAdd = "";
        Result resRemoveHotel;

        currentHotelList = model.getHotelList();
        view.displayHotelSelection(currentHotelList);

        nameOfHotel = view.getInputStr("Please provide the name of the hotel you want to change:");

        if (!model.doesHotelExist(nameOfHotel)) {
            view.displayMessage("Hotel does not exist.");
            return;
        }

        currentRoomList = model.getRoomListOfAHotel(nameOfHotel);
        view.displayRoomList(currentRoomList);

        nameOfRoomToAdd = view.getInputStr("Please provide the name of the room you want to add:");
        resRemoveHotel = model.removeRoomOfHotel(nameOfHotel, nameOfRoomToAdd);

        if (resRemoveHotel.isSuccesful()) {
            view.displayMessage("Room succesfully deleted.");
        } else {
            switch(resRemoveHotel.getCommonError()) {
                case ER_NO_ROOM:
                    view.displayMessage("Room not found.");
                    break;
                case ER_ROOM_HAS_RESERVATION:
                    view.displayMessage("Cannot delete, has a reservation.");
                    break;
                default:
                    view.displayMessage("Unknown Error: " + resRemoveHotel.getMessage());
                    break;
            }
        }
    }


/**
 * Removes a reservation from a selected hotel.
 * Prompts the user for the hotel and reservation names.
 * Confirms the action and removes the reservation if valid.
 */
private void removeReservations(Hotel hotel){
    String nameOfReservation = "";
    Result resRemoveReservation;


    if (!model.doesHotelExist(hotel.getName())){
        view.displayMessage("Hotel does not exist.");
        return;
    }
    
    view.displayReservationInformation(model.getReservations(hotel.getName()));

    nameOfReservation = view.getInputStr("Input the room name of the reservation");

    if (!view.confirmUserAction("removing the selected reservation from the hotel")){
        return;
    }
    resRemoveReservation = model.removeReservation(hotel.getName(), nameOfReservation);
    
    if (resRemoveReservation.isSuccesful()){
        view.displayResultMessage("Reservation succesfully deleted.");
    } else {
        switch (resRemoveReservation.getCommonError()) {
            case ER_NO_HOTEL:
                view.displayResultMessage("Hotel is not found");
                break;
            case ER_NO_RESERVATION:
                view.displayResultMessage("Reservation is not found");
                break;
            default:
                view.displayMessage("Unknown Error: " + resRemoveReservation.getMessage());
                break;
        }
    }
    
}



/**
 * Removes a hotel.
 * Prompts the user for the hotel name.
 * Confirms the action and removes the hotel if valid.
 */
private Boolean removeHotel(Hotel hotel){
    Boolean hasDelete = false;
 
    if (view.confirmUserAction("removing the selected hotel")){
        hasDelete = model.removeHotel(hotel.getName());

        if (hasDelete){
            view.displayMessage("Succesfully remove hotel.");
        } else
            view.displayMessage("Hotel name cannot be found.");
    }

    return hasDelete;
}

/**
 * Manages hotel actions based on the given manager state.
 * Prompts the user for specific actions and executes them.
 *
 * @param manageState the current state of the manager
 */
private void manageHotelActions(MANAGER_STATE manageState, Hotel hotel, Boolean hasDeleted){
    boolean isUsingAFunction = true;
    while (isUsingAFunction) { 
        view.displayManageHotelPrompt(manageState);

        if (!view.confirmUserAction("continue? Enter N to quit.")){
            return;
        }

        switch (manageState){
            case MS_CHANGE_NAME:
                changeHotelName(hotel);
                break;
            case MS_ADD_ROOMS:
                addRooms(hotel);
                break;
            case MS_REMOVE_ROOMS:
                removeRooms(hotel);
                break;
            case MS_UPDATE_PRICE:
                updatePrice(hotel);
                break;
            case MS_REMOVE_RESERVATIONS:
                removeReservations(hotel);
                break;
            case MS_REMOVE_HOTEL:
                isUsingAFunction = removeHotel(hotel);
                break;
            default:
                view.displayMessage("Invalid Display state.");
                break;
        }

    }
}



    /**
     * Removes a reservation from a selected hotel.
     * Prompts the user for the hotel and reservation names.
     * Confirms the action and removes the reservation if valid.
     */
    private void removeReservations(Hotel hotel){
        String nameOfReservation = "";
        Result resRemoveReservation;


        if (!model.doesHotelExist(hotel.getName())){
            view.displayMessage("Hotel does not exist.");
            return;
        }
        
        view.displayReservationInformation(model.getReservations(hotel.getName()));

        nameOfReservation = view.getInputStr("Input the room name of the reservation");

        if (!view.confirmUserAction("removing the selected reservation from the hotel")){
            return;
        }

        errorState = model.removeReservation(nameOfHotel, nameOfReservation);

        if (errorState.isSuccesful())
            view.displayMessage("Reservation succesfully deleted.");
        else{
            switch (errorState.getMessage()) {
                case "Hotel was not found.":
                    view.displayMessage("Hotel is not found");
                    break;
                case ER_NO_RESERVATION:
                    view.displayResultMessage("Reservation is not found");
                    break;
                default:
                    view.displayMessage("Unknown Error: " + resRemoveReservation.getMessage());
                    break;
            }
        }
        
    }



    /**
     * Removes a hotel.
     * Prompts the user for the hotel name.
     * Confirms the action and removes the hotel if valid.
     */
    private Boolean removeHotel(Hotel hotel){
        Boolean hasDelete = false;
    
        if (view.confirmUserAction("removing the selected hotel")){
            hasDelete = model.removeHotel(hotel.getName());

            if (hasDelete){
                view.displayMessage("Succesfully remove hotel.");
            } else
                view.displayMessage("Hotel name cannot be found.");
        }

        return hasDelete;
    }

    /**
     * Manages hotel actions based on the given manager state.
     * Prompts the user for specific actions and executes them.
     *
     * @param manageState the current state of the manager
     */
    private void manageHotelActions(MANAGER_STATE manageState, Hotel hotel, Boolean hasDeleted){
        boolean isUsingAFunction = true;
        while (isUsingAFunction) { 
            view.displayManageHotelPrompt(manageState);

            if (!view.confirmUserAction("continue? Enter N to quit.")){
                return;
            }

            switch (manageState){
                case MS_CHANGE_NAME:
                    changeHotelName(hotel);
                    break;
                case MS_ADD_ROOMS:
                    addRooms(hotel);
                    break;
                case MS_REMOVE_ROOMS:
                    removeRooms(hotel);
                    break;
                case MS_UPDATE_PRICE:
                    updatePrice(hotel);
                    break;
                case MS_REMOVE_RESERVATIONS:
                    removeReservations(hotel);
                    break;
                case MS_REMOVE_HOTEL:
                    isUsingAFunction = removeHotel(hotel);
                    break;
                default:
                    view.displayMessage("Invalid Display state.");
                    break;
            }

        }
    }



        /**
         * Removes a reservation from a selected hotel.
         * Prompts the user for the hotel and reservation names.
         * Confirms the action and removes the reservation if valid.
         */
        private void removeReservations(){
            String nameOfHotel = "", nameOfReservation = "";
            Result errorState;

            nameOfHotel = view.getInputStr("Name of the Hotel you want to remove.");

            if (model.doesHotelExist(nameOfHotel)){
                view.displayMessage("Hotel does not exist.");
                return;
            }

            nameOfReservation = view.getInputStr("Name of the reservation you want to remove.");

            if (!view.confirmUserAction("removing the selected reservation from the hotel")){
                return;
            }

            errorState = model.removeReservations(nameOfHotel, nameOfReservation);

            if (errorState.isSuccesful())
                view.displayMessage("Reservation succesfully deleted.");
            else{
                switch (errorState.getMessage()) {
                    case "Hotel was not found.":
                        view.displayMessage("Hotel is not found");
                        break;
                    case "Reservation not found.":
                        view.displayMessage("Reservation is not found");
                        break;
                }
            }

        }





    /**
     * Main method to manage hotels.
     * Prompts the user for actions and manages the flow of hotel management.
     */
    public void manageHotel(){
        Boolean isPerformingManagingHotel = true, isEnteringHotel = true, isManaging = true, hasDelete = false;
        MANAGER_STATE CurrentState = MS_OVERVIEW;

        Hotel newHotel = new Hotel("");
        
        while (isManaging){
            isEnteringHotel = true;
            isPerformingManagingHotel = true;
            
            while (isEnteringHotel) {
                view.displayManageHotelPrompt(MS_CHOSE_HOTEL);

                if (!view.confirmUserAction("managing the hotel?")){
                    isEnteringHotel = false;
                    isManaging = false;
                    return;
                }
                
                if (view.confirmUserAction("\nuse the date On " + checkInDate + "\n" + checkOutDate + "?")) {
                    model.getTotalAvailableRoomsByDate(validHotel, arrayLocalDatesCheckInCheckOut.get(0));
                    listOfAvailableRooms = model.getAvailableRoomsByDate(validHotel, arrayLocalDatesCheckInCheckOut.get(0));
                    isPerformingBookReservation = false;
                    isInputtingRoom = true;
                }
                    
                view.clearScreen();
            }
            
            while (isPerformingManagingHotel) {
                view.displayManageHotelPrompt(MS_OVERVIEW);
                
                if (!hasDelete)
                    view.displayMessage("Editing Hotel: " + newHotel.getName());

                String userInput = view.getInputStr("Please provide a response: ");
                
                switch (userInput) {
                    case "q":
                        CurrentState = MS_OVERVIEW; 
                        isPerformingManagingHotel = false;
                        break;
                    case "a":
                        CurrentState = MS_CHANGE_NAME; 
                        break;
                    case "b":
                        CurrentState = MS_ADD_ROOMS;
                        break;
                    case "c":
                        CurrentState = MS_REMOVE_ROOMS;
                        break;  
                    case "d":
                        CurrentState = MS_UPDATE_PRICE;
                        break;    
                    case "e":
                        CurrentState = MS_REMOVE_RESERVATIONS;
                        break;
                    case "f":
                        CurrentState = MS_REMOVE_HOTEL;
                        break; 
                    default:
                        CurrentState = MS_OVERVIEW; 
                        view.displayInvalidInputWarning();  
                }
        
                if (CurrentState != MS_OVERVIEW) {
                    manageHotelActions(CurrentState, newHotel, hasDelete);
                }

                if (hasDelete){
                    isPerformingManagingHotel = false;
                }

                view.clearScreen();
            }
        }
    }



        /**
         * Selects a valid hotel based on user input.
         * Displays a list of current hotels and prompts the user for the hotel name.
         * Returns the selected hotel if valid.
         *
         * @return the selected hotel, or null if the hotel does not exist
         */
        private Hotel selectValidHotel() {
            ArrayList<Hotel> listOfHotels;
            String nameOfHotel;

            listOfHotels = model.getHotelList();
            view.displayHotelSelection(listOfHotels);
            nameOfHotel = view.getInputStr("Please provide the name of the hotel you want to change:");

            if (!model.doesHotelExist(nameOfHotel)) {
                view.displayMessage("Hotel does not exist.");
                return null;
            }

            return model.getHotel(nameOfHotel);
        }




        /**
         * Prompts the user to input valid check-in and check-out dates.
         * Displays a date selection prompt and ensures the check-in date is before the check-out date.
         * Returns the selected dates as an ArrayList of LocalDate.
         *
         * @return an ArrayList of LocalDate containing the check-in and check-out dates
         */
        private ArrayList<LocalDate> selectValidCheckInAndCheckOutDates(){
            Boolean isSelectingDate = true;

            ArrayList <LocalDate> result = new ArrayList<LocalDate>();

            while (isSelectingDate) {
                view.displayBookReservationPrompt(SB_DATE_SELECTION);

                result.add(view.getLocalDate("Please input the check-in date:"));
                result.add(view.getLocalDate("Please input the check-in date:"));
                if (result.get(0).isAfter(result.get(1))){
                    view.displayMessage("Check-in-date is AFTER Check-out-date.");
                    view.displayInvalidInputWarning();
                } else {
                    view.displayMessage("Valid Date.");
                    isSelectingDate = false;
                }

            }

            return result;
        }


    /**
     * Manages the process of booking a reservation.
     * Prompts the user for hotel selection and valid check-in and check-out dates.
     * Retrieves the total available rooms by date for the selected hotel.
     */
    public void bookReservation(){
        String checkInDate = new String(), checkOutDate = new String(), guestName = new String();
        Boolean isReserving = true, isPerformingBookReservation = true, isInputtingRoom = true, isInputtingName = true, isChoosingHotel = true, isChoosingRoom = true;
        
        int roomChoice = 0;
        Room room = new Room("");
        ArrayList<Room> listOfAvailableRooms = new ArrayList<Room>();
        Hotel validHotel = new Hotel("");

        ArrayList<LocalDate> arrayLocalDatesCheckInCheckOut = new ArrayList<LocalDate>();

        while (isReserving){
            isChoosingHotel = true;
            isPerformingBookReservation = false;
            isChoosingRoom = false;
            isInputtingName = false;

            while (isChoosingHotel) {
                view.clearScreen();
                view.displayBookReservationPrompt(SB_HOTEL_SELECTION);
                if (!view.confirmUserAction("booking a reservation the hotel?")){
                    return;
                }
                validHotel = selectValidHotel();
                if (validHotel != null) {
                    isChoosingHotel = false;
                    isPerformingBookReservation = true;
                }
                
            }

            while (isPerformingBookReservation) {
                view.clearScreen();
                view.displayBookReservationPrompt(SB_OVERVIEW);
                if (!view.confirmUserAction("booking a reservation the hotel?")){
                    isPerformingBookReservation = false;
                } else {
                    view.clearScreen();

                    arrayLocalDatesCheckInCheckOut = selectValidCheckInAndCheckOutDates();
                
                    checkInDate = arrayLocalDatesCheckInCheckOut.get(0).getMonth() + "/" + arrayLocalDatesCheckInCheckOut.get(0).getDayOfMonth() + "/" + arrayLocalDatesCheckInCheckOut.get(0).getYear();
                    checkOutDate = arrayLocalDatesCheckInCheckOut.get(1).getMonth() + "/" + arrayLocalDatesCheckInCheckOut.get(1).getDayOfMonth() + "/" + arrayLocalDatesCheckInCheckOut.get(1).getYear();
                    
                    if (view.confirmUserAction("\nuse the date On " + checkInDate + "\n" + checkOutDate + "?")) {
                        model.getTotalAvailableRoomsByDate(validHotel, arrayLocalDatesCheckInCheckOut.get(0));
                        listOfAvailableRooms = model.getListOfTotalUnreservedRoomsByDate(validHotel, arrayLocalDatesCheckInCheckOut.get(0));
                        isPerformingBookReservation = false;
                        isInputtingRoom = true;
                    }
                }
            }

            while (isInputtingRoom) {
                view.clearScreen();
                
                if (!view.confirmUserAction("booking a reservation the hotel?")){
                    isInputtingRoom = false;
                } else {
                    view.displayRoomList(listOfAvailableRooms);
                    roomChoice = view.getInputInt("Input room number: ") - 1;
                    roomChoice = view.getInputInt("Input room number: ") - 1;
                    view.displayInvalidInputWarning((roomChoice < 0 || roomChoice >= listOfAvailableRooms.size()), "Room number not within index.");
                    if (roomChoice >= 0 && roomChoice < listOfAvailableRooms.size() ){
                        if (view.confirmUserAction("use room " + listOfAvailableRooms.get(roomChoice).getName() + "?")){
                            room = listOfAvailableRooms.get(roomChoice);
                            isInputtingRoom = false;
                            isInputtingName = true; 
                        } else if (room == null) {
                            isInputtingRoom = false;
                            isInputtingName = false;
                            view.displayInvalidInputWarning();
                        } 
                    }
                }
                view.pressEnterToContinue();
            }

            while (isInputtingName) {
                view.clearScreen();
                view.displayBookReservationPrompt(SB_GUEST_SELECTION);

                if (!view.confirmUserAction("booking a reservation the hotel?")){
                    isInputtingName = false;
                } else {
                    guestName = view.getInputStr("Please input your name:");
                    if (view.confirmUserAction("using the " + guestName)){
                        isChoosingRoom = true;
                        isInputtingName = false;
                    }

                        
                }
            }

            while (isChoosingRoom) {
                view.clearScreen();
                view.displayBookReservationPrompt(SB_RESERVATION_CONFIRMATION);
                view.displayMessage("Hotel name: " + validHotel.getName() + "\tGuest name: " + guestName);
                view.displayMessage("CheckIn: " + arrayLocalDatesCheckInCheckOut.get(0).toString() + "\tCheck-out " + arrayLocalDatesCheckInCheckOut.get(1) );
                view.displayMessage("Room: " + room.getName() + " " + room.getBasePricePerNight() + " PHP per night");
                
                if (!view.confirmUserAction("booking a reservation the hotel?")){
                    isInputtingName = false;
                    isChoosingRoom = false;
                }

                if (isChoosingRoom && view.confirmUserAction("Confirm with these details?"))
                    model.makeReservation(validHotel.getName(), guestName, arrayLocalDatesCheckInCheckOut.get(0), arrayLocalDatesCheckInCheckOut.get(1), room);
                    isChoosingRoom = false;
                }
            }
    }



    /**
     * Starts the main program loop.
     * Displays the main action prompt and handles user input for various actions:
     * creating a hotel, viewing a hotel, managing a hotel, booking a reservation, and quitting the program.
     * Continues running until the user decides to quit.
     */
    public void start() {
        boolean isProgramRunning = true;

        /* program flow */
        while (isProgramRunning) {

            view.clearScreen();
            view.displayMainActionPrompt();

            char userInput;
            boolean isValidInput;
            do {
                userInput = view.getInputChar("\nEnter a response (C/V/B/M/Q): ");
                isValidInput = userInput == 'C' || userInput == 'V' || userInput == 'B' || userInput == 'M' || userInput == 'Q';
                view.displayInvalidInputWarning(isValidInput, "Please provide a valid response!");
            } while (!isValidInput);

            view.clearScreen();

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
            }

            view.clearScreen();
        }

        /* program termination sequence */
        view.displayProgramTerminationMessage();
    }
}