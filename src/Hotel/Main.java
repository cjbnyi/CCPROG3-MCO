package Hotel;
public class Main {

    public static void main(String[] args) {

        /* initialize MVC instances */
        Model model = new Model();
        View  view  = new View();
        HotelGUI hotelGUI = new HotelGUI();
        Controller controller = new Controller(model, view, hotelGUI);

        /* execute program flow */
        controller.start();
    }
}