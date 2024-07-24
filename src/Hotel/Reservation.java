package Hotel;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

/**
 * The Reservation class represents a reservation for a hotel room.
 * It contains information about the guest, the room, and the reservation period.
 */
public class Reservation {

    private final String GUEST_NAME;
    private final LocalDate CHECK_IN_DATE;
    private final LocalDate CHECK_OUT_DATE;
    private final Room ROOM;
    private Discount.DISCOUNT_CODES appliedDiscountCode;

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
        this.GUEST_NAME = guestName;
        this.CHECK_IN_DATE = checkInDate;
        this.CHECK_OUT_DATE = checkOutDate;
        this.ROOM = room;
        this.appliedDiscountCode = null;
    }

    /**
     * Constructs a Reservation object by copying an existing instance.
     * @param r reservation
     */
    public Reservation(Reservation r) {
        this.GUEST_NAME = r.GUEST_NAME;
        this.CHECK_IN_DATE = r.CHECK_IN_DATE;
        this.CHECK_OUT_DATE = r.CHECK_OUT_DATE;
        this.ROOM = r.ROOM;
        this.appliedDiscountCode = r.appliedDiscountCode;
    }


    // ### GETTERS

    /**
     * Returns the name of the guest who made the reservation.
     * 
     * @return the name of the guest
     */
    public String getGuestName() {
        return this.GUEST_NAME;
    }

    /**
     * Returns the check-in date of the reservation.
     * 
     * @return the check-in date of the reservation
     */
    public LocalDate getCheckInDate() {
        return this.CHECK_IN_DATE;
    }

    /**
     * Returns the check-out date of the reservation.
     * 
     * @return the check-out date of the reservation
     */
    public LocalDate getCheckOutDate() {
        return this.CHECK_OUT_DATE;
    }

    /**
     * Returns the room reserved.
     *
     * @return the room reserved
     */
    public Room getRoom() {
        return switch (ROOM) {
            case StandardRoom r -> new StandardRoom(ROOM);
            case DeluxeRoom r -> new DeluxeRoom(ROOM);
            case ExecutiveRoom r -> new ExecutiveRoom(ROOM);
            case null, default -> null;
        };
    }

    /**
     * Returns the applied discount code of a reservation (if there exists).
     */
    public Discount.DISCOUNT_CODES getAppliedDiscountCode() {
        return this.appliedDiscountCode;
    }

    /**
     * Returns the number of days of a reservation.
     *
     * @return the number of days of the reservation
     */
    public int getNumDays() {
        return (int) DAYS.between(getCheckInDate(), getCheckOutDate());
    }


    // ### SETTERS

    /**
     * Sets the applied discount code of a reservation.
     * @param discountCode the discount code to be applied to the reservation
     */
    public void setAppliedDiscountCode(Discount.DISCOUNT_CODES discountCode) {
        this.appliedDiscountCode = discountCode;
    }
}