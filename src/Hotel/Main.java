package Hotel;
public class Main {

    public static void main(String[] args) {

        /* initialize MVC instances */
        Model model = new Model();
        View  view  = new View();
        HotelGUI hotelGUI = new HotelGUI();
        Controller_GUI controller = new Controller_GUI(model, hotelGUI);
        
        controller.start();
    }
}