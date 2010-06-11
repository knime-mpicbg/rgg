package at.ac.arcs.rgg.element.slider;

import javax.swing.JComponent;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.component.VisualComponent;

/**
 *
 * @author ilhami
 */
public class RSlider extends RElement{
    private String label;
    private String var;
    
    private VSlider vslider;
    private VisualComponent[][] visualcomponents;
    
    public RSlider() {
    }
    
    public String getRCode() {
        if(StringUtils.isNotBlank(var))
            return var + "=" + vslider.getValue();
        return ""+vslider.getValue();
    }
    
    public boolean hasVisualComponents() {
        return true;
    }
    
    public VisualComponent[][] getVisualComponents() {
        if(visualcomponents == null)
            visualcomponents=new VisualComponent[][]{{vslider}};
        return visualcomponents;
    }
    
    public boolean isChildAddable() {
        return false;
    }
    
    public VSlider getTextfield() {
        return vslider;
    }
    
    public void setTextfield(VSlider textfield) {
        this.vslider = textfield;
    }
    
    public void setVar(String var) {
        this.var = var;
    }
    
    public String getVar(){
        return var;
    }
    
    public JComponent[][] getSwingComponentMatrix() {
        return vslider.getSwingComponents();
    }

}
