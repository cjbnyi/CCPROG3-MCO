import java.time.LocalDate;
import java.util.ArrayList;

public class Model {

    private ArrayList<Hotel> hotelList;

    public Model() {
        this.hotelList = new ArrayList<Hotel>();
    }

    public Hotel addHotel(String name) {
        for (Hotel hotel : hotelList)
            if (hotel.getName().equals(name))
                return null;
        Hotel newHotel = new Hotel(name);
        this.hotelList.add(newHotel);
        return newHotel;
    }

    public double getHotelEstimatedEarnings(Hotel hotel) {
        double totalEarnings = 0.0;
        for (Reservation reservation : hotel.getReservationList())
            totalEarnings += reservation.getTotalPrice();
        return totalEarnings;
    }

    public int getTotalAvailableRoomsByDate(Hotel hotel, LocalDate date) { // not sure if we should do these
        return hotel.filterAvailableRoomsByDate(date).size();
    }

    public int getTotalBookedRoomsByDate(Hotel hotel, LocalDate date) {
        return hotel.getTotalRooms() - hotel.filterAvailableRoomsByDate(date).size();
    }


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


    /**
     * Gets the array of hotels.
     *
     * @return An array of hotels.
     */
    public ArrayList<Hotel> getHotelList() {
        return this.hotelList;
    }


    public Hotel getHotel(String name) {
        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(name))
                return hotel;
        return null;
    }


    /*
    public ArrayList<Room> filterAvailableRoomsByDate(Hotel hotel, LocalDate date) {
        if (hotel == null) return null;
        ArrayList<Room> availableRooms = new ArrayList<Room>(hotel.getRoomList()); // duplicates the ArrayList
        for (Reservation reservation : hotel.getReservationList()) {
            LocalDate checkInDate = reservation.getCheckInDate();
            LocalDate checkOutDate = reservation.getCheckOutDate();
            if ((date.isAfter(checkInDate) || date.isEqual(checkInDate)) &&
                    (date.isBefore(checkOutDate) || date.isEqual(checkOutDate))) {
                availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }


    public ArrayList<Reservation> filterReservationsByRoom(Hotel hotel, Room room) {
        if (room == null) return null;
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        for (Reservation reservation : hotel.getReservationList())
            if (reservation.getRoom().equals(room))
                reservationList.add(reservation);
        return reservationList;
    }
     */
}