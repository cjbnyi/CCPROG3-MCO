package Hotel;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        this.color = (this.color + 0.625f) % 1.0f;
        return Color.getHSBColor(this.color, 0.5f, 0.5f);
    }
    
    public void setFont(String name, int style, int size){
        this.fontBody = new Font(name, style, size);
    }

    public Font getFontBody(){
        return this.fontBody;
    }

    public JTextField createJTextField(int height, int width){
        JTextField newJTextField = new JTextField();
        newJTextField.setPreferredSize(new Dimension(width, height));
        newJTextField.setFont(fontBody);
        newJTextField.setBackground(Color.white);
        newJTextField.setCaretColor(Color.black);;
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
        return newButton;
    }

    public JButton createSingleJButton(String name, int width, int height){
        JButton newButton = new JButton(name);
        newButton.setBackground(getRandomColor());
        newButton.setFont(fontBody);
        newButton.setPreferredSize(new Dimension(width, height));
        newButton.setMinimumSize(new Dimension(width, height));
        newButton.setMaximumSize(new Dimension(width, height));
        newButton.setSize(new Dimension(width, height));
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

    public ArrayList<JButton> createMultipleJButtons(String[] names, int width, int height){
        ArrayList<JButton> buttonList = new ArrayList<JButton>();
        JButton newButton;
        for (String name : names){
            newButton = createSingleJButton(name, width, height);
            buttonList.add(newButton);
        }
        return buttonList;
    }
}
