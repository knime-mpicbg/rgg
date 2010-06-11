package at.ac.arcs.rgg.element.maimporter.array;

import at.ac.arcs.rgg.element.maimporter.ui.inputselection.InputInfo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ilhami
 */
public class Array {

    public static final String PROP_G = "G";
    public static final String PROP_Gb = "Gb";
    public static final String PROP_R = "R";
    public static final String PROP_Rb = "Rb";
    public static final String PROP_Annotation = "annotation";
    public static final String PROP_OtherColumns = "othercolumns";
    private File file;
    private String source;
    private ArrayInfo arrayInfo;
    private InputInfo G = new InputInfo(PROP_G, InputInfo.OptionType.ONE_TO_ONE);
    private InputInfo Gb = new InputInfo(PROP_Gb, InputInfo.OptionType.ONE_TO_ONE);
    private InputInfo R = new InputInfo(PROP_R, InputInfo.OptionType.ONE_TO_ONE);
    private InputInfo Rb = new InputInfo(PROP_Rb, InputInfo.OptionType.ONE_TO_ONE);
    private InputInfo annotations = new InputInfo(PROP_Annotation, InputInfo.OptionType.MANY_TO_ONE);
    public ArrayList<InputInfo> othercolumns = new ArrayList<InputInfo>();
    private ArrayList<String> allHeaders = new ArrayList<String>();

    public Array(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 
     * @return an InputInfo object for G, or null if the array single color
     * and onyl R existent is.
     */
    public InputInfo getG() {
        if (arrayInfo.getChannelInfo() == ArrayChannelInfo.ONECHANNEL && arrayInfo.getColorInfo() == ArrayColorInfo.R) {
            return null;
        } else {
            return G;
        }
    }

    public void setGHeaderIndex(int index) {
        this.G.setColumns(index);
    }

    /**
     * 
     * @return an InputInfo object for Gb, or null if the array single color
     * and onyl R existent is.
     */
    public InputInfo getGb() {
        if (arrayInfo.getChannelInfo() == ArrayChannelInfo.ONECHANNEL && arrayInfo.getColorInfo() == ArrayColorInfo.R) {
            return null;
        } else {
            return Gb;
        }
    }

    public void setGb(int index) {
        this.Gb.setColumns(index);
    }

    /**
     * 
     * @return an InputInfo object for R(ed) channel, or null if the array single color
     * and only the G(reen) channel available is.
     */
    public InputInfo getR() {
        if (arrayInfo.getChannelInfo() == ArrayChannelInfo.ONECHANNEL && arrayInfo.getColorInfo() == ArrayColorInfo.G) {
            return null;
        } else {
            return R;
        }
    }
    
    public void setR(int index) {
        this.R.setColumns(index);
    }

    /**
     * 
     * @return an InputInfo object for Rb(red background) channel, or null if the array single color
     * and only the G(reen) channel available is.
     */
    public InputInfo getRb() {
        if (arrayInfo.getChannelInfo() == ArrayChannelInfo.ONECHANNEL && arrayInfo.getColorInfo() == ArrayColorInfo.G) {
            return null;
        } else {
            return Rb;
        }
    }

    public void setRb(int index) {
        this.Rb.setColumns(index);
    }

    public InputInfo getAnnotations() {
        return annotations;
    }

    public void setAnnotations(ArrayList<Integer> columns) {
        this.annotations.setColumns(columns);
    }

    public void addAnnotations(Integer column) {
        annotations.addColumn(column);
    }

    public void removeAnnotations(Integer column) {
        annotations.removeColumn(column);
    }

    public List<String> getHeaders() {
        return allHeaders;
    }

    public void setHeaders(ArrayList<String> allHeaders) {
        this.allHeaders = allHeaders;
    }

    public ArrayInfo getArrayInfo() {
        return arrayInfo;
    }

    public void setArrayInfo(ArrayInfo arrayInfo) {
        this.arrayInfo = arrayInfo;
    }

    public List<List<String>> readAssayData(int rownumber) throws IOException {
        return arrayInfo.readAssayData(rownumber);
    }
}
