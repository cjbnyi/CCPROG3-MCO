import java.util.ArrayList;

public class Model {

    private ArrayList<Hotel> hotelList;

    public Model() {
        this.hotelList = new ArrayList<Hotel>();
    }

    // TODO: Implement createHotel()
    public boolean createHotel(String name) {

        for (Hotel hotel : hotelList) {
            return true;
        }
    }
}