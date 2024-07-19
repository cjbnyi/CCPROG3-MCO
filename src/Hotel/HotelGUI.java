package Hotel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class HotelGUI extends JFrame {
    private JTabbedPane tabPane;
    
    private ComponentFactory componentFactory;

    public enum PANEL_NAME{
        HOME("HomePanel"),
        CREATE("CreatePanel"),
        VIEW("ViewPanel"),
        MANAGE("ManagePanel"),
        BOOK("BookPanel");

        private String panelName;

        private PANEL_NAME(String panelName){
            this.panelName = panelName;
        }

        public String getPanelName() {
            return this.panelName;
        }
    }

    public HotelGUI() {
        super("Hotel Manager");
        this.componentFactory = new ComponentFactory();
        setLayout(new BorderLayout());

        setSize(800, 600);
        initializeComponents();

        // by default, the window will not be displayed
        setVisible(true);
        setResizable(false);

        // so that the program will actually stop running
        // when you press the close button

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel initCreatePanel(){
        JPanel createPanel  = componentFactory.createJPanel();
        JPanel hotelPanel = new JPanel();
        JPanel contentPanel = new JPanel();


        return createPanel;
    }



    private JPanel initViewPanel(){
        JPanel viewPanel  = componentFactory.createJPanel();

        JPanel hotelPanel = new JPanel();
        JPanel contentPanel = new JPanel();

        return viewPanel;
    }



    private JPanel initManagePanel(){
        JPanel managePanel = componentFactory.createJPanel();

        Panel hotelPanel = new Panel();
        Panel contentPanel = new Panel();

        return managePanel;
    }



    private JPanel initBookPanel(){
        JPanel homePanel = componentFactory.createJPanel();
        return homePanel;
    }



    private void initializeComponents(){
        this.tabPane = new JTabbedPane();
        
        tabPane.addTab(PANEL_NAME.HOME.getPanelName(),  new PanelHome(this.componentFactory));

        tabPane.addTab(PANEL_NAME.CREATE.getPanelName(), new PanelCreateHotel(this.componentFactory));

        JPanel viewPanel = initViewPanel();
        tabPane.addTab(PANEL_NAME.VIEW.getPanelName(), viewPanel);

        JPanel managePanel = initManagePanel();
        tabPane.addTab(PANEL_NAME.MANAGE.getPanelName(), managePanel);
        
        JPanel bookPanel = initBookPanel();
        tabPane.addTab(PANEL_NAME.BOOK.getPanelName(), bookPanel);

        tabPane.setMnemonicAt(0, KeyEvent.VK_0);
        tabPane.setMnemonicAt(1, KeyEvent.VK_1);
        tabPane.setMnemonicAt(2, KeyEvent.VK_2);
        tabPane.setMnemonicAt(3, KeyEvent.VK_3);
        tabPane.setMnemonicAt(4, KeyEvent.VK_4);
        this.add(this.tabPane);
        
    }
}
