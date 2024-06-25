package Hotel;
import java.time.LocalDate;
import java.util.ArrayList;

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


    // ### 2. SETTERS
    /**
     * Sets the name of a hotel object given its current name.
     *
     * @param currentName - current name of the hotel being renamed
     * @param newName - new name of the hotel being renamed
     * @return 0 if no hotel with the old name exists, 1 if name is not unique, 2 if renaming was successful
     */
    public int setHotelName(String currentName, String newName) {
        boolean hotelExists = false;

        for (Hotel hotel : this.hotelList) {
            String hotelName = hotel.getName();
            if (hotelName.equals(currentName))
                hotelExists = true;
            if (hotelName.equals(newName))
                return 1;
        }

        if (!hotelExists)
            return 0;

        for (Hotel hotel : this.hotelList) {
            String hotelName = hotel.getName();
            if (hotelName.equals(currentName))
                hotel.setName(newName);
        }

        return 2;
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
     * @param nameOfHotel the name of the hotel
     * @param roomToAdd the name of the room to add
     * @return true if the room was successfully added, false otherwise
     */
    public boolean addRoomToAHotel(String nameOfHotel, String roomToAdd){
        boolean hasFoundHotel = false, hasFoundRoom = false;
        Room room; 

        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(nameOfHotel)){
                hasFoundHotel = true;
                room = hotel.addRoom(roomToAdd);

                hasFoundRoom = room.equals(null) ? false : true;
            }

        return hasFoundHotel && hasFoundRoom;
    }


    /**
     * Removes a room from a hotel given the hotel's name and the room name.
     * 
     * @param nameOfHotel the name of the hotel
     * @param strRoomToRemove the name of the room to remove
     * @return 0 if the hotel was not found, 1 if room has reservation, 2 if the room was successfully removed
     */
    public int removeRoomToHotel(String nameOfHotel, String strRoomToRemove) {
        int hasFoundHotel = 0, hasSuccesfulRemoveRoom = 0;
        
        for (Hotel hotel: this.hotelList){
            if (hotel.getName().equals(nameOfHotel)){
                hasFoundHotel = 0;
                hasSuccesfulRemoveRoom = hotel.removeRoom(strRoomToRemove);
            } 
        }

        if (hasFoundHotel == 0)
            return hasFoundHotel;
        else 
            return hasSuccesfulRemoveRoom;
    }

    
    /**
     * Updates the price of a hotel when no reservations are made.
     * 
     * @param strHotel
     * @param setPrice
     * @return 0 - hotel is not found, 1 - reservation is not empty, 2 - price is lower than 100, 3 - succesfully changed price
    */
    public int updatePriceOfAHotel(String strHotel, double setPrice){
        int hasPriceChanged = 0;

        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(strHotel)){
                hasPriceChanged = hotel.updatePrice(setPrice) + 1;
            }

        return hasPriceChanged;
    }

    
    /**
     * Removed Reservations based on String names.
     * 
     * @param strHotel
     * @param strRoomName
     * @return 0 - if hotel not found, 1 - if reservation not found, 2 - if successful.
    */
    public int removeReservations(String strHotel, String strRoomName){
        int hasRemoved = 0;
        for (Hotel hotel : this.hotelList){
            if (hotel.getName().equals(strHotel)){
                hasRemoved = hotel.removeReservation(strRoomName) ? 2 : 1;
            }
        }
        return hasRemoved;
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