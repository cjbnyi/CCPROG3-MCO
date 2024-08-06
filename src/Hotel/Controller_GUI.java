package Hotel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JList;

/** 
 * A Controller that uses the Java Swing.
*/
public class Controller_GUI implements ActionListener {
    private Model model;
    private HotelGUI view;
    private boolean hasConfirmed;
    private boolean hasAgreed;
    private Boolean[] hasSelectedHotel;
    private String actionToConfirm;
    private Integer[] selectedHotels;
    public static final int SYSTEM_MONTH = 7, SYSTEM_YEAR = 2024;
    
    /**
     * Constructs a Controller with the specified Model and View.
     *
     * @param model the model to be used by the controller
     * @param gui the view to be used by the controller
     */
    public Controller_GUI (Model model, HotelGUI gui) {
        this.model = model;
        this.view = gui;

        this.actionToConfirm = "";
        this.hasConfirmed = false;
        this.hasAgreed = false;

        Integer selectedHotel[] = {0, 0, 0, 0};
        this.selectedHotels = selectedHotel;

        Boolean hasSelectedHotel[] = {false, false, false, false};
        this.hasSelectedHotel = hasSelectedHotel;

        gui.setActionListener(this);
    }

    public void start(){
        this.view.runGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String event = e.getActionCommand();
        
        // TODO: Remove once done debugging

        System.out.println("\"" + event + "\"");
        controllerActions(event);
        
    }
    
    private void controllerActions(String event){   
        // Confirmation Panels
        switch (event){
            case "Yes":
                this.hasAgreed = true;
                controllerActions(this.actionToConfirm);
                break;
            case "No":
                this.hasAgreed = false;
                controllerActions(this.actionToConfirm);
                break;
        }

        // Home Panel
        switch (event){
            case "Create Hotel":
                view.selectJtabbedPanelView(1);
                break;
            case "View Hotel":
                view.selectJtabbedPanelView(2);
                break;
            case "Manage Hotel":
                view.selectJtabbedPanelView(3);
                break;
            case "Book Hotel":
                view.selectJtabbedPanelView(4);
                break;
            case "Quit Manager":
                System.exit(0);
                break;
        }

        // Hotel Selection Panel and Create Panel
        switch (event){
            case "<":
                getPrevHotel();
                break;
            case ">":
                getNextHotel();
                break;
            case "Select Hotel":
                confirmSelectedHotel();
                break;
            case "Create Hotel Instance":
                guiCreateHotel();
                break;
        }

        // View Panel
        switch(event){
            case "View High level":
                guiViewHighHotel();
                break;
            case "View Date":
                guiViewLowLevelDate();
                break;
            case "View Room":
                guiViewLowLevelRoom();
                break;
            case "View Reservation":
                guiViewLowLevelReservation();
                break;
        }

        // Manage Panel
        switch(event) {
            case "Change Name":
                guiChangeName();
                break;
            case "Add Rooms":
                guiAddRooms();
                break;
            case "Remove Rooms":
                guiRemoveRooms();
                break;
            case "Update Price":
                guiUpdatePrice();
                break;
            case "Remove Reservation":
                guiRemoveReservation();
                break;
            case "Remove Hotel":
                guiRemoveHotel();
                break;
            case "Date Price":
                guiDatePriceModifier();
                break;
        }

        // Book Panel
        switch (event){
            case "Book Reservation":
                guiBookReservation();
                break;  
        }

        switch (event){
            case "Apply Discount Code":
                guiApplyDiscountCode();
                break;
            case "Remove Discount Code":
                guiRemoveDiscountCode();
                break;
        }

        // TODO: Apply Discount code, and Remove Discount code.
        
    }
    
    /**
     * Refreshes the current hotel selection panel based on the current state.
     */
    private void refreshPanel(){
        ArrayList<Hotel> currentHotelList = model.getHotelList();
        int currentPanelIndex = view.getViewedPanel() - 1;
        PanelHotelSelection hotelSelectionPanel = this.view.getPanelHotelSelectionList().get(currentPanelIndex);
        
        if (hasSelectedHotel[currentPanelIndex]){
            hotelSelectionPanel.setBtnPrevHotelEnable(false);
            hotelSelectionPanel.setBtnNextHotelEnable(false);
        } else {
            hotelSelectionPanel.setBtnPrevHotelEnable(selectedHotels[currentPanelIndex] >= 1);
            hotelSelectionPanel.setBtnNextHotelEnable(selectedHotels[currentPanelIndex] <= currentHotelList.size() - 2);
        }
        hotelSelectionPanel.setBtnSelectHotel(!this.hasConfirmed);
        hotelSelectionPanel.setSelectionVisible(hasSelectedHotel[currentPanelIndex]);
    }


