/*
 * VH4.java
 *
 * Created on 16. November 2006, 20:16
 */

package at.ac.arcs.rgg.element.h4;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;

import javax.swing.*;


/**
 * @author ilhami
 */
public class VH4 extends VisualComponent {

    private JLabel label;
    private String text;
    private boolean enabled;
    private JComponent[][] swingMatrix;


    /**
     * Creates a new instance of VH4
     */
    public VH4(String text) {
        this.text = text;
        label = new JLabel("<html><h4>" + text + "</h4></html>");
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


    public void setColumnSpan(int colspan) {
        if (colspan > 0)
            LayoutInfo.setComponentColumnSpan(label, colspan);
    }
}
