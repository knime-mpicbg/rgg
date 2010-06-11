/*
 * RGGCheckBoxFactory.java
 *
 * Created on 19. Oktober 2006, 08:55
 */
package at.ac.arcs.rgg.element.checkbox;

import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.w3c.dom.Element;

/**
 *
 * @author ilhami
 */
public class RGGCheckBoxFactory extends RElementFactory {

    /** Creates a new instance of RGGCheckBoxFactory */
    public RGGCheckBoxFactory() {
    }

    public RElement createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE) {
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        }

        RCheckBox rcheckbox = new RCheckBox();
        VCheckBox vcheckbox = new VCheckBox();

        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String label = element.getAttribute(RGG.getConfiguration().getString("LABEL"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String selected = element.getAttribute(RGG.getConfiguration().getString("SELECTED"));
        String returnValueBySelected = element.getAttribute(RGG.getConfiguration().getString("RETURN-VALUE-BY-SELECTED"));
        String returnValueByNotSelected = element.getAttribute(RGG.getConfiguration().getString("RETURN-VALUE-BY-NOTSELECTED"));
        String labelPosition = element.getAttribute(RGG.getConfiguration().getString("LABELPOSITION"));
        String id = element.getAttribute(RGG.getConfiguration().getString("ID"));
        String enabled = element.getAttribute(RGG.getConfiguration().getString("ENABLED"));
        /***********************************************************************************************/
        if (StringUtils.isNotBlank(var)) {
            rcheckbox.setVar(var);
        }

        if (StringUtils.isNotBlank(label)) {
            vcheckbox.setLabelText(label);
        }

        if (StringUtils.isNotBlank(colspan) && StringUtils.isNumeric(colspan)) {
            vcheckbox.setColumnSpan(Integer.parseInt(colspan));
        }

        if (StringUtils.isNotBlank(selected)) {
            if (StringUtils.equalsIgnoreCase("TRUE", selected) || StringUtils.equalsIgnoreCase("T", selected)) {
                vcheckbox.setSelected(true);
            }
        }

        if (StringUtils.isNotBlank(returnValueBySelected)) {
            rcheckbox.setReturnValueBySelected(returnValueBySelected);
        }

        if (StringUtils.isNotBlank(returnValueByNotSelected)) {
            rcheckbox.setReturnValueByNotSelected(returnValueByNotSelected);
        }

        if (StringUtils.isNotBlank(labelPosition)) {
            if (StringUtils.equalsIgnoreCase(labelPosition, RGG.getConfiguration().getString("LEFT"))) {
                vcheckbox.setHorizontalLabelPosition(javax.swing.SwingConstants.LEFT);
            } else if (StringUtils.equalsIgnoreCase(labelPosition, RGG.getConfiguration().getString("RIGHT"))) {
                vcheckbox.setHorizontalLabelPosition(javax.swing.SwingConstants.RIGHT);
            } else {
                throw new IllegalArgumentException("labelpositon takes only \"left\" or \"right\"!");
            }
        }
        if (StringUtils.isNotBlank(id)) {
            rggInstance.addObject(id, vcheckbox);
        }

        if (StringUtils.isNotBlank(enabled)) {
            if (util.match("/(\\w+)\\./", enabled)) {
                String sourceid = util.group(1);
                enabled = util.substitute("s/" + sourceid + "\\.//g", enabled);
                AutoBinding<Object, Object, Object, Object> binding =
                        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, // one-way binding
                        rggInstance.getObject(sourceid), // source of value
                        ELProperty.create(enabled), // the property to get
                        vcheckbox, // the "backing bean"
                        BeanProperty.create("enabled") // property to set
                        );
                binding.bind();
            }
        }

        rcheckbox.setCheckBox(vcheckbox);
        
        return rcheckbox;
    }
}
