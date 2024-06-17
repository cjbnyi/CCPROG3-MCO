import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {

    private String name;
    private ArrayList<Room> roomList;
    private ArrayList<Reservation> reservationList;
    private static final int MAX_ROOMS = 50;

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

    public boolean removeRoom(String name) {
        for (Room room : roomList) {
            if (room.getName().equals(name)) {
                roomList.remove(room);
                return true;
            }
        }
        return false;
    }

    // TODO: Implement getRoomAvailabilityOnADate()
    public int getRoomAvailabilityOnADate(LocalDate date) {
        return -1;
    }

    // TODO: Implement getRoomAvailableDatesThisMonth
    public ArrayList<LocalDate> getRoomAvailabilityThisMonth(Room room) {

        ArrayList<LocalDate> availableDates = new ArrayList<LocalDate>();

        // Iterate through each date of the month (possibly through LocalDate?)
            // Check if the room is available on a date through getRoomAvailabilityOnADate()
            // If yes, add the LocalDate date to the ArrayList<>

        return availableDates;
    }

    public String getRoomInfo(String roomName) {
        for (Room room : this.roomList) {
            if (room.getName().equals(roomName)) {
                StringBuilder roomInfo = new StringBuilder (
                    "Name: " + room.getName() + "\n" +
                    "Base price per night: " + Room.getBasePricePerNight() + "\n" +
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
}