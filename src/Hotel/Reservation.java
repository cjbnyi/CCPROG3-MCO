package Hotel;

import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * The Reservation class represents a reservation for a hotel room.
 * It contains information about the guest, the room, and the reservation period.
 */
public class Reservation {

    private final String guestName;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final Room room;


    /**
     * Constructs a Reservation object with the specified guest name, check-in date, 
     * check-out date, and room.
     * 
     * @param guestName the name of the guest
     * @param checkInDate the check-in date of the reservation
     * @param checkOutDate the check-out date of the reservation
     * @param room the room reserved
     */
    public Reservation(String guestName, LocalDate checkInDate, LocalDate checkOutDate, Room room) {
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
    }


    /**
     * Returns the name of the guest who made the reservation.
     * 
     * @return the name of the guest
     */
    public String getGuestName() {
        return this.guestName;
    }


    /**
     * Returns the check-in date of the reservation.
     * 
     * @return the check-in date of the reservation
     */
    public LocalDate getCheckInDate() {
        return this.checkInDate;
    }


    /**
     * Returns the check-out date of the reservation.
     * 
     * @return the check-out date of the reservation
     */
    public LocalDate getCheckOutDate() {
        return this.checkOutDate;
    }


    /**
     * Returns the room reserved.
     * 
     * @return the room reserved
     */
    public Room getRoom() {
        return this.room;
    }


    /**
     * Returns the number of days between the check-in date and check-out date.
     * 
     * @return the number of days of the reservation
     */
    public int getNumDays() {
        return (int) DAYS.between(this.checkInDate, this.checkOutDate);
    }


    /**
     * Returns a breakdown of the price per night for each night of the reservation.
     * 
     * @return a string representing the price breakdown
     */
    public String getPriceBreakdown() {
        String priceBreakdown = "";
        for (int i = 0; i < this.getNumDays(); i++)
            priceBreakdown += "\n" + this.checkInDate.plusDays(i) + " --- " + this.room.getBasePricePerNight();
        return priceBreakdown;
    }


    /**
     * Returns the total price for the entire reservation period.
     * 
     * @return the total price for the reservation
     */
    public double getTotalPrice() {
        return this.room.getBasePricePerNight() * this.getNumDays();
    }
}