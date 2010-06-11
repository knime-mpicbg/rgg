/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author ilhami
 */
public class RGGFileExtensionFilter extends FileFilter {

    // Description of this filter.
    private final String description;
    // Known extensions.
    private final String[] extensions;

    public RGGFileExtensionFilter(String description, String... extensions) {
        this.description = description;
        this.extensions = extensions;
    }

    @Override
    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                return true;
            }
            // NOTE: we tested implementations using Maps, binary search
            // on a sorted list and this implementation. All implementations
            // provided roughly the same speed, most likely because of
            // overhead associated with java.io.File. Therefor we've stuck
            // with the simple lightweight approach.
            String fileName = f.getName();
	    int i = fileName.lastIndexOf('.');
	    if (i > 0 && i < fileName.length() - 1) {
                String desiredExtension = fileName.substring(i+1);
                for (String extension : extensions) {
                    if (desiredExtension.equalsIgnoreCase(extension)) {
                        return true;
                    }
                }
	    }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
