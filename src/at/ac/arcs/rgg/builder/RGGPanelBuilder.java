/*
 * RGGPanelBuilder.java
 *
 * Created on 01. August 2006, 13:15
 */
package at.ac.arcs.rgg.builder;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.Sizes;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;
import at.ac.arcs.rgg.RGGPanelModel;
import com.jgoodies.forms.layout.RowSpec;

/**
 *
 * @author ilhami
 */
public class RGGPanelBuilder {

    private boolean isGroup = false;
    /**
     * Holds major column start indices. To be compatible with {@link FormLayout}
     * first major column starts always by 1.
     * Example:first integer indicates first major column, second integer the second 
     * first major column starts at <code>majorColumnsStartIndices.get(0)</code>
     * second major column starts at <code>majorColumnsStartIndices.get(1)</code>
     * and so on
     */
    private ArrayList<Integer> majorColumnsStartIndices = new ArrayList<Integer>();
//    private static Log log = LogFactory.getLog(RGGPanelBuilder.class);
    /**
     * Creates a new instance of RGGPanelBuilder
     */
    public RGGPanelBuilder() {
    }

    public void setGroup(boolean b) {
        isGroup = b;
    }

    public JPanel buildPanel(RGGPanelModel model, boolean useDefaultDialogBorder, boolean debug) {
        FormLayout layout = getFormLayout(model);
        DefaultFormBuilder builder;
        if (debug) {
            builder = new DefaultFormBuilder(layout, new FormDebugPanel());
        } else {
            builder = new DefaultFormBuilder(layout);
        }

        if (useDefaultDialogBorder) {
            builder.setDefaultDialogBorder();
        }

        JComponent[][] swingMatrix = buildSwingMatrix(model);
        for (int i = 0; i < swingMatrix.length; i++) {
            if (isGroup) {
                layout.appendRow(RowSpec.decode("pref:grow"));
            } else {
                layout.appendRow(RowSpec.decode("pref"));
            //layout.appendRow(new RowSpec("pref"));
            }
            if (i < swingMatrix.length - 1) {
                layout.appendRow(RowSpec.decode("5dlu"));
            //layout.appendRow(new RowSpec("5dlu"));
            }
        }
        int counter = 0;
        for (JComponent[] swingMatrixRow : swingMatrix) {
            if (counter > 0) {
                builder.nextLine(2);
            }
            for (JComponent comp : swingMatrixRow) {
                if (comp == null) {
                    builder.nextColumn();
                } else {
                    builder.append(comp, checkColumnSpan(builder, LayoutInfo.getComponentSpan(comp)));
                }
            }
            counter++;
        }
        return builder.getPanel();
    }

    private FormLayout getFormLayout(RGGPanelModel model) {
        FormLayout layout = new FormLayout();
        for (int i = 1; i <= model.getMajorColumnNumber(); i++) {
            appendMajorColumn(model.getMinorColumnNumber(i), layout);
        }
        return layout;
    }

    /**
     * appends a new major column. each major column has minor columns
     * as much as <code>minorColumns</code>
     * @param minorColumns number of minor columns in this major column
     * @throws IllegalArgumentException if <code>minorColumns</code> is equal to 0.
     */
    private void appendMajorColumn(int minorColumns, FormLayout layout) {
        //TODO columnspec olarak string kullanmak ("pref:grow") yerine
        //     object olustur ve setle.daha efektiv olabilir
//        ColumnSpec colspec;
        if (minorColumns == 0) {
            throw new IllegalArgumentException("Minor columns number can't be zero." +
                    "Each major column must have at least one minor column.");
        }
        if (majorColumnsStartIndices.size() > 0) {
            layout.appendColumn(new ColumnSpec(Sizes.DLUX9)); //add a major glue column
            majorColumnsStartIndices.add(new Integer(layout.getColumnCount() + 1));
        } else {
            majorColumnsStartIndices.add(new Integer(1));
        }

        for (int i = 1; i <= minorColumns; i++) {
            if (isGroup) {
                layout.appendColumn(ColumnSpec.decode("pref:grow"));
            } else {
                layout.appendColumn(ColumnSpec.decode("pref"));
            //layout.appendColumn(new ColumnSpec("pref"));
            }
            if (i != minorColumns) //for the last column don't add a glue column
            {
                layout.appendColumn(new ColumnSpec(Sizes.DLUX2));
            } //add a glue column
        }
    }

    private JComponent[][] buildSwingMatrix(RGGPanelModel model) {
        int rowCount = 0;
        int columnCount = 0;
        for (List<VisualComponent> vcompRow : model.getVisualComponentMatrix()) {
            for (VisualComponent vcomp : vcompRow) {
                rowCount += vcomp.getSwingComponents().length;
                if (vcomp.getSwingComponents()[0].length > columnCount) {
                    columnCount = vcomp.getSwingComponents()[0].length;
                }
            }
        }

        JComponent[][] swingMatrix = new JComponent[rowCount][columnCount];

        int rowIndex = 0;
        int colIndex;
        for (List<VisualComponent> vcompRow : model.getVisualComponentMatrix()) {
            for (VisualComponent vcomp : vcompRow) {
                for (JComponent[] compRow : vcomp.getSwingComponents()) {
                    colIndex = 0;
                    for (JComponent comp : compRow) {
                        swingMatrix[rowIndex][colIndex] = comp;
                        colIndex++;
                    }
                    while (colIndex < columnCount) {
                        swingMatrix[rowIndex][colIndex] = null;
                        colIndex++;
                    }
                    rowIndex++;
                }
            }
        }
        return swingMatrix;
    }

    private int checkColumnSpan(DefaultFormBuilder builder, int colspan) {
        int column = builder.getColumn();
        if (colspan > (builder.getColumnCount() - column + 1)) {
            return (builder.getColumnCount() - column + 1);
        }
        return colspan;
    }
}
