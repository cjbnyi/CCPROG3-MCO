package Hotel;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

/**
 * The Reservation class represents a reservation for a hotel room.
 * It contains information about the guest, the room, and the reservation period.
 */
public class Reservation {

    private final String guestName;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private final Room room;
    private Discount.DISCOUNT_CODES appliedDiscountCode = null;

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
     * Constructs a Reservation object by copying an existing instance.
     * @param r reservation
     */
    public Reservation(Reservation r) {
        this.guestName = r.guestName;
        this.checkInDate = r.checkInDate;
        this.checkOutDate = r.checkOutDate;
        this.room = r.room;
        this.appliedDiscountCode = r.appliedDiscountCode;
    }


    // ### GETTERS

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
     * Returns the number of days of a reservation.
     *
     * @return the number of days of the reservation
     */
    public int getNumDays() {
        return (int) DAYS.between(getCheckInDate(), getCheckOutDate());
    }

    /**
     * Returns the room reserved.
     * 
     * @return the room reserved
     */
    public Room getRoom() {
        return switch (room) {
            case StandardRoom r -> new StandardRoom(room);
            case DeluxeRoom r -> new DeluxeRoom(room);
            case ExecutiveRoom r -> new ExecutiveRoom(room);
            case null, default -> null;
        };
    }

    /**
     * Returns the applied discount code of a reservation (if there exists).
     */
    public Discount.DISCOUNT_CODES getAppliedDiscountCode() {
        return this.appliedDiscountCode;
    }


    // ### SETTERS

    /**
     * Sets the applied discount code of a reservation.
     * @param appliedDiscountCode the discount code to be applied to the reservation
     */
    public void setAppliedDiscountCode(Discount.DISCOUNT_CODES appliedDiscountCode) {
        this.appliedDiscountCode = appliedDiscountCode;
    }
}