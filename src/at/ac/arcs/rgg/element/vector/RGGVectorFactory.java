/*
 * RGGVectorFactory.java
 *
 * Created on 17.04.2007, 12:26:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.vector;

import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import org.w3c.dom.Element;

/**
 *
 * @author ilhami
 */
public class RGGVectorFactory extends RElementFactory{
    
    public RElement createRGGElement(Element element, RGG rggInstance) {
        if(element.getNodeType() != Element.ELEMENT_NODE)
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        
        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String label = element.getAttribute(RGG.getConfiguration().getString("LABEL"));
        String size = element.getAttribute(RGG.getConfiguration().getString("SIZE"));
        String datatype = element.getAttribute(RGG.getConfiguration().getString("VECTOR-TYPE"));
        String alignment = element.getAttribute(RGG.getConfiguration().getString("ALIGNMENT"));
        String defaultvalue = element.getAttribute(RGG.getConfiguration().getString("DEFAULT-VALUE"));
        /***********************************************************************************************/
        
        VVector vvector;
        
        VectorType vtype = datatype.length()==0?VectorType.CHARACTER:VectorType.valueOf(datatype.toUpperCase());
        
        Object[] defaultvalues = StringUtils.split(defaultvalue,',');
        
        if(vtype == VectorType.LOGICAL){
            Boolean[] boolvalues = new Boolean[defaultvalues.length];
            for(int i=0;i<defaultvalues.length;i++){
                if(StringUtils.equalsIgnoreCase(defaultvalues[i].toString(), "TRUE")||
                        StringUtils.equalsIgnoreCase(defaultvalues[i].toString(), "T")){
                    boolvalues[i] = new Boolean(true);
                }else if(StringUtils.equalsIgnoreCase(defaultvalues[i].toString(), "FALSE")||
                        StringUtils.equalsIgnoreCase(defaultvalues[i].toString(), "F")){
                    boolvalues[i] = new Boolean(false);
                }else
                    throw new IllegalArgumentException("Only are \"true\",\"t\",\"false\",\"f\" allowed. not case sensitive.");
            }
            vvector = new VVector(Integer.parseInt(size),boolvalues, vtype);
        }else
            vvector = new VVector(Integer.parseInt(size),defaultvalues, vtype);
        
        RVector rvector = new RVector(vvector);
        
        if(StringUtils.isNotBlank(var)){
            rvector.setVar(var);
        }
        
        if(StringUtils.isNotBlank(label)){
            rvector.setLabel(label);
        }
        
        if(StringUtils.isNotBlank(alignment)){
            if(StringUtils.equalsIgnoreCase(RGG.getConfiguration().getString("VERTICAL"),alignment))
                vvector.setVertical(true);
        }
        
        return rvector;
    }
    
}
