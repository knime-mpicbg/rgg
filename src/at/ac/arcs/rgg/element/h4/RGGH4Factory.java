/*
 * RGGH4Factory.java
 *
 * Created on 16. November 2006, 19:55
 */

package at.ac.arcs.rgg.element.h4;

import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.perl.Perl5Util;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.w3c.dom.Element;

import javax.swing.*;


/**
 * @author ilhami
 */
public class RGGH4Factory extends RElementFactory {

    public RElement createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");

        /****************** initialize and set attributes values **************************************/
        String text = element.getAttribute(RGG.getConfiguration().getString("TEXT"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String alignment = element.getAttribute(RGG.getConfiguration().getString("ALIGNMENT"));
        String enabled = element.getAttribute(RGG.getConfiguration().getString("ENABLED"));
        /***********************************************************************************************/

        Perl5Util util = new Perl5Util();

        VH4 vlabel = new VH4(text);
        if (StringUtils.isNotBlank(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                vlabel.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN")))
                vlabel.setColumnSpan(LayoutInfo.FULL_SPAN);
            else
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN")
                        + " seems not to be a number: " +
                        colspan);
        }

        if (StringUtils.isNotBlank(alignment)) {
            if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("CENTER"), alignment)) {
                vlabel.setHorizontalAlignment(SwingConstants.CENTER);
            } else if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("RIGHT"), alignment)) {
                vlabel.setHorizontalAlignment(SwingConstants.RIGHT);
            } else if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("LEFT"), alignment)) {
                vlabel.setHorizontalAlignment(SwingConstants.LEFT);
            } else if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("TOP"), alignment)) {
                vlabel.setHorizontalAlignment(SwingConstants.TOP);
            } else if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("BOTTOM"), alignment)) {
                vlabel.setHorizontalAlignment(SwingConstants.BOTTOM);
            }
        }

        if (StringUtils.isNotBlank(enabled)) {
            if (util.match("/(\\w+)\\./", enabled)) {
                String id = util.group(1);
                enabled = util.substitute("s/" + id + "\\.//g", enabled);
                AutoBinding<Object, Object, Object, Object> binding =
                        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, // one-way binding
                                rggInstance.getObject(id), // source of value
                                ELProperty.create(enabled), // the property to get
                                vlabel, // the "backing bean"
                                BeanProperty.create("enabled") // property to set
                        );
                binding.bind();
            }
        }

        return new RH4(vlabel);
    }

}
