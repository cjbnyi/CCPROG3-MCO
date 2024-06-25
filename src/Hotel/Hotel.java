package Hotel;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The Hotel class represents a hotel with rooms and reservations.
 */
public class Hotel {
    private String name;
    private ArrayList<Room> roomList;
    private ArrayList<Reservation> reservationList;
    private static final int MAX_ROOMS = 50;
    private static final int DAYS_IN_MONTH = 31;
    
    /**
     * Constructs a Hotel object with a given name.
     * Initializes roomList and reservationList.
     * Also initializes rooms in the hotel.
     * 
     * @param name the name of the hotel
     */
    public Hotel(String name) {
        this.name = name;
        this.roomList = new ArrayList<Room>();
        this.reservationList = new ArrayList<Reservation>();
        this.initRooms();
    }

    /**
     * Initializes rooms in the hotel.
     * Rooms are named from 'A1' to 'J5'.
     */
    private void initRooms() {
        String roomName;
        for (char letter = 'A'; letter <= 'J'; letter++) {
            for (int number = 1; number <= 5; number++) {
                roomName = "" + letter + number;
                Room newRoom = new Room(roomName);
                this.roomList.add(newRoom);
            }
        }
    }

    // ### 1. GETTERS
    
    
    /**
     * Gets the name of the hotel.
     * 
     * @return the name of the hotel
     */
    public String               getName() {
        return this.name;
    }

    /**
     * Gets the list of rooms in the hotel.
     * 
     * @return the list of rooms
     */
    public ArrayList<Room>      getRoomList() {
        return this.roomList;
    }

    /**
     * Gets the list of reservations in the hotel.
     * 
     * @return the list of reservations
     */
    public ArrayList<Reservation> getReservationList() {
        return this.reservationList;
    }

    /**
     * Gets the total number of rooms in the hotel.
     * 
     * @return the total number of rooms
     */
    public int getTotalRooms() {
        return this.roomList.size();
    }

    /**
     * Calculates the estimated earnings of the hotel based on reservations.
     * 
     * @return the estimated earnings of the hotel
     */
    public double               getEstimatedEarnings() {
        double totalEarnings = 0.0;
        for (Reservation reservation : reservationList)
            totalEarnings += reservation.getTotalPrice();
        return totalEarnings;
    }

    /**
     * Retrieves information about a specific room in the hotel.
     * 
     * @param roomName the name of the room
     * @return information about the room, including base price and available dates
     */
    public String               getRoomInfo(String roomName) {
        for (Room room : this.roomList) {
            if (room.getName().equals(roomName)) {
                StringBuilder roomInfo = new StringBuilder (
                    "Name: " + room.getName() + "\n" +
                    "Base price per night: " + room.getBasePricePerNight() + "\n" +
                    "Available dates:"
                );
                ArrayList<LocalDate> availableDates = getRoomAvailabilityThisMonth(room);
                for (LocalDate date : availableDates)
                    roomInfo.append("\n").append(date.toString());
                return roomInfo.toString();
            }
        }
        return "Invalid room name.";
    }

    /**
     * Determines the availability of a room in the current month.
     * 
     * @param room the room to check availability for
     * @return a list of available dates for the room in the current month
     */
    public ArrayList<LocalDate> getRoomAvailabilityThisMonth(Room room) {
        ArrayList<LocalDate> availableDates = new ArrayList<LocalDate>();
        ArrayList<Reservation> roomReservations = filterReservationsByRoom(room);

        for (int i = 0; i < Hotel.DAYS_IN_MONTH; i++)
            availableDates.add(LocalDate.of(View.SYSTEM_YEAR, View.SYSTEM_MONTH, 1 + i));

        for (Reservation reservation : roomReservations)
            for (int j = 0; j < reservation.getNumDays(); j++)
                availableDates.remove(reservation.getCheckInDate().plusDays(j));

        return availableDates;
    }

    // ### 2. SETTERS

    /**
     * Sets the name of the hotel.
     * 
     * @param name the new name for the hotel
     */
    public void     setName(String name) {
        this.name = name;
    }

    // ### 3. MODIFIERS 
    // TODO: Implement makeReservation()
    /**
     * Makes a reservation for a room.
     * 
     * @return -1 (placeholder for future implementation)
     */
    public double makeReservation() {
        return -1;
    }

    /**
     * Removes a reservation.
     * @param nameRoom
     * @return true - removed room, false - has not found room to remove.
    */
    public boolean removeReservation(String nameRoom) {
        for (Reservation reservation : this.reservationList) {
            if (reservation.getRoom().getName().equals(nameRoom)) {
                this.reservationList.remove(reservation);
                return true;
            }
        }
        return false;
    }



