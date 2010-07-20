/*
 * RGGImageFactory.java
 *
 * Created on 16. November 2006, 19:55
 */
package at.ac.arcs.rgg.element.img;

import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import javax.swing.*;
import java.io.File;


/**
 * @author ilhami
 */
public class RGGImageFactory extends RElementFactory {

    public RElement createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE) {
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
            /****************** initialize and set attributes values **************************************/
        }
        String src = element.getAttribute(RGG.getConfiguration().getString("SRC"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String alignment = element.getAttribute(RGG.getConfiguration().getString("ALIGNMENT"));
        /***********************************************************************************************/
        VImage vlabel = new VImage(new File(rggInstance.getBaseDirForFileDialogs(), src));
        if (StringUtils.isNotBlank(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                vlabel.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN"))) {
                vlabel.setColumnSpan(LayoutInfo.FULL_SPAN);
            } else {
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN") + " seems not to be a number: " +
                        colspan);
            }
        }

        if (StringUtils.isNotBlank(alignment)) {
            if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("CENTER"), alignment)) {
                vlabel.setHorizontalAlignment(SwingConstants.CENTER);
            } else if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("RIGHT"), alignment)) {
                vlabel.setHorizontalAlignment(SwingConstants.RIGHT);
            } else if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("LEFT"), alignment)) {
                vlabel.setHorizontalAlignment(SwingConstants.LEFT);
            }
        }
        return new RImage(vlabel);
    }
}
