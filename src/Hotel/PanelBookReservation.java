package Hotel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import Hotel.HotelGUI.PANEL_NAME;

public class PanelBookReservation extends HotelPanel{
    JList<Integer> jListDatesStart;
    JList<Integer> jListDatesEnd;
    JTextField tfRoomName;
    JTextField tfGuestName;
    JButton btnBook;

    PanelBookReservation(ComponentFactory compFactory){
        super(PANEL_NAME.BOOK, compFactory);
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

        JPanel contentPanel = initContentPanel(boxBuildVert, compFactory);
        JPanel settingsPanel = initSettingsPanel(boxBuildVert, compFactory);
        this.add(this.hotelSelectionPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.EAST);
    }

    private JPanel initContentPanel(CompBuilderBoxLayout boxBuildVert, ComponentFactory compFactory) {
        JPanel contentPanel = compFactory.createJPanel();
        
        boxBuildVert.setParent(contentPanel);
        boxBuildVert.setAutoSpace(true);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Book Hotel"));
        this.contentInfo = compFactory.createJTextArea();
        this.contentInfo.setEditable(false);
        JScrollPane scrollPane = compFactory.createJScrollPane(this.contentInfo);

        boxBuildVert.setChild(scrollPane);


        return contentPanel;
    }

    private JPanel initSettingsPanel(CompBuilderBoxLayout boxBuildVert, ComponentFactory compFactory) {
        boxBuildVert.setSpacing(10);
        JPanel settingsPanel = compFactory.createJPanel();
        settingsPanel.setBorder(compFactory.createPadding());

        ArrayList<Integer> dateNums = new ArrayList<Integer>();

        int i = 0;
        for (i = 1; i <= 31; i++){
            dateNums.add(i);
        }

        Integer[] dates = dateNums.toArray(new Integer[dateNums.size()]);

        JList<Integer> jListDatesStart = compFactory.createJListInteger(dates);
        JList<Integer> jListDatesEnd = compFactory.createJListInteger(dates);

        this.jListDatesStart = jListDatesStart;
        this.jListDatesEnd = jListDatesEnd;

        JScrollPane datesStartPanel = compFactory.createJScrollPane(jListDatesStart);
        JScrollPane datesEndPanel = compFactory.createJScrollPane(jListDatesEnd);
        
        this.tfRoomName = compFactory.createJTextField(2*12, 100);
        this.tfGuestName = compFactory.createJTextField(2*12, 100);

        JButton btnBook = compFactory.createSingleJButton(this.jButtonList, "Book Reservation");
        this.btnBook = btnBook;
        
        this.confirmationPanel.setVisible(false);

        boxBuildVert.setParent(settingsPanel);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Start Date:"));
        boxBuildVert.setChild(datesStartPanel);
        boxBuildVert.setChild(compFactory.createJLabelHeading("End Date:"));
        boxBuildVert.setChild(datesEndPanel);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Room Name:"));
        boxBuildVert.setChild(this.tfRoomName);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Guest Name"));
        boxBuildVert.setChild(this.tfGuestName);
        boxBuildVert.setChild(this.btnBook);
        boxBuildVert.setChild(this.confirmationPanel);
        
        return settingsPanel;
    }

    public void setActionListener(ActionListener listener) {
        btnBook.addActionListener(listener);
        this.hotelSelectionPanel.setActionListener(listener);
        confirmationPanel.setActionListener(listener);
    }

    public void setDocumentListener(DocumentListener listener){
        tfRoomName.getDocument().addDocumentListener(listener);
        tfGuestName.getDocument().addDocumentListener(listener);
    }

    public void setListSelectionListener(ListSelectionListener listener){
        jListDatesStart.addListSelectionListener(listener);
        jListDatesEnd.addListSelectionListener(listener);
    }

    public String getRoomName(){
        return this.tfRoomName.getText();
    }

    public String getGuestName(){
        return this.tfGuestName.getText();
    }

    public JList<Integer> getJListDatesStart() {
        return jListDatesStart;
    }

    public JList<Integer> getJListDatesEnd() {
        return jListDatesEnd;
    }
}
