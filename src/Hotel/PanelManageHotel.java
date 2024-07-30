package Hotel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionListener;

import Hotel.HotelGUI.PANEL_NAME;

public class PanelManageHotel extends HotelPanel {
    private JButton btnChangeHotelName;
    private JButton btnAddRooms;
    private JButton btnRemoveRooms;
    private JButton btnUpdatePrice;
    private JButton btnRemoveReservation;
    private JButton btnRemoveHotel;
    private JButton btnApplyDiscountCode;
    private JButton btnRemoveDiscountCode;
    private JTextField tfChangeHotelName;
    private JTextField tfAddRoomName;
    private JTextField tfRemoveRoomName;
    private JTextField tfUpdatePrice;
    private JTextField tfRemoveReservation;
    private JTextField tfDiscountCode;
    private JTextField tfRoomName;
    private JList<Integer> jlDates;

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

    private JPanel initRemoveReservation(ComponentBuilderDirector director, ComponentFactory compFactory, ArrayList<JLabel> jLabels, ArrayList<JTextField> textFields, ArrayList<JButton> buttons){
        director.setBuilder(ComponentBuilderState.LAY_GRID);
        CompBuilderGridLayout gridLayout = (CompBuilderGridLayout) director.getBuilder();
        int componentIndex = 4;
        JPanel removeReservations = compFactory.createJPanel();
        JPanel buttonPanel = compFactory.createJPanel();
        gridLayout.setParent(buttonPanel);
        gridLayout.createNewColumn(GroupLayout.Alignment.CENTER);
        gridLayout.attachColumnGroupComponent(jLabels.get(componentIndex));
        gridLayout.attachColumnGroupComponent(textFields.get(componentIndex));
        gridLayout.attachColumnGroupComponent(buttons.get(componentIndex));

        gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);
        gridLayout.attachRowGroupComponent(jLabels.get(componentIndex));
        gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);
        gridLayout.attachRowGroupComponent(textFields.get(componentIndex));
        gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);
        gridLayout.attachRowGroupComponent(buttons.get(componentIndex));
        gridLayout.finalizeLayout();

        this.jlDates = compFactory.createJListIntegerFilled(31);
        this.confirmationPanel.setVisible(false);
        JScrollPane jspPane = compFactory.createJScrollPaneJList(this.jlDates);
        director.setBuilder(ComponentBuilderState.LAY_BOX_VERTICAL);
        CompBuilderBoxLayout boxBuilderVert = (CompBuilderBoxLayout) director.getBuilder();

        boxBuilderVert.setParent(removeReservations);
        boxBuilderVert.setChild(compFactory.createJLabelHeading("Remove Reservation"));
        boxBuilderVert.setChild(buttonPanel);
        boxBuilderVert.setChild(compFactory.createJLabelHeading("Date of Reservation:"));
        boxBuilderVert.setChild(jspPane);

        return removeReservations;
    }

    private JPanel initManageSettingsPanel(CompBuilderGridLayout gridLayout, ComponentFactory compFactory, ArrayList<JLabel> jLabels, ArrayList<JTextField> textFields, ArrayList<JButton> buttons){
        JPanel manageSettingsPanel = compFactory.createJPanel();
        
        gridLayout.setParent(manageSettingsPanel);
        int i = 0;
        gridLayout.createNewColumn(GroupLayout.Alignment.TRAILING);

        int items = 4;
        for (i = 0; i < items; i++) {
            gridLayout.attachColumnGroupComponent(jLabels.get(i));
        }

        gridLayout.createNewColumn(GroupLayout.Alignment.LEADING);
        for (i = 0; i < items; i++) {
            gridLayout.attachColumnGroupComponent(textFields.get(i));
        }

        gridLayout.createNewColumn(GroupLayout.Alignment.LEADING);
        for (i = 0; i < items; i++) {
            gridLayout.attachColumnGroupComponent(buttons.get(i));
        }
        

        for (i = 0; i < items; i++) {
            gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);
            gridLayout.attachRowGroupComponent(jLabels.get(i));
            gridLayout.attachRowGroupComponent(textFields.get(i));
            gridLayout.attachRowGroupComponent(buttons.get(i));

        }
        gridLayout.finalizeLayout();
        return manageSettingsPanel;
    }

    private JPanel initDiscountPanel(CompBuilderGridLayout gridLayout, ComponentFactory compFactory, ArrayList<JLabel> jLabels, ArrayList<JTextField> textFields){
        JPanel discountPanel = compFactory.createJPanel();
        gridLayout.setParent(discountPanel);
        gridLayout.createNewColumn(GroupLayout.Alignment.TRAILING);
        gridLayout.attachColumnGroupComponent(jLabels.get(6));
        gridLayout.attachColumnGroupComponent(jLabels.get(7));
        gridLayout.createNewColumn(GroupLayout.Alignment.LEADING);
        gridLayout.attachColumnGroupComponent(textFields.get(5));
        gridLayout.attachColumnGroupComponent(textFields.get(6));
        gridLayout.createNewColumn(GroupLayout.Alignment.LEADING);
        
        gridLayout.attachColumnGroupComponent(this.btnApplyDiscountCode);
        gridLayout.attachColumnGroupComponent(this.btnRemoveDiscountCode);

        gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);
        gridLayout.attachRowGroupComponent(jLabels.get(6));
        gridLayout.attachRowGroupComponent(textFields.get(5));
        gridLayout.attachRowGroupComponent(this.btnApplyDiscountCode);

        gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);
        gridLayout.attachRowGroupComponent(jLabels.get(7));
        gridLayout.attachRowGroupComponent(textFields.get(6));
        gridLayout.attachRowGroupComponent(this.btnRemoveDiscountCode);
        gridLayout.finalizeLayout();
        return discountPanel;
    }

    private JPanel initContentPanel(ComponentBuilderDirector director, ComponentFactory compFactory) {
        JPanel contentPanel = compFactory.createJPanel();
        String buttonNames[] = {
            "Change Name",
            "Add Rooms",
            "Remove Rooms",
            "Update Price",
            "Remove Reservation",
            "Remove Hotel",
            "Apply Discount Code",
            "Remove Discount Code"
        };

        String labelNames[] = {
            "New Hotel Name",
            "New Room Name",
            "Room to Remove",
            "New BasePrice",
            "Name of Room with Reservation",
            "Name of Hotel To Remove",
            "Discount Code",
            "Room Name"
        };
        
        ArrayList<JButton> buttons = compFactory.createMultipleJButtons(this.jButtonList, buttonNames);
        ArrayList<JTextField> textFields = compFactory.createMultipleJTextFields(8, 12*2, 100);
        ArrayList<JLabel> jLabels = compFactory.createMultipleJLabel(labelNames);
        
        
        this.btnChangeHotelName     = buttons.get(0);
        this.btnAddRooms            = buttons.get(1);
        this.btnRemoveRooms         = buttons.get(2);
        this.btnUpdatePrice         = buttons.get(3);
        this.btnRemoveReservation   = buttons.get(4);
        this.btnRemoveHotel         = buttons.get(5);
        this.btnApplyDiscountCode   = buttons.get(6);
        this.btnRemoveDiscountCode  = buttons.get(7);

        this.tfChangeHotelName      = textFields.get(0);
        this.tfAddRoomName          = textFields.get(1);
        this.tfRemoveRoomName       = textFields.get(2);
        this.tfUpdatePrice          = textFields.get(3);
        this.tfRemoveReservation    = textFields.get(4);
        this.tfDiscountCode    = textFields.get(5);
        this.tfRoomName   = textFields.get(6);

        JTextArea contentInfo = compFactory.createJTextArea();
        JScrollPane contentPane = compFactory.createJScrollPane(contentInfo);
        contentInfo.setEditable(false);
        this.contentInfo = contentInfo;

        
        
        JPanel removeReservationsPanel = initRemoveReservation(director, compFactory, jLabels, textFields, buttons);
        director.setBuilder(ComponentBuilderState.LAY_GRID);
        CompBuilderGridLayout gridLayout = ((CompBuilderGridLayout) director.getBuilder());
        JPanel manageSettingsPanel = initManageSettingsPanel(gridLayout, compFactory, jLabels, textFields, buttons);
        JPanel discountPanel = initDiscountPanel(gridLayout, compFactory, jLabels, textFields);

        director.setBuilder(ComponentBuilderState.LAY_BOX_HORIZONTAL);
        CompBuilderBoxLayout boxBuildHor = ((CompBuilderBoxLayout) director.getBuilder());
        JPanel defaultActionsPanel = compFactory.createJPanel();
        boxBuildHor.setParent(defaultActionsPanel);
        boxBuildHor.setChild(manageSettingsPanel);
        boxBuildHor.setChild(removeReservationsPanel);

        director.setBuilder(ComponentBuilderState.LAY_BOX_VERTICAL);
        CompBuilderBoxLayout boxBuildVert = ((CompBuilderBoxLayout) director.getBuilder());
        boxBuildVert.setParent(contentPanel);
        boxBuildVert.setAutoSpace(true);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Manage Hotel"));
        boxBuildVert.setChild(contentPane);
        boxBuildVert.setChild(defaultActionsPanel);
        boxBuildVert.setChild(discountPanel);
        boxBuildVert.setChild(jLabels.get(5));
        boxBuildVert.setChild(buttons.get(5));
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
        btnApplyDiscountCode.addActionListener(listener);
        btnRemoveDiscountCode.addActionListener(listener);
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

    public void setListSelectionListener(ListSelectionListener listener){
        jlDates.addListSelectionListener(listener);
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

    public String getTfDiscountCode(){
        return tfDiscountCode.getText();
    }

    public String getTfDiscountCodeRoomName(){
        return tfRoomName.getText();
    }

    public JList<Integer> getJListDates(){
        return this.jlDates;
    }

}
