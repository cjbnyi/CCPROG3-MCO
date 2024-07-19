package Hotel;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelCreateHotel extends JPanel {
    private JButton createButton;
    private JTextField nameField;
    private JTextField priceField;
    
    PanelCreateHotel(ComponentFactory compFactory){
        this.setLayout(new BorderLayout());
        this.setBackground(compFactory.getRandomColor());
        ComponentBuilderBoxCenter compBuildBoxCenter = new ComponentBuilderBoxCenter(CENTER_ALIGNMENT);
        compBuildBoxCenter.setSpacing(50);
        compBuildBoxCenter.setAutoSpace(false);

        // Hotel Selection Panel;
        JPanel hotelSelectionPanel = compFactory.createJPanel();

        compBuildBoxCenter.setParent(hotelSelectionPanel);
        compBuildBoxCenter.assignComponent(compFactory.createJLabelHeading("Hotels:"));
        compBuildBoxCenter.assignComponent(compFactory.createJLabelBody("No hotels created."));

        // Content Panel
        JPanel contentPanel = compFactory.createJPanel();
        compBuildBoxCenter.setParent(contentPanel);
        compBuildBoxCenter.setAutoSpace(true);
        compBuildBoxCenter.assignComponent(compFactory.createJLabelHeading("Create Hotel"));



        JTextField nameField = compFactory.createJTextField(50, 50);
        this.nameField = nameField;
        compBuildBoxCenter.assignComponent(nameField);

        JTextField priceField = compFactory.createJTextField(50, 50);
        this.priceField = priceField;
        compBuildBoxCenter.assignComponent(priceField);

        JButton createButton = compFactory.createSingleJButton("CreateButton", 200, 50);
        compBuildBoxCenter.assignComponent(createButton);
        this.createButton = createButton;

        this.add(hotelSelectionPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
}
