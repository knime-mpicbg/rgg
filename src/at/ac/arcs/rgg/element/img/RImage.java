/*
 * RImage.java
 *
 * Created on 16. November 2006, 20:20
 */

package at.ac.arcs.rgg.element.img;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

import javax.swing.*;


/**
 * @author ilhami
 */
public class RImage extends RElement {

    private VisualComponent[][] visualcomponents;
    private VImage vlabel;


    /**
     * Creates a new instance of RImage
     */
    public RImage(VImage vlabel) {
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
