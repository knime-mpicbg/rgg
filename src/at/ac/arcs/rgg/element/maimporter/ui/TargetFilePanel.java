/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.ui;

import at.ac.arcs.rgg.element.maimporter.ui.model.TargetFileTableModel;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.TableColumnModelListener;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Highlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;


/**
 * @author ilhami
 */
public class TargetFilePanel extends JPanel {

    private TargetFileTableModel model;


    /**
     * Creates new form TargetFilePanel
     */
    public TargetFilePanel() {
        initComponents();
    }


    /**
     * Creates new form TargetFilePanel
     */
    public TargetFilePanel(TargetFileTableModel model) {
        this.model = model;
        initComponents();
        jxTable.setModel(model);
    }


    public TargetFileTableModel getModel() {
        return model;
    }


    public void setModel(TargetFileTableModel model) {
        this.model = model;
        jxTable.setModel(model);
        if (tableScrollPane.getViewport().getView() == jLabel1) {
            tableScrollPane.setViewportView(jxTable);
        }
    }


    void setPrefferedHeight(int height) {
        jxTable.setPreferredScrollableViewportSize(new Dimension(
                jxTable.getPreferredScrollableViewportSize().width, height));
    }


    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        rowLabel = new javax.swing.JLabel();
        columnLabel = new javax.swing.JLabel();
        browseButton = new javax.swing.JButton();
        addRowHyperlink = new org.jdesktop.swingx.JXHyperlink();
        removeRowHyperlink = new org.jdesktop.swingx.JXHyperlink();
        addColumnHyperlink = new org.jdesktop.swingx.JXHyperlink();
        removeColumnHyperlink = new org.jdesktop.swingx.JXHyperlink();
        tableScrollPane = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jXHeader1 = new org.jdesktop.swingx.JXHeader();

        tableScrollPane.setBorder(null);

        fileChooser.setMultiSelectionEnabled(true);

        rowLabel.setText("Rows:");
        columnLabel.setText("Columns:");

        browseButton.setText("Add Microarrays");
        browseButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        addRowHyperlink.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/at/ac/arcs/rgg/element/maimporter/ui/icon/add.png")));

        addRowHyperlink.setText("");
        addRowHyperlink.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        addRowHyperlink.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowHyperlinkActionPerformed(evt);
            }
        });

        removeRowHyperlink.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/at/ac/arcs/rgg/element/maimporter/ui/icon/remove.png")));

        removeRowHyperlink.setText("");
        removeRowHyperlink.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowHyperlinkActionPerformed(evt);
            }
        });

        addColumnHyperlink.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/at/ac/arcs/rgg/element/maimporter/ui/icon/add.png"))); // NOI18N

        addColumnHyperlink.setText("");
        addColumnHyperlink.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        addColumnHyperlink.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addColumnHyperlinkActionPerformed(evt);
            }
        });

        removeColumnHyperlink.setIcon(new javax.swing.ImageIcon(getClass().
                getResource("/at/ac/arcs/rgg/element/maimporter/ui/icon/remove.png"))); // NOI18N

        removeColumnHyperlink.setText("");
        removeColumnHyperlink.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeColumnHyperlinkActionPerformed(evt);
            }
        });

        jxTable = new JXTable();
        jxTable.setHighlighters(new Highlighter[]{HighlighterFactory.createAlternateStriping()});
        jxTable.setSortable(false);
        jxTable.setHorizontalScrollEnabled(true);
        jxTable.setAutoStartEditOnKeyStroke(true);
        jxTable.setCellSelectionEnabled(true);
        jxTable.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Use \"Add Microarrays\" button to add microarray files");
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        tableScrollPane.setViewportView(jLabel1);

        jXHeader1.setDescription("Add sample annotation (phenotypic and technical). Also microarrays can be removed.");

        /****************Layout**********************/
        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout("pref,2dlu,pref,2dlu,pref,25dlu,pref,2dlu,pref,2dlu,pref",//cols
                "3dlu,pref,2dlu");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(layout);
        buttonPanel.add(rowLabel, cc.xy(1, 2));
        buttonPanel.add(addRowHyperlink, cc.xy(3, 2));
        buttonPanel.add(removeRowHyperlink, cc.xy(5, 2));
        buttonPanel.add(columnLabel, cc.xy(7, 2));
        buttonPanel.add(addColumnHyperlink, cc.xy(9, 2));
        buttonPanel.add(removeColumnHyperlink, cc.xy(11, 2));

        setLayout(new BorderLayout());
        add(jXHeader1, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setBorder(null);
    }


    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {

//        fileChooser.setCurrentDirectory((File) rggInstance.getProperty(LASTUSEDDIR));
        int status = fileChooser.showOpenDialog((JButton) evt.getSource());
        if (status == JFileChooser.APPROVE_OPTION) {
        }
    }


    private void addRowHyperlinkActionPerformed(java.awt.event.ActionEvent evt) {
        if (model == null) {
            return;
        }
//        model.addRow(new Object[]{});
    }


    private void removeRowHyperlinkActionPerformed(java.awt.event.ActionEvent evt) {
        if (jxTable.getSelectedRow() == -1 || model == null) {
            return;
        }
        model.removeRow(jxTable.getSelectedRow());
    }


    private void addColumnHyperlinkActionPerformed(java.awt.event.ActionEvent evt) {
        if (model == null) {
            return;
        }
        String colName = JOptionPane.showInputDialog("Name of the new column?");
        if (StringUtils.isNotBlank(colName)) {
            if (colName.equalsIgnoreCase("filename")) {
                JOptionPane.showMessageDialog(this, "FileName column is added already.");
                return;
            }
            model.addColumn(colName);
        }
    }


    private void removeColumnHyperlinkActionPerformed(java.awt.event.ActionEvent evt) {
        if (jxTable.getSelectedColumn() == -1 || model == null) {
            return;
        } else if (model.getColumnName(jxTable.getSelectedColumn()).equalsIgnoreCase("filename")) {
            JOptionPane.showMessageDialog(this, "FileName column can't be deleted.");
            return;
        }

        model.removeColumnAndColumnData(jxTable.getSelectedColumn());
    }


    public TargetFileTableModel getTableModel() {
        return model;
    }


    public String[] getColumnTitles() {
        String[] titles = new String[jxTable.getColumnCount()];
        for (int i = 0; i < jxTable.getColumnCount(); i++) {
            titles[i] = model.getColumnName(i);
        }
        return titles;
    }


    public void addTargetFileTableColumnListener(TableColumnModelListener l) {
        jxTable.getColumnModel().addColumnModelListener(l);
    }


    private JXTable jxTable;
    private org.jdesktop.swingx.JXHyperlink addColumnHyperlink;
    private org.jdesktop.swingx.JXHyperlink addRowHyperlink;
    private javax.swing.JButton browseButton;
    private javax.swing.JLabel columnLabel;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private org.jdesktop.swingx.JXHeader jXHeader1;
    private org.jdesktop.swingx.JXHyperlink removeColumnHyperlink;
    private org.jdesktop.swingx.JXHyperlink removeRowHyperlink;
    private javax.swing.JLabel rowLabel;
    private javax.swing.JScrollPane tableScrollPane;
}
