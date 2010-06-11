package at.ac.arcs.rgg.element;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;

import at.ac.arcs.rgg.component.VisualComponent;


public abstract class RElement {

    protected ArrayList<RElement> childElements;
    protected PropertyChangeSupport changeSupport;


    public RElement() {
        childElements = new ArrayList<RElement>();
        changeSupport = new PropertyChangeSupport(this);
    }


    public abstract String getRCode();


    public abstract boolean hasVisualComponents();


    public abstract VisualComponent[][] getVisualComponents();


    public abstract JComponent[][] getSwingComponentMatrix();


    public abstract boolean isChildAddable();


    public void addChild(RElement elem) {
        if (!isChildAddable()) {
            throw new UnsupportedOperationException("This element doesn't accept any child elements.");
        } else {
            childElements.add(elem);
            return;
        }
    }


    public RElement getChild(int i) {
        return childElements.get(i);
    }


    public List<RElement> getChilds() {
        return childElements;
    }


    public boolean hasChild() {
        return childElements.size() > 0;
    }


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