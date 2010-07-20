/*
 * RGGLabelFactory.java
 *
 * Created on 16. November 2006, 19:55
 */

package at.ac.arcs.rgg.element.labelarea;

import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.w3c.dom.Element;


/**
 * @author ilhami
 */
public class RGGLabelAreaFactory extends RElementFactory {

    public RElement createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");

        /****************** initialize and set attributes values **************************************/
        String text = element.getChildNodes().item(0).getTextContent();
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String enabled = element.getAttribute(RGG.getConfiguration().getString("ENABLED"));
        /***********************************************************************************************/

        VLabelArea vLabelArea = new VLabelArea(text);
        if (StringUtils.isNotBlank(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                vLabelArea.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN")))
                vLabelArea.setColumnSpan(LayoutInfo.FULL_SPAN);
            else
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN")
                        + " seems not to be a number: " +
                        colspan);
        }

        if (StringUtils.isNotBlank(enabled)) {
            if (util.match("/(\\w+)\\./", enabled)) {
                String id = util.group(1);
                enabled = util.substitute("s/" + id + "\\.//g", enabled);
                AutoBinding<Object, Object, Object, Object> binding =
                        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, // one-way binding
                                rggInstance.getObject(id), // source of value
                                ELProperty.create(enabled), // the property to get
                                vLabelArea, // the "backing bean"
                                BeanProperty.create("enabled") // property to set
                        );
                binding.bind();
            }
        }

        RLabelArea rLabelArea = new RLabelArea(vLabelArea);

        return rLabelArea;
    }

}