/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.array.genepix;

import java.io.BufferedReader;
import java.util.List;
import at.ac.arcs.rgg.element.maimporter.array.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author ilhami
 */
public class GenepixArrayCreator extends ArrayCreator {

    private static String[] columns = //G,Gb,R,Rb
            {"F532 Mean", "B532 Median", "F635 Mean", "B635 Median"};
    private static String[] annotations = {"Block", "Row", "Column", "ID", "Name"};

    @Override
    public Array makeArray(ArrayInfo arrayInfo)
            throws FileNotFoundException, IOException, ArrayDetectionException {
        Array array = new Array("genepix");
        array.setArrayInfo(arrayInfo);
        ArrayList<String> headers = extractHeaders(arrayInfo);
        array.setHeaders(headers);

        if (arrayInfo.getChannelInfo() == ArrayChannelInfo.TWOCHANNEL) {
            array.setGHeaderIndex(getInputInfoIndex(columns[0], headers));
            array.setGb(getInputInfoIndex(columns[1], headers));
            array.setR(getInputInfoIndex(columns[2], headers));
            array.setRb(getInputInfoIndex(columns[3], headers));
        }else if(arrayInfo.getColorInfo() == ArrayColorInfo.G){
            array.setGHeaderIndex(getInputInfoIndex(columns[0], headers));
            array.setGb(getInputInfoIndex(columns[1], headers));            
        }else{
            array.setR(getInputInfoIndex(columns[2], headers));
            array.setRb(getInputInfoIndex(columns[3], headers));
        }
        setArraysAnnotations(array, headers);
        return array;
    }

    private ArrayList<String> extractHeaders(ArrayInfo arrayInfo)
            throws FileNotFoundException, IOException, ArrayDetectionException {

        String headerLine = getHeaderLine(arrayInfo.getArrayFile());
        String[] headerSplitted = StringUtils.split(headerLine, '\t');
        if (arrayInfo.getChannelInfo() == ArrayChannelInfo.ONECHANNEL) {
            return filterHeaders(headerSplitted, arrayInfo.getColorInfo());
        } else {
            ArrayList<String> headerList = new ArrayList<String>();
            for (int i = 0; i < headerSplitted.length; i++) {
                headerList.add(StringUtils.strip(headerSplitted[i], "\""));
            }
            return headerList;
        }
    }

    private ArrayList<String> filterHeaders(String[] headerSplitted, ArrayColorInfo colorInfo) {
        if (colorInfo == null) {
            throw new NullPointerException("Color is not set!");
        }
        if (colorInfo == ArrayColorInfo.G) {
            return gFilterHeaders(headerSplitted);
        } else {
            return rFilterHeaders(headerSplitted);
        }
    }

    private ArrayList<String> gFilterHeaders(String[] headerSplitted) {
        ArrayList<String> headerList = new ArrayList<String>();
        for (int i = 0; i < headerSplitted.length; i++) {
            if (!headerSplitted[i].contains("635")) {
                headerList.add(StringUtils.strip(headerSplitted[i], "\""));
            }
        }
        return headerList;
    }

    private ArrayList<String> rFilterHeaders(String[] headerSplitted) {
        ArrayList<String> headerList = new ArrayList<String>();
        for (int i = 0; i < headerSplitted.length; i++) {
            if (!headerSplitted[i].contains("532")) {
                headerList.add(StringUtils.strip(headerSplitted[i], "\""));
            }
        }
        return headerList;
    }

    private String getHeaderLine(File arrayFile)
            throws FileNotFoundException, IOException, ArrayDetectionException {
        LineNumberReader reader = new LineNumberReader(new FileReader(arrayFile));

        String line = reader.readLine();
        if (!line.startsWith("ATF")) {
            throw new ArrayDetectionException(arrayFile, "File is not in Axon Text File (ATF) format");
        }

        line = reader.readLine();
        int numberOfOptionalHeaderRecords = Integer.parseInt(StringUtils.split(line)[0]);

        while ((line = reader.readLine()) != null) {
            if (reader.getLineNumber() == numberOfOptionalHeaderRecords + 3) {
                reader.close();
                return line;
            }
        }
        reader.close();
        throw new ArrayDetectionException(arrayFile, "Premature end of file.");
    }

    private void setArraysAnnotations(Array array, ArrayList<String> headers) {
        ArrayList<Integer> anns = new ArrayList<Integer>();
        for (String ann : annotations) {
            for (String header : array.getHeaders()) {
                if (ann.equals(header)) {
                    anns.add(getInputInfoIndex(header, headers));
                }
            }
        }
        array.setAnnotations(anns);
    }

    private int getInputInfoIndex(String header, ArrayList<String> headers) {
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).equals(header)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Illegal column(header):" + header);
    }

    @Override
    public List<List<String>> readAssayData(File arrayFile, int rownumber, int headerLineNo)
            throws IOException {
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
            if(line == null)
                break;
            assayRow = Arrays.asList(line.split("\t"));
            assayData.add(assayRow);
        }
        reader.close();        
        return assayData;
    }
}
