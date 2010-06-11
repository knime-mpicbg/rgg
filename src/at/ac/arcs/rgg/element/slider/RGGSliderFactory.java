package at.ac.arcs.rgg.element.slider;

import java.lang.IllegalArgumentException;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
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
public class RGGSliderFactory extends RElementFactory{
    
    public RSlider createRGGElement(Element element, RGG rggInstance) {
        if(element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        
        RSlider rslider = new RSlider();
        VSlider vslider = new VSlider();
        
        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String label = element.getAttribute(RGG.getConfiguration().getString("LABEL"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String defaultvalue = element.getAttribute(RGG.getConfiguration().getString("DEFAULT-VALUE"));
        String min = element.getAttribute(RGG.getConfiguration().getString("MIN"));
        String max = element.getAttribute(RGG.getConfiguration().getString("MAX"));
        String paintticks = element.getAttribute(RGG.getConfiguration().getString("PAINT-TICKS"));
        String paintlabels = element.getAttribute(RGG.getConfiguration().getString("PAINT-LABELS"));
        String painttrack = element.getAttribute(RGG.getConfiguration().getString("PAINT-TRACK"));
        String paintvalue = element.getAttribute(RGG.getConfiguration().getString("PAINT-VALUE"));
        String majortickspacing = element.getAttribute(RGG.getConfiguration().getString("MAJOR-TICK-SPACING"));
        String minortickspacing = element.getAttribute(RGG.getConfiguration().getString("MINOR-TICK-SPACING"));
        String enabled = element.getAttribute(RGG.getConfiguration().getString("ENABLED"));
        /***********************************************************************************************/
        
        if(StringUtils.isNotBlank(var)){
            rslider.setVar(var);
        }
        
        if(StringUtils.isNotBlank(label)){
            vslider.setLabelText(label);
        }
        
        if(StringUtils.isNotBlank(colspan)){
            if(StringUtils.isNumeric(colspan)){
                vslider.setColumnSpan(Integer.parseInt(colspan));
            }else if(StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN")))
                vslider.setColumnSpan(LayoutInfo.FULL_SPAN);
            else
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN")
                        +" seems not to be a number: "+
                        colspan +"nor a known keyword!");
        }
        
        if(StringUtils.isNotBlank(defaultvalue)){
            if(StringUtils.isNumeric(defaultvalue)){
                vslider.setValue(Integer.parseInt(defaultvalue));
            }else
                throw new NumberFormatException(RGG.getConfiguration().getString("DEFAULT-VALUE")
                        +" seems not to be a number: " + defaultvalue);
        }
        
        if(StringUtils.isNotBlank(min)){
            if(StringUtils.isNumeric(min)){
                vslider.setMinumum(Integer.parseInt(min));
            }else
                throw new NumberFormatException(RGG.getConfiguration().getString("MIN")
                        +" seems not to be a number: " + min);
        }
        
        if(StringUtils.isNotBlank(max)){
            if(StringUtils.isNumeric(max)){
                vslider.setMaximum(Integer.parseInt(max));
            }else
                throw new NumberFormatException(RGG.getConfiguration().getString("MAX")
                        +" seems not to be a number: " + max);
        }
        
        if(StringUtils.isNotBlank(paintticks)){
            if(StringUtils.equalsIgnoreCase(paintticks,"T")
                    ||StringUtils.equalsIgnoreCase(paintticks,"True")){
                vslider.setPaintTicks(true);
            }
            if(StringUtils.equalsIgnoreCase(paintticks,"F")
                    ||StringUtils.equalsIgnoreCase(paintticks,"False")){
                vslider.setPaintTicks(false);
            }
        }
        
        if(StringUtils.isNotBlank(paintlabels)){
            if(StringUtils.equalsIgnoreCase(paintlabels,"T")
                    ||StringUtils.equalsIgnoreCase(paintlabels,"True")){
                vslider.setPaintLabels(true);
            }
            if(StringUtils.equalsIgnoreCase(paintlabels,"F")
                    ||StringUtils.equalsIgnoreCase(paintlabels,"False")){
                vslider.setPaintLabels(false);
            }
        }
        
        if(StringUtils.isNotBlank(painttrack)){
            if(StringUtils.equalsIgnoreCase(painttrack,"T")
                    ||StringUtils.equalsIgnoreCase(painttrack,"True")){
                vslider.setPaintTrack(true);
            }
            if(StringUtils.equalsIgnoreCase(painttrack,"F")
                    ||StringUtils.equalsIgnoreCase(painttrack,"False")){
                vslider.setPaintTrack(false);
            }
        }
        
        if(StringUtils.isNotBlank(paintvalue)){
            if(StringUtils.equalsIgnoreCase(paintvalue,"T")
                    ||StringUtils.equalsIgnoreCase(paintvalue,"True")){
                vslider.setPaintValue(true);
            }
            if(StringUtils.equalsIgnoreCase(paintvalue,"F")
                    ||StringUtils.equalsIgnoreCase(paintvalue,"False")){
                vslider.setPaintValue(false);
            }
        }
        
        if(StringUtils.isNotBlank(majortickspacing)){
            if(StringUtils.isNumeric(majortickspacing)){
                vslider.setMajorTickSpacing(Integer.parseInt(majortickspacing));
            }else
                throw new NumberFormatException(RGG.getConfiguration().getString("MAJOR-TICK-SPACING")
                        +" seems not to be a number: " + majortickspacing);
        }
        
        if(StringUtils.isNotBlank(minortickspacing)){
            if(StringUtils.isNumeric(minortickspacing)){
                vslider.setMinorTickSpacing(Integer.parseInt(minortickspacing));
            }else
                throw new NumberFormatException(RGG.getConfiguration().getString("MINOR-TICK-SPACING")
                        +" seems not to be a number: " + minortickspacing);
        }
        
        if (StringUtils.isNotBlank(enabled)) {
            if (util.match("/(\\w+)\\./", enabled)) {
                String sourceid = util.group(1);
                enabled = util.substitute("s/" + sourceid + "\\.//g", enabled);
                AutoBinding<Object, Object, Object, Object> binding =
                        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, // one-way binding
                        rggInstance.getObject(sourceid), // source of value
                        ELProperty.create(enabled), // the property to get
                        vslider, // the "backing bean"
                        BeanProperty.create("enabled") // property to set
                        );
                binding.bind();
            }
        }
        
        rslider.setTextfield(vslider);
        
        return rslider;
    }
}
