package Hotel;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import Hotel.HotelGUI.PANEL_NAME;

public abstract class HotelPanel extends JPanel {
    private PANEL_NAME panelName;
    protected PanelEnable confirmationPanel;
    protected JTextComponent contentInfo;
    protected PanelHotelSelection hotelSelectionPanel;
    protected ArrayList<JButton> jButtonList = new ArrayList<JButton>();

    HotelPanel(PANEL_NAME panelName, ComponentFactory compFactory){
        this.panelName = panelName;
        // Hotel Selection Panel;
        this.hotelSelectionPanel = new PanelHotelSelection(compFactory);
        this.confirmationPanel = new PanelEnable(compFactory, "Are you sure?");
        this.jButtonList = new ArrayList<JButton>();
    }

    public void setButtonEnabled(String buttonName, Boolean enabled){
        for (JButton btnIndividual : jButtonList){
            if (btnIndividual.getText().equals(buttonName)){
                btnIndividual.setEnabled(enabled);
            }
        }
    }

    public void setOneButtonOnlyEnabled(String buttonName){
        for (JButton btnIndividual : jButtonList){
            if (btnIndividual.getText().equals(buttonName)){
                btnIndividual.setEnabled(true);
            } else {
                btnIndividual.setEnabled(false);
            }
        }
    }

    public void setAllButtonsDisabled(){
        for (JButton btnIndividual : jButtonList){
            btnIndividual.setEnabled(false);
        }
    }

    public void resetButtonEnabled(){
        for (JButton btnIndividual : jButtonList){
            btnIndividual.setEnabled(true);
        }
    }

    public PANEL_NAME getPanelName(){
        return this.panelName;
    }

    public PanelEnable getConfirmationPanel(){
        return this.confirmationPanel;
    }

    public void setConfirmationPanelVisilibity(Boolean enabled){
        this.confirmationPanel.setVisible(enabled);
        return;
    }

    public void setContentInfo(String text){
        this.contentInfo.setText(text);
    }

    public void addContentInfo(String text){
        this.contentInfo.setText(this.contentInfo.getText() + "\n" + text);
    }

    public void addContentInfo(String[] texts){
        for (String text: texts)
            this.contentInfo.setText(this.contentInfo.getText() + "\n" + text);
    }

    public void resetContentInfo(){
        this.contentInfo.setText("");
    }

    public PanelHotelSelection getHotelPanel(){
        return this.hotelSelectionPanel;
    }

    public void setHotelButtonsEnabled(Boolean enabled){
        this.hotelSelectionPanel.setBtnNextHotelEnable(enabled);
        this.hotelSelectionPanel.setBtnPrevHotelEnable(enabled);
        this.hotelSelectionPanel.setBtnSelectHotel(enabled);
    }

}
