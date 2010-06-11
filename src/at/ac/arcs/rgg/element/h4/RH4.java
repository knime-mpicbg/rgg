/*
 * RH4.java
 *
 * Created on 16. November 2006, 20:20
 */

package at.ac.arcs.rgg.element.h4;

import javax.swing.JComponent;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RH4 extends RElement{
    private VisualComponent[][] visualcomponents;
    private VH4 vh1;
    /** Creates a new instance of RH4 */
    public RH4(VH4 vlabel) {
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
