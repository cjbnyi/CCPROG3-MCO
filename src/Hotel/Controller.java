package Hotel;

import java.time.LocalDate;
import java.util.ArrayList;


import static Hotel.View.MANAGER_STATE;
import static Hotel.View.MANAGER_STATE.*;
import static Hotel.View.SIMULATE_BOOKING;
import static Hotel.View.SIMULATE_BOOKING.*;

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

private boolean isQuit(Boolean outsideLoop){
    String choice = "";
    choice = view.getUserInput("Continue? Type \"quit\" to stop changing names, else the program will continue.");
    
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
    view.displayHotels(currentList);

    oldHotelName = view.getUserInput("Please provide the name of the hotel you want to change:");
    newHotelName = view.getUserInput("Please input a new name: ");
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

private void addRooms(){
    ArrayList<Hotel> currentHotelList;
    ArrayList<Room> currentRoomList;
    String nameOfHotel = "";
    String nameOfRoomToAdd = "";
    boolean isAddingSuccesful = false;

    currentHotelList = model.getHotelList();
    view.displayHotels(currentHotelList);

    nameOfHotel = view.getUserInput("Please provide the name of the hotel you want to change:");

    if (!model.doesHotelExist(nameOfHotel)) {
        return; 
    }

    currentRoomList = model.getRoomListOfAHotel(nameOfHotel);
    view.displayRoomList(currentRoomList);

    nameOfRoomToAdd = view.getUserInput("Please provide the name of the room you want to add:");
    isAddingSuccesful = model.addRoomToAHotel(nameOfHotel, nameOfRoomToAdd);

    if (isAddingSuccesful){
        view.displayMessage("Succesfully added a Room.");
    } else {
        view.displayMessage("Cannot add the Room.");
    }
}

private void removeRooms(){
    ArrayList<Hotel> currentHotelList;
    ArrayList<Room> currentRoomList;
    String nameOfHotel = "";
    String nameOfRoomToAdd = "";
    int isRemovingSuccessful = 0;

    currentHotelList = model.getHotelList();
    view.displayHotels(currentHotelList);

    nameOfHotel = view.getUserInput("Please provide the name of the hotel you want to change:");

    if (!model.doesHotelExist(nameOfHotel)) {
        view.displayMessage("Hotel does not exist.");
        return; 
    }

    currentRoomList = model.getRoomListOfAHotel(nameOfHotel);
    view.displayRoomList(currentRoomList);

    nameOfRoomToAdd = view.getUserInput("Please provide the name of the room you want to add:");
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
    view.displayHotels(currentHotelList);
    nameOfHotel = view.getUserInput("Please provide the name of the hotel you want to change:");

    if (!model.doesHotelExist(nameOfHotel)) {
        view.displayMessage("Hotel does not exist.");
        return; 
    }

    price = view.getUserInputDouble("What is the new price? Price should be greater 100.");
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

private void removeReservations(){
    String nameOfHotel = "", nameOfReservation = "", choice = "";
    int errorState = 0;
    boolean isContinuing = false;
    nameOfHotel = view.getUserInput("Name of the Hotel you want to remove.");

    if (model.doesHotelExist(nameOfHotel)){
        view.displayMessage("Hotel does not exist.");
        return;
    }

    nameOfReservation = view.getUserInput("Name of the reservation you want to remove.");
    choice = view.getUserInput("Are you sure to delete this reservation?");
    isContinuing = choice.equals("yes") || choice.equals("y") || choice.equals("Y") || choice.equals("Yes") || choice.equals("YES");
   

    if (isContinuing){
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
    String choice = "";
    String hotelName = view.getUserInput("Input name of the hotel.");
    Boolean isContinuing = false, hasDelete = false;
    choice = view.getUserInput("Are you sure to delete this reservation?");
    isContinuing = choice.equals("yes") || choice.equals("y") || choice.equals("Y") || choice.equals("Yes") || choice.equals("YES");
   

    if (!isContinuing){
        return ;
    }

    hasDelete = model.removeHotel(hotelName);

    if (hasDelete)
        view.displayMessage("Succesfully remove hotel.");
    else
        view.displayMessage("Hotel name cannot be found.");
    
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
        String userInput = view.getUserInput("Please provide a response: ");
        
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

private Boolean selectValidHotel(){
    ArrayList<Hotel> listOfHotels;
    String nameOfHotel;

    listOfHotels = model.getHotelList();
    view.displayHotels(listOfHotels);
    nameOfHotel = view.getUserInput("Please provide the name of the hotel you want to change:");

    if (!model.doesHotelExist(nameOfHotel)) {
        view.displayMessage("Hotel does not exist.");
        return false; 
    }
    return true;
};

private Boolean selectValidCheckInAndCheckOutDates(LocalDate checkInDate, LocalDate checkOutDate){
    Boolean isSelectingDate = true;

    while (isSelectingDate) {
        view.displayBookReservationPrompt(SB_DATE_SELECTION);

        if (isQuit(isSelectingDate)) {
            return false;
        }

        checkInDate  = view.getLocalDate("Please input the check-in date:");
        checkOutDate = view.getLocalDate("Please input the check-in date:");
        if (checkInDate.isAfter(checkOutDate)){
            view.displayMessage("Check-in-date is AFTER Check-out-date.");
            view.displayInvalidInputWarning();
        } else {
            view.displayMessage("");
            isSelectingDate = false;
        }
    }

    return true;
}

public void bookReservation(){
    String userInput;
    
    Boolean isReserving = true, hasSelectedValidHotel = true;
    LocalDate checkInDate, checkOutDate;

    while (isReserving) {
        view.displayBookReservationPrompt(SB_OVERVIEW);
        userInput = view.getUserInput("Please provide a response: ");


        hasSelectedValidHotel = selectValidHotel();

        //selectValidCheckInAndCheckOutDates(checkInDate, checkOutDate);


        // TODO: Select Room.
        // ? Automated or Manual Selection?
        // Change Status of Room and be viewable in Hotel Feature.
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