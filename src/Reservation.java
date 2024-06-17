import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class Reservation {

    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Room room;

    public Reservation(String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room room) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
    }

    public String getGuestName() {
        return this.guestName;
    }

    public LocalDate getCheckInDate() {
        return this.checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return this.checkOutDate;
    }

    public Room getRoom() {
        return this.room;
    }

    public double getTotalPrice() {
        int numDays = (int) ChronoUnit.DAYS.between(this.checkInDate, this.checkOutDate);
        return Room.getBasePricePerNight() * numDays;
    }

    public String getPriceBreakdown() {
        System.out.println("Price breakdown:");
        // TODO: Implement getPriceBreakdown()
        return "";
    }
}