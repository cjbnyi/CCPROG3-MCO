package Hotel;
import java.time.LocalDate;
import java.util.ArrayList;

import static Hotel.Result.COMMON_ERRORS.*;
import static Hotel.Discount.DISCOUNT_CODES.*;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * The Model class manages a list of hotels and provides methods to manipulate hotel data.
 */
public class Model {

    private ArrayList<Hotel> hotelList;


    /**
     * Constructs a Model object with an empty list of hotels.
     */
    public Model() {
        this.hotelList = new ArrayList<Hotel>();
    }


    // ### 1. GETTERS
    // TODO: Modify such that no object references are returned.
    /**
     * Gets the array of hotels.
     *
     * @return An array of hotels.
     */
    public ArrayList<Hotel> getHotelList() {
        // return this.hotelList;
        return new ArrayList<Hotel>(this.hotelList);
    }


    /**
     * Gets an existing hotel with a specified name.
     *
     * @param name
     * @return The instance of the hotel with the corresponding name; otherwise, null.
     */
    public Hotel getHotel(String name) {
        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(name))
                return hotel;
        return null;
    }


    /**
     * Calculates the estimated earnings for a given hotel.
     * 
     * @param hotel the hotel for which to calculate estimated earnings
     * @return the total estimated earnings for the hotel
     */
    public double getHotelEstimatedEarnings(Hotel hotel) {
        double totalEarnings = 0.0;
        for (Reservation reservation : hotel.getReservationList())
            totalEarnings += reservation.getTotalPrice();
        return totalEarnings;
    }


    /**
     * Returns the total number of available rooms in a hotel on a specific date.
     * 
     * @param hotel the hotel to check
     * @param date the date to check availability
     * @return the total number of available rooms on the specified date
     */
    public int getTotalAvailableRoomsByDate(Hotel hotel, LocalDate date) { // not sure if we should do these
        return hotel.filterAvailableRoomsByDate(date).size();
    }

    /**
     * Returns the Reservation List of a Hotel
     * 
     * @param HotelName
     * @return
    */
    public ArrayList<Reservation> getReservations(String HotelName){
        ArrayList<Reservation> listOfReservations = null;

        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(HotelName)){
                listOfReservations = hotel.getReservationList();
            }
        return listOfReservations;
    }


    /**
     * Returns the list of rooms in a specified hotel.
     *
     * @param nameOfHotel the name of the hotel
     * @return the list of rooms in the hotel
     */
    public ArrayList<Room> getRoomListOfAHotel(String nameOfHotel){

        ArrayList<Room> listOfRooms = null;

        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(nameOfHotel)){
                listOfRooms = hotel.getRoomList();
            }
        return listOfRooms;
    }


    public ArrayList<Room> getListOfTotalUnreservedRoomsByDate(Hotel hotel,LocalDate date){
        return hotel.filterAvailableRoomsByDate(date);
    }


    public double getRoomBasePricePerNight(Hotel hotel, Room room) {

        double basePricePerNight = hotel.getBasePricePerNight();

        return switch(room) {
            case StandardRoom _ -> basePricePerNight * hotel.getStandardMultiplier();
            case DeluxeRoom _ -> basePricePerNight * hotel.getDeluxeMultiplier();
            case ExecutiveRoom _ -> basePricePerNight * hotel.getExecutiveMultiplier();
            case null, default -> -1;
        };
    }


    // ### 2. SETTERS
    /**
     * Sets the name of a hotel object given its current name.
     *
     * @param currentName - current name of the hotel being renamed
     * @param newName - new name of the hotel being renamed
     * @return 
     * <pre>
     * A Result object describing the outcome and success of the function, if Result is not successful, it could have the possible reasons:
     *  - "Old hotel name exists."
     *  - "Given name is not unique within list."
     * </pre>
     */
    public Result setHotelName(String currentName, String newName) {

        boolean hasExistingSameHotelName = false;

        for (Hotel hotel : this.hotelList) {
            String hotelName = hotel.getName();
            
            if (hotelName.equals(currentName))
                hasExistingSameHotelName = true;

            if (hotelName.equals(newName))
                return new Result(ER_NOT_UNIQUE_GIVENNAME);
        }

        if (hasExistingSameHotelName)
            return new Result(ER_EXISTING_OLD_NAME);

        for (Hotel hotel : this.hotelList) {
            String hotelName = hotel.getName();
            if (hotelName.equals(currentName))
                hotel.setName(newName);
        }

        return new Result(ER_SUCCESSFUL);
    }


    // ### 3. FILTERS
    /**
     * Checks if a hotel with the specified name exists.
     *
     * @param nameOfHotel the name of the hotel to check
     * @return true if the hotel exists, false otherwise
     */
    public boolean doesHotelExist(String nameOfHotel){
        boolean hasFoundHotel = false;
        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(nameOfHotel)){
                hasFoundHotel = true;
            }

        return hasFoundHotel;
    }


    // ### 4. MODIFIERS
    /**
     * Adds a new hotel with the specified name.
     * 
     * @param name the name of the new hotel
     * @return the newly created hotel, or null if a hotel with the given name already exists
     */
    public Hotel addHotel(String name) {
        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(name))
                return null;
        Hotel newHotel = new Hotel(name);
        this.hotelList.add(newHotel);
        return newHotel;
    }


    /**
     * Adds a room to a hotel given the hotel's name and the room name.
     * 
     * @param nameOfHotel the name of the hotel to which the room should be added
     * @param roomToAdd the name of the room to add
     * 
     * @return 
     * <pre>
     * a Result object indicating the outcome of the operation, if it is not successful, then it has the following possible messages:
     *         - "Hotel is at Max." if the hotel already has 50 rooms;
     *         - "Name of hotel does not exist." if the specified hotel is not found;
     *         - "Room name not unique." If the room name is not unique in the list.
     * <pre/>
     */
    public Result addRoomToAHotel(String nameOfHotel, String roomToAdd){
        boolean hasFoundHotel = false, hasFoundRoom = false;
        Room room; 

        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(nameOfHotel)){

                hasFoundHotel = true;
                
                if (hotel.getRoomList().size() >= Hotel.MAX_ROOMS){
                    return new Result(ER_MAX_CAPACITY);
                }

                room = hotel.addRoom(roomToAdd);
                hasFoundRoom = (null == room) ? false : true;
            }

        if (!hasFoundHotel)
            return new Result(ER_NO_HOTEL);
        else if (!hasFoundRoom)
            return new Result(ER_NOT_UNIQUE_GIVENNAME);
        else 
            return new Result(ER_SUCCESSFUL);   
    }


    public boolean makeReservation(String nameOfHotel, String GuestName, LocalDate checkInDate, LocalDate checkOutDate, Room room){
        boolean hasMadeReservation = false;
        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(nameOfHotel)){
                hasMadeReservation = hotel.makeReservation(GuestName, checkInDate, checkOutDate, room);
            }
        return hasMadeReservation;
    }


    /**
     * Removes a specified room from a specified hotel.
     * 
     * @param nameOfHotel the name of the hotel from which to remove the room
     * @param strRoomToRemove the name of the room to remove
     * @return 
     * <pre>
     * a Result object indicating the outcome of the operation:
     *      - "Hotel was not found." if the hotel with the specified name is not found in the hotel list.
     *      - "Room has a reservation." if the room has one or more reservations
     *      - "Room does not exist." if the room with the specified name is not found
     *      - "Removal successful." if the room was successfully removed. Has a isSuccesful Boolean of true
     * <pre/>
     */
    public Result removeRoomToHotel(String nameOfHotel, String strRoomToRemove) {
        Result resRemoveRoom;
        for (Hotel hotel: this.hotelList){
            if (hotel.getName().equals(nameOfHotel)){
                resRemoveRoom = hotel.removeRoom(strRoomToRemove);
                return resRemoveRoom;
            } 
        }

        return new Result(ER_NO_HOTEL);
    }

    
    /**
     * Updates the price of a hotel when no reservations are made.
     * 
     * @param strHotel the name of the hotel for which to update the price
     * @param setPrice the new base price to set for each room in the specified hotel
     * @return  
     * <pre>
     * a Result object indicating the outcome of the operation:
     *      - "Hotel was not found." if the hotel with the specified name is not found in the hotel list.
     *      - "Reservation list is empty." if there are existing reservations
     *      - "Price is lower than 100." if the provided price is less than 100
     *      - "Base price set." if the price was successfully updated
     * 
     * <pre/>
    */
    public Result updatePriceOfAHotel(String strHotel, double setPrice){
        Result resUpdatePrice;
        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(strHotel)){
                resUpdatePrice = hotel.updatePrice(setPrice);
                return resUpdatePrice;
            } 

        return new Result(ER_NO_HOTEL);
    }

    
    /**
     * Removes a reservation from a specified room in a specified hotel.
     * 
     * @param strHotel the name of the hotel where the reservation should be removed
     * @param strRoomName the name of the room from which the reservation should be removed
     * @return 
     * <pre>
     * a Result object indicating the outcome of the operation:
     *         - "Hotel was not found." if the specified hotel is not found
     *         - "Reservation not found." if the specified reservation is not found in the hotel
     *         - "Reservation was successful." if the specified reservation was done succesful. Note that is has a isSuccesful boolean of true. 
     * </pre>  
     */
    public Result removeReservations(String strHotel, String strRoomName){
        // Assume not found.
        Boolean hasRemoveReservation = false;
        
        for (Hotel hotel : this.hotelList) {
            if (hotel.getName().equals(strHotel)) {
                hasRemoveReservation = hotel.removeReservation(strRoomName);
                return new Result(hasRemoveReservation ? ER_SUCCESSFUL : ER_NO_RESERVATION);
            }
        }
        return new Result(ER_NO_HOTEL);
    }


    /**
     * Removes a Hotel
     * @param strHotel
     * @return true if removed succesful false, if not.
    */
    public boolean removeHotel(String strHotel){
        boolean hasRemovedHotel = false;

        for (Hotel hotel : this.hotelList) {
            if (hotel.getName().equals(strHotel)){
                this.hotelList.remove(hotel);
                hasRemovedHotel = true;
            }
        }

        return hasRemovedHotel;
    }


    // ### DISCOUNT-RELATED METHODS

    // TODO: Verify
    /**
     * Sets the discount code of a reservation if the code is applicable.
     * @param reservation the reservation being discounted
     * @param discountCode the discount code being applied
     */
    public Result applyDiscountCode(Reservation reservation, String discountCode) {

        if (reservation.getAppliedDiscountCode() != null) {
            return new Result(ER_EXISTING_DISCOUNT);
        }

        if (discountCode.equals(I_WORK_HERE.getStringID())) {
            reservation.setAppliedDiscountCode(I_WORK_HERE);
        } else if (discountCode.equals(STAY4_GET1.getStringID())) {
            if (getNumDaysOfReservation(reservation) < 5) {
                return new Result(ER_STAY4_GET1_INVALID);
            }
            reservation.setAppliedDiscountCode(STAY4_GET1);
        } else if (discountCode.equals(PAYDAY.getStringID())) {

            int checkInDay      = reservation.getCheckInDate().getDayOfMonth();
            int checkOutDay     = reservation.getCheckOutDate().getDayOfMonth();
            boolean has15th     = checkInDay <= 15 && 15 < checkOutDay;
            boolean has30th     = checkInDay <= 30 && 30 < checkOutDay;

            if (!(has15th || has30th)) {
                return new Result(ER_PAYDAY_INVALID);
            }
            reservation.setAppliedDiscountCode(PAYDAY);
        } else {
            return new Result(ER_INVALID_CODE);
        }

        return new Result(ER_SUCCESSFUL);
    }


    /**
     * Removes a reservation's existing applied discount code.
     */
    public void removeDiscountCode(Reservation reservation) {
        reservation.setAppliedDiscountCode(null);
    }

    // ### RESERVATION-RELATED METHODS

    /**
     * Returns the number of days of a reservation.
     *
     * @return the number of days of the reservation
     */
    public int getNumDaysOfReservation(Reservation reservation) {
        return (int) DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
    }

    /**
     * Returns the total price of a reservation.
     *
     * @param hotel the hotel in which the reservation was made
     * @param reservation the reservation made
     */
    public double getReservationTotalPrice(Hotel hotel, Reservation reservation) {

        double basePricePerNight = hotel.getBasePricePerNight();
        double roomTypeMultiplier;
        int checkInDay = reservation.getCheckInDate().getDayOfMonth();
        int checkOutDay = reservation.getCheckOutDate().getDayOfMonth();
        double totalPrice = 0f;
        double priceRate;
        double discount;
        Discount.DISCOUNT_CODES appliedDiscountCode = reservation.getAppliedDiscountCode();

        roomTypeMultiplier = switch(reservation.getRoom()) {
            case StandardRoom _ -> hotel.getStandardMultiplier();
            case DeluxeRoom _ -> hotel.getDeluxeMultiplier();
            case ExecutiveRoom _ -> hotel.getExecutiveMultiplier();
            case null, default -> 0f;
        };

        for (int i = checkInDay; i < checkOutDay; i++) {
            priceRate = hotel.getPriceRateForADay(i - 1);
            totalPrice += basePricePerNight * roomTypeMultiplier * priceRate;
        }

        discount = switch(appliedDiscountCode) {
            case Discount.DISCOUNT_CODES.I_WORK_HERE ->
                    totalPrice * 0.1;
            case Discount.DISCOUNT_CODES.STAY4_GET1 ->
                    basePricePerNight * roomTypeMultiplier * hotel.getPriceRateForADay(checkInDay - 1);
            case Discount.DISCOUNT_CODES.PAYDAY ->
                    totalPrice * 0.07;
            case null -> 0f;
        };

        return totalPrice - discount;
    }
}