    /**
     * Updates the GUI for hotel selection.
     */
    private void guiUpdateHotelSelection(){
        ArrayList<Hotel> currentHotelList = model.getHotelList();
        String listString = new String("");
        ArrayList<PanelHotelSelection> panelList = this.view.getPanelHotelSelectionList();
        
        refreshPanel();

        if (currentHotelList.isEmpty()){
            
            for (PanelHotelSelection hotelSelection : panelList){
                hotelSelection.setSelectedHotel("None selected");
                hotelSelection.setHotelList("No hotels created.");
                hotelSelection.setBtnSelectHotel(false);
                hotelSelection.setBtnNextHotelEnable(false);
                hotelSelection.setBtnPrevHotelEnable(false);
            }
            
            return;
        }
        
        int i = 1;
        for (Hotel currHotel : currentHotelList){
            listString += "" + i + ") " + currHotel.getName() + "\n";
            i++;
        }
        
        i = 0;
        for (PanelHotelSelection hotelSelection : panelList){
            selectedHotels[i] = 0;
            hotelSelection.setHotelList(listString);
            hotelSelection.setBtnSelectHotel(true);
            hotelSelection.setSelectedHotel(currentHotelList.getFirst().getName());
            hotelSelection.setBtnPrevHotelEnable(false);
            hotelSelection.setBtnNextHotelEnable(currentHotelList.size() != 1);
            i++;

        }
    }


    /**
     * Retrieves and displays the previous hotel in the selection.
     */
    private void getPrevHotel(){
        ArrayList<Hotel> currentHotelList = model.getHotelList();
        int currentPanelIndex = view.getViewedPanel() - 1;
        PanelHotelSelection hotelSelectionPanel = this.view.getPanelHotelSelectionList().get(currentPanelIndex);
        System.out.println(selectedHotels[currentPanelIndex] + currentHotelList.get(selectedHotels[currentPanelIndex]).getName());
        hotelSelectionPanel.setBtnPrevHotelEnable(selectedHotels[currentPanelIndex] > 1);
        hotelSelectionPanel.setBtnNextHotelEnable(true);
        selectedHotels[currentPanelIndex] -= 1;
        hotelSelectionPanel.setSelectedHotel(currentHotelList.get(selectedHotels[currentPanelIndex]).getName());
    }

    /**
     * Retrieves and displays the next hotel in the selection.
     */
    private void getNextHotel(){
        ArrayList<Hotel> currentHotelList = model.getHotelList();
        int currentPanelIndex = view.getViewedPanel() - 1;
        PanelHotelSelection hotelSelectionPanel = this.view.getPanelHotelSelectionList().get(currentPanelIndex);
        System.out.println(selectedHotels[currentPanelIndex] + currentHotelList.get(selectedHotels[currentPanelIndex]).getName());
        hotelSelectionPanel.setBtnPrevHotelEnable(true);
        hotelSelectionPanel.setBtnNextHotelEnable(selectedHotels[currentPanelIndex] < currentHotelList.size() - 2);
        selectedHotels[currentPanelIndex] += 1;

        hotelSelectionPanel.setSelectedHotel(currentHotelList.get(selectedHotels[currentPanelIndex]).getName());
    }


    /**
     * Resets the user agreement flags.
     */
    private void resetUserAgreement(){
        this.hasConfirmed = false;
        this.hasAgreed = false;
    }

    /**
     * Retrieves the selected hotel from a given hotel panel.
     *
     * @param panel The hotel panel from which to retrieve the selected hotel.
     * @return The selected hotel, or null if none is selected.
     */
    private Hotel getHotelPanelSelectedHotel(HotelPanel panel){
        ArrayList<Hotel> currentHotelList = model.getHotelList();
        int currentPanelIndex = view.getViewedPanel() - 1;

        if (currentHotelList.isEmpty() || !hasSelectedHotel[currentPanelIndex]){
            panel.setContentInfo("No hotel selected.");
            return null;
        } else 

        return currentHotelList.get(selectedHotels[currentPanelIndex]);
    }


    /**
     * Checks if the user has not confirmed an action and updates the hotel panel accordingly.
     *
     * @param hotelPanel The hotel panel to update.
     * @param buttonName The name of the button triggering the confirmation.
     * @return True if the user has not confirmed, false otherwise.
     */
    private boolean hasUserNotConfirmed(HotelPanel hotelPanel, String buttonName) {
        System.out.println(this.hasConfirmed);
        Boolean hasNotConfirmed = !this.hasConfirmed;
        if (hasNotConfirmed) {
            view.setOnlyPanelEnable(hotelPanel.getPanelName());
            hotelPanel.setAllButtonsDisabled();
            hotelPanel.setConfirmationPanelVisilibity(true);
            hotelPanel.setHotelButtonsEnabled(false);
            this.actionToConfirm = buttonName;
            this.hasConfirmed = true;
            System.out.println("bus\n");
        }
        return hasNotConfirmed;
    }

