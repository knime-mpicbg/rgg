package at.ac.arcs.rgg.element.matrix;

import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;


/**
 * @author ilhami
 */
public class RGGMatrixFactory extends RElementFactory {

    public RElement createRGGElement(Element element, at.ac.arcs.rgg.RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");

        RMatrix rMatrix = new RMatrix();
        VMatrix vMatrix = new VMatrix(rggInstance);

        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String datatype = element.getAttribute(RGG.getConfiguration().getString("DATA-TYPE"));
        String id = element.getAttribute(RGG.getConfiguration().getString("ID"));
        /***********************************************************************************************/

        if (StringUtils.isNotBlank(datatype)) {
            if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("NUMERIC"), datatype)) {
                vMatrix.setNumeric(true);
            }
        }

        if (StringUtils.isNotBlank(var)) {
            rMatrix.setVar(var);
        }

        if (StringUtils.isNotBlank(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                vMatrix.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN")))
                vMatrix.setColumnSpan(LayoutInfo.FULL_SPAN);
            else
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN")
                        + " seems not to be a number: " +
                        colspan + "nor a known keyword!");
        }

        if (StringUtils.isNotBlank(id)) {
            rggInstance.addObject(id, vMatrix);
        }
        rMatrix.setVMatrix(vMatrix);

        return rMatrix;
    }
}
