import java.time.LocalDate;
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

    public int getNumDays() {
        return (int) DAYS.between(this.checkInDate, this.checkOutDate);
    }

    public double getTotalPrice() {
        return this.room.getBasePricePerNight() * this.getNumDays();
    }

    public String getPriceBreakdown() {
        String priceBreakdown = "Price breakdown:";
        for (int i = 0; i < this.getNumDays(); i++)
            priceBreakdown += "\n" + this.checkInDate.plusDays(i) + " --- " + this.room.getBasePricePerNight();
        return priceBreakdown;
    }
}