/*
 * RGGGroupFactory.java
 *
 * Created on 18. Oktober 2006, 22:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.group;

import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.RGGModel;
import at.ac.arcs.rgg.RGGPanelModel;
import at.ac.arcs.rgg.builder.RGGPanelBuilder;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.factories.RGGFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.w3c.dom.Element;


/**
 * @author ilhami
 */
public class RGGGroupFactory extends RElementFactory {

    /**
     * Creates a new instance of RGGGroupFactory
     */
    public RGGGroupFactory() {
    }


    public RElement createRGGElement(Element element, RGG rggInstance) {
        try {
            if (element.getNodeType() != Element.ELEMENT_NODE) {
                throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
            }
            String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
            String debug = element.getAttribute("debug");

            RGGModel rggmodel = RGGFactory.createRGGModel(element, rggInstance);
            RGGPanelModel panelmodel = new RGGPanelModel(rggmodel);
            RGGPanelBuilder builder = new RGGPanelBuilder();
            builder.setGroup(true);
            JPanel panel = builder.buildPanel(panelmodel, false, debug.equalsIgnoreCase("true"));
            VGroup vGroup = new VGroup(panel);
            if (StringUtils.isNotBlank(colspan) && StringUtils.isNumeric(colspan)) {
                vGroup.setColumnSpan(Integer.parseInt(colspan));

            } else if (colspan.equalsIgnoreCase("full"))
                vGroup.setColumnSpan(LayoutInfo.FULL_SPAN);

            RGroup group = new RGroup(vGroup, rggmodel);

            return group;
        } catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException("GUI element is not found. maybe typo!");
        } catch (InstantiationException ex) {
            throw new IllegalArgumentException("Error!");
        } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException("Error!");
        }
    }
}
