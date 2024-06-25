package Hotel;

/**
 * The Room class represents a room in a hotel.
 * Each room has a name and a base price per night.
 */
public class Room {

    private String name;
    private double basePricePerNight = 1299.0;
    
    /**
     * Constructs a Room object with the specified name.
     * @param name the name of the room
     */
    public Room(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the room.
     * @return the name of the room
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the base price per night for the room.
     * @return the base price per night for the room
     */
    public double getBasePricePerNight() {
        return this.basePricePerNight;
    }

    /**
     * Sets the base price per night for the room.
     * @param basePricePerNight the new base price per night for the room
     */
    public void setBasePricePerNight(double basePricePerNight) {
        this.basePricePerNight = basePricePerNight;
    }
}