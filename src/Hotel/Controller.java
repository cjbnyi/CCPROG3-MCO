package Hotel;

import java.time.LocalDate;
import java.util.ArrayList;

import Hotel.View.MANAGER_STATE;

import static Hotel.View.MANAGER_STATE;
import static Hotel.View.MANAGER_STATE.*;

import static Hotel.View.SIMULATE_BOOKING.*;


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
                view.displayInvalidInputWarning(isValidBasePrice, "Please provide a base price greater than 0!");
            } catch(NumberFormatException e) {
                roomBasePrice = 0;
                isValidBasePrice = false;
                view.displayInvalidInputWarning(isValidBasePrice, "Please provide a numeric response!");
            }
        } while(!isValidBasePrice);

        if (view.confirmUserAction("creating a new hotel")) {
            hotel = model.addHotel(hotelName);
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
                Reservation reservation = hotel.getReservation(room, date);

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
    }


    /**
     * Checks if the user wants to quit the current operation.
     *
     * @param outsideLoop a boolean indicating the current state of the loop
     * @return true if the user wants to quit, false otherwise
     */
    private boolean isQuit(Boolean outsideLoop){
        String choice = "";
        choice = view.getInputStr("Continue? Type \"quit\" to stop changing names, else the program will continue.");

        if (choice.equals("quit")){
            outsideLoop = false;
            return true;
        } else
            return false;
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
        int setHotelState = 0;

        currentList = model.getHotelList();
        view.displayHotelSelection(currentList);

        oldHotelName = view.getInputStr("Please provide the name of the hotel you want to change:");
        newHotelName = view.getInputStr("Please input a new name: ");

        if (view.confirmUserAction("changing the hotel name")) {
            setHotelState = model.setHotelName(oldHotelName, newHotelName);

            switch(setHotelState) {
                case 0:
                    view.displayMessage("Hotel does not exist.");
                    break;
                case 1:
                    view.displayMessage("Name is not unique, please provide a new one.");
                    break;
                case 2:
                    view.displayMessage("Changed Hotel name.");
                    break;
            }
        }
    }


    /**
     * Adds a room to a selected hotel.
     * Displays a list of current hotels and rooms, prompts the user for the hotel and room names.
     * Confirms the action and adds the room if valid.
     */
    private void addRooms(){
        ArrayList<Hotel> currentHotelList;
        ArrayList<Room> currentRoomList;
        String nameOfHotel = "";
        String nameOfRoomToAdd = "";
        boolean isAddingSuccesful = false;

        currentHotelList = model.getHotelList();
        view.displayHotelSelection(currentHotelList);

        nameOfHotel = view.getInputStr("Please provide the name of the hotel you want to change:");

        if (!model.doesHotelExist(nameOfHotel)) {
            return;
        }

        currentRoomList = model.getRoomListOfAHotel(nameOfHotel);
        view.displayRoomList(currentRoomList);

        nameOfRoomToAdd = view.getInputStr("Please provide the name of the room you want to add:");
        if (view.confirmUserAction("adding a room to the selected hotel")) {
            isAddingSuccesful = model.addRoomToAHotel(nameOfHotel, nameOfRoomToAdd);
            if (isAddingSuccesful){
                view.displayMessage("Succesfully added a Room.");
            } else {
                view.displayMessage("Cannot add the Room.");
            }
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
        int isRemovingSuccessful = 0;

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
        isRemovingSuccessful = model.removeRoomToHotel(nameOfHotel, nameOfRoomToAdd);

        switch(isRemovingSuccessful) {
            case 0:
                view.displayMessage("Room not found.");
                break;
            case 1:
                view.displayMessage("Cannot delete, has a reservation.");
                break;
            case 2:
                view.displayMessage("Room succesfully deleted.");
                break;
        }
    }


    /**
     * Updates the price of rooms in a selected hotel.
     * Displays a list of current hotels, prompts the user for the hotel name and new price.
     * Confirms the action and updates the price if valid.
     */
    private void updatePrice(){
        ArrayList<Hotel> currentHotelList;
        String nameOfHotel = "";
        double price = 0.0;

        int errorState = 0;

        currentHotelList = model.getHotelList();
        view.displayHotelSelection(currentHotelList);
        nameOfHotel = view.getInputStr("Please provide the name of the hotel you want to change:");

        if (!model.doesHotelExist(nameOfHotel)) {
            view.displayMessage("Hotel does not exist.");
            return;
        }

        price = view.getInputDouble("What is the new price? Price should be greater 100.");

        if (view.confirmUserAction("updating the selected hotel's price per room")){
            errorState = model.updatePriceOfAHotel(nameOfHotel, price);
            switch (errorState) {
                case 0:
                    view.displayMessage("Hotel not found.");
                    break;
                case 1:
                    view.displayMessage("At least one reservation is made, deletion is not possible.");
                    break;
                case 2:
                    view.displayMessage("Price is lower than 100. Please Change to a higher than the minimum price.");
                    break;
                case 3:
                    view.displayMessage("Price succesfully changed");
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
        int errorState = 0;

        nameOfHotel = view.getInputStr("Name of the Hotel you want to remove.");

        if (model.doesHotelExist(nameOfHotel)){
            view.displayMessage("Hotel does not exist.");
            return;
        }

        nameOfReservation = view.getInputStr("Name of the reservation you want to remove.");

        if (view.confirmUserAction("removing the selected reservation from the hotel")){
            errorState = model.removeReservations(nameOfHotel, nameOfReservation);
            switch (errorState) {
                case 0:
                    view.displayMessage("Hotel is not found");
                    break;
                case 1:
                    view.displayMessage("Reservation is not found");
                    break;
                case 2:
                    view.displayMessage("Reservation succesfully deleted.");
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * Removes a hotel.
     * Prompts the user for the hotel name.
     * Confirms the action and removes the hotel if valid.
     */
    private void removeHotel(){
        String hotelName = view.getInputStr("Input name of the hotel.");
        Boolean hasDelete = false;

        if (view.confirmUserAction("removing the selected hotel")){
            hasDelete = model.removeHotel(hotelName);

            if (hasDelete)
                view.displayMessage("Succesfully remove hotel.");
            else
                view.displayMessage("Hotel name cannot be found.");
        }
    }


    /**
     * Manages hotel actions based on the given manager state.
     * Prompts the user for specific actions and executes them.
     *
     * @param ManageState the current state of the manager
     */
    private void manageHotelActions(MANAGER_STATE ManageState){
        boolean isUsingAFunction = true;
        while (isUsingAFunction) {
            view.displayManageHotelPrompt(ManageState);


            if (!view.confirmUserAction("Continue or Quit manager? Enter N to quit.")){
                return;
            }

            switch (ManageState){
                case MS_CHANGE_NAME:
                    changeHotelName();
                    break;
                case MS_ADD_ROOMS:
                    addRooms();
                    break;
                case MS_REMOVE_ROOMS:
                    removeRooms();
                    break;
                case MS_UPDATE_PRICE:
                    updatePrice();
                    break;
                case MS_REMOVE_RESERVATIONS:
                    removeReservations();
                    break;
                case MS_REMOVE_HOTEL:
                    removeHotel();
                    break;
                case MS_OVERVIEW:
                    break;
            }
        }
    }


    /**
     * Main method to manage hotels.
     * Prompts the user for actions and manages the flow of hotel management.
     */
    public void manageHotel(){
        boolean isManaging = true;
        MANAGER_STATE CurrentState = MS_OVERVIEW;
        while (isManaging) {
            view.displayManageHotelPrompt(MS_OVERVIEW);
            String userInput = view.getInputStr("Please provide a response: ");

            switch (userInput) {
                case "quit":
                    CurrentState = MS_OVERVIEW;
                    isManaging = false;
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
                manageHotelActions(CurrentState);
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

            if (isQuit(isSelectingDate)) {
                return result;
            }

            result.add(view.getLocalDate("Please input the check-in date:"));
            result.add(view.getLocalDate("Please input the check-in date:"));
            if (result.get(0).isAfter(result.get(1))){
                view.displayMessage("Check-in-date is AFTER Check-out-date.");
                view.displayInvalidInputWarning();
            } else {
                view.displayMessage("Valid Date.");
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
        String userInput;

        Boolean isReserving = true;
        Hotel validHotel;
        ArrayList<LocalDate> CheckDates;

        while (isReserving) {
            view.displayBookReservationPrompt(SB_OVERVIEW);
            userInput = view.getInputStr("Please provide a response: ");


            validHotel = selectValidHotel();
            CheckDates = selectValidCheckInAndCheckOutDates();

            model.getTotalAvailableRoomsByDate(validHotel, CheckDates.get(0));
            // ? Automated or Manual Selection?
            // Change Status of Room and be viewable in Hotel Feature.
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