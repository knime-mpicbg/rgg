/*
 * VSeperator.java
 *
 * Created on 16. November 2006, 19:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.separator;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;


/**
 * @author ilhami
 */
public class VSeparator extends VisualComponent {

    private JComponent seperator;
    private String title;
    private JComponent[][] swingMatrix;


    /**
     * Creates a new instance of VSeperator
     */
    public VSeparator(String title) {
        this.title = title;
        initComponents();
    }


    private void initComponents() {
        seperator = DefaultComponentFactory.getInstance().createSeparator(getTitle());
        swingMatrix = new JComponent[][]{{seperator}};
    }


    public boolean isVisualComponent() {
        return true;
    }


    public JComponent[][] getSwingComponents() {
        return swingMatrix;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(final String title) {
        this.title = title;
        for (int i = 0; i < seperator.getComponentCount(); i++) {
            if (seperator.getComponent(i) instanceof JLabel) {
                final JLabel label = (JLabel) seperator.getComponent(i);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        label.setText(title);
                    }
                });
            }
        }
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0)
            LayoutInfo.setComponentColumnSpan(seperator, colspan);
    }
}
