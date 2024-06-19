public class Room {

    private String name;
    private double basePricePerNight = 1299.0;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public double getBasePricePerNight() {
        return this.basePricePerNight;
    }

    public void setBasePricePerNight(double basePricePerNight) {
        this.basePricePerNight = basePricePerNight;
    }
}