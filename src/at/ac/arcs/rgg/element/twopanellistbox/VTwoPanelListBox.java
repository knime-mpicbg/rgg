/*
 * VList.java
 *
 * Created on 25. Juli 2006, 15:32
 */

package at.ac.arcs.rgg.element.twopanellistbox;

import at.ac.arcs.rgg.component.VisualComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author ilhami
 */
public class VTwoPanelListBox extends VisualComponent {

    private JLabel label;
    private JComponent[][] swingComponents;

    private boolean labelTextSet = false;
    private TwoPaneSelectionPanel<String> selectPanel;


    /**
     * Creates a new instance of VList
     */
    public VTwoPanelListBox() {
        initComponents();
    }


    public TwoPaneSelectionPanel<String> getSelectPanel() {
        return selectPanel;
    }


    private void initComponents() {
        label = new JLabel();
        selectPanel = new TwoPaneSelectionPanel<String>(false, new DefaultListCellRenderer());
    }


    public void setListData(List<String> items) {
        selectPanel.update(items, new ArrayList<String>());
    }


    public JComponent[][] getSwingComponents() {
        if (swingComponents == null) {
            if (labelTextSet)
                swingComponents = new JComponent[][]{{label}, {selectPanel}};
            else
                swingComponents = new JComponent[][]{{selectPanel}};
        }

        return swingComponents;
    }

//    public void setSelectedIndex(int index){
//        list.setSelectedIndex(index);
//    }
//
//
//    public void setSelectedIndices(int[] selection) {
//        list.setSelectedIndices(selection);
//    }
//
//
//    public int getSelectedIndex() {
//        return list.getSelectedIndex();
//    }
//
//
//    public int[] getSelectedIndices() {
//        return list.getSelectedIndices();
//    }
//
//
//    public Object getSelectedValue(){
//        return list.getSelectedValue();
//    }


    public List<String> getSelectedValues() {
        return new ArrayList<String>(selectPanel.getIncludedColumnSet());
    }


    public JLabel getLabel() {
        return label;
    }


    public void setLabelText(final String labeltext) {
        labelTextSet = true;
        label.setText(labeltext);
    }


    public String getLabelText() {
        return label.getText();
    }


    public boolean isVisualComponent() {
        return true;
    }


    public void setColumnSpan(int colspan) {
        //todo what is this
        if (colspan > 0)
            layoutInfo.setComponentColumnSpan(selectPanel, colspan);
    }


    public void setIncludes(final List<String> options) {
        selectPanel.include(options);
    }
}