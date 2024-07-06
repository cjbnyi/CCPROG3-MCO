package Hotel;

import javax.swing.JFrame;
import java.applet.*;
import java.awt.*;

public class HotelGUI extends JFrame {

    public HotelGUI() {
        super("Pokedex");
        setLayout(new BorderLayout());

        setSize(500, 500);

        // by default, the window will not be displayed
        //setVisible(true);
        setResizable(false);

        // so that the program will actually stop running
        // when you press the close button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
