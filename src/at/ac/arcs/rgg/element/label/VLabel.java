/*
 * VLabel.java
 *
 * Created on 16. November 2006, 20:16
 */

package at.ac.arcs.rgg.element.label;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.jdesktop.swingx.JXLabel;

import javax.swing.*;


/**
 * @author ilhami
 */
public class VLabel extends VisualComponent {

    private JXLabel label;
    private String text;
    private JComponent[][] swingMatrix;
    private boolean enabled;


    /**
     * Creates a new instance of VLabel
     */
    public VLabel(String text) {
        enabled = true;
        this.text = text;
        label = new JXLabel(text);
        label.setLineWrap(true);
//        label.setMaxLineSpan(2);
        swingMatrix = new JComponent[][]{{label}};
    }


    public JComponent[][] getSwingComponents() {
        return swingMatrix;
    }


    public String getText() {
        return text;
    }


    public void setText(final String text) {
        this.text = text;

        label.setText(text);

    }


    public void setHorizontalAlignment(final int alignment) {

        label.setHorizontalAlignment(alignment);

    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        label.setEnabled(enabled);
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0)
            LayoutInfo.setComponentColumnSpan(label, colspan);
    }
}
