package Hotel;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.applet.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class HotelGUI extends JFrame {
    private JTabbedPane tabPane;
    private Button quitButton;
    private Button createButton;
    private Button viewButton;
    private Button bookButton;
    private Button manageButton;
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
        setLayout(new BorderLayout());

        setSize(800, 600);

        // by default, the window will not be displayed
        setVisible(true);
        setResizable(false);

        initializeComponents();
        // so that the program will actually stop running
        // when you press the close button

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
    private Panel initHomePanel(){
        Panel homePanel = new Panel();
        homePanel.setLayout(new FlowLayout());
        homePanel.setBackground(Color.RED);

        createButton    = new Button("Create Hotel");
        viewButton      = new Button("View Hotel");
        bookButton      = new Button("Book Button");
        manageButton    = new Button("Manage Button");
        quitButton      = new Button("Quit Manager");

        homePanel.add(createButton);
        homePanel.add(viewButton);
        homePanel.add(bookButton);
        homePanel.add(manageButton);
        homePanel.add(quitButton);

        return homePanel;
    }



    private Panel initCreatePanel(){
        Panel createPanel = new Panel();
        createPanel.setLayout(new FlowLayout());
        createPanel.setBackground(Color.ORANGE);

        Panel hotelPanel = new Panel();
        Panel contentPanel = new Panel();


        return createPanel;
    }



    private Panel initViewPanel(){
        Panel viewPanel = new Panel();
        viewPanel.setLayout(new FlowLayout());
        viewPanel.setBackground(Color.RED);

        Panel hotelPanel = new Panel();
        Panel contentPanel = new Panel();

        return viewPanel;
    }



    private Panel initManagePanel(){
        Panel managePanel = new Panel();
        managePanel.setLayout(new FlowLayout());
        managePanel.setBackground(Color.RED);

        Panel hotelPanel = new Panel();
        Panel contentPanel = new Panel();

        return managePanel;
    }



    private Panel initBookPanel(){
        Panel homePanel = new Panel();
        homePanel.setLayout(new FlowLayout());
        homePanel.setBackground(Color.RED);
        return homePanel;
    }



    private void initializeComponents(){
        this.tabPane = new JTabbedPane();
        
        Panel homePanel = initHomePanel();
        tabPane.addTab(PANEL_NAME.HOME.getPanelName(), homePanel);

        Panel createPanel = initCreatePanel();
        tabPane.addTab(PANEL_NAME.CREATE.getPanelName(), createPanel);

        Panel viewPanel = initViewPanel();
        tabPane.addTab(PANEL_NAME.VIEW.getPanelName(), viewPanel);

        Panel managePanel = initManagePanel();
        tabPane.addTab(PANEL_NAME.MANAGE.getPanelName(), managePanel);
        
        Panel bookPanel = initBookPanel();
        tabPane.addTab(PANEL_NAME.BOOK.getPanelName(), bookPanel);

        tabPane.setMnemonicAt(0, KeyEvent.VK_0);
        tabPane.setMnemonicAt(1, KeyEvent.VK_1);
        tabPane.setMnemonicAt(2, KeyEvent.VK_2);
        tabPane.setMnemonicAt(3, KeyEvent.VK_3);
        tabPane.setMnemonicAt(4, KeyEvent.VK_4);
        this.add(this.tabPane);
        
    }
}
