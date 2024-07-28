package Hotel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import Hotel.HotelGUI.PANEL_NAME;

public class PanelViewHotel extends HotelPanel {
    private JButton viewHighLevel;
    private JButton viewLowLevel1;
    private JButton viewLowLevel2;
    private JButton viewLowLevel3;
    private JTextField tfRoomName;
    private JTextField tfReservation;
    private JList<Integer> jLDateSelected;

    public enum COMPONENT_NAMES {
        viewHighLevel,
        viewLowLevel1,
        viewLowLevel2,
        viewLowLevel3,
        tfRoomName,
        tfReservation,
        jLDateSelected,
        hotelSelectionPanel,
        contentInfo;
    }

    PanelViewHotel(ComponentFactory compFactory){
        super(PANEL_NAME.VIEW, compFactory);
        init(compFactory);
        this.setVisible(true);
    }
    
    private void init(ComponentFactory compFactory){
        this.setLayout(new BorderLayout());
        this.setBackground(compFactory.getRandomColor());
        
        ComponentBuilderDirector director = new ComponentBuilderDirector(ComponentBuilderState.LAY_BOX_VERTICAL);
        CompBuilderBoxLayout boxBuildVert = ((CompBuilderBoxLayout) director.getBuilder());

        

        boxBuildVert.setSpacing(25);
        boxBuildVert.setAutoSpace(false);
        JPanel contentPanel = initContentPanel(boxBuildVert, compFactory);
        JPanel settingsPanel = initSettingsPanel(boxBuildVert, compFactory);
        this.add(this.hotelSelectionPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.EAST);
    }

    public JPanel initSettingsPanel(CompBuilderBoxLayout boxBuildVert, ComponentFactory compFactory) {
        JPanel settingsPanel = compFactory.createJPanel();
        settingsPanel.setBorder(compFactory.createPadding());
        String[] settingButtonNames = {
            "View High level",
            "View Date",
            "View Room",
            "View Reservation"
        };

        ArrayList<JButton> settingButtons = compFactory.createMultipleJButtons(this.jButtonList, settingButtonNames);
        ArrayList<JTextField> inputFields = compFactory.createMultipleJTextFields(2, 30, 140);
        ArrayList<Integer> dateNums = new ArrayList<Integer>();

        int i = 0;
        for (i = 1; i <= 31; i++){
            dateNums.add(i);
        }

        Integer[] dates = dateNums.toArray(new Integer[dateNums.size()]);

        JList<Integer> jListDates = compFactory.createJListInteger(dates);


        this.jLDateSelected = jListDates;

        JScrollPane datesStartPanel = compFactory.createJScrollPane(jListDates);

        boxBuildVert.setParent(settingsPanel);
        boxBuildVert.setChild(compFactory.createJLabelHeading("View Options:"));
        boxBuildVert.setChild(compFactory.createJLabelBody("High Level Options:"));
        this.viewHighLevel = settingButtons.get(0);
        boxBuildVert.setChild(settingButtons.get(0));
        boxBuildVert.setChild(compFactory.createJLabelBody("Low Level Options:"));
        boxBuildVert.setChild(datesStartPanel);
        boxBuildVert.setChild(settingButtons.get(1));
        boxBuildVert.setChild(inputFields.get(0));
        boxBuildVert.setChild(settingButtons.get(2));
        boxBuildVert.setChild(inputFields.get(1));
        boxBuildVert.setChild(settingButtons.get(3));
        this.viewLowLevel1 = settingButtons.get(1);
        this.viewLowLevel2 = settingButtons.get(2);
        this.viewLowLevel3 = settingButtons.get(3);
        this.tfRoomName =  inputFields.get(0);
        this.tfReservation =  inputFields.get(1);


        return settingsPanel;
    }

    public JPanel initContentPanel(CompBuilderBoxLayout boxBuildVert, ComponentFactory compFactory) {
        JPanel contentPanel = compFactory.createJPanel();
        JTextArea viewInfo = compFactory.createJTextArea();
        JScrollPane hotelListScrollPane = compFactory.createJScrollPane(viewInfo);
        viewInfo.setEditable(false);
        this.contentInfo = viewInfo;
        boxBuildVert.setParent(contentPanel);
        boxBuildVert.setAutoSpace(true);
        boxBuildVert.setChild(compFactory.createJLabelHeading("View Hotel"));
        boxBuildVert.setChild(hotelListScrollPane);
        
        return contentPanel;
    }

    public void setListSelectionListener(ListSelectionListener listener){
        jLDateSelected.addListSelectionListener(listener);
    }

    public void setDocumentListener(DocumentListener listener){
        tfRoomName.getDocument().addDocumentListener(listener);
        tfReservation.getDocument().addDocumentListener(listener);
    }

    public void setActionListener(ActionListener listener){
        viewHighLevel.addActionListener(listener);
        
        viewLowLevel1.addActionListener(listener);
        viewLowLevel2.addActionListener(listener);
        viewLowLevel3.addActionListener(listener);
        hotelSelectionPanel.setActionListener(listener);
    }

    public JTextField getTfReservation() {
        return tfReservation;
    }

    public JTextField getTfRoomName() {
        return tfRoomName;
    }

    public JList<Integer> getjLDateSelected() {
        return jLDateSelected;
    }

   
}
