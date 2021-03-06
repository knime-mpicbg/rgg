/*
 * VGapRow.java
 *
 * Created on 16. November 2006, 22:57
 */

package at.ac.arcs.rgg.element.gaprow;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;
import com.jgoodies.forms.util.DefaultUnitConverter;

import javax.swing.*;
import java.awt.*;


/**
 * @author ilhami
 */
public class VGapRow extends VisualComponent {

    private JPanel gaprow;
    private JComponent[][] swingMatrix;


    /**
     * Creates a new instance of VGapRow
     */
    public VGapRow(int height) {
        gaprow = new JPanel();
        gaprow.setPreferredSize(
                new Dimension(gaprow.getWidth(),
                        DefaultUnitConverter.getInstance().dialogUnitYAsPixel(height, gaprow)));
        swingMatrix = new JComponent[][]{{gaprow}};
        LayoutInfo.setComponentColumnSpan(gaprow, LayoutInfo.FULL_SPAN);
    }


    public JComponent[][] getSwingComponents() {
        return swingMatrix;
    }
}