    /**
     * Adds a room.
     * @param name
     * @return true if adds a room with a unique name, false if not.
    */
    public Room addRoom(String name) {
        for (Room room : this.roomList)
            if (room.getName().equals(name))
                return null;
        Room room = new Room(name);
        this.roomList.add(room);
        return room;
    }

    /**
     * 
     * @param price
     * @return 0 - if reservation list is empty, 1 - if price is low, 2 - if base price is set
    */
    public int updatePrice(double price) {
        if (this.reservationList.isEmpty())
            return 0;
        
        if (price < 100)
            return 1;

        for (Room room: this.roomList){
            room.setBasePricePerNight(price);
        }

        return 2;
    }


    // ### 2.1. FILTERS
    /**
     * Filters and returns a list of available rooms by a specified date.
     * 
     * @param date the date to check availability for
     * @return a list of available rooms on the specified date
     */
    public ArrayList<Room>          filterAvailableRoomsByDate(LocalDate date) {
        ArrayList<Room> availableRooms = new ArrayList<Room>(this.roomList); // duplicates the ArrayList
        for (Reservation reservation : this.reservationList) {
            LocalDate checkInDate = reservation.getCheckInDate();
            LocalDate checkOutDate = reservation.getCheckOutDate();
            if ((date.isAfter(checkInDate) || date.isEqual(checkInDate)) &&
                (date.isBefore(checkOutDate) || date.isEqual(checkOutDate))) {
                availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }

    /**
     * Filters and returns a list of reservations for a specific room.
     * 
     * @param room the room to filter reservations for
     * @return a list of reservations for the specified room
     */
    public ArrayList<Reservation>   filterReservationsByRoom(Room room) {
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        for (Reservation reservation : this.reservationList)
            if (reservation.getRoom().equals(room))
                reservationList.add(reservation);
        return reservationList;
    }

    /**
     * Removes a room from the hotel's roomList[] given its name.
     *
     * @param name - name of the room to be removed from the hotel's roomList[]
     * @return 0 - room does not exist; 1 - room has a reservation; 2 - removal successful
     */
    public int removeRoom(String name) {
        for (Room room : roomList) {
            if (room.getName().equals(name)) {
                if (filterReservationsByRoom(room).isEmpty()) {
                    this.roomList.remove(room);
                    return 2;
                } else {
                    return 1;
                }
            }
        }
        return 0;
    }
        
    /**
     * Retrieves a room from the hotel's roomList given its name.
     * 
     * @param roomName the name of the room to retrieve
     * @return the room object with the specified name, or null if no such room exists
     */
    public Room getRoom(String roomName) {
        for (Room room : this.roomList)
            if (room.getName().equals(roomName))
                return room;
        return null;
    }

    /**
     * Retrieves a reservation for a specific room and check-in date.
     * 
     * @param room the room to check for reservations
     * @param checkInDate the check-in date of the reservation
     * @return the reservation object, or null if no reservation exists for the specified room and date
     */
    public Reservation getReservation(Room room, LocalDate checkInDate) {
        ArrayList<Reservation> reservationList = filterReservationsByRoom(room);
        for (Reservation reservation : reservationList)
            if (reservation.getCheckInDate().equals(checkInDate))
                return reservation;
        return null;
    }

    /**
     * Retrieves information about a specific room in the hotel.
     * 
     * @param room the room to retrieve information for
     * @return information about the room, including base price and available dates
     */
    public String getRoomInfo(Room room) { // refactor this
        StringBuilder roomInfo = new StringBuilder (
            "Room name: " + room.getName() + "\n\n" +
            "Base price per night: " + room.getBasePricePerNight() + "\n\n" +
            "Available dates:"
        );
        ArrayList<LocalDate> availableDates = getRoomAvailabilityThisMonth(room);
        for (LocalDate date : availableDates)
            roomInfo.append("\n").append(date.toString());
        return roomInfo.toString();
    }

    /**
     * Sets the base price per night for all rooms in the hotel if there are no reservations.
     * 
     * @param newPrice the new base price per night
     * @return true if the base price was successfully set, false otherwise
     */
    public boolean setRoomBasePrice(double newPrice) {
        if (newPrice <= 0 || !this.reservationList.isEmpty())
            return false;
        for (Room room : this.roomList)
            room.setBasePricePerNight(newPrice);
        return true;
    }


}