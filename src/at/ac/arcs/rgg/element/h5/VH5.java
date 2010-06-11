/*
 * VH5.java
 *
 * Created on 16. November 2006, 20:16
 */

package at.ac.arcs.rgg.element.h5;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;


/**
 * @author ilhami
 */
public class VH5 extends VisualComponent {

    private JLabel label;
    private String text;
    private boolean enabled;
    private JComponent[][] swingMatrix;


    /**
     * Creates a new instance of VH5
     */
    public VH5(String text) {
        this.text = text;
        label = new JLabel("<html><h5>" + text + "</h5></html>");
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
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                label.setText(text);
            }
        });
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                label.setEnabled(enabled);
            }
        });
    }


    public void setHorizontalAlignment(final int alignment) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                label.setHorizontalAlignment(alignment);
            }
        });
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0)
            LayoutInfo.setComponentColumnSpan(label, colspan);
    }
}
