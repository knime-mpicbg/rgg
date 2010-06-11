package at.ac.arcs.rgg.element.maimporter.array;

import java.io.File;

/**
 *
 * @author ilhami
 */
public interface IArrayRecognizer {

    /**
     * 
     * @param array Array file
     * @return ArrayInfo
     */
    ArrayInfo recognize(File array)
            throws ArrayDetectionException;
}
