/*
 * $Id: ColumnControlButton.java,v 1.40 2008/02/20 20:06:37 kleopatra Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package at.ac.arcs.rgg.element.maimporter.ui.inputselection;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.plaf.ColumnControlButtonAddon;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;
import org.jdesktop.swingx.table.DefaultTableColumnModelExt;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.table.TableColumnModelExt;

/**
 * A component to allow interactive customization of <code>JXTable</code>'s
 * columns. 
 * It's main purpose is to allow toggling of table columns' visibility. 
 * Additionally, arbitrary configuration actions can be exposed.
 * <p>
 * 
 * This component is installed in the <code>JXTable</code>'s
 * trailing corner, if enabled:
 * 
 * <pre><code>
 * table.setColumnControlVisible(true);
 * </code></pre>
 * 
 * From the perspective of a <code>JXTable</code>, the component's behaviour is
 * opaque. Typically, the button's action is to popup a component for user
 * interaction. <p>
 * 
 * This class is responsible for handling/providing/updating the lists of
 * actions and to keep all action's state in synch with Table-/Column state. 
 * The visible behaviour of the popup is delegated to a
 * <code>ColumnControlPopup</code>. <p>
 * 
 * @see TableColumnExt
 * @see TableColumnModelExt
 * @see JXTable#setColumnControl
 * 
 */
public class InputSelectorTable extends JXTable {

    public static final String NONE_OPTION = "-";
    

    static {
        LookAndFeelAddons.contribute(new ColumnControlButtonAddon());
    }
    // TODO: the table reference is a potential leak?
    /** The table which is controlled by this. */
    private JXTable table;
    /** Listener for table property changes. */
    private PropertyChangeListener tablePropertyChangeListener;
    /** Listener for table's columnModel. */
    TableColumnModelListener columnModelListener;
    private InputList inputs;

    /** the list of actions for column menuitems.*/
//    private List<ColumnVisibilityAction> columnVisibilityActions;
    /**
     * Creates a column control button for the table. Uses the default
     * icon as provided by the addon.
     * 
     * @param table  the <code>JXTable</code> controlled by this component
     */
    public InputSelectorTable(JXTable table, InputList options) {
        super();
        init();
        installTable(table);
        setOptions(options);
    }

    public InputSelectorTable(JXTable table) {
        super();
        init();
        installTable(table);
    }

    private TableColumnExt createTableColumn(TableColumn column) {
        FilterColumnExt newColumn = new FilterColumnExt(column);
        return newColumn;
    }

    private void createTableColumns() {

        DefaultTableModel m = getDTM();
        DefaultTableColumnModelExt cm = new DefaultTableColumnModelExt();
        List<TableColumn> columns = table.getColumns(true);
        for (TableColumn column : columns) {
            TableColumnExt action = createTableColumn(column);
            if (action != null) {
                cm.addColumn(action);
                m.addColumn(column.getHeaderValue());
            }
        }
        setColumnModel(cm);
    }

