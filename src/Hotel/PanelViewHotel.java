package Hotel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelViewHotel extends JPanel{
    private JButton viewHighLevel;
    private JButton viewLowLevel1;
    private JButton viewLowLevel2;
    private JButton viewLowLevel3;
    private PanelHotelSelection hotelSelectionPanel;
    private JLabel viewInfo;

    PanelViewHotel(ComponentFactory compFactory){
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

        // Hotel Selection Panel;
        PanelHotelSelection hotelSelectionPanel = new PanelHotelSelection(compFactory);
        JPanel contentPanel = initContentPanel(boxBuildVert, compFactory);
        JPanel settingsPanel = initSettingsPanel(boxBuildVert, compFactory);
        this.hotelSelectionPanel = hotelSelectionPanel;
        this.add(hotelSelectionPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
        this.add(settingsPanel, BorderLayout.EAST);
    }

    public JPanel initSettingsPanel(CompBuilderBoxLayout boxBuildVert, ComponentFactory compFactory) {
        JPanel settingsPanel = compFactory.createJPanel();
        settingsPanel.setBorder(compFactory.createPadding());
        String[] settingButtonNames = {
            "View High level",
            "View 1",
            "View 2",
            "View 3"
        };

        ArrayList<JButton> settingButtons = compFactory.createMultipleJButtons(settingButtonNames);


        boxBuildVert.setParent(settingsPanel);
        boxBuildVert.setChild(compFactory.createJLabelHeading("View Options:"));
        boxBuildVert.setChild(compFactory.createJLabelBody("High Level Options:"));
        this.viewHighLevel = settingButtons.get(0);
        boxBuildVert.setChild(settingButtons.get(0));
        boxBuildVert.setChild(compFactory.createJLabelBody("Low Level Options:"));
        boxBuildVert.setChild(settingButtons.get(1));
        boxBuildVert.setChild(settingButtons.get(2));
        boxBuildVert.setChild(settingButtons.get(3));
        this.viewLowLevel1 = settingButtons.get(1);
        this.viewLowLevel2 = settingButtons.get(2);
        this.viewLowLevel3 = settingButtons.get(3);
        return settingsPanel;
    }

    public JPanel initContentPanel(CompBuilderBoxLayout boxBuildVert, ComponentFactory compFactory) {
        JPanel contentPanel = compFactory.createJPanel();
        JLabel viewInfo = compFactory.createJLabelBody("No hotel selected to view information");
        this.viewInfo = viewInfo;
        boxBuildVert.setParent(contentPanel);
        boxBuildVert.setAutoSpace(true);
        boxBuildVert.setChild(compFactory.createJLabelHeading("View Hotel"));
        boxBuildVert.setChild(viewInfo);
        
        return contentPanel;
    }

    public void setActionListener(ActionListener listener){
        viewHighLevel.addActionListener(listener);
        viewLowLevel1.addActionListener(listener);
        viewLowLevel2.addActionListener(listener);
        viewLowLevel3.addActionListener(listener);
        hotelSelectionPanel.setActionListener(listener);
    }
    
    public void setViewInfo(String text){
        this.viewInfo.setText(text);
    }
   
    public PanelHotelSelection getHotelPanel(){
        return this.hotelSelectionPanel;
    }

}
