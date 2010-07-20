package at.ac.arcs.rgg.element.maimporter.array.affymetrix;

import at.ac.arcs.rgg.element.maimporter.array.ArrayDetectionException;
import at.ac.arcs.rgg.element.maimporter.array.ArrayInfo;
import at.ac.arcs.rgg.element.maimporter.array.IArrayRecognizer;

import java.io.File;


/**
 * @author ilhami
 */
public class AffymetrixArrayRecognizer implements IArrayRecognizer {

    public ArrayInfo recognize(File array) throws ArrayDetectionException {
        String ext = array.getName().substring(array.getName().lastIndexOf('.') + 1, array.getName().length());
        if (ext.equalsIgnoreCase("CEL")) {
            ArrayInfo inf = new ArrayInfo();
            inf.setArraySource("affymetrix");
            return inf;
        } else
            return null;
    }
}
