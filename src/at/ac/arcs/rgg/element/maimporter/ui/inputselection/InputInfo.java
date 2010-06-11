/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.ui.inputselection;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


/**
 * @author ahmet
 */
public class InputInfo implements Comparable {

    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private ArrayList<Integer> columns;
    private String id;


    public String getId() {
        return id;
    }


    public enum OptionType {

        ONE_TO_ONE, //one column to one input, one input to one column
        MANY_TO_ONE// many columns can have the same input, an input can be bound to only one column
    }


    private OptionType optionType = OptionType.ONE_TO_ONE;


    public InputInfo(String id, OptionType type) {
        this(id, type, -1);
    }


    public InputInfo(String id, OptionType type, int column) {
        if (id == null) {
            throw new NullPointerException("id can't be null");
        }
        columns = new ArrayList<Integer>();

        if (column > 0)
            columns.add(column);
        this.id = id;
        setOptionType(type);
    }


    /**
     * Get the value of optionType
     *
     * @return the value of optionType
     */
    public OptionType getOptionType() {
        return optionType;
    }


    /**
     * Set the value of optionType
     *
     * @param optionType new value of optionType
     */
    public void setOptionType(OptionType optionType) {
        if (optionType == null) {
            optionType = OptionType.ONE_TO_ONE;
        }
        this.optionType = optionType;
    }


    /**
     * Get the value of column
     *
     * @return the value of column
     */
    public int getFirstColumn() {
        if (columns.isEmpty()) {
            return -1;
        }
        return columns.get(0);
    }


    public boolean isAssignedToColumns() {
        return !columns.isEmpty();
    }


    public ArrayList<Integer> getColumns() {
        return columns;
    }


    public void setColumns(ArrayList<Integer> columns) {
        ArrayList<Integer> old = this.columns;
        this.columns = columns;
        changeSupport.firePropertyChange(id, old, columns);
    }


    public void setColumns(Integer column) {
        ArrayList<Integer> old = this.columns;
        if (optionType == OptionType.ONE_TO_ONE) {
            columns = new ArrayList<Integer>();
            columns.add(column);
        } else {
            throw new IllegalStateException("This method can be used " +
                    "only if there is a one-to-one relation!");
        }
        changeSupport.firePropertyChange(id, old, columns);
    }


    /**
     * Set the value of column
     *
     * @param column new value of column
     */
    public boolean setFirstColumn(int column) {

        if (optionType == OptionType.ONE_TO_ONE && !columns.isEmpty()) {
            return false;
        }
        ArrayList<Integer> old = new ArrayList<Integer>(columns);
        columns.add(0, column);
        changeSupport.firePropertyChange(id, old, columns);
        return true;
    }


    public boolean addColumn(int column) {
        if (optionType == OptionType.ONE_TO_ONE && !columns.isEmpty()) {
            return false;
        }
        ArrayList<Integer> old = new ArrayList<Integer>(columns);
        columns.add(column);
        changeSupport.firePropertyChange(id, old, columns);
        return true;
    }


    public void removeColumn(int column) {
        ArrayList<Integer> old = new ArrayList<Integer>(columns);
        columns.remove((Integer) column);
        changeSupport.firePropertyChange(id, old, columns);
    }


    public int compareTo(Object other) {
        if (other == null) {
            return 1;
        }

        return this.getId().compareTo(((InputInfo) other).getId());
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InputInfo other = (InputInfo) obj;
        if (!this.id.equalsIgnoreCase(other.id) && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    @Override
    public String toString() {
        return id;
    }


    public boolean isEnabled() {
        if (optionType == OptionType.ONE_TO_ONE && isAssignedToColumns())
            return false;
        return true;
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        changeSupport.addPropertyChangeListener(listener);
    }


    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        changeSupport.removePropertyChangeListener(listener);
    }
}
