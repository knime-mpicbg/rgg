/*
 * RLabel.java
 *
 * Created on 16. November 2006, 20:20
 */
package at.ac.arcs.rgg.element.labelarea;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

import javax.swing.*;


/**
 * @author ilhami
 */
public class RLabelArea extends RElement {

    private VisualComponent[][] visualcomponents;
    private VLabelArea vLabelArea;


    /**
     * Creates a new instance of RLabel
     */
    public RLabelArea(VLabelArea vLabelArea) {
        this.vLabelArea = vLabelArea;
        visualcomponents = new VisualComponent[][]{{vLabelArea}};
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
        return vLabelArea.getSwingComponents();
    }

}