    /**
     * Checks if the user has not agreed to an action and updates the hotel panel accordingly.
     *
     * @param hotelPanel The hotel panel to update.
     * @param buttonName The name of the button triggering the agreement check.
     * @return True if the user has not agreed, false otherwise.
     */
    private boolean hasUserNotAgreed(HotelPanel hotelPanel, String buttonName){
        System.out.println(this.hasAgreed);
        Boolean hasUserNotAgreed = !this.hasAgreed;
        if (hasUserNotAgreed) {
            hotelPanel.setContentInfo("Cancelled creation.");
            hotelPanel.resetButtonEnabled();
            hotelPanel.setConfirmationPanelVisilibity(false);
            this.actionToConfirm = "";
            resetUserAgreement();
            confirmSelectedHotel();
            view.resetPanelEnable(); 
        }
        return hasUserNotAgreed;
    }

    /**
     * Checks if the user has agreed to an action and updates the hotel panel accordingly.
     *
     * @param hotelPanel The hotel panel to update.
     * @return True if the user has agreed, false otherwise.
     */
    private boolean hasUserAgreed(HotelPanel hotelPanel){
        System.out.println(this.hasAgreed);
        Boolean hasUserAgreed = this.hasAgreed;
        if (this.hasAgreed){
            hotelPanel.setConfirmationPanelVisilibity(false);
            hotelPanel.resetButtonEnabled();
            this.actionToConfirm = "";
            resetUserAgreement();
            confirmSelectedHotel();
            view.resetPanelEnable();
        }

        return hasUserAgreed;
    }
    
    /**
     * Confirms the selected hotel and refreshes the panel.
     */
    private void confirmSelectedHotel(){
        int currentPanelIndex = view.getViewedPanel() - 1;
        hasSelectedHotel[currentPanelIndex] = !hasSelectedHotel[currentPanelIndex];
        refreshPanel();
    }

    /**
     * Checks if the user has failed agreement and updates the hotel panel accordingly.
     *
     * @param hotelPanel The hotel panel to update.
     * @param nameOfButton The name of the button triggering the agreement check.
     * @param disagreementText The text to display if the user disagrees.
     * @return True if the user has failed the agreement, false otherwise.
     */
    private boolean hasUserFailedAgreement(HotelPanel hotelPanel, String nameOfButton, String disagreementText){
        if (hasUserNotConfirmed(hotelPanel, nameOfButton))
        return true;
   
        if (hasUserNotAgreed(hotelPanel, nameOfButton)){
            hotelPanel.setContentInfo(disagreementText);
            return true;
        }

        hasUserAgreed(hotelPanel);
        return false;
    }

    /**
     * Checks if the base price of a hotel is invalid.
     *
     * @param hotelBasePrice The base price of the hotel.
     * @return True if the base price is invalid, false otherwise.
     */
    private boolean isBasePriceInvalid(Double hotelBasePrice){
        return hotelBasePrice < 100.0 || !Double.isFinite(hotelBasePrice);
    }

    /**
     * Checks if a hotel clone exists.
     *
     * @param hotel The hotel to check.
     * @return True if the hotel clone exists, false otherwise.
     */
    private boolean doesHotelCloneNotExist(Hotel hotel){
        return hotel == null;
    }

    /**
     * Checks if a room clone exists.
     *
     * @param room The room to check.
     * @return True if the room clone exists, false otherwise.
     */
    private boolean doesRoomCloneExist(Room room){
        return room == null;
    }


    /**
     * Checks if a reservation clone exists.
     *
     * @param reservation The reservation to check.
     * @return True if the reservation clone exists, false otherwise.
     */
    private boolean doesReservationCloneNotExist(Reservation reservation){
        return reservation == null;
    }

    /**
     * Checks if a given string is empty after trimming.
     *
     * @param givenString The string to check.
     * @return True if the string is empty after trimming, false otherwise.
     */
    private boolean isStringTrimmedEmpty(String givenString){
        return givenString.trim().equals("");
    }

