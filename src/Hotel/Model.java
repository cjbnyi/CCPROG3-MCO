package Hotel;
import static Hotel.Discount.DISCOUNT_CODES.I_WORK_HERE;
import static Hotel.Discount.DISCOUNT_CODES.PAYDAY;
import static Hotel.Discount.DISCOUNT_CODES.STAY4_GET1;
import static Hotel.Result.COMMON_ERRORS.ER_EXISTING_DISCOUNT;
import static Hotel.Result.COMMON_ERRORS.ER_HOTEL_EXISTS;
import static Hotel.Result.COMMON_ERRORS.ER_INVALID_CODE;
import static Hotel.Result.COMMON_ERRORS.ER_INVALID_PRICE_RATE;
import static Hotel.Result.COMMON_ERRORS.ER_MAX_CAPACITY;
import static Hotel.Result.COMMON_ERRORS.ER_NOT_UNIQUE_GIVENNAME;
import static Hotel.Result.COMMON_ERRORS.ER_NO_HOTEL;
import static Hotel.Result.COMMON_ERRORS.ER_PAYDAY_INVALID;
import static Hotel.Result.COMMON_ERRORS.ER_STAY4_GET1_INVALID;
import static Hotel.Result.COMMON_ERRORS.ER_SUCCESSFUL;
import static java.time.temporal.ChronoUnit.DAYS;

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


    // ### HELPERS

    // TODO: DONE! (remove)
    /**
     * Gets an existing hotel with a specified name.
     * Note: Helper function for the Model.
     * @param name
     * @return The instance of the hotel with the corresponding name; otherwise, null.
     */
    private Hotel getHotel(String name) {
        for (Hotel hotel : this.hotelList) {
            if (hotel.getName().equals(name)) {
                return hotel;
            }
        }
        return null;
    }

    /**
     * Gets an existing hotel with a specified name.
     * Note: Equivalent to getHotel() but for outside usage.
     * @param name
     * @return The instance of the hotel with the corresponding name; otherwise, null.
     */
    public Hotel getHotelClone(String name) {
        for (Hotel hotel : this.hotelList) {
            if (hotel.getName().equals(name)) {
                return new Hotel(hotel);
            }
        }
        return null;
    }


    // ### GETTERS

    // TODO: DONE! (remove)
    /**
     * Gets the array of hotels.
     *
     * @return An array of hotels.
     */
    public ArrayList<Hotel> getHotelList() {
        return new ArrayList<Hotel>(this.hotelList);
    }


    // ### ROOM-RELATED METHODS

    // TODO: DONE! (remove)
    /**
     * Creates a copy of a room instance.
     *
     * @return a copy of the room instance
     */
    private Room createRoomCopy(Room room) {
        return switch (room) {
            case StandardRoom r -> new StandardRoom(room);
            case DeluxeRoom r -> new DeluxeRoom(room);
            case ExecutiveRoom r -> new ExecutiveRoom(room);
            case null, default -> null;
        };
    }

    // TODO: DONE! (remove)
    /**
     * Gets the type of room.
     * @param room the room being checked
     * @return type of the room
     */
    public String getRoomTypeString(Room room) {
        return switch(room) {
            case StandardRoom _ -> "Standard";
            case DeluxeRoom _ -> "Deluxe";
            case ExecutiveRoom _ -> "Executive";
            case null, default -> null;
        };
    }


    // ### RESERVATION-RELATED METHODS

    // TODO: DONE! (remove)
    /**
     * Returns the number of days of a reservation.
     *
     * @return the number of days of the reservation
     */
    public int getNumDaysOfReservation(Reservation reservation) {
        return (int) DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
    }

    // TODO: DONE! (remove)
    /**
     * Returns the price of a reservation at a particular day.
     * @return the price of a reservation at a particular day
     */
    public double getReservationPriceForADay(Hotel hotel, Reservation reservation, int day) {
        double basePricePerNight = hotel.getBasePricePerNight();
        double roomTypeMultiplier = switch(reservation.getRoom()) {
            case StandardRoom r -> hotel.getStandardMultiplier();
            case DeluxeRoom r -> hotel.getDeluxeMultiplier();
            case ExecutiveRoom r -> hotel.getExecutiveMultiplier();
            case null, default -> 0f;
        };
        double priceRate = hotel.getPriceRateForADay(day);
        return basePricePerNight * roomTypeMultiplier * priceRate;
    }

    // TODO: DONE! (remove)
    public double getReservationDiscount(Hotel hotel, Reservation reservation, double totalPrice) {
        int firstDay = reservation.getCheckInDate().getDayOfMonth();
        return switch(reservation.getAppliedDiscountCode()) {
            case Discount.DISCOUNT_CODES.I_WORK_HERE -> totalPrice * 0.1;
            case Discount.DISCOUNT_CODES.STAY4_GET1 -> getReservationPriceForADay(hotel, reservation, firstDay);
            case Discount.DISCOUNT_CODES.PAYDAY -> totalPrice * 0.07;
            case null -> 0f;
        };
    }

    // TODO: Refactor this.
    /**
     * Returns the total price of a reservation.
     *
     * @param hotel the hotel in which the reservation was made
     * @param reservation the reservation made
     */
    public double getReservationTotalPrice(Hotel hotel, Reservation reservation) {
        int checkInDay = reservation.getCheckInDate().getDayOfMonth();
        int checkOutDay = reservation.getCheckOutDate().getDayOfMonth();
        double totalPrice = 0f;
        double priceRate;
        double discount;
        Discount.DISCOUNT_CODES appliedDiscountCode = reservation.getAppliedDiscountCode();

        double roomTypeMultiplier = switch(reservation.getRoom()) {
            case StandardRoom r -> hotel.getStandardMultiplier();
            case DeluxeRoom r -> hotel.getDeluxeMultiplier();
            case ExecutiveRoom r -> hotel.getExecutiveMultiplier();
            case null, default -> 0f;
        };

        for (int i = checkInDay; i < checkOutDay; i++) {
            totalPrice += getReservationPriceForADay(hotel, reservation, i);
        }
        return totalPrice - getReservationDiscount(hotel, reservation, totalPrice);
    }

    // TODO: DONE! (remove)
    /**
     * Return the price breakdown of a reservation in string.
     */
    public String getReservationPriceBreakdown(Hotel hotel, Reservation reservation) {

        String priceBreakdown = "";
        double dayPrice;
        double totalPrice = 0f;

        int firstDay = reservation.getCheckInDate().getDayOfMonth();
        for (int i = 0; i < getNumDaysOfReservation(reservation); i++) {
            dayPrice = getReservationPriceForADay(hotel, reservation, i + 1);
            priceBreakdown += "July " + (firstDay + i) + ", 2024 | ₱" + dayPrice + "\n";
            totalPrice += dayPrice;
        }

        double discount = getReservationDiscount(hotel, reservation, totalPrice);
        priceBreakdown += "Discount | ₱" + discount + "\n";
        priceBreakdown += "Total | ₱" + (totalPrice - discount);
        return priceBreakdown;
    }


    // ### HOTEL-RELATED METHODS

    // TODO: DONE! (remove)
    /**
     * Filters and returns a list of reservations for a specific room.
     *
     * @param room the room to filter reservations for
     * @return a list of reservations for the specified room
     */
    public ArrayList<Reservation> filterHotelReservationsByRoom(Hotel hotel, Room room) {
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        for (Reservation reservation : hotel.getReservationList()) {
            if (reservation.getRoom().equals(room)) {
                reservationList.add(reservation);
            }
        }
        return reservationList;
    }

    // TODO: DONE! (remove)
    public Reservation getReservation(String hotelName, String roomName, LocalDate checkInDate) {

        Room room = getRoomOfAHotel(hotelName, roomName);

        if (null == room) {
            return null;
        }

        Hotel hotel = getHotel(hotelName);
        assert hotel != null;
        ArrayList<Reservation> reservationList = hotel.getReservationList();

        for (Reservation r : reservationList) {
            if (r.getCheckInDate().equals(checkInDate)) {
                return r;
            }
        }

        return null;
    }

    // TODO: DONE! (remove)
    /**
     *
     * @param hotelName the name of the hotel
     * @param roomName the name of the room
     * @return room instance if the hotel and the room exist; null, otherwise
     */
    public Room getRoomOfAHotel(String hotelName, String roomName) {

        Hotel hotel = getHotel(hotelName);
        if (null == hotel) {
            return null;
        }

        ArrayList<Room> roomList = hotel.getRoomList();
        for (Room room : roomList) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }

        return null;
    }

    // TODO: DONE! (remove)
    /**
     * Filters and returns a list of available rooms by a specified date.
     *
     * @param date the date to check availability for
     * @return a list of available rooms on the specified date
     */
    public ArrayList<Room> getAvailableRoomsByDate(Hotel hotel, LocalDate date) {

        ArrayList<Room> availableRooms = hotel.getRoomList();
        ArrayList<Reservation> reservationList = hotel.getReservationList();

        for (Reservation reservation : reservationList) {
            LocalDate checkInDate = reservation.getCheckInDate();
            LocalDate checkOutDate = reservation.getCheckOutDate();
            if ((date.isAfter(checkInDate) || date.isEqual(checkInDate)) && date.isBefore(checkOutDate)) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

    // TODO: DONE! (remove)
    /**
     * Returns the total number of available rooms in a hotel on a specific date.
     *
     * @param hotel the hotel to check
     * @param date the date to check availability
     * @return the total number of available rooms on the specified date
     */
    public int getNumOfAvailableRoomsByDate(Hotel hotel, LocalDate date) {
        return getAvailableRoomsByDate(hotel, date).size();
    }

    // TODO: DONE! (remove)
    /**
     * Calculates the estimated earnings for a given hotel.
     *
     * @param hotel the hotel for which to calculate estimated earnings
     * @return the total estimated earnings for the hotel
     */
    public double getHotelEstimatedEarnings(Hotel hotel) {
        double totalEarnings = 0.0;
        for (Reservation reservation : hotel.getReservationList()) {
            totalEarnings += getReservationTotalPrice(hotel, reservation);
        }
        return totalEarnings;
    }

    // TODO: Decide whether to keep (ignore for now).
    /*
     * Returns the Reservation List of a Hotel
     *
     * @param hotelName the name of the hotel
     * @return
    */
    public ArrayList<Reservation> getReservations(String hotelName) {

        ArrayList<Reservation> listOfReservations = null;

        for (Hotel hotel : this.hotelList)
            if (hotel.getName().equals(hotelName)){
                listOfReservations = hotel.getReservationList();
            }

        return listOfReservations;
    }

    // TODO: Decide whether to keep (ignore for now).
    /**
     * Returns the list of rooms in a specified hotel.
     *
     * @param nameOfHotel the name of the hotel
     * @return the list of rooms in the hotel
     */
    public ArrayList<Room> getRoomListOfAHotel(String nameOfHotel) {

        ArrayList<Room> listOfRooms = null;

        for (Hotel hotel : this.hotelList) {
            if (hotel.getName().equals(nameOfHotel)) {
                listOfRooms = hotel.getRoomList();
            }
        }
        return listOfRooms;
    }

    // TODO: DONE! (remove)
    /**
     * Adds a room to a hotel given the hotel's name and the room name.
     *
     * @param hotelName the name of the hotel to which the room will be added
     * @param roomName the name of the room to be added
     * @param roomType the type of room to be added
     *
     * @return
     * <pre>
     * a Result object indicating the outcome of the operation, if it is not successful, then it has the following possible messages:
     *         - "Hotel is at Max." if the hotel already has 50 rooms;
     *         - "Name of hotel does not exist." if the specified hotel is not found;
     *         - "Room name not unique." If the room name is not unique in the list.
     * <pre/>
     */
    public Result addRoomToAHotel(String hotelName, String roomName, Room.ROOM_TYPE roomType) {

        Hotel hotel = getHotel(hotelName);

        if (null == hotel) {
            return new Result(ER_NO_HOTEL);
        }
        if (hotel.getTotalRooms() >= Hotel.MAX_ROOMS) {
            return new Result(ER_MAX_CAPACITY);
        }
        if (getRoomOfAHotel(hotelName, roomName) != null) {
            return new Result(ER_NOT_UNIQUE_GIVENNAME);
        }

        hotel.addRoom(roomName, roomType);
        return new Result(ER_SUCCESSFUL);
    }

    // TODO: DONE! (remove)
    public double getRoomBasePricePerNight(Hotel hotel, Room room) {
        double basePricePerNight = hotel.getBasePricePerNight();
        return switch(room) {
            case StandardRoom r -> basePricePerNight * hotel.getStandardMultiplier();
            case DeluxeRoom r -> basePricePerNight * hotel.getDeluxeMultiplier();
            case ExecutiveRoom r -> basePricePerNight * hotel.getExecutiveMultiplier();
            case null, default -> -1;
        };
    }

    // TODO: DONE! (remove)
    /**
     * Checks if a hotel with the specified name exists.
     *
     * @param hotelName the name of the hotel to check
     * @return true if the hotel exists, false otherwise
     */
    public boolean doesHotelExist(String hotelName) {
        for (Hotel hotel : this.hotelList) {
            if (hotel.getName().equals(hotelName)) {
                return true;
            }
        }
        return false;
    }

    // TODO: DONE! (remove)
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

        if (!doesHotelExist(currentName)) {
            return new Result(ER_NO_HOTEL);
        }

        if (doesHotelExist(newName)) {
            return new Result(ER_NOT_UNIQUE_GIVENNAME);
        }

        Hotel hotel = getHotel(currentName);
        assert hotel != null;
        hotel.setName(newName);
        return new Result(ER_SUCCESSFUL);
    }

    // TODO: DONE! (remove)
    /**
     * Adds a new hotel with the specified name.
     * 
     * @param newHotel the new hotel instance
     * @return the newly created hotel, or null if a hotel with the given name already exists
     */
    public Result addHotel(Hotel newHotel) {
        if (doesHotelExist(newHotel.getName())) {
            return new Result(ER_HOTEL_EXISTS);
        }
        this.hotelList.add(newHotel);
        return new Result(ER_SUCCESSFUL);
    }

    /**
     * Adds a new hotel with the specified name.
     * 
     * @param newHotel the new hotel instance
     * @return the newly created hotel, or null if a hotel with the given name already exists
     */
    public Result addHotel(String name, Double price) {
        
        if (doesHotelExist(name)) {
            return new Result(ER_HOTEL_EXISTS);
        }  

        if (price < 100) {
            return new Result(ER_INVALID_PRICE_RATE);
        }

        Hotel newHotel = new Hotel(name);
        newHotel.setBasePrice(price);

        this.hotelList.add(newHotel);
        return new Result(ER_SUCCESSFUL);
    }


    // TODO: DONE! (remove)
    /**
     * Sets the base price per night for all rooms in the hotel if there are no reservations.
     *
     * @param hotelName the hotel name string whose base price is being modified
     * @param newBasePrice the new base price per night
     */
    public Result setHotelBasePrice(String hotelName, double newBasePrice) {
        Hotel hotel = getHotel(hotelName);
        if (null == hotel) {
            return new Result(ER_NO_HOTEL);
        }
        return hotel.setBasePrice(newBasePrice);
    }

    // TODO: DONE! (remove)
    public boolean makeReservation(String hotelName, String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room room) {
        Hotel hotel = getHotel(hotelName);
        if (null == hotel) {
            return false;
        }
        hotel.makeReservation(guestName, checkInDate, checkOutDate, room);
        return true;
    }

    // TODO: DONE! (remove)
    /**
     * Removes a specified room from a specified hotel.
     * 
     * @param hotelName the name of the hotel from which to remove the room
     * @param roomName the name of the room to remove
     * @return 
     * <pre>
     * a Result object indicating the outcome of the operation:
     *      - "Hotel was not found." if the hotel with the specified name is not found in the hotel list.
     *      - "Room has a reservation." if the room has one or more reservations
     *      - "Room does not exist." if the room with the specified name is not found
     *      - "Removal successful." if the room was successfully removed. Has a isSuccessful Boolean of true
     * <pre/>
     */
    public Result removeRoomOfHotel(String hotelName, String roomName) {
        Hotel hotel = getHotel(hotelName);
        if (null == hotel) {
            return new Result(ER_NO_HOTEL);
        }
        hotel.removeRoom(roomName);
        return new Result(ER_SUCCESSFUL);
    }

    // TODO: DONE! (remove)
    /**
     * Removes a reservation from a specified room in a specified hotel.
     * 
     * @param hotelName the name of the hotel where the reservation should be removed
     * @param roomName the name of the room from which the reservation should be removed
     * @return 
     * <pre>
     * a Result object indicating the outcome of the operation:
     *         - "Hotel was not found." if the specified hotel is not found
     *         - "Reservation not found." if the specified reservation is not found in the hotel
     *         - "Reservation was successful." if the specified reservation was done succesful. Note that is has a isSuccesful boolean of true. 
     * </pre>  
     */
    public Result removeReservation(String hotelName, String roomName, LocalDate checkInDate) {
        Hotel hotel = getHotel(hotelName);
        if (null == hotel) {
            return new Result(ER_NO_HOTEL);
        }
        return hotel.removeReservation(roomName, checkInDate);
    }

    // TODO: DONE! (remove)
    /**
     * Removes a Hotel
     * @param hotelName name of the hotel
     * @return true if removed successful; false, if not.
    */
    public boolean removeHotel(String hotelName) {
        Hotel hotel = getHotel(hotelName);
        if (null == hotel) {
            return false;
        }
        this.hotelList.remove(hotel);
        return true;
    }


    // ### DISCOUNT-RELATED METHODS

    // TODO: DONE! (remove)
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

    // TODO: DONE! (remove)
    /**
     * Removes a reservation's existing applied discount code.
     */
    public void removeDiscountCode(Reservation reservation) {
        reservation.setAppliedDiscountCode(null);
    }
}