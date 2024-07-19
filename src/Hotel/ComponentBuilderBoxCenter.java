package Hotel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

public class ComponentBuilderBoxCenter {
    private float alignment;
    private JComponent parent;
    private int spacing;
    private boolean doesNextComponentHasSpacing;
    
    ComponentBuilderBoxCenter(float alignment) {
        this.alignment = alignment;
        this.spacing = 0;
        this.doesNextComponentHasSpacing = true;
    }


    public void assignComponent(JComponent child) {
        child.setAlignmentX(this.alignment);
        this.parent.add(child);

        if (this.doesNextComponentHasSpacing)
            this.parent.add(Box.createRigidArea(new Dimension(0, spacing)));
    }

    public void addSpacing(){
        this.parent.add(Box.createRigidArea(new Dimension(0, spacing)));
    }


    public void setParent(JComponent parent){
        parent.setLayout(new BoxLayout(parent, BoxLayout.PAGE_AXIS));
        this.parent = parent;
    }

    public void setSpacing(int height) {
        this.spacing = height;
    }

    public void setAutoSpace(boolean hasSpacing){
        this.doesNextComponentHasSpacing = hasSpacing;
    }
}
