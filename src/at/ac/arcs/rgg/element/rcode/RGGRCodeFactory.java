/*
 * RGGRCodeFactory.java
 *
 * Created on 19. Oktober 2006, 09:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.rcode;

import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author ilhami
 */
public class RGGRCodeFactory extends RElementFactory{
    
    /** Creates a new instance of RGGRCodeFactory */
    public RGGRCodeFactory() {
    }

    public RElement createRGGElement(Element element,at.ac.arcs.rgg.RGG rggInstance) {
//        if(element.getNodeType() != Element.ELEMENT_NODE)
//            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        RRCode rcode = new RRCode();
        rcode.setRCode(element.getTextContent());
        return rcode;
    }
    
    public static RRCode createRCode(Text textnode){
        RRCode rcode = new RRCode();
        rcode.setRCode(textnode.getTextContent());
        return rcode;
    }
    
}
