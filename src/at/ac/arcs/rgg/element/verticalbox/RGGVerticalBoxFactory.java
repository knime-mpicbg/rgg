/*
 * RGGVerticalBoxFactory.java
 *
 * Created on 18. Oktober 2006, 22:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.verticalbox;

import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.element.rcode.RGGRCodeFactory;
import at.ac.arcs.rgg.factories.RElementFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


/**
 * @author ilhami
 */
public class RGGVerticalBoxFactory extends RElementFactory {

    /**
     * Creates a new instance of RGGVerticalBoxFactory
     */
    public RGGVerticalBoxFactory() {
    }


    public RElement createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        RVerticalBox vbox = new RVerticalBox();

        Element child;
        for (int i = 0; i < element.getChildNodes().getLength(); i++) {
            if (element.getChildNodes().item(i).getNodeType() == Element.ELEMENT_NODE) {
                child = (Element) element.getChildNodes().item(i);
                try {
                    vbox.addChild(RElementFactory.getElementFactoryForName(
                            child.getNodeName()).createRGGElement(child, rggInstance));
                } catch (InstantiationException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            } else if (element.getChildNodes().item(i).getNodeType() == Element.TEXT_NODE) {
                vbox.addChild(RGGRCodeFactory.createRCode((Text) element.getChildNodes().item(i)));
            }
        }

        return vbox;
    }

}
