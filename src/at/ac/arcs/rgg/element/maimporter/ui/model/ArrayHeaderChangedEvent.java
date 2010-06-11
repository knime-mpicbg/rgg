/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.maimporter.ui.model;

import javax.swing.event.ChangeEvent;


/**
 * @author ilhami
 */
public class ArrayHeaderChangedEvent extends ChangeEvent {

    private int headerRow;


    public ArrayHeaderChangedEvent(Object source, int headerRow) {
        super(source);
        this.headerRow = headerRow;
    }


    public int getHeaderRow() {
        return headerRow;
    }
}
