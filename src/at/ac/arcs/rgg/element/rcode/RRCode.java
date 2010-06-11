/*
 * RRCode.java
 *
 * Created on 19. Oktober 2006, 09:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.rcode;

import javax.swing.JComponent;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;


/**
 * @author ilhami
 */
public class RRCode extends RElement {

    private String rcode;


    /**
     * Creates a new instance of RRCode
     */
    public RRCode() {
    }


    public String getRCode() {
        return rcode;
    }


    public boolean hasVisualComponents() {
        return false;
    }


    public VisualComponent[][] getVisualComponents() {
        return null;
    }


    public boolean isChildAddable() {
        return false;
    }


    public void setRCode(String rcode) {
        this.rcode = rcode;
    }


    public JComponent[][] getSwingComponentMatrix() {
        return new JComponent[0][];
    }

}
