/*
 * VRadioButton.java
 *
 * Created on 25. Juli 2006, 15:32
 */

package at.ac.arcs.rgg.element.radiobutton;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * @author ilhami
 */
public class VRadioButton extends VisualComponent {

    private JRadioButton radioButton;
    private JComponent[][] swingMatrix;
    private Boolean selected = new Boolean(false);
    private boolean enabled;


    /**
     * Creates a new instance of VRadioButton
     */
    public VRadioButton() {
        enabled = true;
        initializeComponents();
    }


    private void initializeComponents() {
        radioButton = new JRadioButton();
        radioButton.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                setSelected(radioButton.isSelected());
            }
        });
        swingMatrix = new JComponent[][]{{radioButton}};
    }


    public JComponent[][] getSwingComponents() {
        return swingMatrix;
    }


    public void setSelected(boolean selected) {
        changeSupport.firePropertyChange("selected", this.selected, new Boolean(selected));
        this.selected = selected;
    }


    public boolean getSelected() {
        return radioButton.isSelected();
    }


    public void setDefaultValue(boolean selected) {
        this.selected = selected;
        radioButton.setSelected(selected);
    }


    public JRadioButton getRadioButton() {
        return radioButton;
    }


    public String getLabelText() {
        return radioButton.getText();
    }


    public void setLabelText(final String labeltext) {

        radioButton.setText(labeltext);

    }


    public void addToButtonGroup(ButtonGroup bg) {
        bg.add(radioButton);
    }


    public void removeFromButtonGroup(ButtonGroup bg) {
        bg.remove(radioButton);
    }


    public boolean isVisualComponent() {
        return true;
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0)
            LayoutInfo.setComponentColumnSpan(radioButton, colspan);
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        radioButton.setEnabled(enabled);
    }


    public void setHorizontalLabelPosition(int labelPosition) {
        radioButton.setHorizontalTextPosition(labelPosition);
    }
}
