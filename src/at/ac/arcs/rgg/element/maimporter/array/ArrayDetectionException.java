/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.arcs.rgg.element.maimporter.array;

import java.io.File;

/**
 *
 * @author ilhami
 */
public class ArrayDetectionException extends Exception{
    private File arrayFile;
    
    public ArrayDetectionException(File arrayFile,String msg){
        super(msg);
        this.arrayFile=arrayFile;
    }

    public File getArrayFile() {
        return arrayFile;
    }

    public void setArrayFile(File arrayFile) {
        this.arrayFile = arrayFile;
    }
    
}
