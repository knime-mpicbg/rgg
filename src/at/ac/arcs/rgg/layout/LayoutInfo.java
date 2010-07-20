/*
 * LayoutInfo.java
 *
 * Created on 03. Oktober 2006, 16:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.layout;

import java.util.HashMap;
import javax.swing.JComponent;


/**
 * @author ilhami
 */
public class LayoutInfo {

    public static int FULL_SPAN = Integer.MAX_VALUE;
    private static HashMap<JComponent, Integer> component_colspan = new HashMap<JComponent, Integer>();


    /**
     * Creates a new instance of LayoutInfo
     */
    public LayoutInfo() {
    }


    public static Integer getComponentSpan(JComponent component) {
        if (component == null) return 1;
        else if (!component_colspan.containsKey(component)) return 1;
        return component_colspan.get(component);
    }


    public static void setComponentColumnSpan(JComponent component, Integer colspan) {
        if (component == null)
            throw new NullPointerException("component can't be null!");

        int colspan_with_glue_columns = colspan + (colspan - 1);
        if (colspan_with_glue_columns < 0)
            colspan_with_glue_columns = colspan;
        component_colspan.put(component, colspan_with_glue_columns); // colspan=2 means without considering glue columns. actuall colspan is colspan + glue columns number
    }
}
