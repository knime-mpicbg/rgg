/*
 * RVerticalBox.java
 *
 * Created on 18. Oktober 2006, 10:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.verticalbox;

import at.ac.arcs.rgg.component.EmptyPlaceHolder;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;

import javax.swing.*;
import java.util.ArrayList;


/**
 * @author ilhami
 */
public class RVerticalBox extends RElement {

    private static EmptyPlaceHolder eph = new EmptyPlaceHolder();
    private VisualComponent[][] visualComponentMatrix;
    VVerticalBox vvbox;


    /**
     * Creates a new instance of RVerticalBox
     */
    public RVerticalBox() {
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
        if (this.visualComponentMatrix != null) return this.visualComponentMatrix;
        ArrayList<ArrayList<VisualComponent>> visualComponentMatrixList = new ArrayList<ArrayList<VisualComponent>>();
        int width = 0; // the width of the matrix. by each element will be updated.
        int height = 0; // the height of the matrix. by each element will be updated.
        ArrayList<VisualComponent> visualComponentRow = null;

        for (RElement child : getChilds()) {
            //            int rowcounter = 0;
            if (child.hasVisualComponents()) {
                //vcomprow: corresponds to a line in the child's visualcomponent-matrix
                for (VisualComponent[] vcompRow : child.getVisualComponents()) {
                    visualComponentRow = new ArrayList<VisualComponent>();
                    for (VisualComponent vcomp : vcompRow)
                        visualComponentRow.add(vcomp);
                    visualComponentMatrixList.add(visualComponentRow);
                    //                    rowcounter++;
                    //                    //this means, whether it is at the beginning (height = 0)
                    //                    //or this child's visualcomponent-matrix height is higher
                    //                    //than any before child's elements.
                    //                    //therefore it should create a new visualComponentRow.
                    //                    //if width > 0 then fill with EmpytPlaceHolder as much as width
                    //                    if((height-rowcounter)<0){
                    //                        visualComponentRow = new ArrayList<VisualComponent>();
                    //                        visualComponentMatrix.add(visualComponentRow);
                    //                        for(int i=0;i<width;i++)
                    //                            visualComponentRow.add(eph);
                    //                        height++;
                    //                    }else//there exists already visualComponentRow for this line. use it!
                    //                        visualComponentRow = visualComponentMatrix.get(rowcounter-1);
                    //
                    //                    for(VisualComponent vcomp:vcompRow){
                    //                        if(vcomp == null)
                    //                            visualComponentRow.add(eph);
                    //                        else
                    //                            visualComponentRow.add(vcomp);
                    //                    }
                }
            } else
                continue;
            if (visualComponentRow.size() > width)
                width = visualComponentRow.size();
        }
        this.visualComponentMatrix = new VisualComponent[visualComponentMatrixList.size()][width];
        for (int i = 0; i < visualComponentMatrixList.size(); i++) {
            while (visualComponentMatrixList.get(i).size() < width)
                visualComponentMatrixList.get(i).add(eph);
            for (int j = 0; j < width; j++)
                this.visualComponentMatrix[i][j] = visualComponentMatrixList.get(i).get(j);
        }
        //        return this.visualComponentMatrix;
//        return new VisualComponent[][]{{createVVerticalBox(visualComponentMatrix)}};
        return new VisualComponent[][]{{createVVerticalBox2()}};
    }


    private VVerticalBox createVVerticalBox(VisualComponent[][] visualComponentMatrix) {
        ArrayList<ArrayList<JComponent>> swingMatrix = new ArrayList<ArrayList<JComponent>>();
        for (int i = 0; i < visualComponentMatrix.length; i++) {
            ArrayList<JComponent> swingMatrixRow = new ArrayList<JComponent>();
            for (int j = 0; j < visualComponentMatrix[i].length; j++) {
                swingMatrixRow.add(visualComponentMatrix[i][j].getSwingComponents()[0][0]);
            }
            swingMatrix.add(swingMatrixRow);
        }
        JComponent[][] swingArray = new JComponent[swingMatrix.size()][swingMatrix.get(0).size()];
        for (int i = 0; i < swingArray.length; i++) {
            for (int j = 0; j < swingArray[0].length; j++) {
                swingArray[i][j] = swingMatrix.get(i).get(j);
            }
        }
        vvbox = new VVerticalBox(swingArray);

        return vvbox;
    }


    private VVerticalBox createVVerticalBox2() {
        int rowCount = 0;
        int columnCount = 0;
        for (RElement child : getChilds()) {
            if (child.hasVisualComponents()) {
                rowCount += child.getSwingComponentMatrix().length;
                if (child.getSwingComponentMatrix()[0].length > columnCount)
                    columnCount = child.getSwingComponentMatrix()[0].length;
            }
        }

        JComponent[][] swingMatrix = new JComponent[rowCount][columnCount];

        int rowIndex = 0;
        int colIndex;
        for (RElement child : getChilds()) {
            if (child.hasVisualComponents()) {
                for (JComponent[] compRow : child.getSwingComponentMatrix()) {
                    colIndex = 0;
                    for (JComponent comp : compRow) {
                        swingMatrix[rowIndex][colIndex] = comp;
                        colIndex++;
                    }
                    while (colIndex < columnCount) {
//                        swingMatrix[rowIndex][colIndex] = new JLabel("");
                        swingMatrix[rowIndex][colIndex] = null;
                        colIndex++;
                    }
                    rowIndex++;
                }
            }
        }

        return vvbox = new VVerticalBox(swingMatrix);
    }


    public boolean isChildAddable() {
        return true;
    }


    public JComponent[][] getSwingComponentMatrix() {
        return vvbox.getSwingComponents();
    }
}
