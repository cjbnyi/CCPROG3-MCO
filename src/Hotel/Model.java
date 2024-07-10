package Hotel;
import java.time.LocalDate;
import java.util.ArrayList;

import static Hotel.Result.COMMON_ERRORS.*;

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
    /**
     * Gets the array of hotels.
     *
     * @return An array of hotels.
     */
    public ArrayList<Hotel> getHotelList() {
        return this.hotelList;
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

    // ### 2. SETTERS


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
}