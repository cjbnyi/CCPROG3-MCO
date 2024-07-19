package Hotel;

import java.awt.LayoutManager;

import javax.swing.JComponent;

public interface ComponentBuilder {
    public void resetBuilder();
    public void assignParent(JComponent parent);
    public void assignPosition(LayoutManager layoutManager);
    public void assignComponent(JComponent child);
    
}