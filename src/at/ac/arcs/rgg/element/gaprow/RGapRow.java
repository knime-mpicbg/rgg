/*
 * RGapRow.java
 *
 * Created on 16. November 2006, 20:20
 */

package at.ac.arcs.rgg.element.gaprow;

import javax.swing.JComponent;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;


/**
 * @author ilhami
 */
public class RGapRow extends RElement {

    private VisualComponent[][] visualcomponents;
    private VGapRow vgaprow;


    /**
     * Creates a new instance of RGapRow
     */
    public RGapRow(VGapRow vgaprow) {
        this.vgaprow = vgaprow;
        visualcomponents = new VisualComponent[][]{{vgaprow}};
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
        return vgaprow.getSwingComponents();
    }

}
