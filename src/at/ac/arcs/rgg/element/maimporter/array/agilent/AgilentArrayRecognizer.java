/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.array.agilent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.element.maimporter.array.ArrayChannelInfo;
import at.ac.arcs.rgg.element.maimporter.array.ArrayColorInfo;
import at.ac.arcs.rgg.element.maimporter.array.ArrayDetectionException;
import at.ac.arcs.rgg.element.maimporter.array.ArrayInfo;
import at.ac.arcs.rgg.element.maimporter.array.IArrayRecognizer;

/**
 *
 * @author ilhami
 */
public class AgilentArrayRecognizer implements IArrayRecognizer {

    public ArrayInfo recognize(File array) throws ArrayDetectionException {
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(array));
            String line = reader.readLine();

            if (checkTYPE(line)) {
                line = reader.readLine();
                if (checkFEPARAMS(line)) {
                    line = reader.readLine();
                    ArrayInfo inf = checkAndCreateArrayInfo(line);
                    inf.setArrayFile(array);
                    return setHeaderLineNo(inf, reader);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AgilentArrayRecognizer.class.getName()).log(Level.SEVERE, null, ex);
            throw new ArrayDetectionException(array, ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(AgilentArrayRecognizer.class.getName()).log(Level.SEVERE, null, ex);
            throw new ArrayDetectionException(array, ex.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(AgilentArrayRecognizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private ArrayInfo checkAndCreateArrayInfo(String line) {
        ArrayInfo inf = new ArrayInfo();
        inf.setArraySource("agilent");
        String[] parts = StringUtils.split(line, '\t');
        if (parts.length > 5 && parts[4].contains("Agilent")) {
            inf.setArrayCreator(new AgilentArrayCreator());
            if (StringUtils.isNumeric(parts[5])) {
                if (parts[5].equals("1")) {
                    inf.setChannelInfo(ArrayChannelInfo.ONECHANNEL);
                } else if (parts[5].equals("2")) {
                    inf.setChannelInfo(ArrayChannelInfo.TWOCHANNEL);
                }
                return inf;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private boolean checkFEPARAMS(String line) {
        String[] parts;
        if (line != null && line.startsWith("FEPARAMS")) {
            parts = StringUtils.split(line);
            if (parts.length > 5 && parts[4].equals("Scan_ScannerName") && parts[5].equals("Scan_NumChannels")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkTYPE(String line) {
        return line != null && line.startsWith("TYPE");
    }

    private ArrayInfo setHeaderLineNo(ArrayInfo inf, LineNumberReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("FEATURES")) {
                inf.setHeaderLineNo(reader.getLineNumber());
                if (inf.getChannelInfo() == ArrayChannelInfo.ONECHANNEL) {
                    setArrayColor(inf, line);
                }
                return inf;
            }
        }
        return null;
    }

    private void setArrayColor(ArrayInfo inf, String line) {
        String[] fields = StringUtils.split(line, '\t');
        boolean gFlag = false;
        boolean rFlag = false;
        for (String field : fields) {
            for (String column : AgilentArrayCreator.columns) {
                if (field.equalsIgnoreCase(column)) {
                    if (column.charAt(0) == 'g') {
                        gFlag = true;
                    }
                    if (column.charAt(0) == 'r') {
                        rFlag = true;
                    }
                }
            }
            if (gFlag || rFlag) {
                if (gFlag) {
                    inf.setColorInfo(ArrayColorInfo.G);
                } else {
                    inf.setColorInfo(ArrayColorInfo.R);
                }
                break;
            }
        }
    }
}
