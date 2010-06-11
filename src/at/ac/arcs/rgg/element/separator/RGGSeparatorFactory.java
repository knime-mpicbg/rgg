/*
 * RGGSeperatorFactory.java
 *
 * Created on 16. November 2006, 19:55
 */
package at.ac.arcs.rgg.element.separator;

import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;


/**
 * @author ilhami
 */
public class RGGSeparatorFactory extends RElementFactory {

    private static Log log = LogFactory.getLog(RGGSeparatorFactory.class);


    @Override
    public RElement createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE) {
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
            /****************** initialize and set attributes values **************************************/
        }
        String label = element.getAttribute(RGG.getConfiguration().getString("LABEL"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        /***********************************************************************************************/
        VSeparator vseparator = new VSeparator(label);
        if (StringUtils.isNotBlank(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                vseparator.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN"))) {
                vseparator.setColumnSpan(LayoutInfo.FULL_SPAN);
            } else {
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN")
                        + " seems not to be a number: " +
                        colspan + "nor a known keyword!");
            }
        }
        return new RSeparator(vseparator);
    }
}
