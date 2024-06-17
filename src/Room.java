public class Room {

    private String name;
    private static double basePricePerNight = 1299.0;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static double getBasePricePerNight() {
        return Room.basePricePerNight;
    }

    public static void setBasePricePerNight(double basePricePerNight) {
        Room.basePricePerNight = basePricePerNight;
    }


}