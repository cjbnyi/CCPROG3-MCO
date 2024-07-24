package Hotel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import java.applet.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class HotelGUI extends JFrame {
    private JTabbedPane tabPane;
            
    private PanelHome panelHome;
    private PanelCreateHotel panelCreateHotel;
    private PanelViewHotel panelViewHotel;
    private PanelManageHotel panelManageHotel;
    private PanelBookReservation panelBookReservation;
    private ArrayList<PanelHotelSelection> panelHotelSelectionList;
    private boolean isEnableOnly;
    private int enableOnly;
    
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
        this.enableOnly = -1;
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

    private void initializeComponents(){
        this.tabPane = new JTabbedPane();
        this.panelHome = new PanelHome(this.componentFactory);
        this.panelCreateHotel= new PanelCreateHotel(this.componentFactory);
        this.panelViewHotel = new PanelViewHotel(this.componentFactory);
        this.panelManageHotel = new PanelManageHotel(this.componentFactory);
        this.panelBookReservation = new PanelBookReservation(this.componentFactory);

        tabPane.addTab(PANEL_NAME.HOME.getPanelName(), this.panelHome);
        tabPane.addTab(PANEL_NAME.CREATE.getPanelName(), this.panelCreateHotel);
        tabPane.addTab(PANEL_NAME.VIEW.getPanelName(), this.panelViewHotel);
        tabPane.addTab(PANEL_NAME.MANAGE.getPanelName(), this.panelManageHotel);
        tabPane.addTab(PANEL_NAME.BOOK.getPanelName(), this.panelBookReservation);
        this.panelHotelSelectionList = new ArrayList<PanelHotelSelection>();
        this.panelHotelSelectionList.add(this.panelCreateHotel.getHotelPanel());
        this.panelHotelSelectionList.add(this.panelViewHotel.getHotelPanel());
        this.panelHotelSelectionList.add(this.panelManageHotel.getHotelPanel());
        this.panelHotelSelectionList.add(this.panelBookReservation.getHotelPanel());
        this.add(this.tabPane); 
    }

    public void setActionListener(ActionListener listener){
        this.panelHome.setActionListener(listener);
        this.panelCreateHotel.setActionListener(listener);
        this.panelViewHotel.setActionListener(listener);
        this.panelManageHotel.setActionListener(listener);
        this.panelBookReservation.setActionListener(listener);
    }

    public void setDocumentListener(DocumentListener listener){
        this.panelCreateHotel.setDocumentListener(listener);
        this.panelManageHotel.setDocumentListener(listener);
        this.panelBookReservation.setDocumentListener(listener);
    }

    public void setListSelectionListener(ListSelectionListener listener){
        this.panelBookReservation.setListSelectionListener(listener);
    }

    public PanelCreateHotel getPanelCreateHotel(){
        return this.panelCreateHotel;
    }

    public void setHotelList(String text){
        this.panelCreateHotel.getHotelPanel().setHotelList(text);
        this.panelViewHotel.getHotelPanel().setHotelList(text);
        this.panelManageHotel.getHotelPanel().setHotelList(text);
        this.panelBookReservation.getHotelPanel().setHotelList(text);
    }

    public void setSelectedSelectedHotel(String text){
        this.panelCreateHotel.getHotelPanel().setSelectedHotel(text);
        this.panelViewHotel.getHotelPanel().setSelectedHotel(text);
        this.panelManageHotel.getHotelPanel().setSelectedHotel(text);
        this.panelBookReservation.getHotelPanel().setSelectedHotel(text);
    }

    public void setBtnNextHotelEnable(Boolean enable){
        this.panelCreateHotel.getHotelPanel().setBtnNextHotelEnable(enable);
        this.panelViewHotel.getHotelPanel().setBtnNextHotelEnable(enable);
        this.panelManageHotel.getHotelPanel().setBtnNextHotelEnable(enable);
        this.panelBookReservation.getHotelPanel().setBtnNextHotelEnable(enable);
    }

    public void setBtnPrevHotelEnable(Boolean enable){
        this.panelCreateHotel.getHotelPanel().setBtnPrevHotelEnable(enable);
        this.panelViewHotel.getHotelPanel().setBtnPrevHotelEnable(enable);
        this.panelManageHotel.getHotelPanel().setBtnPrevHotelEnable(enable);
        this.panelBookReservation.getHotelPanel().setBtnPrevHotelEnable(enable);
    }

    public void setOnlyPanelEnable(PANEL_NAME panel){
        this.isEnableOnly = true;
        switch (panel){
            case HOME:
            this.enableOnly = 0;
                break;
            case CREATE:
            this.enableOnly = 1;
                break;
            case VIEW:
            this.enableOnly = 2;
                break;
            case MANAGE:
            this.enableOnly = 3;
                break;
            case BOOK:
            this.enableOnly = 4;
                break;
        }
        this.tabPane.setSelectedIndex(this.enableOnly);
        int i = 0;
        for (PANEL_NAME _ : PANEL_NAME.values()){
            if (i != this.enableOnly)
                tabPane.setEnabledAt(i, false);
            i++;
        }
    }

    public void resetPanelEnable(){
        this.isEnableOnly = false;
        this.enableOnly = -1;
        int i = 0;
        for (PANEL_NAME _ : PANEL_NAME.values()){
            if (i != this.enableOnly)
                tabPane.setEnabledAt(i, true);
            i++;
        }
    }

    public boolean getIsEnableOnly(){
        return this.isEnableOnly;
    }

    public int getEnabledOnlyPanel(){
        return this.enableOnly;
    }

    public ArrayList<PanelHotelSelection> getPanelHotelSelectionList(){
        return this.panelHotelSelectionList;
    }

    public void selectJtabbedPanelView(int i){
        this.tabPane.setSelectedIndex(i);
    }


    public int getViewedPanel(){
        return this.tabPane.getSelectedIndex();
    }
}
