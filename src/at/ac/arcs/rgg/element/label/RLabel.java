/*
 * RLabel.java
 *
 * Created on 16. November 2006, 20:20
 */
package at.ac.arcs.rgg.element.label;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

import javax.swing.*;


/**
 * @author ilhami
 */
public class RLabel extends RElement {

    private VisualComponent[][] visualcomponents;
    private VLabel vlabel;


    /**
     * Creates a new instance of RLabel
     */
    public RLabel(VLabel vlabel) {
        this.vlabel = vlabel;
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
        return vlabel.getSwingComponents();
    }

}
