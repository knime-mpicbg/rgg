/*
 * RFileChooser.java
 *
 * Created on 23. Oktober 2006, 23:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.filechooser;

import java.io.File;
import javax.swing.JComponent;

import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;


/**
 * @author ilhami
 */
public class RFileChooser extends RElement {

    private String var;
    private String label;
    private String description;
    private String[] extensions;
    private VisualComponent[][] visualcomponents;
    private VFileChooser vFilechooser;


    /**
     * Creates a new instance of RFileChooser
     */
    public RFileChooser() {
    }


    public String getRCode() {
        StringBuilder rcodebuilder = new StringBuilder();
        if (StringUtils.isNotBlank(var)) {
            rcodebuilder.append(var + "<-");
        }


        if (vFilechooser.isFilesSelected()) {
            if (vFilechooser.isMultiSelectionEnabled()) {
                rcodebuilder.append("c(");
                File[] files = vFilechooser.getSelectedFiles();
                for (int i = 0; i < files.length; i++) {
                    rcodebuilder.append("\"" + files[i].getPath() + "\"");
                    if (i != (files.length - 1)) {
                        rcodebuilder.append(",");
                    }
                }
                rcodebuilder.append(")");
            } else {
                rcodebuilder.append("\"" + vFilechooser.getFilePath() + "\"");
            }
        } else {
            rcodebuilder.append("NA");
        }
        return StringUtils.replace(rcodebuilder.toString(), "\\", "/");
    }


    public boolean hasVisualComponents() {
        return true;
    }


    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vFilechooser}};
        }
        return visualcomponents;
    }


    public boolean isChildAddable() {
        return false;
    }


    public VFileChooser getFileChooser() {
        return vFilechooser;
    }


    public void setFileChooser(VFileChooser filechooser) {
        this.vFilechooser = filechooser;
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public String[] getExtensions() {
        return extensions;
    }


    public void setExtensions(String[] extensions) {
        this.extensions = extensions;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public VFileChooser getVFileChooser() {
        return vFilechooser;
    }


    public void setVFileChooser(VFileChooser filechooser) {
        this.vFilechooser = filechooser;
    }


    public String getVariable() {
        return var;
    }


    public void setVariable(String var) {
        this.var = var;
    }


    public JComponent[][] getSwingComponentMatrix() {
        return vFilechooser.getSwingComponents();
    }

}
