/*
 * RGGPanelModel.java
 *
 * Created on 01. August 2006, 13:48
 */
package at.ac.arcs.rgg;

import java.util.ArrayList;
import javax.swing.JComponent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;

/**
 *
 * @author ilhami
 */
public class RGGPanelModel {

    private static Log log = LogFactory.getLog(RGGPanelModel.class);
    // all visual components will be placed into this matrix row by row.
    private ArrayList<ArrayList<VisualComponent>> matrix = new ArrayList<ArrayList<VisualComponent>>();

    /**
     * Creates a new instance of RGGPanelModel
     */
    public RGGPanelModel(RGGModel rggmodel) {
        RElement rggelem;
        for (int i = 0; i < rggmodel.getElementSequence().size(); i++) {
            rggelem = rggmodel.getElementSequence().get(i);
            if (rggelem.hasVisualComponents()) {
                add(rggelem.getVisualComponents());
            }
        }
    }

    public void add(VisualComponent[][] componentMatrix) {
        ArrayList<VisualComponent> row;
        for (VisualComponent[] compoenntRow : componentMatrix) {
            row = new ArrayList<VisualComponent>();
            for (VisualComponent comp : compoenntRow) {
                row.add(comp);
            }
            matrix.add(row);
        }
    }

    public int getMajorColumnNumber() {
        int major = 0;
        for (ArrayList<VisualComponent> row : matrix) {
            if (row.size() > major) {
                major = row.size();
            }
        }
        if (log.isTraceEnabled()) {
            log.trace("majorcolnumber:" + major);
        }
        return major;
    }

    public int getMinorColumnNumber(int majorColumnno) {
        if (majorColumnno < 1 || majorColumnno > getMajorColumnNumber()) {
            throw new IndexOutOfBoundsException("Major columns no can't be" +
                    "smaller than 1 or bigger than actual major column numbers," +
                    "which this model has!");
        }
        int minorColumnNumber = 0;
        VisualComponent visualComp;
        for (ArrayList<VisualComponent> vrow : matrix) {
            if (vrow.size() < majorColumnno) {
                continue;
            //because major columns index begins at 1
            //first it must be subtracted by one.
            }
            visualComp = vrow.get(majorColumnno - 1);
            for (JComponent[] swingComps : visualComp.getSwingComponents()) {
                int sum = 0;
                for (JComponent swingComp : swingComps) {
                    //abstract glue columns
                    sum += LayoutInfo.getComponentSpan(swingComp) - (LayoutInfo.getComponentSpan(swingComp) - 1);
                }
                if (sum > minorColumnNumber) {
                    minorColumnNumber = sum;
                }
            }
        }
        if (log.isTraceEnabled()) {
            log.trace("minorcolnumber for majorcolno " + majorColumnno + " : " + minorColumnNumber);
        }
        return minorColumnNumber;
    }

    public ArrayList<ArrayList<VisualComponent>> getVisualComponentMatrix() {
        return matrix;
    }
}
