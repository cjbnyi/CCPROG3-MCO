package Hotel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

public class CompBuilderBoxLayout extends ComponentBuilder{
    private float alignment;
    private int spacing;
    private boolean doesNextComponentHasSpacing;
    private int preferredAxis;
    
    CompBuilderBoxLayout(float alignment) {
        super();
        this.preferredAxis = BoxLayout.PAGE_AXIS;
        this.alignment = alignment;
        this.spacing = 0;
        this.doesNextComponentHasSpacing = true;
    }

    CompBuilderBoxLayout(float alignment, int preferredAxis) {
        super();
        this.preferredAxis = preferredAxis;
        this.alignment = alignment;
        this.spacing = 0;
        this.doesNextComponentHasSpacing = true;
    }

    public void setPreferredAxis(int preferredAxis) {
        this.preferredAxis = preferredAxis;
    }

    public void resetBuilder(){
        this.parent = null;
    }

    public void setChild(JComponent child) {
        child.setAlignmentX(this.alignment);
        this.parent.add(child);

        if (this.doesNextComponentHasSpacing)
            this.parent.add(Box.createRigidArea(new Dimension(0, spacing)));
    }

    public void setParent(JComponent parent){
        parent.setLayout(new BoxLayout(parent, this.preferredAxis));
        this.parent = parent;
    }

    public void addSpacing(){
        this.parent.add(Box.createRigidArea(new Dimension(0, spacing)));
    }

    public void setSpacing(int height) {
        this.spacing = height;
    }

    public void setAutoSpace(boolean hasSpacing){
        this.doesNextComponentHasSpacing = hasSpacing;
    }
}
