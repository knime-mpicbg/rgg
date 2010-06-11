/*
 * VisualComponent.java
 *
 * Created on 03. August 2006, 10:02
 */

package at.ac.arcs.rgg.component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;

/**
 *
 * @author ilhami
 */
public abstract class VisualComponent{
    protected LayoutInfo layoutInfo = new LayoutInfo();
    public abstract JComponent[][] getSwingComponents();
    protected PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public LayoutInfo getLayoutInfo() {
        return layoutInfo;
    }

    public void setLayoutInfo(LayoutInfo layoutinfo) {
        this.layoutInfo = layoutinfo;
    }
    
    public  void addPropertyChangeListener(PropertyChangeListener listener) {
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
