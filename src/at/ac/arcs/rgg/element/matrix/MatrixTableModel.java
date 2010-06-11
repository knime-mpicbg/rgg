/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.matrix;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;


/**
 * @author ilhami
 */
public class MatrixTableModel extends DefaultTableModel {

    public void removeColumnAndColumnData(int columnindex) {
        for (int i = 0; i < getRowCount(); i++) {
            ((Vector) dataVector.get(i)).remove(columnindex);
        }
        columnIdentifiers.remove(columnindex);
        fireTableStructureChanged();
    }
}
