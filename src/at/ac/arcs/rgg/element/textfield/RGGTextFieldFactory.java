/*
 * RGGTextFieldFactory.java
 *
 * Created on 17. Oktober 2006, 10:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.textfield;

import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.perl.Perl5Util;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.w3c.dom.Element;


/**
 * @author ilhami
 */
public class RGGTextFieldFactory extends RElementFactory {

    public RTextField createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE) {
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        }

        RTextField rtextfield = new RTextField();
        VTextField vtextfield = new VTextField();

        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String label = element.getAttribute(RGG.getConfiguration().getString("LABEL"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String defaultvalue = element.getAttribute(RGG.getConfiguration().getString("DEFAULT-VALUE"));
        String datatype = element.getAttribute(RGG.getConfiguration().getString("DATA-TYPE"));
        String size = element.getAttribute(RGG.getConfiguration().getString("SIZE"));
        String enabled = element.getAttribute(RGG.getConfiguration().getString("ENABLED"));
        /***********************************************************************************************/
        if (StringUtils.isNotBlank(var)) {
            rtextfield.setVar(var);
        }

        if (StringUtils.isNotBlank(label)) {
            rtextfield.setLabel(label);
        }

        vtextfield.setLabelText(rtextfield.getLabel());

        if (StringUtils.isNotBlank(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                vtextfield.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN"))) {
                vtextfield.setColumnSpan(LayoutInfo.FULL_SPAN);
            } else {
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN") + " seems not to be a number: " +
                        colspan + "nor a known keyword!");
            }
        }

        if (StringUtils.isNotBlank(defaultvalue)) {
            vtextfield.setDefaultvalue(defaultvalue);
        }

        if (StringUtils.isNotBlank(datatype)) {
            if (StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("NUMERIC"), datatype)) {
                vtextfield.setNumeric(true);
            }
        }

        if (StringUtils.isNotBlank(size) && StringUtils.isNumeric(size)) {
            vtextfield.setColumns(Integer.parseInt(size));
        }

        if (StringUtils.isNotBlank(enabled)) {
            if (util.match("/(\\w+)\\./", enabled)) {
                String id = util.group(1);
                enabled = util.substitute("s/" + id + "\\.//g", enabled);
                AutoBinding<Object, Object, Object, Object> binding =
                        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, // one-way binding
                                rggInstance.getObject(id), // source of value
                                ELProperty.create(enabled), // the property to get
                                vtextfield, // the "backing bean"
                                BeanProperty.create("enabled") // property to set
                        );
                binding.bind();
            }
        }

        rtextfield.setTextfield(vtextfield);
        return rtextfield;
    }
}
