/*
 * VCheckBox.java
 *
 * Created on 25. Juli 2006, 15:30
 */
package at.ac.arcs.rgg.element.checkbox;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author ilhami
 */
public class VCheckBox extends VisualComponent {

    private JCheckBox checkBox;
    private JComponent[][] swingMatrix;
    private Boolean selected;
    private Boolean enabled;


    public VCheckBox() {
        enabled = true;
        checkBox = new JCheckBox();
        checkBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setSelected(checkBox.isSelected());
            }
        });
        swingMatrix = new JComponent[][]{{checkBox}};

    }


    public boolean isVisualComponent() {
        return true;
    }


    public JComponent[][] getSwingComponents() {
        return swingMatrix;
    }


    public String getLabelText() {
        return checkBox.getText();
    }


    public void setLabelText(final String labeltext) {
        checkBox.setText(labeltext);
    }


    public String getDefaultvalue() {
        return selected.toString();
    }


    public void setSelected(boolean selected) {
        checkBox.setSelected(selected);
        changeSupport.firePropertyChange("selected", this.selected, new Boolean(selected));
        this.selected = selected;
    }


    public boolean isSelected() {
        return checkBox.isSelected();
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(final boolean e) {

        checkBox.setEnabled(e);

        Boolean oldvalue = this.enabled;
        this.enabled = e;
        changeSupport.firePropertyChange("enabled", oldvalue, this.enabled);
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(checkBox, colspan);
        }
    }


    public void setHorizontalLabelPosition(int labelPosition) {
        checkBox.setHorizontalTextPosition(labelPosition);
    }
}
