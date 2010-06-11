/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.maimporter.ui.model;

import javax.swing.event.ChangeListener;

/**
 *
 * @author ilhami
 */
public interface ArrayHeaderRowChangeListener extends ChangeListener{
   void stateChanged(ArrayHeaderChangedEvent evt);
}
