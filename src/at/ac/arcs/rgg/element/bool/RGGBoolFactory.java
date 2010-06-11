/*
 * RGGBoolFactory.java
 *
 * Created on 19. Oktober 2006, 09:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.bool;

import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.perl.Perl5Util;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.w3c.dom.Element;


/**
 * @author ilhami
 */
public class RGGBoolFactory extends RElementFactory {

    /**
     * Creates a new instance of RGGBoolFactory
     */
    public RGGBoolFactory() {
    }


    public RElement createRGGElement(Element element, RGG rggInstance) {
//        if(element.getNodeType() != Element.ELEMENT_NODE)
//            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");

        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String id = element.getAttribute(RGG.getConfiguration().getString("ID"));
        String value = element.getAttribute(RGG.getConfiguration().getString("VALUE"));
        String defaultvalue = element.getAttribute(RGG.getConfiguration().getString("DEFAULT-VALUE"));
        /***********************************************************************************************/
        Perl5Util util = new Perl5Util();
        RBool rbool = new RBool();

        if (StringUtils.isNotBlank(var)) {
            rbool.setVar(var);
        }

        if (StringUtils.isNotBlank(id)) {
            rggInstance.addObject(id, rbool);
        }

        if (StringUtils.isNotBlank(defaultvalue)) {
            if (StringUtils.equalsIgnoreCase("TRUE", defaultvalue) ||
                    StringUtils.equalsIgnoreCase("T", defaultvalue)) {
                rbool.setValue(true);
            }
        }

        if (StringUtils.isNotBlank(value)) {
            if (util.match("/(\\w+)\\./", value)) {
                String sourceid = util.group(1);
                value = util.substitute("s/" + sourceid + "\\.//g", value);
                AutoBinding<Object, Object, Object, Object> binding =
                        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, // one-way binding
                                rggInstance.getObject(sourceid), // source of value
                                ELProperty.create(value), // the property to get
                                rbool, // the "backing bean"
                                BeanProperty.create("value") // property to set
                        );
                binding.bind();
            }
        }

        return rbool;
    }
}
