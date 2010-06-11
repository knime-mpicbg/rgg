/*
 * RGGRadioButtonFactory.java
 *
 * Created on 12. November 2006, 01:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.radiobutton;

import org.apache.commons.lang.StringUtils;
import org.apache.oro.text.perl.Perl5Util;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.w3c.dom.Element;

/**
 *
 * @author ilhami
 */
public class RGGRadioButtonFactory extends RElementFactory{
    
    public RElement createRGGElement(Element element,RGG rggInstance) {
        if(element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        
        Perl5Util util = new Perl5Util();
        
        VRadioButton vradioButton = new VRadioButton();
        RRadioButton rradioButton = new RRadioButton(vradioButton);        
        
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
        
        if(StringUtils.isNotBlank(var)){
            rradioButton.setVar(var);
        }
        
        if(StringUtils.isNotBlank(label)){
            rradioButton.setLabel(label);
        }
        
        if(StringUtils.isNotBlank(element.getAttribute(RGG.getConfiguration().getString("BUTTONGROUP")))){
            rradioButton.setButtonGroup(element.getAttribute(RGG.getConfiguration().getString("BUTTONGROUP")));
        }
  
        if(StringUtils.isNotBlank(colspan)){
            if(StringUtils.isNumeric(colspan)){
                rradioButton.setColumnSpan(Integer.parseInt(colspan));
            }else if(StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN")))
                rradioButton.setColumnSpan(LayoutInfo.FULL_SPAN);
            else
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN")
                        +" seems not to be a number: "+
                        colspan +"nor a known keyword!");
        }        
        
        if(StringUtils.isNotBlank(selected)){
            if(StringUtils.equalsIgnoreCase(selected,"T")
                    ||StringUtils.equalsIgnoreCase(selected,"True")){
                rradioButton.setSelected(true);
            }
            if(StringUtils.equalsIgnoreCase(selected,"F")
                    ||StringUtils.equalsIgnoreCase(selected,"False")){
                rradioButton.setSelected(false);
            }
        }
        
        if (StringUtils.isNotBlank(returnValueBySelected)) {
            rradioButton.setReturnValueBySelected(returnValueBySelected);
        }
        
        if (StringUtils.isNotBlank(returnValueByNotSelected)) {
            rradioButton.setReturnValueByNotSelected(returnValueByNotSelected);
        }
        
        if(StringUtils.isNotBlank(labelPosition)){
            if(StringUtils.equalsIgnoreCase(labelPosition, RGG.getConfiguration().getString("LEFT"))){
                vradioButton.setHorizontalLabelPosition(javax.swing.SwingConstants.LEFT);
            }else if(StringUtils.equalsIgnoreCase(labelPosition, RGG.getConfiguration().getString("RIGHT"))){
                vradioButton.setHorizontalLabelPosition(javax.swing.SwingConstants.RIGHT);
            }else{
                throw new IllegalArgumentException("labelpositon takes only \"left\" or \"right\"!");
            }
        }
        
        if (StringUtils.isNotBlank(id)) {
            rggInstance.addObject(id, vradioButton);
        }
        
        if (StringUtils.isNotBlank(enabled)) {
            if (util.match("/(\\w+)\\./", enabled)) {
                String sourceid = util.group(1);
                enabled = util.substitute("s/" + sourceid + "\\.//g", enabled);
                AutoBinding<Object, Object, Object, Object> binding =
                        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, // one-way binding
                        rggInstance.getObject(sourceid), // source of value
                        ELProperty.create(enabled), // the property to get
                        vradioButton, // the "backing bean"
                        BeanProperty.create("enabled") // property to set
                        );
                binding.bind();
            }
        }
        
        return rradioButton;
    }
    
}
