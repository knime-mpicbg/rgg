/*
 * VLabel.java
 *
 * Created on 16. November 2006, 20:16
 */

package at.ac.arcs.rgg.element.labelarea;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;

import javax.swing.*;
import java.awt.*;


/**
 * @author ilhami
 */
public class VLabelArea extends VisualComponent {

    private JTextArea labelArea;
    private String text;
    private JComponent[][] swingMatrix;
    private boolean enabled;


    /**
     * Creates a new instance of VLabel
     */
    public VLabelArea(String text) {
        enabled = true;
        this.text = text;
        labelArea = new JTextArea();
        labelArea.setEditable(false);
        labelArea.setEnabled(true);
        labelArea.setLineWrap(true);
        labelArea.setWrapStyleWord(true);

        labelArea.setText(text);
//        labelArea.setForeground(Color.BLACK);

        labelArea.setBackground(SystemColor.window);


//        label.setMaxLineSpan(2);
        swingMatrix = new JComponent[][]{{labelArea}};
    }


    public JComponent[][] getSwingComponents() {
        return swingMatrix;
    }


    public String getText() {
        return text;
    }


    public void setText(final String text) {
        this.text = text;

        labelArea.setText(text);
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        labelArea.setEnabled(enabled);
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0)
            LayoutInfo.setComponentColumnSpan(labelArea, colspan);
    }
}