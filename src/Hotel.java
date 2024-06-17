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

    public double makeReservation() {
        // TODO: Implement makeReservation()
        return 0.0;
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

    public int getRoomAvailabilityOnADate(LocalDate date) {
        // TODO: Implement getRoomAvailabilityOnADate()
        return 1;
    }
}