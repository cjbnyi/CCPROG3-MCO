package Hotel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import Hotel.HotelGUI.PANEL_NAME;

public class PanelManageHotel extends HotelPanel {
    private JButton btnChangeHotelName;
    private JButton btnAddRooms;
    private JButton btnRemoveRooms;
    private JButton btnUpdatePrice;
    private JButton btnRemoveReservation;
    private JButton btnRemoveHotel;
    private JTextField tfChangeHotelName;
    private JTextField tfAddRoomName;
    private JTextField tfRemoveRoomName;
    private JTextField tfUpdatePrice;
    private JTextField tfRemoveReservation;


    PanelManageHotel(ComponentFactory compFactory){
        super(PANEL_NAME.MANAGE, compFactory);
        init(compFactory);
        this.setVisible(true);
    }
    
    private void init(ComponentFactory compFactory){
        this.setLayout(new BorderLayout());
        this.setBackground(compFactory.getRandomColor());
        
        CompBuilderBoxLayout boxBuildVert;
        
        ComponentBuilderDirector director = new ComponentBuilderDirector(ComponentBuilderState.LAY_BOX_VERTICAL);

        boxBuildVert = ((CompBuilderBoxLayout) director.getBuilder());
        boxBuildVert.setSpacing(10);
        boxBuildVert.setAutoSpace(false);


        JPanel contentPanel = initContentPanel(director, compFactory);

        this.add(this.hotelSelectionPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    public JPanel initContentPanel(ComponentBuilderDirector director, ComponentFactory compFactory) {
        JPanel contentPanel = compFactory.createJPanel();
        String buttonNames[] = {
            "Change Name",
            "Add Rooms",
            "Remove Rooms",
            "Update Price",
            "Remove Reservation",
            "Remove Hotel",
        };
        
        ArrayList<JButton> buttons = compFactory.createMultipleJButtons(this.jButtonList, buttonNames);
        ArrayList<JTextField> textFields = compFactory.createMultipleJTextFields(5, 12*2, 100);
        ArrayList<JLabel> jLabels = compFactory.createMultipleJLabel(buttonNames);

        this.btnChangeHotelName     = buttons.get(0);
        this.btnAddRooms            = buttons.get(1);
        this.btnRemoveRooms         = buttons.get(2);
        this.btnUpdatePrice         = buttons.get(3);
        this.btnRemoveReservation   = buttons.get(4);
        this.btnRemoveHotel         = buttons.get(5);

        this.tfChangeHotelName      = textFields.get(0);
        this.tfAddRoomName          = textFields.get(1);
        this.tfRemoveRoomName       = textFields.get(2);
        this.tfUpdatePrice          = textFields.get(3);
        this.tfRemoveReservation    = textFields.get(4);
        
        CompBuilderBoxLayout boxBuildVert = ((CompBuilderBoxLayout) director.getBuilder());
        director.setBuilder(ComponentBuilderState.LAY_GRID);
        CompBuilderGridLayout gridLayout = ((CompBuilderGridLayout) director.getBuilder());

        JTextArea contentInfo = compFactory.createJTextArea();
        JScrollPane contentPane = compFactory.createJScrollPane(contentInfo);
        contentInfo.setEditable(false);
        this.contentInfo = contentInfo;

        boxBuildVert.setParent(contentPanel);
        boxBuildVert.setAutoSpace(true);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Manage Hotel"));
        boxBuildVert.setChild(contentPane);
        JPanel manageSettingsPanel = compFactory.createJPanel();

        gridLayout.setParent(manageSettingsPanel);
        int i = 0;


        gridLayout.createNewColumn(GroupLayout.Alignment.TRAILING);
        int items = 5;
        for (i = 0; i < items; i++) {
            gridLayout.createColumnGroupComponent(jLabels.get(i));
        }
        gridLayout.createNewColumn(GroupLayout.Alignment.LEADING);
        for (i = 0; i < items; i++) {
            gridLayout.createColumnGroupComponent(textFields.get(i));
        }
        gridLayout.createNewColumn(GroupLayout.Alignment.LEADING);
        for (i = 0; i < items; i++) {
            gridLayout.createColumnGroupComponent(buttons.get(i));
        }

        gridLayout.createNewColumn(GroupLayout.Alignment.TRAILING);


        for (i = 0; i < items; i++) {
            gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);
            gridLayout.createRowGroupComponent(jLabels.get(i));
            gridLayout.createRowGroupComponent(textFields.get(i));
            gridLayout.createRowGroupComponent(buttons.get(i));

        }

        gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);

        gridLayout.finalizeLayout();
        this.confirmationPanel.setVisible(false);
        
        boxBuildVert.setChild(manageSettingsPanel);
        boxBuildVert.setChild(jLabels.get(i));
        boxBuildVert.setChild(buttons.get(i));
        boxBuildVert.setChild(this.confirmationPanel);
        return contentPanel;
    }

    public void setActionListener(ActionListener listener){
        btnChangeHotelName.addActionListener(listener);
        btnAddRooms.addActionListener(listener);
        btnRemoveRooms.addActionListener(listener);
        btnUpdatePrice.addActionListener(listener);
        btnRemoveReservation.addActionListener(listener);
        btnRemoveHotel.addActionListener(listener);
        hotelSelectionPanel.setActionListener(listener);
        confirmationPanel.setActionListener(listener);
    };

    public void setDocumentListener(DocumentListener listener){
        tfChangeHotelName.getDocument().addDocumentListener(listener);
        tfAddRoomName.getDocument().addDocumentListener(listener);
        tfRemoveRoomName.getDocument().addDocumentListener(listener);
        tfUpdatePrice.getDocument().addDocumentListener(listener);
        tfRemoveReservation.getDocument().addDocumentListener(listener);
    }

    public String getTfChangeName(){
        return tfChangeHotelName.getText();
    }

    public String getTfAddRoomName(){
        return tfAddRoomName.getText();
    }

    public String getTfRemoveRoomName(){
        return tfRemoveRoomName.getText();
    }

    public String getTfUpdatePrice(){
        return tfUpdatePrice.getText();
    }

    public String getTfRemoveReservation(){
        return tfRemoveReservation.getText();
    }

    
}
