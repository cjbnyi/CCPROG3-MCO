package Hotel;
public class Main {

    public static void main(String[] args) {

        /* initialize MVC instances */
        Model model = new Model();
        View  view  = new View();
        Controller controller = new Controller(model, view);

        /* execute program flow */
        controller.start();
    }
}