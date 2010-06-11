package at.ac.arcs.rgg.element.maimporter.array;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * @author ilhami
 */
public class GenericArrayCreator extends ArrayCreator {

    @Override
    public Array makeArray(ArrayInfo arrayInfo) throws FileNotFoundException, IOException, ArrayDetectionException {
        Array array = new Array(ArrayInfo.GENERIC);
        array.setArrayInfo(arrayInfo);
        ArrayList<String> headers = extractHeaders(arrayInfo);
        array.setHeaders(headers);
        return array;
    }


    @Override
    public List<List<String>> readAssayData(File arrayFile, int rownumber, int headerLineNo) throws IOException {
        LineNumberReader reader = new LineNumberReader(
                new BufferedReader(new FileReader(arrayFile)));
        List<List<String>> assayData = new ArrayList();
        List<String> assayRow;

        String line;
        while ((line = reader.readLine()) != null) {
            if (reader.getLineNumber() == headerLineNo) {
                break;
            }
        }
        for (int i = rownumber; i > 0; i--) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            assayRow = Arrays.asList(line.split("\t"));
            assayData.add(assayRow);
        }
        reader.close();
        return assayData;
    }


    private ArrayList<String> extractHeaders(ArrayInfo arrayInfo) throws FileNotFoundException, IOException {
        String headerLine = getHeaderLine(arrayInfo);
        String[] headerSplitted = StringUtils.split(headerLine, '\t');

        ArrayList<String> headerList = new ArrayList<String>();
        for (int i = 0; i < headerSplitted.length; i++) {
            headerList.add(StringUtils.strip(headerSplitted[i], "\""));
        }
        return headerList;
    }


    private String getHeaderLine(ArrayInfo arrayInfo) throws FileNotFoundException, IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(arrayInfo.getArrayFile()));
        String line = null;
        while (reader.getLineNumber() < arrayInfo.getHeaderLineNo()) {
            line = reader.readLine();
        }
        reader.close();
        return line;
    }
}
