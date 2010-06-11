/*
 * VComboBox.java
 *
 * Created on 25. Juli 2006, 15:32
 */
package at.ac.arcs.rgg.element.combobox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;


/**
 * @author ilhami
 */
public class VComboBox extends VisualComponent {

    private JComboBox comboBox;
    JLabel label;
    private boolean labelTextSet = false;
    private JComponent[][] swingMatrix;
    private boolean enabled;
    private Object[] items;


    /**
     * Creates a new instance of VComboBox
     */
    public VComboBox() {
        enabled = true;
        initComponents();
    }


    private void initComponents() {
        comboBox = new JComboBox(new String[]{"          "});
        label = new JLabel();
    }


    public JComponent[][] getSwingComponents() {
        if (swingMatrix == null) {
            if (labelTextSet) {
                swingMatrix = (new JComponent[][]{
                        new JComponent[]{
                                label, comboBox
                        }
                });
            } else {
                swingMatrix = (new JComponent[][]{
                        new JComponent[]{
                                comboBox
                        }
                });
            }
        }
        return swingMatrix;
    }


    public void setSelectedIndex(int index) {
        comboBox.setSelectedIndex(index);
    }


    public void setSelectedItem(Object item) {
        comboBox.setSelectedItem(item);
    }


    public int getSelectedIndex() {
        return comboBox.getSelectedIndex();
    }


    public Object getSelectedItem() {
        return comboBox.getSelectedItem();
    }


    public JComboBox getComboBox() {
        return comboBox;
    }


    public JLabel getLabel() {
        return label;
    }


    public void setLabelText(final String labeltext) {
        labelTextSet = true;
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                label.setText(labeltext);
            }
        });
    }


    public String getLabelText() {
        return label.getText();
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(final boolean e) {
        this.enabled = e;
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                comboBox.setEnabled(e);
                label.setEnabled(e);
            }
        });
    }


    public boolean isVisualComponent() {
        return true;
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(comboBox, Integer.valueOf(colspan));
        }
    }


    public Object[] getItems() {
        return items;
    }


    public void setItems(Object[] items) {
        this.items = items;
        if (items == null) {
            comboBox.setModel(new DefaultComboBoxModel());
        } else {
            comboBox.setModel(new DefaultComboBoxModel(items));
        }
    }

}
