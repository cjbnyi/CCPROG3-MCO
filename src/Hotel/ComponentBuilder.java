package Hotel;

import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

public abstract class ComponentBuilder {
    protected JComponent parent;

    public void resetBuilder(){
        this.parent = null;
    }
    
    public void setChild(JComponent child) {
        this.parent.add(child);
    }

    public void setParent(JComponent parent){
        parent.setLayout(new BoxLayout(parent, BoxLayout.PAGE_AXIS));
        this.parent = parent;
    }
}