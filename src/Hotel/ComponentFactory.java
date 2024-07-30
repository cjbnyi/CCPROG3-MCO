package Hotel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class ComponentFactory {
    private float color;
    private Font fontBody;
    private Font fontHeading;

    ComponentFactory() {
        this.color = 0.0f;
        this.fontBody = new Font("Helvetica", Font.PLAIN, 12);
        this.fontHeading = new Font("Helvetica", Font.BOLD, 20);
    }

    public Color getRandomColor(){   
        this.color = (this.color + 0.72514f) % 1.0f;
        return Color.getHSBColor(this.color, 0.3f, 0.95f);
    }
    
    public void setFont(String name, int style, int size){
        this.fontBody = new Font(name, style, size);
    }

    public Font getFontBody(){
        return this.fontBody;
    }

    private void setStrictSize(JComponent component, int height, int width){
        Dimension size = new Dimension(width, height);
        component.setMaximumSize(size);
        component.setPreferredSize(size);
        component.setMinimumSize(size);
    }

    public JTabbedPane createTabbedPane(){
        JTabbedPane newTabbedPane = new JTabbedPane();
        newTabbedPane.setBackground(getRandomColor());
        return newTabbedPane;
    }

    public JList<Integer> createJListIntegerFilled(int intCap){
        ArrayList<Integer> dateNums = new ArrayList<Integer>();
        int i = 0;
        for (i = 1; i <= intCap; i++){
            dateNums.add(i);
        }
        Integer[] dates = dateNums.toArray(new Integer[dateNums.size()]);
        JList<Integer> newJList = new JList<Integer>(dates);
        newJList.setVisibleRowCount(3);
        return newJList;
    };

    public JList<Integer> createJListInteger(Integer[] intListInpt){
        JList<Integer> newJList = new JList<Integer>(intListInpt);
        newJList.setVisibleRowCount(3);
        return newJList;
    }

    public JList<String> createJListString(String[] stringListInput){
        JList<String> newJList = new JList<String>(stringListInput);
        newJList.setVisibleRowCount(3);
        return newJList;
    }

    public JTextPane createJTextPane(){
        return new JTextPane();
    }

    public JScrollPane createJScrollPane(JComponent inputComponent){
        return new JScrollPane(inputComponent);
    }

    public JScrollPane createJScrollPane(JList<String> stringListInput){
        return new JScrollPane(stringListInput);
    }


    public JTextField createJTextField(int height, int width){
        JTextField newJTextField = new JTextField();
        setStrictSize(newJTextField, height, width);
        newJTextField.setFont(fontBody);
        newJTextField.setBackground(Color.white);
        newJTextField.setCaretColor(Color.black);
        return newJTextField;
    }

    public JLabel createJLabelBody(String name){
        JLabel newLabel = new JLabel(name);
        newLabel.setForeground(Color.BLACK);
        newLabel.setFont(fontBody);
        return newLabel;
    }

    public JLabel createJLabelHeading(String name){
        JLabel newLabel = new JLabel(name);
        newLabel.setForeground(Color.BLACK);
        newLabel.setFont(fontHeading);
        return newLabel;
    }

    public EmptyBorder createPadding(){
        return new EmptyBorder(10, 10, 10, 10);
    }

    public JPanel createJPanel(){
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new FlowLayout());
        newPanel.setBackground(getRandomColor());
        
        return newPanel;
    }

    public JPanel createJPanel(LayoutManager layoutManager){
        JPanel newPanel = new JPanel();
        newPanel.setLayout(layoutManager);
        newPanel.setBackground(getRandomColor());
        
        return newPanel;
    }

    public JButton createSingleJButton(){
        JButton newButton = new JButton();
        newButton.setBackground(getRandomColor());

        return newButton;
    }

    public JButton createSingleJButton(String name){
        JButton newButton = new JButton(name);
        newButton.setBackground(getRandomColor());
        newButton.setFont(fontBody);
        newButton.setForeground(Color.BLACK);
        return newButton;
    }

    public JButton createSingleJButton(ArrayList<JButton> jbuttonList, String name){
        JButton newButton = new JButton(name);
        newButton.setBackground(getRandomColor());
        newButton.setFont(fontBody);
        newButton.setForeground(Color.BLACK);
        jbuttonList.add(newButton);
        return newButton;
    }

    public JButton createSingleJButton(String name, int width, int height){
        JButton newButton = new JButton(name);
        newButton.setBackground(getRandomColor());
        newButton.setFont(fontBody);
        newButton.setForeground(Color.BLACK);
        setStrictSize(newButton, height, width);
        return newButton;
    }

    public JButton createSingleJButton(ArrayList<JButton> jbuttonList, String name, int width, int height){
        JButton newButton = new JButton(name);
        newButton.setBackground(getRandomColor());
        newButton.setFont(fontBody);
        newButton.setForeground(Color.BLACK);
        setStrictSize(newButton, height, width);
        jbuttonList.add(newButton);
        return newButton;
    }

    public ArrayList<JButton> createMultipleJButtons(int n){
        int i;
        ArrayList<JButton> buttonList = new ArrayList<JButton>();
        JButton newButton;
        for (i = 0; i < n; i++){
            newButton = createSingleJButton();
            buttonList.add(newButton);
        }
        return buttonList;
    }

    public ArrayList<JButton> createMultipleJButtons(ArrayList<String> names){
        ArrayList<JButton> buttonList = new ArrayList<JButton>();
        JButton newButton;
        for (String name : names){
            newButton = createSingleJButton(name);
            buttonList.add(newButton);
        }
        return buttonList;
    }

    public ArrayList<JButton> createMultipleJButtons(String[] names){
        ArrayList<JButton> buttonList = new ArrayList<JButton>();
        JButton newButton;
        for (String name : names){
            newButton = createSingleJButton(name);
            buttonList.add(newButton);
        }
        return buttonList;
    }
    
    public ArrayList<JButton> createMultipleJButtons(ArrayList<JButton> jButtonList, String[] names){
        ArrayList<JButton> buttonList = new ArrayList<JButton>();
        JButton newButton;
        for (String name : names){
            newButton = createSingleJButton(name);
            jButtonList.add(newButton);
            buttonList.add(newButton);
        }
        return buttonList;
    }

    public ArrayList<JTextField> createMultipleJTextFields(int n, int height, int width){
        ArrayList<JTextField> buttonList = new ArrayList<JTextField>();
        JTextField newButton;
        int i = 0;
        for (i = 0; i < n; i++){
            newButton = createJTextField(height, width);
            buttonList.add(newButton);
        }
        return buttonList;
    }

    public ArrayList<JLabel> createMultipleJLabel(String[] names){
        ArrayList<JLabel> jLabelsList = new ArrayList<JLabel>();
        JLabel newButton;
        for (String name: names){
            newButton = createJLabelBody(name);
            jLabelsList.add(newButton);
        }
        return jLabelsList;
    }

    public ArrayList<JButton> createMultipleJButtons(ArrayList<JButton> jbuttonList, String[] names, int width, int height){
        ArrayList<JButton> buttonList = new ArrayList<JButton>();
        JButton newButton;
        for (String name : names){
            newButton = createSingleJButton(jbuttonList, name, width, height);
            buttonList.add(newButton);
        }
        return buttonList;
    }

    public ArrayList<JButton> createMultipleJButtons(String[] names, int width, int height){
        ArrayList<JButton> buttonList = new ArrayList<JButton>();
        JButton newButton;
        for (String name : names){
            newButton = createSingleJButton(name, width, height);
            buttonList.add(newButton);
        }
        return buttonList;
    }


    public JTextArea createJTextArea(){
        JTextArea newJTextArea = new JTextArea();
        newJTextArea.setFont(fontBody);
        return newJTextArea;
    }

    public GroupLayout.Group createGroup(GroupLayout.Group parentGroup, GroupLayout.Group childGroup){
        return parentGroup.addGroup(childGroup);
    };

    public GroupLayout.Group createGroup(GroupLayout.Group parentGroup, GroupLayout.ParallelGroup childGroup){
        return parentGroup.addGroup(childGroup);
    };

    public GroupLayout.Group createGroup(GroupLayout.Group parentGroup, GroupLayout.SequentialGroup childGroup){
        return parentGroup.addGroup(childGroup);
    };

    public GroupLayout.SequentialGroup createSequentialGroup(GroupLayout layout){
        return layout.createSequentialGroup();
    }

    public GroupLayout.ParallelGroup createParellelGroup(GroupLayout layout){
        return layout.createParallelGroup();
    }
}
