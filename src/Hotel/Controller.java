package Hotel;

import java.time.LocalDate;
import java.util.ArrayList;

import Hotel.View.MANAGER_STATE;

import static Hotel.View.MANAGER_STATE;
import static Hotel.View.MANAGER_STATE.*;

import static Hotel.View.SIMULATE_BOOKING.*;

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


private boolean isQuit(Boolean outsideLoop){
    String choice = "";
    choice = view.getInputStr("Continue? Type \"quit\" to stop changing names, else the program will continue.");
    
    if (choice.equals("quit")){
        outsideLoop = false;
        return true;
    } else 
        return false;
}

private void changeHotelName() {
    ArrayList<Hotel> currentList;
    String oldHotelName = "";
    String newHotelName = "";
    int setHotelState = 0;

    currentList = model.getHotelList();
    view.displayHotelSelection(currentList);

    oldHotelName = view.getInputStr("Please provide the name of the hotel you want to change:");
    newHotelName = view.getInputStr("Please input a new name: ");
    
    if (view.confirmUserInput()) {
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
    if (view.confirmUserInput()) {
        isAddingSuccesful = model.addRoomToAHotel(nameOfHotel, nameOfRoomToAdd);
        if (isAddingSuccesful){
            view.displayMessage("Succesfully added a Room.");
        } else {
            view.displayMessage("Cannot add the Room.");
        }
    }
}

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
    
    if (view.confirmUserInput()){
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

private void removeReservations(){
    String nameOfHotel = "", nameOfReservation = "";
    int errorState = 0;

    nameOfHotel = view.getInputStr("Name of the Hotel you want to remove.");

    if (model.doesHotelExist(nameOfHotel)){
        view.displayMessage("Hotel does not exist.");
        return;
    }

    nameOfReservation = view.getInputStr("Name of the reservation you want to remove.");

    if (view.confirmUserInput()){
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

private void removeHotel(){
    String hotelName = view.getInputStr("Input name of the hotel.");
    Boolean hasDelete = false;
 
    if (view.confirmUserInput()){
        hasDelete = model.removeHotel(hotelName);

        if (hasDelete)
            view.displayMessage("Succesfully remove hotel.");
        else
            view.displayMessage("Hotel name cannot be found.");
    }
}

private void manageHotelActions(MANAGER_STATE ManageState){
    boolean isUsingAFunction = true;
    while (isUsingAFunction) { 
        view.displayManageHotelPrompt(ManageState);

        if (isQuit(isUsingAFunction)){
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

private Hotel selectValidHotel(){
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
};



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

public void start() {

    boolean isProgramRunning = true;

    /* program flow */
    while (isProgramRunning) {

        view.displayMainActionPrompt();

        String userInput = view.getInputStr("\nPlease provide a response: ");

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