/*
 * VList.java
 *
 * Created on 25. Juli 2006, 15:32
 */

package at.ac.arcs.rgg.element.listbox;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import at.ac.arcs.rgg.component.VisualComponent;

/**
 *
 * @author ilhami
 */
public class VListBox extends VisualComponent{
    private JScrollPane scrollPane;
    private JList list;
    private JLabel label;
    private JComponent[][] swingComponents;

    private boolean labelTextSet = false;
    /**
     * Creates a new instance of VList
     */
    public VListBox() {
        initComponents();
    }

    private void initComponents() {
        scrollPane = new JScrollPane();
        list = new JList();
        list.setVisibleRowCount(4);
        scrollPane.setViewportView(list);
        label = new JLabel();
    }

    public void setListData(Object[] items){
        list.setListData(items);
    }

    public JComponent[][] getSwingComponents() {
        if(swingComponents == null){
            if(labelTextSet)
                swingComponents = new JComponent[][]{{label},{scrollPane}};
            else
                swingComponents = new JComponent[][]{{scrollPane}};
        }

        return swingComponents;
    }

    public void setSelectedIndex(int index){
        list.setSelectedIndex(index);
    }


    public void setSelectedIndices(int[] selection) {
        list.setSelectedIndices(selection);
    }


    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }


    public int[] getSelectedIndices() {
        return list.getSelectedIndices();
    }


    public Object getSelectedValue(){
        return list.getSelectedValue();
    }

    public Object[] getSelectedValues(){
        return list.getSelectedValues();
    }

    public JList getList(){
        return list;
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

    public boolean isVisualComponent() {
        return true;
    }

    public void setColumnSpan(int colspan){
        if(colspan > 0)
            layoutInfo.setComponentColumnSpan(list,colspan);
    }

    public void setItems(Object[] items) {
        list.setListData(items);
    }

    public void setVisibleRowCount(int rowcount){
        list.setVisibleRowCount(rowcount);
    }

}
