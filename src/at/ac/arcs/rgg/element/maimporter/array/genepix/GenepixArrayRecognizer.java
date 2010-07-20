package at.ac.arcs.rgg.element.maimporter.array.genepix;

import at.ac.arcs.rgg.element.maimporter.array.*;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author ilhami
 */
public class GenepixArrayRecognizer implements IArrayRecognizer {

    @SuppressWarnings("empty-statement")
    public ArrayInfo recognize(File array) throws ArrayDetectionException {
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(array));
            String line = reader.readLine();

            if (checkATF(line)) {
                ArrayInfo inf = new ArrayInfo();
                inf.setArraySource("genepix");
                inf.setArrayFile(array);
                inf.setArrayCreator(new GenepixArrayCreator());

                line = reader.readLine();
                int optionalHeaderRecordsNumber =
                        Integer.parseInt(StringUtils.split(line)[0]);

                while ((line = reader.readLine()) != null) {
                    if (reader.getLineNumber() < optionalHeaderRecordsNumber + 3) {
                        if (!setArrayInfoFields(inf, line)) {
                            continue;
                        }
                        while ((line = reader.readLine()) != null &&
                                reader.getLineNumber() < optionalHeaderRecordsNumber + 3) ;

                        if (reader.getLineNumber() == optionalHeaderRecordsNumber + 3) {
                            inf.setHeaderLineNo(reader.getLineNumber());
                            return inf;
                        } else {
                            throw new ArrayDetectionException(array,
                                    "Premature end of file before " +
                                            "reaching the declared header line");
                        }
                    } else {
                        break;
                    }
                }
            }
            return null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenepixArrayRecognizer.class.getName()).log(Level.SEVERE, null, ex);
            throw new ArrayDetectionException(array, ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(GenepixArrayRecognizer.class.getName()).log(Level.SEVERE, null, ex);
            throw new ArrayDetectionException(array, ex.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(GenepixArrayRecognizer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    private boolean checkATF(String line) {
        return line != null && line.startsWith("ATF");
    }


    private boolean setArrayInfoFields(ArrayInfo inf, String line) {
        if (line.contains("Wavelengths") || line.contains("ImageName")) {
            if (line.contains("635") && line.contains("532")) {
                inf.setChannelInfo(ArrayChannelInfo.TWOCHANNEL);
            } else if (line.contains("635")) {
                inf.setChannelInfo(ArrayChannelInfo.ONECHANNEL);
                inf.setColorInfo(ArrayColorInfo.R);
            } else if (line.contains("532")) {
                inf.setChannelInfo(ArrayChannelInfo.ONECHANNEL);
                inf.setColorInfo(ArrayColorInfo.G);
            }
            return true;
        }
        return false;
    }
}
