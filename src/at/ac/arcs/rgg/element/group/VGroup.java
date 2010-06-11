/*
 * VGroup.java
 *
 * Created on 26.05.2007, 03:32:13
 */

package at.ac.arcs.rgg.element.group;

import javax.swing.JComponent;
import javax.swing.JPanel;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;


/**
 * @author ilhami
 */
public class VGroup extends VisualComponent {

    private JComponent[][] swingComponents;
    JPanel groupPanel;


    public VGroup(JPanel groupPanel) {
        this.groupPanel = groupPanel;
        this.swingComponents = new JPanel[][]{{groupPanel}};
    }


    public JComponent[][] getSwingComponents() {
        return swingComponents;
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0)
            LayoutInfo.setComponentColumnSpan(groupPanel, colspan);
    }

}
