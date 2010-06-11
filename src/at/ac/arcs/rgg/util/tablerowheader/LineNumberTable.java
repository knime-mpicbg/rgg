/*
 * LineNumberTable.java
 *
 * Created on 22. August 2005, 22:38
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package at.ac.arcs.rgg.util.tablerowheader;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class LineNumberTable extends JTable {

    private JTable mainTable;


    public LineNumberTable(JTable table) {
        super();
        mainTable = table;
        setAutoCreateColumnsFromModel(false);
        setModel(mainTable.getModel());
        setSelectionModel(mainTable.getSelectionModel());
        setAutoscrolls(false);
        addColumn(new TableColumn());
        getColumnModel().getColumn(0).setCellRenderer(mainTable.getTableHeader().getDefaultRenderer());
        getColumnModel().getColumn(0).setHeaderValue("Nr");
        getColumnModel().getColumn(0).setPreferredWidth(30);
        setPreferredScrollableViewportSize(getPreferredSize());
    }


    public boolean isCellEditable(int row, int column) {
        return false;
    }


    public Object getValueAt(int row, int column) {
        return new Integer(row + 1);
    }


    public int getRowHeight(int row) {
        return mainTable.getRowHeight();
    }


    public Class getColumnClass(int column) {
        return Integer.class;
    }


    public static void main(String[] args)
            throws Exception {
        DefaultTableModel model = new DefaultTableModel(100, 5);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JTable lineTable = new LineNumberTable(table);
        scrollPane.setRowHeaderView(lineTable);

        JFrame frame = new JFrame("Line Number Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

}