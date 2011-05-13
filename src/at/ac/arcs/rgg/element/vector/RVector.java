/*
 * RVector.java
 *
 * Created on 17.04.2007, 10:55:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.vector;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author ilhami
 */
public class RVector extends RElement {

    private String label;
    private String var;

    private VisualComponent[][] visualcomponents;

    private VVector vvector;


    public RVector(VVector vvector) {
        this.vvector = vvector;
    }


    public String getRCode() {
        StringBuilder rbuilder = new StringBuilder();
        if (var != null)
            rbuilder.append(var + "<-");
        rbuilder.append("c(");
        if (vvector.getVectortype() == VectorType.NUMERIC) {
            for (JComponent textfield : vvector.getVectorlist()) {
                rbuilder.append(((JFormattedTextField) textfield).getText() + ",");
            }
        } else if (vvector.getVectortype() == VectorType.CHARACTER) {
            for (JComponent textfield : vvector.getVectorlist()) {
                rbuilder.append("\"" + ((JFormattedTextField) textfield).getText() + "\",");
            }
        } else {
            for (JComponent component : vvector.getVectorlist()) {
                rbuilder.append(new Boolean(((JCheckBox) component).isSelected()).toString().toUpperCase() + ",");
            }
        }
        rbuilder.deleteCharAt(rbuilder.length() - 1);
        rbuilder.append(")");
        return rbuilder.toString();
    }


    public boolean hasVisualComponents() {
        return true;
    }


    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null)
            visualcomponents = new VisualComponent[][]{{vvector}};
        return visualcomponents;
    }


    public boolean isChildAddable() {
        return false;
    }


    public String getVar() {
        return var;
    }


    public void setVar(String var) {
        this.var = var;
        if (StringUtils.isBlank(getLabel()))
            setLabel(var);
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
        vvector.setLabelText(label);
    }


    public JComponent[][] getSwingComponentMatrix() {
        return vvector.getSwingComponents();
    }


    @Override
    public void persistState(Map<String, Object> persistMap) {

        List vecValues = new ArrayList();

        if (vvector.getVectortype() == VectorType.NUMERIC) {
            for (JComponent textfield : vvector.getVectorlist()) {
                vecValues.add(((JFormattedTextField) textfield).getText());
            }
        } else if (vvector.getVectortype() == VectorType.CHARACTER) {
            for (JComponent textfield : vvector.getVectorlist()) {
                vecValues.add(((JFormattedTextField) textfield).getText());
            }
        } else {
            for (JComponent component : vvector.getVectorlist()) {
                vecValues.add(((JCheckBox) component).isSelected());
            }
        }

        persistMap.put(vvector.getLabelText(), vecValues);
    }


    @Override
    public void restoreState(Map<String, Object> persistMap) {
        if (persistMap.containsKey(vvector.getLabelText())) {
            List values = (List) persistMap.get(vvector.getLabelText());


            // is the length the same? If not don't do any restore
            if (vvector.getVectorlist().size() != values.size()) {
                return;
            }


            if (vvector.getVectortype() == VectorType.LOGICAL) {
                ArrayList<JComponent> vectorlist = vvector.getVectorlist();
                for (int i = 0; i < vectorlist.size(); i++) {
                    JComponent component = vectorlist.get(i);
                    ((JCheckBox) component).setSelected((Boolean) values.get(i));
                }
            } else if (vvector.getVectortype() == VectorType.CHARACTER) {
                ArrayList<JComponent> vectorlist = vvector.getVectorlist();
                for (int i = 0; i < vectorlist.size(); i++) {
                    JComponent textfield = vectorlist.get(i);
                    ((JFormattedTextField) textfield).setText((String) values.get(i));
                }
            }
        }
    }

}
