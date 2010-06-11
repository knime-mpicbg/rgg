package at.ac.arcs.rgg.element.targetfileeditor;

import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.w3c.dom.Element;

/**
 *
 * @author ilhami
 */
public class RGGTargetFileEditorFactory extends RElementFactory{
    
    public RTargetFileEditor createRGGElement(Element element,at.ac.arcs.rgg.RGG rggInstance) {
        if(element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        
        RTargetFileEditor rTargetFileEditor = new RTargetFileEditor();
        VTargetFileEditor vTargetFileEditor = new VTargetFileEditor(rggInstance);
        
        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));        
        /***********************************************************************************************/
        
        if(StringUtils.isNotBlank(var)){
            rTargetFileEditor.setVar(var);
        }
               
        if(StringUtils.isNotBlank(colspan)){
            if(StringUtils.isNumeric(colspan)){
                vTargetFileEditor.setColumnSpan(Integer.parseInt(colspan));
            }else if(StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN")))
                vTargetFileEditor.setColumnSpan(LayoutInfo.FULL_SPAN);
            else
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN")
                        +" seems not to be a number: "+
                        colspan +"nor a known keyword!");
        }
        rTargetFileEditor.setVTargetFileEditor(vTargetFileEditor);
        
        return rTargetFileEditor;
    }
}
