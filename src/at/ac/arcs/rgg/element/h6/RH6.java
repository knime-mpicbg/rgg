/*
 * RH6.java
 *
 * Created on 16. November 2006, 20:20
 */

package at.ac.arcs.rgg.element.h6;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

import javax.swing.*;


/**
 * @author ilhami
 */
public class RH6 extends RElement {

    private VisualComponent[][] visualcomponents;
    private VH6 vh1;


    /**
     * Creates a new instance of RH6
     */
    public RH6(VH6 vlabel) {
        this.vh1 = vlabel;
        visualcomponents = new VisualComponent[][]{{vlabel}};
    }


    public String getRCode() {
        return "";
    }


    public boolean hasVisualComponents() {
        return true;
    }


    public VisualComponent[][] getVisualComponents() {
        return visualcomponents;
    }


    public boolean isChildAddable() {
        return false;
    }


    public JComponent[][] getSwingComponentMatrix() {
        return vh1.getSwingComponents();
    }

}
