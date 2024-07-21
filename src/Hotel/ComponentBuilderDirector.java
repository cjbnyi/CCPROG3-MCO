package Hotel;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import static javax.swing.JPanel.*;
import Hotel.ComponentBuilderState.*;
import Hotel.ComponentBuilderState;

public class ComponentBuilderDirector {
    
    ComponentBuilderState builderState;
    HashMap<ComponentBuilderState, ComponentBuilder> mapBuilder;
    
    ComponentBuilderDirector(ComponentBuilderState builderState){
        this.builderState = builderState;
        this.mapBuilder = new HashMap<ComponentBuilderState, ComponentBuilder>();

        mapBuilder.put(ComponentBuilderState.LAY_BOX_VERTICAL, new CompBuilderBoxLayout(CENTER_ALIGNMENT));
        mapBuilder.put(ComponentBuilderState.LAY_BOX_HORIZONTAL, new CompBuilderBoxLayout(CENTER_ALIGNMENT, BoxLayout.LINE_AXIS));
        mapBuilder.put(ComponentBuilderState.LAY_FLOW, new CompBuilderFlowLayout());
        mapBuilder.put(ComponentBuilderState.LAY_GRID, new CompBuilderGridLayout());

    }

    public void setBuilder(ComponentBuilderState builderState){
        this.builderState = builderState;
    }

    public void setParent(JComponent parent){
        for (ComponentBuilder builder: mapBuilder.values()){
            builder.setParent(parent);
        }
    }


    public ComponentBuilder getBuilder() {
        return this.mapBuilder.get(this.builderState);
    }
}
