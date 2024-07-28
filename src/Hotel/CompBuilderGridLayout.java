package Hotel;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;

public class CompBuilderGridLayout extends ComponentBuilder{
    
    private GroupLayout groupLayout;
    private GroupLayout.SequentialGroup verticalGroup;
    private GroupLayout.SequentialGroup horizontalGroup;
    private boolean hasVerticalNotMadeOneParallelGroup;
    private boolean hasHorizonalNotMadeOneParallelGroup;
    private GroupLayout.ParallelGroup vertParallelGroup;
    private GroupLayout.ParallelGroup horParallelGroup;

    CompBuilderGridLayout(){
        super();
    }

    @Override
    public void setParent(JComponent parent) {
        super.setParent(parent);
        ComponentFactory componentFactory = new ComponentFactory();
        parent.setBorder(componentFactory.createPadding());
        this.groupLayout = new GroupLayout(parent);
        this.groupLayout.setAutoCreateGaps(true);
        this.groupLayout.setAutoCreateContainerGaps(true);
        this.hasHorizonalNotMadeOneParallelGroup = true;
        this.hasVerticalNotMadeOneParallelGroup = true;
        this.verticalGroup = componentFactory.createSequentialGroup(this.groupLayout);
        this.horizontalGroup = componentFactory.createSequentialGroup(this.groupLayout);
    }

    public void createNewColumn(Alignment alignment){
        if (!this.hasHorizonalNotMadeOneParallelGroup) {
            this.horizontalGroup.addGroup(this.horParallelGroup);
        } else {
            this.hasHorizonalNotMadeOneParallelGroup = false;
        }
        this.horParallelGroup = this.groupLayout.createParallelGroup(alignment);
    }

    public void createColComponent(JComponent component){
        this.horizontalGroup.addComponent(component);
    }

    public void createColumnGroupComponent(JComponent component){
        this.horParallelGroup.addComponent(component);
    }

    public void createNewRow(Alignment alignment){
        if (!this.hasVerticalNotMadeOneParallelGroup) {
            this.verticalGroup.addGroup(this.vertParallelGroup);
        } else {
            this.hasVerticalNotMadeOneParallelGroup = false;
        }
        this.vertParallelGroup = this.groupLayout.createParallelGroup(alignment);
    }

    public void createRowComponent(JComponent component){
        this.verticalGroup.addComponent(component);
    }

    public void createRowGroupComponent(JComponent component){
        this.vertParallelGroup.addComponent(component);
    }
    
    public void finalizeLayout(){
        this.hasHorizonalNotMadeOneParallelGroup = true;
        this.hasVerticalNotMadeOneParallelGroup = true;
        groupLayout.setHorizontalGroup(this.horizontalGroup);
        groupLayout.setVerticalGroup(this.verticalGroup);
        parent.setLayout(groupLayout);
    }

    @Deprecated()
    public void setChild(JComponent child) {
        this.parent.add(child);
    }
}