/*
 * RGGListFactory.java
 *
 * Created on 12. November 2006, 01:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.twopanellistbox;

import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

import java.util.Arrays;


/**
 * @author ilhami
 */
public class RGGTwoPanelListBoxFactory extends RElementFactory {

    public RElement createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");


        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String label = element.getAttribute(RGG.getConfiguration().getString("LABEL"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String items = element.getAttribute(RGG.getConfiguration().getString("ITEMS"));
        String datatype = element.getAttribute(RGG.getConfiguration().getString("DATA-TYPE"));
        /***********************************************************************************************/
        VTwoPanelListBox vList = new VTwoPanelListBox();
        RTwoPanelListBox rListBox = new RTwoPanelListBox();

        if (StringUtils.isNotBlank(items)) {
            vList.setListData(Arrays.asList(StringUtils.split(items, ',')));
        }
        rListBox.setVList(vList);

        if (StringUtils.isNotBlank(var)) {
            rListBox.setVar(var);
        }

        if (StringUtils.isNotBlank(label)) {
            rListBox.setLabel(label);
        }

        if (StringUtils.isNotBlank(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                vList.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN")))
                vList.setColumnSpan(LayoutInfo.FULL_SPAN);
            else
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN")
                        + " seems not to be a number: " +
                        colspan);
        }

        if (StringUtils.isNotBlank(datatype)) {
            if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("NUMERIC"), datatype))
                rListBox.setNumeric(true);
        }

        return rListBox;
    }

}