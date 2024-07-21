package Hotel;

import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelEnable extends JPanel {
    private boolean hasUserAccepted;
    private JButton btnYes;
    private JButton btnNo;
    private JLabel promptMesssage;

    PanelEnable(ComponentFactory componentFactory, String promptMesssage){
        init(componentFactory, promptMesssage);
    }

    private void init(ComponentFactory compFactory, String promptMesssage){
        ComponentBuilderDirector director = new ComponentBuilderDirector(ComponentBuilderState.LAY_BOX_VERTICAL);

        this.setBackground(compFactory.getRandomColor());

        CompBuilderBoxLayout boxBuildVert = ((CompBuilderBoxLayout) director.getBuilder());

        JPanel responsePanel = compFactory.createJPanel();

        this.promptMesssage = compFactory.createJLabelBody(promptMesssage);
        this.btnYes = compFactory.createSingleJButton("Yes");
        this.btnNo = compFactory.createSingleJButton("No");

        GroupLayout layout = new GroupLayout(responsePanel);
        responsePanel.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(this.btnYes)
            .addComponent(this.btnNo)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup()
                .addComponent(this.btnYes)
                .addComponent(this.btnNo)
            )
        );

        boxBuildVert.setSpacing(5);
        boxBuildVert.setAutoSpace(true);
        boxBuildVert.setParent(this);
        boxBuildVert.addSpacing();
        boxBuildVert.setChild(this.promptMesssage);
        responsePanel.add(this.btnYes);
        responsePanel.add(this.btnNo);
        boxBuildVert.setChild(responsePanel);
    }
    
    public void setActionListener(ActionListener listener){
        btnYes.addActionListener(listener);
        btnNo.addActionListener(listener);
    }
    
    public boolean getHasUserAccepted(){
        return this.hasUserAccepted;
    }


}
