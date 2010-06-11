/*
 * RSeperator.java
 *
 * Created on 16. November 2006, 19:43
 */

package at.ac.arcs.rgg.element.separator;

import javax.swing.JComponent;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RSeparator extends RElement{
    private VisualComponent[][] visualcomponents;
    private VSeparator vseperator;
    /** Creates a new instance of RSeperator */
    public RSeparator(VSeparator vseperator) {
        this.vseperator = vseperator;
        visualcomponents = new VisualComponent[][]{{vseperator}};
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
        return vseperator.getSwingComponents();
    }

}
