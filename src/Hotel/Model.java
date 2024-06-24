package Hotel;
import java.time.LocalDate;
import java.util.ArrayList;

public class Model {

    private ArrayList<Hotel> hotelList;

    public Model() {
        this.hotelList = new ArrayList<Hotel>();
    }

    // ### 1. GETTERS

    public int getTotalAvailableRoomsByDate(Hotel hotel, LocalDate date) { // not sure if we should do these
        return hotel.filterAvailableRoomsByDate(date).size();
    }

    public int getTotalBookedRoomsByDate(Hotel hotel, LocalDate date) {
        return hotel.getTotalRooms() - hotel.filterAvailableRoomsByDate(date).size();
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
        // find a way to use confirmAction()

        for (Hotel hotel : this.hotelList) {
            String hotelName = hotel.getName();
            if (hotelName.equals(currentName))
                hotel.setName(newName);
        }

        return 2;
    }


    public boolean addHotel(String name) {
        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(name))
                return false;
        Hotel newHotel = new Hotel(name);
        this.hotelList.add(newHotel);
        return true;
    }

    
    /**
     * Adds a room to a hotel given the hotel's name and the room.
     * @param nameOfHotel - hotel to add rooms
     * @return false if not added a room succesful, true if it has. 
     */
    public boolean addRoomToAHotel(String nameOfHotel, String roomToAdd){
        boolean hasFoundHotel = false, hasSuccesfulAddedRoom = false;
        
        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(nameOfHotel)){
                hasFoundHotel = true;
                hasSuccesfulAddedRoom = hotel.addRoom(roomToAdd);
            }

        return hasFoundHotel && hasSuccesfulAddedRoom;
    }

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

    public ArrayList<Room> getRoomListOfAHotel(String nameOfHotel){
        ArrayList<Room> listOfRooms = null;

        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(nameOfHotel)){
                listOfRooms = hotel.getRoomList();
            }
        return listOfRooms;
    }
    
    public boolean doesHotelExist(String nameOfHotel){
        boolean hasFoundHotel = false;
        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(nameOfHotel)){
                hasFoundHotel = true;
            }

        return hasFoundHotel;
    }

    /**
     * Gets the array of hotels.
     *
     * @return An array of hotels.
     */
    public ArrayList<Hotel> getHotelList() {
        return this.hotelList;
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