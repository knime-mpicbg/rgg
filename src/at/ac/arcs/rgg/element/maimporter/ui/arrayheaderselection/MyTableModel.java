/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.maimporter.ui.arrayheaderselection;

import javax.swing.table.TableModel;


/**
 * @author ahmet
 */
public interface MyTableModel extends TableModel {

    void setSelection(int rowindex, boolean isSelected);

    boolean getSelection(int rowindex);

    void setSelectedRow(int rowindex);

    int getSelectedRow();
}
