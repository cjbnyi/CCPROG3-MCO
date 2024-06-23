import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {

    private String name;
    private ArrayList<Room> roomList;
    private ArrayList<Reservation> reservationList;
    private static final int MAX_ROOMS = 50;
    private static final int DAYS_IN_MONTH = 31;

    public Hotel(String name) {
        this.name = name;
        this.roomList = new ArrayList<Room>();
        this.reservationList = new ArrayList<Reservation>();
        this.initRooms();
    }

    private void initRooms() {
        String roomName;
        for (char letter = 'A'; letter <= 'J'; letter++) {
            for (int number = 0; number <= 9; number++) {
                roomName = "" + letter + number;
                Room newRoom = new Room(roomName);
                this.roomList.add(newRoom);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Room> getRoomList() {
        return this.roomList;
    }

    public int getTotalRooms() {
        return this.roomList.size();
    }

    public double getEstimatedEarnings() {
        double totalEarnings = 0.0;
        for (Reservation reservation : reservationList)
            totalEarnings += reservation.getTotalPrice();
        return totalEarnings;
    }

    // TODO: Implement makeReservation()
    public double makeReservation() {
        return -1;
    }

    public boolean addRoom(String name) {
        for (Room room : this.roomList)
            if (room.getName().equals(name))
                return false;
        Room room = new Room(name);
        this.roomList.add(room);
        return true;
    }

    public ArrayList<Room> filterAvailableRoomsByDate(LocalDate date) {
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

    public ArrayList<Reservation> filterReservationsByRoom(Room room) {
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

    public ArrayList<LocalDate> getRoomAvailabilityThisMonth(Room room) {

        ArrayList<LocalDate> availableDates = new ArrayList<LocalDate>();
        ArrayList<Reservation> roomReservations = filterReservationsByRoom(room);

        for (int i = 0; i < Hotel.DAYS_IN_MONTH; i++)
            availableDates.add(LocalDate.of(2024, 1, 1));

        for (Reservation reservation : roomReservations)
            for (int j = 0; j < reservation.getNumDays(); j++)
                availableDates.remove(reservation.getCheckInDate().plusDays(j));

        return availableDates;
    }

    public String getRoomInfo(String roomName) {
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

    public boolean setRoomBasePrice(double newPrice) {
        if (!this.reservationList.isEmpty())
            return false;
        for (Room room : this.roomList)
            room.setBasePricePerNight(newPrice);
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }
}