/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.maimporter.array;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ilhami
 */
public abstract class ArrayCreator {
    public abstract Array makeArray(ArrayInfo arrayInfo) 
            throws FileNotFoundException,IOException,ArrayDetectionException;

    public abstract List<List<String>> readAssayData(File arrayFile, int rownumber, int headerLineNo)
            throws IOException;
}
