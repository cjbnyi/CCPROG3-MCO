package Hotel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import Hotel.HotelGUI.PANEL_NAME;

public class PanelCreateHotel extends HotelPanel {
    private JButton createButton;
    private JTextField nameField;
    private JTextField priceField;
    private final String defaultHotelInfoText = "Register name of hotel and its price per night";
    PANEL_NAME panelName;

    PanelCreateHotel(ComponentFactory compFactory){
        super(PANEL_NAME.CREATE, compFactory);
        init(compFactory);
        panelName = PANEL_NAME.CREATE;
        this.setVisible(true);
    }
    
    private void init(ComponentFactory compFactory){
        ComponentBuilderDirector director = new ComponentBuilderDirector(ComponentBuilderState.LAY_BOX_VERTICAL);

        this.setLayout(new BorderLayout());
        this.setBackground(compFactory.getRandomColor());

   
        JPanel contentPanel = initContentPanel(director, compFactory);
    
        this.add(this.hotelSelectionPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel initPricePanel(CompBuilderGridLayout gridLayout, ComponentFactory compFactory){
        JPanel inputPanel = compFactory.createJPanel();
        GroupLayout layout = new GroupLayout(inputPanel);

        gridLayout.setParent(inputPanel);
        //inputPanel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        JLabel nameFieldLabel = compFactory.createJLabelBody("Name of Hotel: ");
        JTextField nameField = compFactory.createJTextField(36, 12*15); // Letter size * 30 
        this.nameField = nameField;

        JLabel priceFieldLabel = compFactory.createJLabelBody("Price of Stay: ");
        JTextField priceField = compFactory.createJTextField(36, 12*15);
        this.priceField = priceField;

        gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);
        gridLayout.attachRowGroupComponent(nameFieldLabel);
        gridLayout.attachRowGroupComponent(nameField);
        gridLayout.createNewRow(GroupLayout.Alignment.BASELINE);
        gridLayout.attachRowGroupComponent(priceFieldLabel);
        gridLayout.attachRowGroupComponent(priceField);

        gridLayout.createNewColumn(GroupLayout.Alignment.TRAILING);
        gridLayout.attachColumnGroupComponent(nameFieldLabel);
        gridLayout.attachColumnGroupComponent(priceFieldLabel);
        gridLayout.createNewColumn(GroupLayout.Alignment.TRAILING);
        gridLayout.attachColumnGroupComponent(nameField);
        gridLayout.attachColumnGroupComponent(priceField);
        gridLayout.finalizeLayout();

        return inputPanel;
    }

    private JPanel initContentPanel(ComponentBuilderDirector director, ComponentFactory compFactory){
        
        
        JPanel contentPanel = compFactory.createJPanel();
        JLabel contentTitle = compFactory.createJLabelHeading("Create Hotel");
        
        JTextArea hotelInfo = compFactory.createJTextArea();
        this.contentInfo = hotelInfo;
        
        JButton createButton = compFactory.createSingleJButton(this.jButtonList, "Create Hotel Instance", 200, 50);
        this.createButton = createButton;

        director.setBuilder(ComponentBuilderState.LAY_GRID);
        CompBuilderGridLayout gridLayout = (CompBuilderGridLayout) director.getBuilder();
        JPanel inputPanel = initPricePanel(gridLayout, compFactory);

        this.confirmationPanel.setVisible(false);
        
        director.setBuilder(ComponentBuilderState.LAY_BOX_VERTICAL);
        CompBuilderBoxLayout boxBuildVert = ((CompBuilderBoxLayout) director.getBuilder());
        boxBuildVert.setSpacing(50);
        boxBuildVert.setAutoSpace(false);
        boxBuildVert.setAutoSpace(true);

        boxBuildVert.setParent(contentPanel);
        boxBuildVert.setChild(contentTitle);
        boxBuildVert.setChild(hotelInfo);
        boxBuildVert.setChild(inputPanel);
        boxBuildVert.setChild(createButton);
        boxBuildVert.setChild(this.confirmationPanel);
        
        return contentPanel;
    }

    public void setActionListener(ActionListener listener){
        createButton.addActionListener(listener);
        hotelSelectionPanel.setActionListener(listener);
        confirmationPanel.setActionListener(listener);
    }
    
    public void setDocumentListener(DocumentListener listener){
        nameField.getDocument().addDocumentListener(listener);
        priceField.getDocument().addDocumentListener(listener);
    }

    public JTextField getNameField(){
        return this.nameField;
    }

    public JTextField getPriceField(){
        return this.priceField;
    }

    public void resetHotelInfoText(String text){
        this.contentInfo.setText(defaultHotelInfoText);
    }



}
