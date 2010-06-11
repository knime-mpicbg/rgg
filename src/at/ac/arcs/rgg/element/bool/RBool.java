/*
 * RBool.java
 *
 * Created on 19. Oktober 2006, 09:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.bool;

import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

/**
 *
 * @author ilhami
 */
public class RBool extends RElement {

    private Boolean value;
    private String var;

    /** Creates a new instance of RBool */
    public RBool() {
        value = new Boolean(false);
    }

    @Override
    public String getRCode() {
        StringBuffer rbuf = new StringBuffer();
        if (StringUtils.isNotBlank(var)) {
            rbuf.append(var);
            rbuf.append("=");
        }
        if (value) {
            rbuf.append("TRUE");
        } else {
            rbuf.append("FALSE");
        }
        return rbuf.toString();
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        Boolean oldValue = this.value;
        this.value = value;
        changeSupport.firePropertyChange("value", oldValue, value);
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
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

    public JComponent[][] getSwingComponentMatrix() {
        return new JComponent[][]{};
    }

}