    private TableCellEditor getCellEditor(Object[] items) {
        JComboBox comboBox = new JComboBox(items);
        comboBox.setRenderer(new ComboRenderer());

        DefaultCellEditor editor = new DefaultCellEditor(comboBox) {

            int col;
            Object initval;

            @Override
            public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
                col = arg4;
                initval = arg1;
                return super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
            }

            @Override
            public boolean stopCellEditing() {
                if (getCellEditorValue() == InputList.NONE_OPTION) {
                    if (initval != null) {
                        ((InputInfo) initval).removeColumn(col);
                    }
                    return super.stopCellEditing();
                }


                if (getCellEditorValue() != null && getCellEditorValue() != InputList.NONE_OPTION && ((InputInfo) getCellEditorValue()).isEnabled()) {
                    if (initval != null) {
                        ((InputInfo) initval).removeColumn(col);
                    }
                    ((InputInfo) getCellEditorValue()).addColumn(col);
                    return super.stopCellEditing();
                }
                super.cancelCellEditing();
                return false;
            }
        };
        return editor;
    }

    private DefaultTableColumnModelExt getColumnModelExt() {
        if (getColumnModel() instanceof DefaultTableColumnModelExt) {
            return ((DefaultTableColumnModelExt) getColumnModel());
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void setOptions(InputList options) {
        this.inputs = options;
        getDTM().removeRow(0);
        Vector rowData = new Vector();
        getDTM().addRow(rowData);
        for (int i = 0; i < getColumnModelExt().getColumnCount(true); i++) {
            getDTM().setValueAt(InputList.NONE_OPTION, 0, i);
        }


        for (InputInfo ininfo : inputs.getInputs()) {
            for (int i = ininfo.getColumns().size() - 1; i >= 0; i--) {
                int j = ininfo.getColumns().get(i);
                try {
                    getDTM().setValueAt(ininfo, 0, j);
                } catch (Exception e) {
                    ininfo.removeColumn(j);
                }
            }
        }

        Object[] items = inputs.getInputs();
        setDefaultEditor(Object.class, getCellEditor(items));
        setDefaultRenderer(Object.class, new ComboCellRenderer(items));

    }

    DefaultTableModel getDTM() {
        DefaultTableModel m = (DefaultTableModel) getModel();
        return m;
    }
//-------------------------- Action in synch with column properties
    /**
     * A specialized <code>Action</code> which takes care of keeping in synch with
     * TableColumn state.
     * 
     * NOTE: client must call releaseColumn if this action is no longer needed!
     * 
     */
    public class FilterColumnExt extends TableColumnExt {

        private TableColumn column;
        private PropertyChangeListener columnListener;
        /** flag to distinguish selection changes triggered by
         *  column's property change from those triggered by
         *  user interaction. Hack around #212-swingx.
         */
        private boolean fromColumn;

        /**
         * Creates a action synched to the table column.
         * 
         * @param column the <code>TableColumn</code> to keep synched to.
         */
        public FilterColumnExt(TableColumn column) {
            super(column.getModelIndex(), column.getWidth());
//            setStateAction();
            installColumn(column);
        }

        /**
         * Releases all references to the synched <code>TableColumn</code>. 
         * Client code must call this method if the
         * action is no longer needed. After calling this action must not be
         * used any longer.
         */
        public void releaseColumn() {
            column.removePropertyChangeListener(columnListener);
            column = null;
        }

        /**
         * Does nothing. Synch from action state to TableColumn state
         * is done in itemStateChanged.
         */
        public void actionPerformed(ActionEvent e) {
        }

        /**
         * Synchs selected property to visible. This
         * is called on change of tablecolumn's <code>visible</code> property.
         * 
         * @param visible column visible state to synch to.
         */
        private void updateFromColumnVisible(boolean visible) {
            fromColumn = true;
            setVisible(visible);
            fromColumn = false;
        }

        /**
         * Synchs name property to value. This is called on change of 
         * tableColumn's <code>headerValue</code> property.
         * 
         * @param value
         */
        private void updateFromColumnHeader(Object value) {
            setTitle(String.valueOf(value));
        }

        private void updateFromColumnWidth(Object newValue) {
            setWidth(Integer.valueOf(String.valueOf(newValue)));
            setPreferredWidth(Integer.valueOf(String.valueOf(newValue)));
        }
        // -------------- init
        private void installColumn(TableColumn column) {
            this.column = column;
            column.addPropertyChangeListener(getColumnListener());
            updateFromColumnHeader(column.getHeaderValue());
            setVisible((column instanceof TableColumnExt) ? ((TableColumnExt) column).isVisible() : true);

        }

        /**
         * Returns the listener to column's property changes. The listener
         * is created lazily if necessary.
         * 
         * @return the <code>PropertyChangeListener</code> listening to 
         *   <code>TableColumn</code>'s property changes, guaranteed to be 
         *   not <code>null</code>.
         */
        protected PropertyChangeListener getColumnListener() {
            if (columnListener == null) {
                columnListener = createPropertyChangeListener();
            }
            return columnListener;
        }

        /**
         * Creates and returns the listener to column's property changes.
         * Subclasses are free to roll their own.
         * <p>
         * Implementation note: this listener reacts to column's
         * <code>visible</code> and <code>headerValue</code> properties and
         * calls the respective <code>updateFromXX</code> methodes.
         * 
         * @return the <code>PropertyChangeListener</code> to use with the
         *         column
         */
        protected PropertyChangeListener createPropertyChangeListener() {
            return new PropertyChangeListener() {

                public void propertyChange(PropertyChangeEvent evt) {
                    if ("visible".equals(evt.getPropertyName())) {
                        updateFromColumnVisible((Boolean) evt.getNewValue());
                    } else if ("headerValue".equals(evt.getPropertyName())) {
                        updateFromColumnHeader(evt.getNewValue());
                    } else if ("width".equals(evt.getPropertyName())) {
                        updateFromColumnWidth(evt.getNewValue());
                    }
                }
            };
        }
    }

//-------------------------- updates from table propertyChangelistener
    /**
     * Adjusts internal state after table's column model property has changed.
     * Handles cleanup of listeners to the old/new columnModel (Note, that
     * it listens to the column model only if it can control column visibility).
     * Updates content of popup.
     * 
     * @param oldModel the old <code>TableColumnModel</code> we had been listening to.
     */
    protected void updateFromColumnModelChange(TableColumnModel oldModel) {
        if (oldModel != null) {
            oldModel.removeColumnModelListener(columnModelListener);
        }
        populateModel();
        table.getColumnModel().addColumnModelListener(getColumnModelListener());
    }

    /**
     * Synchs this button's enabled with table's enabled.
     *
     */
    protected void updateFromTableEnabledChanged() {
//        getAction().setEnabled(table.isEnabled());
    }

    /**
     * Method to check if we can control column visibility POST: if true we can
     * be sure to have an extended TableColumnModel
     * 
     * @return boolean to indicate if controlling the visibility state is
     *   possible. 
     */
    protected boolean canControl() {
        return table.getColumnModel() instanceof TableColumnModelExt;
    }

//  ------------------------ updating the popup
    /**
     * Populates the popup from scratch.
     * 
     * If applicable, creates and adds column visibility actions. Always adds
     * additional actions.
     */
    protected void populateModel() {
        clearAll();

        createTableColumns();
        DefaultTableModel m = (DefaultTableModel) getModel();

        m.addRow(new Object[getColumnModelExt().getColumnCount(true)]);

        if (inputs != null) {
            for (int i = 0; i < getColumnModelExt().getColumnCount(true); i++) {
                getDTM().setValueAt(InputList.NONE_OPTION, 0, i);
            }
        }

    }

    /**
     * 
     * removes all components from the popup, making sure to release all
     * columnVisibility actions.
     * 
     */
    protected void clearAll() {

        List<TableColumn> oldcolumns = getColumnModelExt().getColumns(true);
        for (TableColumn toDel : oldcolumns) {
            ((FilterColumnExt) toDel).releaseColumn();
            getColumnModelExt().removeColumn(toDel);
        }
        getDTM().getDataVector().clear();
        getDTM().setColumnCount(0);
    }
    //--------------------------- init
    private void installTable(JXTable table) {
        this.table = table;
        table.addPropertyChangeListener(getTablePropertyChangeListener());
        updateFromColumnModelChange(null);
        updateFromTableEnabledChanged();
    }

    /**
     * Initialize the column control button's gui
     */
    private void init() {
        setTableHeader(null);
        setColumnControlVisible(true);
        setShowGrid(false);
        setAutoCreateColumnsFromModel(false);
        setRowHeight(23);

    }

    // -------------------------------- listeners
    /**
     * Returns the listener to table's property changes. The listener is 
     * lazily created if necessary. 
     * @return the <code>PropertyChangeListener</code> for use with the 
     *  table, guaranteed to be not <code>null</code>.
     */
    protected PropertyChangeListener getTablePropertyChangeListener() {
        if (tablePropertyChangeListener == null) {
            tablePropertyChangeListener = createTablePropertyChangeListener();
        }
        return tablePropertyChangeListener;
    }

    /**
     * Creates the listener to table's property changes. Subclasses are free
     * to roll their own. <p>
     * Implementation note: this listener reacts to table's <code>enabled</code> and
     * <code>columnModel</code> properties and calls the respective 
     * <code>updateFromXX</code> methodes.
     * 
     * @return the <code>PropertyChangeListener</code> for use with the table.
     */
    protected PropertyChangeListener createTablePropertyChangeListener() {
        return new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ("columnModel".equals(evt.getPropertyName())) {
                    System.out.println("column model changed");
                    updateFromColumnModelChange((TableColumnModel) evt.getOldValue());
                } else if ("enabled".equals(evt.getPropertyName())) {
                    updateFromTableEnabledChanged();
                } else if ("autoResizeMode".equals(evt.getPropertyName())) {
                    setAutoResizeMode((Integer) evt.getNewValue());
                } else if ("model".equals(evt.getPropertyName())) {
                    populateModel();
                }
            }
        };
    }

    /**
     * Returns the listener to table's column model. The listener is 
     * lazily created if necessary. 
     * @return the <code>TableColumnModelListener</code> for use with the 
     *  table's column model, guaranteed to be not <code>null</code>.
     */
    protected TableColumnModelListener getColumnModelListener() {
        if (columnModelListener == null) {
            columnModelListener = createColumnModelListener();
        }
        return columnModelListener;
    }

    /**
     * Creates the listener to columnModel. Subclasses are free to roll their
     * own.
     * <p>
     * Implementation note: this listener reacts to "real" columnRemoved/-Added by
     * populating the popups content from scratch.
     * 
     * @return the <code>TableColumnModelListener</code> for use with the
     *         table's columnModel.
     */
    protected TableColumnModelListener createColumnModelListener() {
        return new TableColumnModelListener() {

            /** Tells listeners that a column was added to the model. */
            public void columnAdded(TableColumnModelEvent e) {
                // quickfix for #192
                if (!isVisibilityChange(e, true)) {
//                    populateModel();
                }
            }

            /** Tells listeners that a column was removed from the model. */
            public void columnRemoved(TableColumnModelEvent e) {
                if (!isVisibilityChange(e, false)) {
//                    populateModel();
                }
            }

            /**
             * Check if the add/remove event is triggered by a move to/from the
             * invisible columns. 
             * 
             * PRE: the event must be received in columnAdded/Removed.
             * 
             * @param e the received event
             * @param added if true the event is assumed to be received via
             *        columnAdded, otherwise via columnRemoved.
             * @return boolean indicating whether the removed/added is a side-effect
             *    of hiding/showing the column.
             */
            private boolean isVisibilityChange(TableColumnModelEvent e,
                    boolean added) {
                // can't tell
                if (!(e.getSource() instanceof DefaultTableColumnModelExt)) {
                    return false;
                }
                DefaultTableColumnModelExt model = (DefaultTableColumnModelExt) e.getSource();
                if (added) {
                    return model.isAddedFromInvisibleEvent(e.getToIndex());
                } else {
                    return model.isRemovedToInvisibleEvent(e.getFromIndex());
                }
            }

            /** Tells listeners that a column was repositioned. */
            public void columnMoved(TableColumnModelEvent e) {
                if (!isVisibilityChange(e, true)) {
                    moveColumn(e.getFromIndex(), e.getToIndex());
                }
            }

            /** Tells listeners that a column was moved due to a margin change. */
            public void columnMarginChanged(ChangeEvent e) {
            }

            /**
             * Tells listeners that the selection model of the TableColumnModel
             * changed.
             */
            public void columnSelectionChanged(ListSelectionEvent e) {
            }
        };

    }

    public class ComboCellRenderer extends DefaultTableCellRenderer {

        private Object[] items;

        public ComboCellRenderer(Object... items) {
            this.items = items;
        }

        @Override
        public final Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

            JComboBox combo = new JComboBox(items);
            combo.setBorder(javax.swing.BorderFactory.createEtchedBorder());
            combo.setBackground(getBackground());
            if (table.getValueAt(row, col) != null) {
                combo.setSelectedItem(table.getValueAt(row, col));
            }
            return combo; // Insert the combo

        }
    }

    class ComboRenderer extends JLabel implements ListCellRenderer {

        public ComboRenderer() {
            setOpaque(true);
            setBorder(new EmptyBorder(1, 1, 1, 1));
        }

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            if (value != null && !((InputInfo) value).isEnabled()) {
                setBackground(list.getBackground());
                setForeground(UIManager.getColor("Label.disabledForeground"));
            }
            setFont(list.getFont());
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
} // end class
