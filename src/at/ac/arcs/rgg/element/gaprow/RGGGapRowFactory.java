/*
 * RGGGapRowFactory.java
 *
 * Created on 16. November 2006, 19:55
 */

package at.ac.arcs.rgg.element.gaprow;

import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;


/**
 * @author ilhami
 */
public class RGGGapRowFactory extends RElementFactory {

    public RElement createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
//        else if(!element.getParentNode().getNodeName().equalsIgnoreCase("rgg"))
//            throw new IllegalArgumentException("A <gaprow> can be only used as a direct child of the root node \"rgg\"!");
        /****************** initialize and set attributes values **************************************/
        String height = element.getAttribute(RGG.getConfiguration().getString("HEIGHT"));
        /***********************************************************************************************/

        VGapRow vgaprow;
        if (StringUtils.isNotBlank(height)) {
            if (StringUtils.isNumeric(height)) {
                vgaprow = new VGapRow(Integer.parseInt(height));
            } else
                throw new NumberFormatException(RGG.getConfiguration().getString("HEIGHT")
                        + " seems not to be a number: " +
                        height);
        } else {
            vgaprow = new VGapRow(3);
        }

        return new RGapRow(vgaprow);
    }

}
