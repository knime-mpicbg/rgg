/*
 * EmptyPlaceHolder.java
 *
 * Created on 18. Oktober 2006, 11:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.component;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author ilhami
 */
public class EmptyPlaceHolder extends VisualComponent{
    static JComponent[][] swingMatrix = new JComponent[][]{{new JPanel()}};
    /** Creates a new instance of EmptyPlaceHolder */
    public EmptyPlaceHolder() {
    }

    public boolean isVisualComponent() {
        return true;
    }

    public JComponent[][] getSwingComponents() {
        return swingMatrix;
    }
    
}