    /**
     * Creates a new hotel via the GUI.
     */
    private void guiCreateHotel(){
        PanelCreateHotel createHotel = view.getPanelCreateHotel();
        String strHotelNameField = createHotel.getNameField().getText();
        Hotel hotel = model.getHotelClone(strHotelNameField);

        Boolean hasExistingName = !doesHotelCloneNotExist(hotel) || isStringTrimmedEmpty(strHotelNameField); /* hotel does not exist yet */
        if (hasExistingName){
            createHotel.setContentInfo("Please insert a valid hotel name.");
            return;
        } 

        String strPriceFieldText = view.getPanelCreateHotel().getPriceField().getText();
        Double hotelBasePrice = 0.0;

        try {
            hotelBasePrice = Double.parseDouble(strPriceFieldText);
        } catch (NumberFormatException e) {
            view.getPanelCreateHotel().setContentInfo("Please insert a valid price.");
            return;
        }

        if (isBasePriceInvalid(hotelBasePrice)){
            view.getPanelCreateHotel().setContentInfo("Price should be finite and greater than P100.0.");
            return;
        }

        if (hasUserFailedAgreement(createHotel, "Create Hotel Instance", "Cancelled creation")) 
            return;
        
        Result hotelResult = model.addHotel(strHotelNameField, hotelBasePrice);
        if (hotelResult.isSuccesful()){
            createHotel.setContentInfo("Hotel succesfully created.");
            createHotel.getNameField().setText("");
            createHotel.getPriceField().setText("");
            guiUpdateHotelSelection();
            return;
        }
        
        createHotel.setContentInfo("Error: " + hotelResult.getMessage());
    }

    /**
     * Displays high-level information about the selected hotel.
     */
    private void guiViewHighHotel(){
        PanelViewHotel viewPanel = this.view.getPanelViewHotel();
        Hotel hotel = getHotelPanelSelectedHotel(viewPanel);
        boolean isHotelNull = hotel == null;
        if (isHotelNull){
            return;
        }
        viewPanel.setContentInfo("");
        double estimatedEarnings = model.getHotelEstimatedEarnings(hotel);
  
        String output[] = {
            "Hotel name: " + hotel.getName(),
            "Total number of rooms: " + hotel.getTotalRooms(),
            "Estimated earnings: " + estimatedEarnings
        };
        viewPanel.addContentInfo(output);
    }

    /**
     * Displays low-level date information about the selected hotel.
     */
    private void guiViewLowLevelDate(){
        PanelViewHotel viewPanel = this.view.getPanelViewHotel();
        Hotel hotel = getHotelPanelSelectedHotel(viewPanel);


        if (doesHotelCloneNotExist(hotel)){
            return;
        }
        viewPanel.setContentInfo("");
        int daySelection = viewPanel.getjLDateSelected().getSelectedIndex();
        if (daySelection == -1){
            viewPanel.setContentInfo("Select a date.");
            return;
        }

        LocalDate date = LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, viewPanel.getjLDateSelected().getSelectedValue());
        
        int availableRooms = model.getNumOfAvailableRoomsByDate(hotel, date);

        String output[] = {
            "Hotel: " + hotel.getName(), 
            "Available Rooms: " + availableRooms, 
            "Unvailable Rooms: " + (hotel.getTotalRooms() - availableRooms)
        };
        
