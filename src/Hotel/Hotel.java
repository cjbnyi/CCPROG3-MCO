package Hotel;
import java.time.LocalDate;
import java.util.ArrayList;

import static Hotel.Result.COMMON_ERRORS.*;
/**
 * The Hotel class represents a hotel with rooms and reservations.
 */
public class Hotel {

    public static final int MAX_ROOMS = 50;
    private static final int DAYS_IN_MONTH = 31;

    private String name;
    private ArrayList<Room> roomList;
    private ArrayList<Reservation> reservationList;
    private double[] priceRateList;

    private double basePricePerNight = 1299.0;
    private double standardMultiplier = 1.0;
    private double deluxeMultiplier = 1.2;
    private double executiveMultiplier = 1.35;


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
        this.priceRateList = new double[DAYS_IN_MONTH];
        this.initRooms();
        this.initPriceRates();
    }


    public Hotel(Hotel h) {
        this.name = h.name;
        this.roomList = h.roomList;
        this.reservationList = h.reservationList;
        this.basePricePerNight = h.basePricePerNight;
        this.standardMultiplier = h.standardMultiplier;
        this.deluxeMultiplier = h.deluxeMultiplier;
        this.executiveMultiplier = h.executiveMultiplier;
    }


    // ### 1. HELPERS
    /**
     * Initializes the price rates of rooms in the hotel.
     * Price rates are set to a default of 1.0.
     */
    private void initPriceRates() {
        for (int i = 0; i < DAYS_IN_MONTH; i++) {
            priceRateList[i] = 1.0;
        }
    }


    /**
     * Initializes rooms in the hotel.
     * Rooms are named from 'A0' to 'E9'.
     */
    private void initRooms() {
        String roomName;
        Room newRoom;
        for (char letter = 'A'; letter <= 'E'; letter++) {
            for (int number = 0; number <= 9; number++) {
                roomName = "" + letter + number;
                if (letter <= 'B') {
                    newRoom = new StandardRoom(roomName);
                } else if (letter <= 'D') {
                    newRoom = new DeluxeRoom(roomName);
                } else {
                    newRoom = new ExecutiveRoom(roomName);
                }
                this.roomList.add(newRoom);
            }
        }
    }


    /**
     * Creates a copy of a room instance.
     *
     * @return a copy of the room instance
     */
    private Room createRoomCopy(Room room) {
        return switch (room) {
            case StandardRoom _ -> new StandardRoom(room);
            case DeluxeRoom _ -> new DeluxeRoom(room);
            case ExecutiveRoom _ -> new ExecutiveRoom(room);
            case null, default -> null;
        };
    }


    // ### 2. GETTERS
    /**
     * Gets the name of the hotel.
     * 
     * @return the name of the hotel
     */
    public String getName() {
        return this.name;
    }


    /**
     * Gets the list of rooms in the hotel.
     * 
     * @return the list of rooms
     */
    public ArrayList<Room> getRoomList() {
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
     * Gets a list of price rates for the month.
     * @return the list of price rates for the month
     */
    public double[] getPriceRateList() {
        return this.priceRateList.clone();
    }


    /**
     *
     */
    public double getBasePricePerNight() {
        return this.basePricePerNight;
    }


    /**
     *
     */
    public double getStandardMultiplier() {
        return this.standardMultiplier;
    }


    /**
     *
     */
    public double getDeluxeMultiplier() {
        return deluxeMultiplier;
    }


    /**
     *
     */
    public double getExecutiveMultiplier() {
        return executiveMultiplier;
    }


    // ### 3. FILTERS
    /**
     * Gets the total number of rooms in the hotel.
     * 
     * @return the total number of rooms
     */
    public int getTotalRooms() {
        return this.roomList.size();
    }


    /**
     * Determines the availability of a room in the current month.
     * 
     * @param room the room to check availability for
     * @return a list of available dates for the room in the current month
     */
    public ArrayList<LocalDate> getRoomAvailabilityThisMonth(Room room) {

        ArrayList<LocalDate> availableDates = new ArrayList<LocalDate>();
        ArrayList<Reservation> roomReservations = filterReservationsByARoom(room);

        for (int i = 0; i < Hotel.DAYS_IN_MONTH; i++)
            availableDates.add(LocalDate.of(View.SYSTEM_YEAR, View.SYSTEM_MONTH, 1 + i));

        for (Reservation reservation : roomReservations)
            for (int j = 0; j < reservation.getNumDays(); j++)
                availableDates.remove(reservation.getCheckInDate().plusDays(j));

        return availableDates;
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
                return createRoomCopy(room);
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
        ArrayList<Reservation> reservationList = filterReservationsByARoom(room);
        for (Reservation reservation : reservationList)
            if (reservation.getCheckInDate().equals(checkInDate))
                return new Reservation(reservation);
        return null;
    }


    /**
     * Gets the price rate for a particular day of the month.
     *
     * @return the price rate for a particular day of the month
     */
    public double getPriceRateForADay(int day) {
        return this.priceRateList[day-1];
    }


    // ### 2. SETTERS
    /**
     * Sets the name of the hotel.
     * 
     * @param name the new name for the hotel
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Sets the price rate for a day.
     *
     * @return a Result object describing the outcome
     */
    public Result setPriceRateForADay(int day, double newPriceRate) {

        if (!(day >= 1 && day <= 31)) {
            return new Result(ER_INVALID_DAY);
        }

        if (!(newPriceRate > 0)) {
            return new Result(ER_INVALID_PRICE_RATE);
        }

        this.priceRateList[day-1] = newPriceRate;
        return new Result(ER_SUCCESSFUL);
    }


    /**
     * Sets the base price per night for all rooms in the hotel if there are no reservations.
     *
     * @param newBasePrice the new base price per night
     * @return true if the base price was successfully set, false otherwise
     */
    public Result setBasePrice(double newBasePrice) {
        if (newBasePrice <= 100) {
            return new Result(ER_LOWER_THAN_BASEPRICE);
        }
        if (!this.reservationList.isEmpty()) {
            return new Result(ER_HOTEL_HAS_RESERVATION);
        }

        this.basePricePerNight = newBasePrice;
        return new Result(ER_SUCCESSFUL);
    }


    // ### 3. MODIFIERS
    /** TODO: Continue here.
     * Makes a Reservation 
     * @param GuestName - New Guest Name
     * @param checkInDate - check in date
     * @param checkOutDate  - check out date
     * @param room - Room to rserve
     * @return whether if its succesful or not.
    */
    public boolean makeReservation(String GuestName, LocalDate checkInDate, LocalDate checkOutDate, Room room) {

        Reservation newReservation = new Reservation(GuestName, checkInDate, checkOutDate, room);
        Boolean hasNoCoincidingReservation = false;
        ArrayList<Room> availableRooms = new ArrayList<Room>(this.roomList); // duplicates the ArrayList
        
        for (Reservation reservation : this.reservationList) {
            LocalDate reservationCheckInDate = reservation.getCheckInDate();
            LocalDate reservationCheckOutDate = reservation.getCheckOutDate();
            if ((checkInDate.isAfter(reservationCheckInDate) || checkInDate.isEqual(reservationCheckInDate)) &&
                (checkOutDate.isBefore(reservationCheckOutDate))) {
                availableRooms.remove(reservation.getRoom());
            }
        }
        
        for (Room availableRoom : availableRooms) {
            if (availableRoom.equals(room)) {
                hasNoCoincidingReservation = true;
                this.reservationList.add(newReservation);
            }
        }

        return hasNoCoincidingReservation;
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
     * @param newRoom room to be added
     * @return ER_SUCCESSFUL if room is successfully added, some corresponding error if not
    */
    public Result addRoom(Room newRoom) {

        if (this.roomList.size() >= MAX_ROOMS)
            return new Result(ER_MAX_CAPACITY);

        for (Room room : this.roomList)
            if (room.getName().equals(name))
                return new Result(ER_EXISTING_OLD_NAME);

        /* the room can now be added */
        this.roomList.add(newRoom);
        return new Result(ER_SUCCESSFUL);
    }


    /**
     * Updates the base price per night for all rooms in the room list.
     * 
     * @param price the new base price to set for each room
     * @return 
     * <pre>
     * a Result object indicating the outcome of the operation:
     *      - "Reservation list is empty." if there are existing reservations
     *      - "Price is lower than 100." if the provided price is less than 100
     *      - "Base price set." if the price was successfully updated
     * <pre/>
     */
    public Result updatePrice(double price) {

        if (!this.reservationList.isEmpty())
            return new Result(ER_EMPTY_RESERVATION_LIST);
        
        if (price < 100)
            return new Result(ER_LOWER_THAN_BASEPRICE);

        this.basePricePerNight = price;

        return new Result("Base price set.", true);
    }


    // ### 4. FILTERS
    /**
     * Filters and returns a list of available rooms by a specified date.
     * 
     * @param date the date to check availability for
     * @return a list of available rooms on the specified date
     */
    public ArrayList<Room> filterAvailableRoomsByDate(LocalDate date) {
        ArrayList<Room> availableRooms = new ArrayList<Room>(this.roomList); // duplicates the ArrayList
        for (Reservation reservation : this.reservationList) {
            LocalDate checkInDate = reservation.getCheckInDate();
            LocalDate checkOutDate = reservation.getCheckOutDate();
            if ((date.isAfter(checkInDate) || date.isEqual(checkInDate)) &&
                (date.isBefore(checkOutDate))) {
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
    public ArrayList<Reservation>   filterReservationsByARoom(Room room) {
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        for (Reservation reservation : this.reservationList)
            if (reservation.getRoom().equals(room))
                reservationList.add(reservation);
        return reservationList;
    }


    /**
     * Filters and returns a list of existing standard room reservations
     *
     * @return a list of existing standard room reservations
     */
    public ArrayList<Reservation> filterStandardRoomReservations() {
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        for (Reservation r : this.reservationList)
            if (r.getRoom() instanceof StandardRoom)
                reservationList.add(r);
        return reservationList;
    }


    /**
     * Filters and returns a list of existing deluxe room reservations
     *
     * @return a list of existing deluxe room reservations
     */
    public ArrayList<Reservation> filterDeluxeRoomReservations() {
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        for (Reservation r : this.reservationList)
            if (r.getRoom() instanceof DeluxeRoom)
                reservationList.add(r);
        return reservationList;
    }


    /**
     * Filters and returns a list of existing executive room reservations
     *
     * @return a list of existing executive room reservations
     */
    public ArrayList<Reservation> filterExecutiveRoomReservations() {
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        for (Reservation r : this.reservationList)
            if (r.getRoom() instanceof ExecutiveRoom)
                reservationList.add(r);
        return reservationList;
    }


    /**
     * Removes a room from the room list given the room's name.
     * 
     * @param name the name of the room to remove
     * @return 
     * <pre> 
     * a Result object indicating the outcome of the operation:
     * - "Removal successful." if the room was successfully removed
     * - "Room has a reservation." if the room has one or more reservations
     * - "Room does not exist." if the room with the specified name is not found
     * <pre/>
     */
    public Result removeRoom(String name) {
        for (Room room : roomList) {
            if (!room.getName().equals(name)) {
                continue;
            }

            if (filterReservationsByARoom(room).isEmpty()) {
                this.roomList.remove(room);
                return new Result("Removal successful.", true);
            } else {
                return new Result(ER_ROOM_HAS_RESERVATION);
            }
        }
        return new Result(ER_NO_ROOM);
    }
}