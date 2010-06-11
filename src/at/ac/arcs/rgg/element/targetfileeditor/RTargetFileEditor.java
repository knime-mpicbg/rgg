package at.ac.arcs.rgg.element.targetfileeditor;

import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.component.VisualComponent;


/**
 * @author ilhami
 */
public class RTargetFileEditor extends RElement {

    private String var;
    private VTargetFileEditor vTargetFileEditor;
    private VisualComponent[][] visualcomponents;


    public RTargetFileEditor() {
    }


    public String getRCode() {
        StringBuilder rcodebuilder = new StringBuilder();
        if (StringUtils.isNotBlank(var)) {
            rcodebuilder.append(var + "=");
        }
        if (vTargetFileEditor.getColumnCount() == 0) {
            return rcodebuilder.append("NA").toString();
        }

        Object[] values;
        rcodebuilder.append("data.frame(");
        for (int i = 0; i < vTargetFileEditor.getColumnCount(); i++) {
            rcodebuilder.append(vTargetFileEditor.getColumnName(i) + "= c(");
            values = vTargetFileEditor.getValuesAtColumn(i);

            if (isNumeric(values)) {
                for (Object value : vTargetFileEditor.getValuesAtColumn(i)) {
                    rcodebuilder.append(value == null ? "" : value.toString() + ",");
                }
            } else {
                for (Object value : vTargetFileEditor.getValuesAtColumn(i)) {
                    rcodebuilder.append('"');
                    rcodebuilder.append(value == null ? "" : value.toString());
                    rcodebuilder.append("\",");
                }
            }
            rcodebuilder.deleteCharAt(rcodebuilder.length() - 1);
            rcodebuilder.append("),");
        }
        rcodebuilder.deleteCharAt(rcodebuilder.length() - 1);
        rcodebuilder.append(")");

        return rcodebuilder.toString();
    }


    public void setVTargetFileEditor(VTargetFileEditor vTargetFileEditor) {
        this.vTargetFileEditor = vTargetFileEditor;
    }


    public boolean hasVisualComponents() {
        return true;
    }


    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vTargetFileEditor}};
        }
        return visualcomponents;
    }


    public boolean isChildAddable() {
        return false;
    }


    public void setVar(String var) {
        this.var = var;
    }


    public String getVar() {
        return var;
    }


    public JComponent[][] getSwingComponentMatrix() {
        return vTargetFileEditor.getSwingComponents();
    }


    private boolean isNumeric(Object[] values) {
        for (Object v : values) {
            if (!(v instanceof Integer)) {
                return false;
            }
        }
        return true;
    }

}
