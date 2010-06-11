/*
 * VVector.java
 *
 * Created on 17.04.2007, 10:57:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.vector;

import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import at.ac.arcs.rgg.component.VisualComponent;

/**
 *
 * @author ilhami
 */
public class VVector extends VisualComponent{
    private ArrayList<JComponent> vectorlist = new ArrayList<JComponent>();
    
    private JLabel label;
    
    private boolean vertical = false;
    
    private VectorType vectortype;
    
    private JComponent[][] swingComponents;
    
    /**
     *
     * @param numberoffields
     */
    public VVector(int numberoffields, Object[] defaultvalues,VectorType vectortype) {
        this.vectortype = vectortype;
        initializeComponents(numberoffields,defaultvalues,vectortype);
    }
    
    private void initializeComponents(int numberoffields, Object[] defaultvalues,VectorType vectortype){
        label = new JLabel();
        if(vectortype == VectorType.NUMERIC || vectortype == VectorType.CHARACTER){
            JFormattedTextField textfield;
            for(int i=0;i<numberoffields;i++){
                textfield = new JFormattedTextField();
                textfield.setColumns(5);
                if(i<defaultvalues.length)
                    textfield.setText(defaultvalues[i].toString());
                vectorlist.add(textfield);
            }
        }else if(vectortype == VectorType.LOGICAL){
            for(int i=0;i<numberoffields;i++){
                vectorlist.add(new JCheckBox(" ",(Boolean)defaultvalues[i]));
            }
        }
    }
    
    /**
     *
     * @return
     */
    public JComponent[][] getSwingComponents() {
        if(swingComponents == null){
            if(isVertical()){
                swingComponents = new JComponent[vectorlist.size()+1][1];
                swingComponents[0][0] = label;
                for(int i=1;i<=vectorlist.size();i++){
                    swingComponents[i][0] = vectorlist.get(i-1);
                }
            }else{
                swingComponents = new JComponent[1][vectorlist.size()+1];
                swingComponents[0][0] = label;
                for(int i=1;i<=vectorlist.size();i++){
                    swingComponents[0][i] = vectorlist.get(i-1);
                }
            }
        }
        return swingComponents;
    }
    
    public ArrayList<JComponent> getVectorlist() {
        return vectorlist;
    }
    
    /**
     *
     * @return
     */
    public String getLabelText() {
        return label.getText();
    }
    
    /**
     *
     * @param labeltext
     */
    public void setLabelText(final String labeltext) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                label.setText(labeltext);
                
            }
        });
    }
    
    //TODO span'i ayarla
    /**
     *
     * @param colspan
     */
    public void setColumnSpan(int colspan){
        if(colspan > 0){
            for(JComponent jftf:vectorlist)
                layoutInfo.setComponentColumnSpan(jftf,colspan);
        }
    }

    /**
     *
     * @param vertical
     */
    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }
    
    /**
     *
     * @return
     */
    public boolean isVertical() {
        return vertical;
    }
    
    public VectorType getVectortype() {
        return vectortype;
    }
    
    public void setVectortype(VectorType vectortype) {
        this.vectortype = vectortype;
    }
}
