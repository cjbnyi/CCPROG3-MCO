package Hotel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

public class PanelBookReservation extends JPanel{
    JLabel bookInfo;
    JList<String> jListDatesStart;
    JList<String> jListDatesEnd;
    JTextField tfRoomName;
    JButton btnBook;
    private PanelEnable confirmationPanel;
    PanelHotelSelection hotelSelectionPanel;


    PanelBookReservation(ComponentFactory compFactory){
        init(compFactory);
        this.setVisible(true);
    }
    
    private void init(ComponentFactory compFactory){
        this.setLayout(new BorderLayout());
        this.setBackground(compFactory.getRandomColor());
        
        CompBuilderBoxLayout boxBuildVert;
        ComponentBuilderDirector director = new ComponentBuilderDirector(ComponentBuilderState.LAY_BOX_VERTICAL);

        boxBuildVert = ((CompBuilderBoxLayout) director.getBuilder());
        boxBuildVert.setSpacing(50);
        boxBuildVert.setAutoSpace(false);

        // Hotel Selection Panel;
        
        PanelHotelSelection hotelSelectionPanel = new PanelHotelSelection(compFactory);
        JPanel contentPanel = initContentPanel(boxBuildVert, compFactory);
        JPanel settingsPanel = initSettingsPanel(boxBuildVert, compFactory);
        this.hotelSelectionPanel = hotelSelectionPanel;
        this.add(hotelSelectionPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.EAST);
    }

    public JPanel initContentPanel(CompBuilderBoxLayout boxBuildVert, ComponentFactory compFactory) {
        JPanel contentPanel = compFactory.createJPanel();
        
        boxBuildVert.setParent(contentPanel);
        boxBuildVert.setAutoSpace(true);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Book Hotel"));
        this.bookInfo = compFactory.createJLabelBody("No Booking information available");

        boxBuildVert.setChild(this.bookInfo);


        return contentPanel;
    }

    public JPanel initSettingsPanel(CompBuilderBoxLayout boxBuildVert, ComponentFactory compFactory) {
        boxBuildVert.setSpacing(10);
        JPanel settingsPanel = compFactory.createJPanel();
        settingsPanel.setBorder(compFactory.createPadding());
        // String[] settingButtonNames = {
        //     "Enter Start",
        //     "Enter End",
        //     "Enter R.No."
        // };

        // ArrayList<JButton> settingButtons = compFactory.createMultipleJButtons(settingButtonNames);
        // ArrayList<JTextField> textFields = compFactory.createMultipleJTextFields(3, 2*12, 30);

        ArrayList<Integer> dateNums = new ArrayList<Integer>();
        ArrayList<String> stringDates = new ArrayList<String>();
        

        int i = 0;
        for (i = 1; i <= 31; i++){
            dateNums.add(i);
        }

        dateNums.iterator().forEachRemaining((n) -> stringDates.add(String.valueOf(n)));
        String[] dates = stringDates.toArray(new String[stringDates.size()]);

        JList<String> jListDatesStart = compFactory.createJList(dates);
        JList<String> jListDatesEnd = compFactory.createJList(dates);

        this.jListDatesStart = jListDatesStart;
        this.jListDatesEnd = jListDatesEnd;

        JScrollPane datesStartPanel = compFactory.createJScrollPane(jListDatesStart);
        JScrollPane datesEndPanel = compFactory.createJScrollPane(jListDatesEnd);
        
        this.tfRoomName = compFactory.createJTextField(2*12, 12*5);

        JButton btnBook = compFactory.createSingleJButton("Book Reservation");
        this.btnBook = btnBook;

        this.confirmationPanel = new PanelEnable(compFactory, "Are you sure?");

        boxBuildVert.setParent(settingsPanel);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Start Date:"));
        boxBuildVert.setChild(datesStartPanel);
        boxBuildVert.setChild(compFactory.createJLabelHeading("End Date:"));
        boxBuildVert.setChild(datesEndPanel);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Room Name:"));
        boxBuildVert.setChild(this.tfRoomName);
        boxBuildVert.setChild(this.btnBook);
        boxBuildVert.setChild(this.confirmationPanel);
        
        return settingsPanel;
    }

    public void setActionListener(ActionListener listener) {
        btnBook.addActionListener(listener);
        hotelSelectionPanel.setActionListener(listener);
        confirmationPanel.setActionListener(listener);
    }

    public void setDocumentListener(DocumentListener listener){
        tfRoomName.getDocument().addDocumentListener(listener);
    }

    public void setListSelectionListener(ListSelectionListener listener){
        jListDatesStart.addListSelectionListener(listener);
        jListDatesEnd.addListSelectionListener(listener);
    }

    public void setBookInfo(String text){
        this.bookInfo.setText(text);
    }

    public PanelHotelSelection getHotelPanel(){
        return this.hotelSelectionPanel;
    }

    public PanelEnable getConfirmationPanel(){
        return this.confirmationPanel;
    }
}
