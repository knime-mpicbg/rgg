/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.ui.inputselection;

import javax.swing.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ahmet
 */


/**
 * The <code>AdjustmentController</code> synchronizes the adjustment value of the ScrollBars of all registered
 * <code>JScrollPane</code>s.
 */
public class AdjustmentController implements AdjustmentListener {

    private List horizontalBars;
    private List verticalBars;


    /**
     * Creates a new instance of <code>AdjustmentController</code>.
     */
    public AdjustmentController() {
        super();
        horizontalBars = new ArrayList();
        verticalBars = new ArrayList();
    }


    /**
     * Adds the both <code>JScrollBar</code>s of the given <code>scrollPane </code> to the lists of horizontal and
     * vertical bars. Adds this controller as an <code>AdjustmentListener</code> to the bars.
     *
     * @param scrollPane a <code>JScrollPane</code> to register.
     */
    public final void registerScrollPane(final JScrollPane scrollPane) {
        JScrollBar scrollBar = scrollPane.getHorizontalScrollBar();
        scrollBar.addAdjustmentListener(this);
        horizontalBars.add(scrollBar);

//        scrollBar = scrollPane.getVerticalScrollBar();
//        scrollBar.addAdjustmentListener(this);
//        verticalBars.add(scrollBar);
    }

    /* (non-Javadoc)
     * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(
     * java.awt.event.AdjustmentEvent)
     */


    public final void adjustmentValueChanged(final AdjustmentEvent e) {
        final JScrollBar scrollBar = (JScrollBar) e.getAdjustable();
        if (verticalBars.contains(scrollBar)) {
            synchronizeScrollBars(verticalBars, e.getValue());
        } else if (horizontalBars.contains(scrollBar)) {
            synchronizeScrollBars(horizontalBars, e.getValue());
        }
    }


    /**
     * Sets the adjustment value of all ScrollBars in the specified list to the given value.
     *
     * @param bars  A <code>List</code> of the <code>JScrollBar</code>s.
     * @param value The adjustment value to set.
     */
    private void synchronizeScrollBars(final List bars, final int value) {
        JScrollBar bar;
        for (int i = 0; i < bars.size(); i++) {
            bar = (JScrollBar) bars.get(i);
            bar.setValue(value);
        }
    }
}