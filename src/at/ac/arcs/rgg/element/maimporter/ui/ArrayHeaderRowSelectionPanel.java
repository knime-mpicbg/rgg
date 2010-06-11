/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.maimporter.ui;

import at.ac.arcs.rgg.element.maimporter.ui.arrayheaderselection.ARCTable;
import at.ac.arcs.rgg.element.maimporter.ui.model.ArrayHeaderRowTableModel;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXHeader;


/**
 * @author ilhami
 */
public class ArrayHeaderRowSelectionPanel extends JPanel {

    private ARCTable table;


    public ArrayHeaderRowSelectionPanel() {
        initComponents();
    }


    private void initComponents() {
        setLayout(new BorderLayout());

        JXHeader header = new JXHeader();
        header.setDescription("Please set the header line");
        add(header, BorderLayout.NORTH);

        table = new ARCTable();
        add(table, BorderLayout.CENTER);
    }


    public void setModel(ArrayHeaderRowTableModel model) {
        table.setModel(model);
    }

}
