package at.ac.arcs.rgg.element.matrix;

import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.component.VisualComponent;

/**
 *
 * @author ilhami
 */
public class RMatrix extends RElement {

    private String var;
    private VMatrix vMatrix;
    private VisualComponent[][] visualcomponents;

    public RMatrix() {
    }

    public String getRCode() {
        StringBuilder rcodebuilder = new StringBuilder();
        if (StringUtils.isNotBlank(var)) {
            rcodebuilder.append(var + "=");
        }
        if (vMatrix.getColumnCount() == 0) {
            return rcodebuilder.append("NA").toString();
        }

        Object[] values;
        rcodebuilder.append("matrix(c(");
        for (int i = 0; i < vMatrix.getColumnCount(); i++) {
            values = vMatrix.getValuesAtColumn(i);

            if (isNumeric(values)) {
                for (Object value : values) {
                    rcodebuilder.append(value == null ? "" : value.toString() + ",");
                }
            } else {
                for (Object value : vMatrix.getValuesAtColumn(i)) {
                    rcodebuilder.append('"');
                    rcodebuilder.append(value == null ? "" : value.toString());
                    rcodebuilder.append("\",");
                }
            }
        }
        rcodebuilder.deleteCharAt(rcodebuilder.length() - 1);
        rcodebuilder.append("), ncol=" + vMatrix.getColumnCount() + ")");

        return rcodebuilder.toString();
    }

    public void setVMatrix(VMatrix vMatrix) {
        this.vMatrix = vMatrix;
    }

    public boolean hasVisualComponents() {
        return true;
    }

    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vMatrix}};
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
        return vMatrix.getSwingComponents();
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
