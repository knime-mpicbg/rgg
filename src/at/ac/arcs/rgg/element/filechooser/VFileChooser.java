/*
 * VFileChooser.java
 *
 * Created on 24. Oktober 2006, 00:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.filechooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;
import at.ac.arcs.rgg.util.RGGFileExtensionFilter;

/**
 *
 * @author ilhami
 */
public class VFileChooser extends VisualComponent {

    private final static String LASTUSEDDIR = "lastuseddir";
    private JLabel label;
    private JFileChooser filechooser;
    private JTextField selectedFilePathField;
    private JButton button;
    private File[] selectedFiles;
    private JComponent[][] swingComps;
    private RGG rggInstance;
    private boolean enabled = true;

    /** Creates a new instance of VFileChooser */
    public VFileChooser(RGG rggInstance) {
        this.rggInstance = rggInstance;
        rggInstance.setProperty(LASTUSEDDIR, rggInstance.getBaseDirForFileDialogs());
        initComponents();
    }

    private void initComponents() {
        filechooser = new JFileChooser();
        selectedFilePathField = new JTextField("Click \"Browse\" to open file dialog");
        selectedFilePathField.setEditable(false);
        selectedFilePathField.setCaretPosition(0);
        button = new JButton("   Browse   ");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                filechooser.setCurrentDirectory((File) rggInstance.getProperty(LASTUSEDDIR));
                int returnval = filechooser.showDialog(button, null);
                if (returnval == JFileChooser.APPROVE_OPTION) {
                    if (filechooser.isDirectorySelectionEnabled()) {
                        rggInstance.setProperty(LASTUSEDDIR, filechooser.getSelectedFile());
                    } else {
                        rggInstance.setProperty(LASTUSEDDIR, filechooser.getSelectedFile().getParentFile());
                    }
                    selectedFilePathField.setText(filechooser.getSelectedFile().getPath());
                    setSelectedFiles(filechooser.getSelectedFiles());
                }
            }
        });

    }

    public JFileChooser getFileChooser(){
        return filechooser;
    }
    
    public boolean isVisualComponent() {
        return true;
    }

    public JComponent[][] getSwingComponents() {
        if (label != null) {
            swingComps = new JComponent[][]{{label, selectedFilePathField, button}};
        } else {
            swingComps = new JComponent[][]{{selectedFilePathField, button}};
        }
        return swingComps;
    }

    public void setExtensionFilter(String description, String[] fileExtensions) {
        final RGGFileExtensionFilter filter = new RGGFileExtensionFilter(description, fileExtensions);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                filechooser.addChoosableFileFilter(filter);
            }
        });
    }

    public boolean isFilesSelected() {
        if (filechooser.getSelectedFile() != null ||
                filechooser.getSelectedFiles().length > 0) {
            return true;
        }
        return false;
    }

    public String getFilePath() {
        return filechooser.getSelectedFile().getPath();
    }

    public File[] getSelectedFiles() {
        return selectedFiles;
    }

    public void setSelectedFiles(File[] selectedFiles) {
        changeSupport.firePropertyChange("selectedFiles", this.selectedFiles, selectedFiles);
        this.selectedFiles = selectedFiles;
    }

    public void setLabel(String text) {
        label = new JLabel(text);
    }

    public void setFileSelectionMode(int mode) {
        filechooser.setFileSelectionMode(mode);
    }

    public int getFileSelectionMode(){
        return filechooser.getFileSelectionMode();
    }
    
    public void setMultiSelectionEnabled(boolean b) {
        filechooser.setMultiSelectionEnabled(b);
    }

    public boolean isMultiSelectionEnabled() {
        return filechooser.isMultiSelectionEnabled();
    }

    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(selectedFilePathField, colspan);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        selectedFilePathField.setEnabled(enabled);
        button.setEnabled(enabled);
        if (label != null) {
            label.setEnabled(enabled);
        }
    }
}
