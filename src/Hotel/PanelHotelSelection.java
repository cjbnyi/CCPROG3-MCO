package Hotel;

import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelHotelSelection extends JPanel{
    private JLabel selectedHotel;
    private JLabel hotelList;
    private JButton btnSelectHotel;
    private JButton btnNextHotel;
    private JButton btnPrevHotel;

    PanelHotelSelection(ComponentFactory compFactory){
        ComponentBuilderDirector director = new ComponentBuilderDirector(ComponentBuilderState.LAY_BOX_VERTICAL);
        CompBuilderBoxLayout boxBuildVert = ((CompBuilderBoxLayout) director.getBuilder());
        boxBuildVert.setSpacing(50);
        boxBuildVert.setAutoSpace(false);
        initHotelSelectionPanel(boxBuildVert, compFactory);
        this.setVisible(true);
    }

    private void initHotelSelectionPanel(CompBuilderBoxLayout boxBuildVert, ComponentFactory compFactory) {
        this.setBackground(compFactory.getRandomColor());
        this.setBorder(compFactory.createPadding());
        JPanel responsePanel = compFactory.createJPanel();
        JLabel hotelList = compFactory.createJLabelBody("No hotels created.");
        JLabel selectedHotel = compFactory.createJLabelBody("None selected");
        JButton btnSelectHotel = compFactory.createSingleJButton("Select Hotel");
        JButton btnNextButton = compFactory.createSingleJButton("<");
        JButton btnPrevButton = compFactory.createSingleJButton(">");

        this.hotelList = hotelList;
        this.selectedHotel = hotelList;
        this.btnSelectHotel = btnSelectHotel;
        this.btnNextHotel = btnNextButton;
        this.btnPrevHotel = btnPrevButton;

        GroupLayout layout = new GroupLayout(responsePanel);
        responsePanel.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(btnNextButton)
            .addComponent(btnPrevButton)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
                .addComponent(btnNextButton)
                .addComponent(btnPrevButton)
            )
        );

        boxBuildVert.setParent(this);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Select Hotel"));
        boxBuildVert.setChild(selectedHotel);
        boxBuildVert.setChild(responsePanel);
        boxBuildVert.setChild(btnSelectHotel);
        boxBuildVert.setChild(compFactory.createJLabelHeading("Hotels:"));
        boxBuildVert.setChild(hotelList);
        
    }


}
