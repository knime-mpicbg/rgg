/*
 * RH2.java
 *
 * Created on 16. November 2006, 20:20
 */

package at.ac.arcs.rgg.element.h2;

import javax.swing.JComponent;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;


/**
 * @author ilhami
 */
public class RH2 extends RElement {

    private VisualComponent[][] visualcomponents;
    private VH2 vh;


    /**
     * Creates a new instance of RH2
     */
    public RH2(VH2 vlabel) {
        this.vh = vlabel;
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
        return vh.getSwingComponents();
    }

}
