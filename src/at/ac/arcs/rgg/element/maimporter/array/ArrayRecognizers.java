/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.array;

import at.ac.arcs.rgg.element.maimporter.array.affymetrix.AffymetrixArrayRecognizer;
import java.io.File;
import java.util.ArrayList;
import at.ac.arcs.rgg.element.maimporter.array.agilent.AgilentArrayRecognizer;
import at.ac.arcs.rgg.element.maimporter.array.genepix.GenepixArrayRecognizer;

/**
 *
 * @author ilhami
 */
public class ArrayRecognizers {

    private static ArrayList<IArrayRecognizer> list;

    public static ArrayList<IArrayRecognizer> constructArrayRecognizersList() {
        if (list == null) {
            list = new ArrayList<IArrayRecognizer>();
            list.add(new AffymetrixArrayRecognizer());
            list.add(new GenepixArrayRecognizer());
            list.add(new AgilentArrayRecognizer());
        }
        return list;
    }

    public static ArrayList<ArrayInfo> recognizeArraysInTargetFile(TargetFile targetFile)
            throws ArrayDetectionException {
        ArrayList<IArrayRecognizer> recognizers =
                ArrayRecognizers.constructArrayRecognizersList();

        ArrayList<ArrayInfo> arrayInfos = new ArrayList<ArrayInfo>();
        ArrayInfo arrayInfo = null;
        for (File array : targetFile.getFiles()) {
            for (IArrayRecognizer ar : recognizers) {
                arrayInfo = ar.recognize(array);
                if (arrayInfo != null) {
                    break;
                }
            }
            if(arrayInfo == null){
                arrayInfo = createGenericTypeArrayInfo(array);
            }
            arrayInfos.add(arrayInfo);
        }
        return arrayInfos;
    }
    
    public static  boolean checkArraysForUniformFeatureSet(ArrayList<ArrayInfo> arrayInfos) {
        String arrayType = arrayInfos.get(0).getArraySource();
        ArrayChannelInfo channelinf = arrayInfos.get(0).getChannelInfo();
        ArrayColorInfo colorinf = arrayInfos.get(0).getColorInfo();
        for (int i = 1; i < arrayInfos.size(); i++) {
            if (!arrayInfos.get(i).getArraySource().equals(arrayType)
                    && arrayInfos.get(i).getChannelInfo() != channelinf) {
                return false;
            }
        }
        return true;
    }

    private static ArrayInfo createGenericTypeArrayInfo(File array) {
        ArrayInfo inf = new ArrayInfo();
        inf.setArraySource(ArrayInfo.GENERIC);
        inf.setArrayFile(array);
        inf.setArrayCreator(new GenericArrayCreator());
        return inf;
    }
}
