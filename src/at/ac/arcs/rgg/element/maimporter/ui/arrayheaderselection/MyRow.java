/*
 * MyList.java
 *
 * Created on 28. August 2004, 03:04
 */

package at.ac.arcs.rgg.element.maimporter.ui.arrayheaderselection;


/**
 * This class is a sub-class of {@link java.util#Vector } which
 * has additional feautes to manage the rows in a table.
 * @author Administrator
 */
public class MyRow<T> extends java.util.ArrayList<T> {
    private int size = 18;
    private Boolean selection=true;
    private String toolTip;
    
    /**
     * Creates a new instance of MyList
     */
    public MyRow() {
    }
    
    /**
     * Creates a new instance of MyList and sets the default size.
     * @param defaultsize Default collection size.
     */
    public MyRow(int defaultsize){
        super(defaultsize);
    }
    
    
    
    /**
     * Sets the size of this row, which will be used as the
     * row-size in the table.
     * @param size Size to set.
     */
    public void setMaxRowSize(int size_p){ size_p = size_p*15;  if(size_p > this.size) this.size = size_p; }
    
    /**
     * Sets the size of this row directly.
     * @see #setMaxRowSize(int)
     * @param size size
     */
    public void setSizeFix(int size){
        this.size = size;
    }
    /**
     * Returns size.
     * @return size
     */
    public int getSize(){ return size;}
    
    /**
     * Getter for property toolTip.
     * @return Value of property toolTip.
     */
    public java.lang.String getToolTip() {
        return toolTip;
    }
    
    /**
     * Setter for property toolTip.
     * @param toolTip New value of property toolTip.
     */
    public void setToolTip(java.lang.String toolTip) {
        this.toolTip = toolTip;
    }
    
    
    public Boolean getSelection(){
        if(selection==null)
            selection= false;
        return selection;
    }
    public void setSelection(Boolean status){
        selection=status;
    }
    
   
}
