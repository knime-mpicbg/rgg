/*
 * RHorizontalBox.java
 *
 * Created on 18. Oktober 2006, 10:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.horizontalbox;

import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JLabel;

import at.ac.arcs.rgg.component.EmptyPlaceHolder;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;


/**
 * @author ilhami
 */
public class RHorizontalBox extends RElement {

    private static EmptyPlaceHolder eph = new EmptyPlaceHolder();
    private VisualComponent[][] visualComponentMatrix;
    private VHorizontalBox vhbox;


    /**
     * Creates a new instance of RHorizontalBox
     */
    public RHorizontalBox() {
    }


    public String getRCode() {
        StringBuffer rcode = new StringBuffer();
        int i;
        for (i = 0; i < getChilds().size() - 1; i++) {
            rcode.append(getChild(i).getRCode());
        }
        rcode.append(getChild(i).getRCode());
        return rcode.toString();
    }


    public boolean hasVisualComponents() {
        return true;
    }


    public VisualComponent[][] getVisualComponents() {
        if (this.visualComponentMatrix != null) {
            return this.visualComponentMatrix;
        }
        ArrayList<ArrayList<VisualComponent>> visualComponentMatrix = new ArrayList<ArrayList<VisualComponent>>();
        int width = 0; // the width of the matrix. by each element will be updated.

        int height = 0; // the height of the matrix. by each element will be updated.

        ArrayList<VisualComponent> visualComponentRow = null;

        for (RElement child : getChilds()) {
            int rowcounter = 0;
            if (child.hasVisualComponents()) {
                for (VisualComponent[] vcompRow : child.getVisualComponents()) {
                    rowcounter++;
                    //this means, whether it is at the beginning (height = 0) 
                    //or this child's visualcomponent[][] is higher
                    //than any before child elements. 
                    //therefore it should create a new visualComponentRow.
                    //if width > 0 then fill with EmpytPlaceHolder as much as width
                    if ((height - rowcounter) < 0) {
                        visualComponentRow = new ArrayList<VisualComponent>();
                        visualComponentMatrix.add(visualComponentRow);
                        for (int i = 0; i < width; i++) {
                            visualComponentRow.add(eph);
                        }
                        height++;
                    } else//there exists already visualComponentRow for this line. use it!
                    {
                        visualComponentRow = visualComponentMatrix.get(rowcounter - 1);
                    }
                    for (VisualComponent vcomp : vcompRow) {
                        if (vcomp == null) {
                            visualComponentRow.add(eph);
                        } else {
                            visualComponentRow.add(vcomp);
                        }
                    }
                }
            } else {
                continue;
            }
            width = visualComponentRow.size(); // width is equal to the last visualComponentRow.size(). last or first doesn't make difference

        }
        this.visualComponentMatrix = new VisualComponent[height][width];
        for (int i = 0; i < visualComponentMatrix.size(); i++) {
            for (int j = 0; j < visualComponentMatrix.get(i).size(); j++) {
                this.visualComponentMatrix[i][j] = visualComponentMatrix.get(i).get(j);
            }
        }
//        return this.visualComponentMatrix;
        return new VisualComponent[][]{{createHBox()}};
    }


    public boolean isChildAddable() {
        return true;
    }


    private VHorizontalBox createHBox() {
        int rowCount = 0;
        int columnCount = 0;
        for (RElement child : getChilds()) {
            if (child.hasVisualComponents()) {
                columnCount += child.getSwingComponentMatrix()[0].length;
                if (child.getSwingComponentMatrix().length > rowCount) {
                    rowCount = child.getSwingComponentMatrix().length;
                }
            }
        }

        JComponent[][] swingMatrix = new JComponent[rowCount][columnCount];

        int rowIndex = 0;
        int colIndex = 0;
        for (RElement child : getChilds()) {
            rowIndex = 0;
            if (child.hasVisualComponents()) {
                for (int i = 0; i < child.getSwingComponentMatrix().length; i++) {
                    for (JComponent comp : child.getSwingComponentMatrix()[i]) {
                        swingMatrix[rowIndex][colIndex] = comp;
                        colIndex++;
                    }
                    rowIndex++;
                    if (i + 1 < child.getSwingComponentMatrix().length) {
                        colIndex -= child.getSwingComponentMatrix()[i].length;
                    }
                }
            }
        }

        for (int i = 0; i < swingMatrix.length; i++) {
            for (int j = 0; j < swingMatrix[i].length; j++) {
                if (swingMatrix[i][j] == null) {
                    swingMatrix[i][j] = new JLabel();
                }
            }
        }

        return vhbox = new VHorizontalBox(swingMatrix);
    }


    public JComponent[][] getSwingComponentMatrix() {
        return vhbox.getSwingComponents();
    }


    public class VHorizontalBox extends VisualComponent {

        public VHorizontalBox(JComponent swingComponents[][]) {
            this.swingComponents = swingComponents;
        }


        public JComponent[][] getSwingComponents() {
            return swingComponents;
        }


        private JComponent swingComponents[][];
    }

}
