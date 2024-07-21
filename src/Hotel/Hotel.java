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

    public Hotel(Hotel hotel) {

        this.name = hotel.name;
        this.roomList = hotel.getRoomList();
        this.reservationList = hotel.getReservationList();
        this.priceRateList = hotel.getPriceRateList();

        this.basePricePerNight = hotel.basePricePerNight;
        this.standardMultiplier = hotel.standardMultiplier;
        this.deluxeMultiplier = hotel.deluxeMultiplier;
        this.executiveMultiplier = hotel.executiveMultiplier;
    }


    // ### HELPERS

    // TODO: DONE! (remove)
    /**
     * Initializes the price rates of rooms in the hotel.
     * Price rates are set to a default of 1.0.
     */
    private void initPriceRates() {
        for (int i = 0; i < DAYS_IN_MONTH; i++) {
            priceRateList[i] = 1.0;
        }
    }

    // TODO: DONE! (remove)
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

    // TODO: DONE! (remove)
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

    // TODO: DONE! (remove)
    /**
     * Gets the name of the hotel.
     * 
     * @return the name of the hotel
     */
    public String getName() {
        return this.name;
    }

    // TODO: DONE! (remove)
    /**
     * Gets the list of rooms in the hotel.
     * 
     * @return the list of rooms
     */
    public ArrayList<Room> getRoomList() {
        return new ArrayList<Room>(this.roomList);
    }

    // TODO: DONE! (remove)
    /**
     * Gets the list of reservations in the hotel.
     * 
     * @return the list of reservations
     */
    public ArrayList<Reservation> getReservationList() {
        return new ArrayList<Reservation>(this.reservationList);
    }

    // TODO: DONE! (remove)
    /**
     * Gets a list of price rates for the month.
     * @return the list of price rates for the month
     */
    public double[] getPriceRateList() {
        double[] priceRateList = new double[DAYS_IN_MONTH];
        System.arraycopy(this.priceRateList, 0, priceRateList, 0, DAYS_IN_MONTH);
        return priceRateList;
    }

    // TODO: DONE! (remove)
    /**
     *
     */
    public double getBasePricePerNight() {
        return this.basePricePerNight;
    }

    // TODO: DONE! (remove)
    /**
     *
     */
    public double getStandardMultiplier() {
        return this.standardMultiplier;
    }

    // TODO: DONE! (remove)
    /**
     *
     */
    public double getDeluxeMultiplier() {
        return deluxeMultiplier;
    }

    // TODO: DONE! (remove)
    /**
     *
     */
    public double getExecutiveMultiplier() {
        return executiveMultiplier;
    }


    // ### 3. FILTERS

    // TODO: DONE! (remove)
    /**
     * Gets the total number of rooms in the hotel.
     * 
     * @return the total number of rooms
     */
    public int getTotalRooms() {
        return this.roomList.size();
    }

    // TODO: DONE! (remove)
    /**
     * Determines the availability of a room in the current month.
     * 
     * @param room the room to check availability for
     * @return a list of available dates for the room in the current month
     */
    public ArrayList<LocalDate> getRoomAvailabilityThisMonth(Room room) {

        ArrayList<LocalDate> availableDates = new ArrayList<LocalDate>();
        ArrayList<Reservation> roomReservations = filterReservationsByRoom(room);

        for (int i = 0; i < Hotel.DAYS_IN_MONTH; i++) {
            availableDates.add(LocalDate.of(View.SYSTEM_YEAR, View.SYSTEM_MONTH, 1 + i));
        }

        for (Reservation reservation : roomReservations) {
            for (int j = 0; j < reservation.getNumDays(); j++) {
                availableDates.remove(reservation.getCheckInDate().plusDays(j));
            }
        }

        return availableDates;
    }

    // TODO: DONE! (remove)
    /**
     * Retrieves a room from the hotel's roomList given its name.
     *
     * @param roomName the name of the room to retrieve
     * @return the room object with the specified name, or null if no such room exists
     */
    public Room getRoom(String roomName) {
        for (Room room : this.roomList) {
            if (room.getName().equals(roomName)) {
                return createRoomCopy(room);
            }
        }
        return null;
    }

    // TODO: DONE! (remove)
    /**
     * Gets the price rate for a particular day of the month.
     *
     * @return the price rate for a particular day of the month
     */
    public double getPriceRateForADay(int day) {
        return this.priceRateList[day-1];
    }


    // ### SETTERS

    // TODO: DONE! (remove)
    /**
     * Sets the name of the hotel.
     * 
     * @param name the new name for the hotel
     */
    public void setName(String name) {
        this.name = name;
    }

    // TODO: DONE! (remove)
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


    // ### MODIFIERS

    // TODO: DONE! (remove)
    /**
     * Checks if the provided check in and check out dates coincide with that of a reservation.
     *
     * @param reservation reservation being compared
     * @param checkInDate check in date being compared with the reservation
     * @param checkOutDate check out date being compared with the reservation
     * @return true if date coincides reservation; false, otherwise
     */
    public boolean datesCoincideReservation(Reservation reservation, LocalDate checkInDate, LocalDate checkOutDate) {

        LocalDate reservationCheckInDate = reservation.getCheckInDate();
        LocalDate reservationCheckOutDate = reservation.getCheckOutDate();

        boolean checkInCoincides =
            checkInDate.isEqual(reservationCheckInDate) || checkInDate.isAfter(reservationCheckInDate) &&
            checkInDate.isBefore(reservationCheckOutDate);

        boolean checkOutCoincides =
            checkOutDate.isEqual(reservationCheckInDate) || checkOutDate.isAfter(reservationCheckInDate) &&
            checkOutDate.isBefore(reservationCheckOutDate);

        return checkInCoincides || checkOutCoincides;
    }


    // TODO: DONE! (remove)
    /**
     * Makes a Reservation
     * @param guestName - New Guest Name
     * @param checkInDate - check in date
     * @param checkOutDate  - check out date
     * @param room - particular room to reserve
     * @return reservation instance if a reservation is successful; null, otherwise
    */
    public Reservation makeReservation(String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room room) {

        // boolean hasNoCoincidingReservation = false; ### Removed because we may need to process the reservation
        ArrayList<Room> availableRooms = new ArrayList<Room>(this.roomList);

        /* filter existing reservations based on the room type */
        ArrayList<Reservation> filteredReservations = switch(room) {
            case StandardRoom _ -> filterStandardReservations();
            case DeluxeRoom _ -> filterDeluxeReservations();
            case ExecutiveRoom _ -> filterExecutiveReservations();
            case null, default -> null;
        };

        /* filter available rooms based on the provided dates */
        assert filteredReservations != null;
        for (Reservation reservation : filteredReservations) {
            if (datesCoincideReservation(reservation, checkInDate, checkOutDate)) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        /* check if the desired room is available */
        for (Room availableRoom : availableRooms) {
            if (availableRoom.equals(room)) {
                // hasNoCoincidingReservation = true;
                Reservation newReservation = new Reservation(guestName, checkInDate, checkOutDate, room);
                this.reservationList.add(newReservation);
                return newReservation;
            }
        }

        return null;
    }

    // TODO: DONE! (remove)
    /**
     * Adds a room to the hotel.
     * @param roomName room to be added
     * @param roomType type of the room
     * @return ER_SUCCESSFUL if room is successfully added, some corresponding error if not
    */
    public void addRoom(String roomName, Room.ROOM_TYPE roomType) {

        Room newRoom = switch(roomType) {
            case Room.ROOM_TYPE.STANDARD -> new StandardRoom(roomName);
            case Room.ROOM_TYPE.DELUXE -> new DeluxeRoom(roomName);
            case Room.ROOM_TYPE.EXECUTIVE -> new ExecutiveRoom(roomName);
        };

        /* the room can be added */
        this.roomList.add(newRoom);
    }

    // TODO: DONE! (remove)
    /**
     * Updates the base price per night for all rooms in the room list.
     * 
     * @param newBasePrice the new base price to set for each room
     * @return 
     * <pre>
     * a Result object indicating the outcome of the operation:
     *      - "Reservation list is empty." if there are existing reservations
     *      - "Price is lower than 100." if the provided price is less than 100
     *      - "Base price set." if the price was successfully updated
     * <pre/>
     */
    public Result setBasePrice(double newBasePrice) {

        if (!this.reservationList.isEmpty()) {
            return new Result(ER_HOTEL_HAS_RESERVATION);
        }
        
        if (newBasePrice < 100) {
            return new Result(ER_LOWER_THAN_BASEPRICE);
        }

        this.basePricePerNight = newBasePrice;

        return new Result("Base price set.", true);
    }


    // ### FILTERS

    // TODO: DONE! (remove)
    /**
     * Filters and returns a list of reservations for a specific room.
     *
     * @param room the room to filter reservations for
     * @return a list of reservations for the specified room
     */
    public ArrayList<Reservation> filterReservationsByRoom(Room room) {
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        for (Reservation reservation : this.reservationList) {
            if (reservation.getRoom().equals(room)) {
                reservationList.add(reservation);
            }
        }
        return reservationList;
    }

    // TODO: DONE! (remove)
    public ArrayList<Reservation> filterStandardReservations() {
        ArrayList<Reservation> rList = new ArrayList<Reservation>();
        for (Reservation r : this.reservationList) {
            if (r.getRoom() instanceof StandardRoom) {
                rList.add(r);
            }
        }
        return rList;
    }

    // TODO: DONE! (remove)
    public ArrayList<Reservation> filterDeluxeReservations() {
        ArrayList<Reservation> rList = new ArrayList<Reservation>();
        for (Reservation r : this.reservationList) {
            if (r.getRoom() instanceof DeluxeRoom) {
                rList.add(r);
            }
        }
        return rList;
    }

    // TODO: DONE! (remove)
    public ArrayList<Reservation> filterExecutiveReservations() {
        ArrayList<Reservation> rList = new ArrayList<Reservation>();
        for (Reservation r : this.reservationList) {
            if (r.getRoom() instanceof ExecutiveRoom) {
                rList.add(r);
            }
        }
        return rList;
    }


    // ### MODIFIERS

    // TODO: DONE! (remove)
    /**
     * Removes a room from the room list given the room's name.
     * 
     * @param roomName the name of the room to remove
     * @return 
     * <pre> 
     * a Result object indicating the outcome of the operation:
     * - "Removal successful." if the room was successfully removed
     * - "Room has a reservation." if the room has one or more reservations
     * - "Room does not exist." if the room with the specified name is not found
     * <pre/>
     */
    public Result removeRoom(String roomName) {

        boolean hasSameName;
        boolean hasNoReservations;

        for (Room room : roomList) {

            hasSameName = room.getName().equals(roomName);
            hasNoReservations = filterReservationsByRoom(room).isEmpty();

            if (hasSameName) {
                if (hasNoReservations) {
                    this.roomList.remove(room);
                    return new Result("Removal successful.", true);
                } else {
                    return new Result(ER_ROOM_HAS_RESERVATION);
                }
            }
        }

        return new Result(ER_NO_ROOM);
    }

    // TODO: DONE! (remove)
    /**
     * Removes a reservation.
     * @param roomName name of the room
     * @return true - removed room, false - has not found room to remove.
     */
    public Result removeReservation(String roomName, LocalDate checkInDate) {

        String currentRoomName;
        LocalDate currentCheckInDate;

        for (Reservation reservation : this.reservationList) {

            currentRoomName = reservation.getRoom().getName();
            currentCheckInDate = reservation.getCheckInDate();

            if (currentRoomName.equals(roomName) && currentCheckInDate.isEqual(checkInDate)) {
                this.reservationList.remove(reservation);
                return new Result(ER_SUCCESSFUL);
            }
        }
        return new Result(ER_NO_RESERVATION);
    }

    // TODO: Implement when needed.
    /*
    /**
     * Updates a room instance in the hotel based on a copy instance.
     * @param room updated room instance
     *
    public Result updateRoom(Room room) {

    }
    */
}