        viewPanel.addContentInfo(output);
    }

    /**
     * Displays low-level room information about the selected hotel.
     */
    private void guiViewLowLevelRoom(){
        PanelViewHotel viewPanel = this.view.getPanelViewHotel();
        Hotel hotel = getHotelPanelSelectedHotel(viewPanel);

        if (doesHotelCloneNotExist(hotel)){
            return;
        }

        viewPanel.setContentInfo("");
        view.guiDisplayRoomSelection(viewPanel, hotel.getName(), hotel.getRoomList());

        String roomName = viewPanel.getTfRoomName().getText();
        if (isStringTrimmedEmpty(roomName)) 
            return;
        

        Room room = model.getRoomOfAHotel(hotel.getName(), roomName);
        if (doesRoomCloneExist(room)) {
            viewPanel.setContentInfo("Room does not exist. \nInput a valid room name, or empty the textfield to go back.");
            return;
        }

        String lowLevelRoomInfo[] = {
            "Name: " + room.getName(),
            "Type: " + model.getRoomTypeString(room),
            "To go back to the list, please empty the View Room Textfield and press again."
        };
        viewPanel.addContentInfo(lowLevelRoomInfo);
    }

    /**
     * Displays low-level reservation information about the selected hotel.
     */
    private void guiViewLowLevelReservation(){
        PanelViewHotel viewPanel = this.view.getPanelViewHotel();
        Hotel hotel = getHotelPanelSelectedHotel(viewPanel);
        
        if (doesHotelCloneNotExist(hotel))
            return;
        
        viewPanel.setContentInfo("");
        view.guiDisplayRoomSelection(viewPanel, hotel.getName(), hotel.getRoomList());
        String roomName = viewPanel.getTfRoomName().getText();

        if (isStringTrimmedEmpty(roomName)) 
            return;
        
        Room room = model.getRoomOfAHotel(hotel.getName(), roomName);
        if (doesRoomCloneExist(room)) {
            viewPanel.setContentInfo("Room does not exist. \nInput a valid room name, or empty the textfield to go back.");
            return;
        }

        viewPanel.setContentInfo("\nSelected hotel: " + hotel.getName() + "\nSelected room: " + roomName);
        ArrayList<Reservation> reservationList = model.filterHotelReservationsByRoom(hotel, room);
        

        if (reservationList.isEmpty()){
            viewPanel.addContentInfo("\nNo reservation currently exists.");
            return;
        }
        else {
            viewPanel.addContentInfo("\nList of reservations:");
            int i = 1;
            for (Reservation reservation : reservationList) {
                viewPanel.addContentInfo(i + ".)" + "Check-in date: " + reservation.getCheckInDate() + "\n");
                viewPanel.addContentInfo("Room: " + reservation.getRoom().getName() + "\n");
                ++i;
            }
        }

        int daySelection = viewPanel.getjLDateSelected().getSelectedIndex();
        if (daySelection == -1){
            viewPanel.addContentInfo("Select a date.");
            return;
        }

        LocalDate date = LocalDate.of(2024, View.SYSTEM_MONTH, viewPanel.getjLDateSelected().getSelectedValue());
        Reservation reservation = model.getReservation(hotel.getName(), room.getName(), date);

        if (reservation == null) {
            String strReservationNullResponse[] = {
                "No reservation on room " + roomName + ".",
                "To go back to the list, please empty the View Room Textfield and press again."
            };

            viewPanel.addContentInfo(strReservationNullResponse);
            return;
        }

        
            String  guestName       = reservation.getGuestName(),
                priceBreakdown  = model.getReservationPriceBreakdown(hotel, reservation); 
            int checkInDay  = reservation.getCheckInDate().getDayOfMonth(),
                checkOutDay = reservation.getCheckOutDate().getDayOfMonth();
            double totalPrice = model.getReservationTotalPrice(hotel, reservation);

            String guiLowLevelReservationInfo[] = {
                "Guest: " + guestName,
                "Check-in: July " + checkInDay + ", 2024",
                "Check-out: July " + checkOutDay + ", 2024",
                "Price breakdown:" + priceBreakdown,
                "Total price: " + totalPrice
            };

            viewPanel.addContentInfo(guiLowLevelReservationInfo);
        
    }

    /**
     * Changes the name of the selected hotel via the GUI.
     */
    private void guiChangeName(){
        PanelManageHotel manageHotel = view.getPanelManageHotel();
        Hotel hotel = getHotelPanelSelectedHotel(manageHotel);
        boolean isHotelNull = hotel == null;
        if (isHotelNull){
            return;
        }

        String wantedName = manageHotel.getTfChangeName();
        if (model.doesHotelExist(wantedName)){
            manageHotel.setContentInfo("Name already exists. Please provide a unique name");
            return;
        } else if (isStringTrimmedEmpty(wantedName)){
            manageHotel.setContentInfo("New name is empty, please provide a name.");
            return;
        }

        if (hasUserFailedAgreement(manageHotel, "Change Name", "Cancelled name change."))
            return;
        
        manageHotel.setContentInfo("Changed the name " + hotel.getName() + " to " + wantedName);
        model.setHotelName(hotel.getName(), wantedName);
        
        guiUpdateHotelSelection();
    }

    /**
     * Adds rooms to the selected hotel via the GUI.
     */
    private void guiAddRooms(){
        PanelManageHotel manageHotel = view.getPanelManageHotel();
        Hotel hotel = getHotelPanelSelectedHotel(manageHotel);
        boolean isHotelNull = hotel == null;

        if (isHotelNull){
            return;
        }
    
        String wantedName = manageHotel.getTfAddRoomName();    
        if (isStringTrimmedEmpty(wantedName)){
            manageHotel.setContentInfo("");
            view.guiDisplayRoomSelection(manageHotel, hotel.getName(), hotel.getRoomList());
            return;
        }

        if (hasUserFailedAgreement(manageHotel, "Add Rooms", "Cancelled adding a room"))
            return;

        Result resAddRoom = model.addRoomToAHotel(hotel.getName(), wantedName, Room.ROOM_TYPE.STANDARD);
        
        if (resAddRoom.isSuccesful()){
            manageHotel.setContentInfo("Succesfully added a Room.");
            guiUpdateHotelSelection();
            return;
        }

        switch(resAddRoom.getCommonError()){
            case ER_MAX_CAPACITY:
                manageHotel.setContentInfo("Hotel is at max capacity.");
                break;
            case ER_NO_HOTEL:
                manageHotel.setContentInfo("Name of hotel does not exist.");
                break;
            case ER_NOT_UNIQUE_GIVENNAME:
                manageHotel.setContentInfo("Room name is not unique.");
                break;
            default:
                manageHotel.setContentInfo("Unknown Error: " + resAddRoom.getMessage());
                break;
        }    
    }

    /**
     * Removes rooms from the selected hotel via the GUI.
     */
    private void guiRemoveRooms(){
        PanelManageHotel manageHotel = view.getPanelManageHotel();
        Hotel hotel = getHotelPanelSelectedHotel(manageHotel);
        boolean isHotelNull = hotel == null;
        if (isHotelNull){
            return;
        }

        String wantedName = manageHotel.getTfRemoveRoomName();
        if (isStringTrimmedEmpty(wantedName)){
            manageHotel.setContentInfo("Please provide the name of the room you want to DELETE:");
            view.guiDisplayRoomSelection(manageHotel, hotel.getName(), hotel.getRoomList());
            return;
        }
;
        hasUserFailedAgreement(manageHotel, "Remove Rooms", "Cancelled remove rooms");

        Result resRemoveRoom = model.removeRoomOfHotel(hotel.getName(), wantedName);
        if (resRemoveRoom.isSuccesful()){
            manageHotel.setContentInfo("Room successfully deleted.");
            return;
        }

        switch (resRemoveRoom.getCommonError()) {
            case ER_NO_ROOM:
                manageHotel.setContentInfo("Room not found.");
                break;
            case ER_ROOM_HAS_RESERVATION:
                manageHotel.setContentInfo("Cannot delete, has a reservation.");
                break;
            default:
                manageHotel.setContentInfo("Unknown Error: " + resRemoveRoom.getMessage());
                break;
        }

    }

    /**
     * Updates the price of the selected hotel via the GUI.
     */
    private void guiUpdatePrice(){
        PanelManageHotel manageHotel = view.getPanelManageHotel();
        Hotel hotel = getHotelPanelSelectedHotel(manageHotel);

        if (doesHotelCloneNotExist(hotel)){
            return;
        }

        double price = 0.0;
        String tfPrice = manageHotel.getTfUpdatePrice();

        try {
            price = Double.parseDouble(tfPrice);
        } catch (NumberFormatException e) {
            manageHotel.setContentInfo("Please insert a valid price.");
            return;
        }

        if (isBasePriceInvalid(price)){
            manageHotel.setContentInfo("Price should be finite and greater than P100.0.");
            return;
        }

        if (hasUserFailedAgreement(manageHotel, "Update Price", "Cancelled price change."))
            return;

        Result errorState = model.setHotelBasePrice(hotel.getName(), price);

        if (errorState.isSuccesful()) {
            manageHotel.setContentInfo("Price succesfully changed");
            return;
        }

        switch (errorState.getCommonError()) {
            case ER_NO_HOTEL:
                manageHotel.setContentInfo("Hotel not found.");
                break;
            case ER_ROOM_HAS_RESERVATION:
                manageHotel.setContentInfo("At least one reservation is made for this room; modification of price is not possible.");
                break;
            case ER_LOWER_THAN_BASEPRICE:
                manageHotel.setContentInfo("Price is lower than 100. Please Change to a higher than the minimum price.");
                break;
            default:
                manageHotel.setContentInfo("Unknown Error: " + errorState.getMessage());
                break;
        }
    }

    /**
     * Removes a reservation from the selected hotel via the GUI.
     */
    private void guiRemoveReservation(){
        PanelManageHotel manageHotel = view.getPanelManageHotel();
        Hotel hotel = getHotelPanelSelectedHotel(manageHotel);        
        
        if (doesHotelCloneNotExist(hotel))
            return;

        String roomName = manageHotel.getTfRemoveReservation();
        
        if (isStringTrimmedEmpty(roomName)){
            view.displayReservationInformation(manageHotel, model.getReservations(hotel.getName()));
            return;
        }

        if (hasUserFailedAgreement(manageHotel, "Remove Reservation", "Cancelled price change."))
            return;

        JList<Integer> jList = manageHotel.getJListDates();
        Boolean emptyDateSelection = jList.getSelectedIndex() == -1;

        if (emptyDateSelection) {
            manageHotel.addContentInfo("Please select a date on the starting and end datelist.");
            return;
        }

        Result resRemoveReservation = model.removeReservation(hotel.getName(), roomName, LocalDate.of(View.SYSTEM_YEAR, View.SYSTEM_MONTH, jList.getSelectedValue())); /* placeholder */
        
        if (resRemoveReservation.isSuccesful()) {
            manageHotel.setContentInfo("Reservation successfully deleted.");
            return;
        }
        
        switch (resRemoveReservation.getCommonError()) {
            case ER_NO_HOTEL:
                manageHotel.setContentInfo("Hotel is not found");
                break;
            case ER_NO_RESERVATION:
                manageHotel.setContentInfo("Reservation is not found");
                break;
            default:
                manageHotel.setContentInfo("Unknown Error: " + resRemoveReservation.getMessage());
                break;
        }          
    }

    /**
     * Removes the selected hotel via the GUI.
     */
    private void guiRemoveHotel(){
        PanelManageHotel manageHotel = view.getPanelManageHotel();
        Hotel hotel = getHotelPanelSelectedHotel(manageHotel);
    
        if (doesHotelCloneNotExist(hotel)) 
            return;

        if (hasUserFailedAgreement(manageHotel, "Remove Hotel", "Cancelled remove hotel."))
            return;
        
        Boolean hasDelete = model.removeHotel(hotel.getName());

        if (hasDelete) {
            manageHotel.setContentInfo("Successfully removed hotel.");
            guiUpdateHotelSelection();
        } else
            manageHotel.setContentInfo("Hotel name cannot be found.");
        
    }
    
    /**
     * Books a reservation for the selected hotel via the GUI.
     */
    private void guiBookReservation() {
        PanelBookReservation bookReservation = view.getPanelBookReservation();
        Hotel hotel = getHotelPanelSelectedHotel(bookReservation);

        if (doesHotelCloneNotExist(hotel)){
            return;
        }

        ArrayList<Room> listOfAvailableRooms = new ArrayList<Room>();
        JList<Integer> jListStart = bookReservation.getJListDatesStart(),
                         jListEnd = bookReservation.getJListDatesEnd();

        // Check if empty
        Boolean emptyDateSelection = jListStart.getSelectedIndex() == -1 || jListEnd.getSelectedIndex() == -1;
        bookReservation.setContentInfo("");
        if (emptyDateSelection) {
            bookReservation.addContentInfo("Please select a date on the starting and end datelist.");
            return;
        }
        
        // Get the dates
        LocalDate startDate = LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, jListStart.getSelectedValue()), 
                    endDate = LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, jListEnd.getSelectedValue());

        bookReservation.addContentInfo("Using the dates from " + startDate + " to " + endDate);
        listOfAvailableRooms = model.getAvailableRoomsByDate(hotel, startDate);

        // Get room from dates
        String roomName = bookReservation.getRoomName();
        Room roomToReserve = model.getAvailableRoomByDateAndRoom(hotel, endDate, roomName);
        Boolean roomDoesNotExist = roomToReserve == null;
        if (roomDoesNotExist){
            bookReservation.addContentInfo("Please input a valid input room name.");
            view.displayRoomList(bookReservation, hotel, listOfAvailableRooms);
            return;
        }
        bookReservation.addContentInfo("Reserving room " + roomName + "");    

        // Get Guest name
        String guestName = bookReservation.getGuestName();
        if (isStringTrimmedEmpty(guestName)) {
            bookReservation.addContentInfo("Input a name with at least one non-white-space character.");
            return;
        }

        String strHotelInfo[] = {
            "Hotel name: " + hotel.getName(),
            "Guest name: " + guestName,
            "Check In: " +  startDate,
            "Check Out: " + endDate,
            "Room: " + roomName + " " + model.getRoomBasePricePerNight(hotel, roomToReserve) + " PHP per night"
        };

        bookReservation.addContentInfo(strHotelInfo);

        // Confirmation
        if (hasUserFailedAgreement(bookReservation, "Book Reservation", "Cancelled booking a reservation.")){
            return;
        }

        model.makeReservation(hotel.getName(), guestName, startDate,endDate, roomToReserve);
        guiUpdateHotelSelection();
    }
    
    /**
    * Applies a discount code to a reservation via the GUI.
    */
    private void guiApplyDiscountCode(){
        PanelManageHotel manageHotel = view.getPanelManageHotel();
        Hotel hotel = getHotelPanelSelectedHotel(manageHotel);        
        
        if (doesHotelCloneNotExist(hotel))
            return;

        String roomName = manageHotel.getTfDiscountCodeRoomName();
        String strDiscount = manageHotel.getTfDiscountCode();
        
        if (isStringTrimmedEmpty(roomName)){
            view.displayReservationInformation(manageHotel, model.getReservations(hotel.getName()));
            return;
        } else if (isStringTrimmedEmpty(strDiscount)){
            manageHotel.setContentInfo("Please enter a discount code!");
            return;
        }

        JList<Integer> jListReservationDate = manageHotel.getJListDates();

        // Check if empty
        Boolean emptyDateSelection = jListReservationDate.getSelectedIndex() == -1;
        if (emptyDateSelection) {
            manageHotel.addContentInfo("Please select a date on the starting and end datelist.");
            return;
        }

        // Get the dates
        LocalDate date = LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, jListReservationDate.getSelectedValue());
        Reservation grabbedReservation = model.getReservation(hotel.getName(), roomName, date);

        if (doesReservationCloneNotExist(grabbedReservation)){
            manageHotel.setContentInfo("Reservation does not exist.");
            return;
        }

        if (hasUserFailedAgreement(manageHotel, "Apply Discount Code", "Cancelled Discount."))
            return;

        Result resDiscount = model.applyDiscountCode(grabbedReservation, strDiscount);

        if (resDiscount.isSuccesful()){
            manageHotel.addContentInfo("Discount code applied.");
        } else {
            switch(resDiscount.getCommonError()){
                case ER_EXISTING_DISCOUNT:
                case ER_STAY4_GET1_INVALID:
                case ER_PAYDAY_INVALID:
                case ER_INVALID_CODE:
                    manageHotel.addContentInfo("Error, Invalid usage of code. Reason: " + resDiscount.getMessage());
                    break;
                default:
                    manageHotel.addContentInfo("Unknown Error: " + resDiscount.getMessage());
            }
        }
        
    }
    
    /**
     * Applies a remove discount code to a reservation via the GUI.
     */
    private void guiRemoveDiscountCode(){
        PanelManageHotel manageHotel = view.getPanelManageHotel();
        Hotel hotel = getHotelPanelSelectedHotel(manageHotel);        
        
        if (doesHotelCloneNotExist(hotel))
            return;

        String roomName = manageHotel.getTfDiscountCodeRoomName();
        
        if (isStringTrimmedEmpty(roomName)){
            view.displayReservationInformation(manageHotel, model.getReservations(hotel.getName()));
            return;
        }

        JList<Integer> jListReservationDate = manageHotel.getJListDates();
        manageHotel.resetContentInfo();
        // Check if empty
        Boolean emptyDateSelection = jListReservationDate.getSelectedIndex() == -1;
        if (emptyDateSelection) {
            manageHotel.addContentInfo("Please select a date on the starting and end datelist.");
            return;
        }

        // Get the dates
        LocalDate date = LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, jListReservationDate.getSelectedValue());
        Reservation grabbedReservation = model.getReservation(hotel.getName(), roomName, date);

        if (doesReservationCloneNotExist(grabbedReservation)){
            manageHotel.setContentInfo("Reservation does not exist.");
            return;
        }

        if (hasUserFailedAgreement(manageHotel, "Remove Discount Code", "Cancelled discount code."))
            return;


        model.removeDiscountCode(grabbedReservation);
        manageHotel.setContentInfo("Removed Discount!");
    }

    /**
     * Applies the date price to a hotel.
     */
    private void guiDatePriceModifier(){
        PanelManageHotel manageHotel = view.getPanelManageHotel();
        Hotel hotel = getHotelPanelSelectedHotel(manageHotel);        
        
        if (doesHotelCloneNotExist(hotel))
            return;
        
        JList<Integer> jListReservationDate = manageHotel.getJListDatePriceList();
        ArrayList<String> datePriceInfo = new ArrayList<String>();
        manageHotel.resetContentInfo();
        double priceList[] = hotel.getPriceRateList();
        int i = 0;
        for (i = 0; i < priceList.length; i++){
            datePriceInfo.add("Day " + (i+1) + ": Price " + Double.toString(priceList[i]) + "%");
        }


        // Check if empty
        Boolean emptyDateSelection = jListReservationDate.getSelectedIndex() == -1;
        if (emptyDateSelection) {
            manageHotel.addContentInfo("Please select a date on the starting datelist.");
            
            manageHotel.addContentInfo(datePriceInfo);
            return;
        }

        Double price = 0.0;
        String tfPrice = manageHotel.getTfDatePriceForTheDay();

        try {
            price = Double.parseDouble(tfPrice);
        } catch (NumberFormatException e) {
            manageHotel.setContentInfo("Please insert a valid rate multiplier.");
            manageHotel.addContentInfo(datePriceInfo);
            return;
        }

        Boolean isWithinAllowedDatePrice = (Double.isFinite(price) && price >= 0.50 && price <= 1.50);
        
        if (!isWithinAllowedDatePrice) {
            manageHotel.setContentInfo("Please insert a valid rate multiplier. Must be between 0.5 to 1.50 inclusively");
            return;
        }

        if (hasUserFailedAgreement(manageHotel, "Date Price", "Date price changed failed"))
            return;

        Result resDatePrice = model.datePriceModifier(hotel.getName(), jListReservationDate.getSelectedValue(), price);

        if (resDatePrice.isSuccesful()) {
            manageHotel.setContentInfo("Date Price successful");
        } else {
            switch (resDatePrice.getCommonError()) {
                case ER_INVALID_DAY:
                case ER_INVALID_PRICE_RATE:
                case ER_NO_HOTEL:
                    manageHotel.setContentInfo("Date Price Failed, Reason: " + resDatePrice.getMessage());
                    break;
                default:
                    manageHotel.setContentInfo("Date Price Failed, Unknonw Reason: " + resDatePrice.getMessage());
                    break;
            }
        }
    }
}
