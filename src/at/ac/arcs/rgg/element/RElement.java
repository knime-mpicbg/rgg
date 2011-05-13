package at.ac.arcs.rgg.element;

import at.ac.arcs.rgg.component.VisualComponent;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;


public abstract class RElement {

    protected PropertyChangeSupport changeSupport;


    public RElement() {
        changeSupport = new PropertyChangeSupport(this);
    }


    public abstract String getRCode();


    public abstract boolean hasVisualComponents();


    public abstract VisualComponent[][] getVisualComponents();


    public abstract JComponent[][] getSwingComponentMatrix();


    public abstract boolean isChildAddable();


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        } else {
            changeSupport.addPropertyChangeListener(listener);
            return;
        }
    }


    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        } else {
            changeSupport.removePropertyChangeListener(listener);
            return;
        }
    }


    // add the ability to persist and restore the configuration of the template
    //todo this should be abstract to enforce its implementation
    public void persistState(Map<String, Object> persistMap) {

    }


    public void restoreState(Map<String, Object> persistMap) {

    }
}