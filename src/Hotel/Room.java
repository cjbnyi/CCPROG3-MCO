package Hotel;

/**
 * The Room class represents a room in a hotel.
 * Each room has a name and a base price per night.
 */
public abstract class Room {

    protected String name;


    Room(String name) {
        this.name = name;
    }


    Room(Room room) {
        this.name = room.name;
    }


    /**
     * Returns the name of the room.
     * @return the name of the room
     */
    public String getName() {
        return this.name;
    }
}