/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.maimporter.array;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;


/**
 * @author ilhami
 */
public class TargetFile {

    private ArrayList<String> header;
    private ArrayList<ArrayList<String>> targetFileData;
    private File path;
    private File[] files;


    private TargetFile(File basedir, ArrayList<String> header,
                       ArrayList<ArrayList<String>> targetFileData) throws TargetFileException {
        this.path = basedir;
        this.header = header;
        this.targetFileData = targetFileData;
        setFiles();
    }


    private TargetFile(File[] arrays) {
        header = new ArrayList<String>();
        header.add("FileName");
        this.path = arrays[0].getParentFile();

        targetFileData = new ArrayList<ArrayList<String>>();
        ArrayList<String> dataLine;
        for (File array : arrays) {
            dataLine = new ArrayList<String>();
            dataLine.add(array.getName());
            targetFileData.add(dataLine);
        }
        this.files = arrays;
    }


    private void setFiles() throws TargetFileException {
        int fileNameIndex = -1;
        for (int i = 0; i < header.size(); i++) {
            if (header.get(i).equalsIgnoreCase("FileName")) {
                fileNameIndex = i;
                break;
            }
        }
        files = new File[targetFileData.size()];
        for (int i = 0; i < targetFileData.size(); i++) {
            files[i] = new File(path, targetFileData.get(i).get(fileNameIndex));
        }
        int missingFileCounter = 0;
        StringBuffer missingFileSBuf = new StringBuffer();
        for (File target : files) {
            if (!target.exists()) {
                missingFileCounter++;
                missingFileSBuf.append(target.getName() + "\n");
            }
        }
        if (missingFileCounter > 0) {
            throw new TargetFileException(missingFileCounter +
                    " Microarray(s) written in the target file don't/doesn't exist\n" + missingFileSBuf.toString());
        }
    }


    public File[] getFiles() {
        return files;
    }


    public File getPath() {
        return path;
    }


    public void setPath(File path) {
        this.path = path;
    }


    public ArrayList<String> getHeader() {
        return header;
    }


    public void setHeader(ArrayList<String> header) {
        this.header = header;
    }


    public ArrayList<ArrayList<String>> getTargetFileData() {
        return targetFileData;
    }


    public void setTargetFileData(ArrayList<ArrayList<String>> targetFileData) {
        this.targetFileData = targetFileData;
    }


    private static ArrayList<ArrayList<String>> getTargetFileData(BufferedReader reader, int headercount)
            throws TargetFileException {
        try {
            ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
            ArrayList<String> dataLine;
            String line;
            String[] parts;
            while ((line = reader.readLine()) != null) {
                parts = StringUtils.split(line);
                if (parts.length != headercount) {
                    throw new TargetFileException("Corrupt target file. " +
                            "header count is not equal to underlying data!");
                }

                dataLine = new ArrayList<String>();
                for (String s : parts) {
                    dataLine.add(s);
                }
                data.add(dataLine);
            }
            return data;
        } catch (IOException ex) {
            Logger.getLogger(TargetFile.class.getName()).log(Level.SEVERE, null, ex);
            throw new TargetFileException(ex.getMessage());
        }
    }


    private static String[] getHeader(File targetFile, String line)
            throws TargetFileException {
        if (line == null || line.trim().length() == 0) {
            throw new TargetFileException("Target file \"" + targetFile.getName() + "\" or the first line is empty");
        } else {
            return StringUtils.split(line);
        }
    }


    private static boolean hasFileNameHeader(String[] header) {
        for (String str : header) {
            if (str.equalsIgnoreCase("FileName")) {
                return true;
            }
        }
        return false;
    }


    public String getFileNameHeader() {
        for (String str : header) {
            if (str.equalsIgnoreCase("FileName")) {
                return str;
            }
        }
        return null;
    }


    public static TargetFile createTargetFile(File targetFile)
            throws TargetFileException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(targetFile));
            String line = reader.readLine();
            while (line.trim().startsWith("#")) {
                line = reader.readLine();
            }

            String[] header = getHeader(targetFile, line);
            if (!hasFileNameHeader(header)) {
                throw new TargetFileException("Couldn't find the header \"FileName\" " +
                        "in the target file.\nPlease check it.");
            }

            ArrayList<ArrayList<String>> targetFileData =
                    getTargetFileData(reader, header.length);
            List headerlist = Arrays.asList(header);
            return new TargetFile(targetFile.getParentFile(),
                    new ArrayList<String>(headerlist), targetFileData);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TargetFile.class.getName()).log(Level.SEVERE, null, ex);
            throw new TargetFileException(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(TargetFile.class.getName()).log(Level.SEVERE, null, ex);
            throw new TargetFileException(ex.getMessage());
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(TargetFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }


    public static TargetFile createTargetFile(File[] arrays) {
        return new TargetFile(arrays);
    }


    public String toRCode() {
        StringBuffer rbuf = new StringBuffer();
        rbuf.append("data.frame(");
        for (int i = 0; i < header.size(); i++) {
            rbuf.append(header.get(i) + " = c(");
            for (ArrayList<String> line : targetFileData) {
                rbuf.append("\"");
                rbuf.append(line.get(i));

                if (header.get(i).equalsIgnoreCase("filename")) {
                    rbuf.append("\"\n");
                } else {
                    rbuf.append("\"");
                }
                rbuf.append(",");
            }
            rbuf.deleteCharAt(rbuf.length() - 1);
            rbuf.append("),");
        }
        rbuf.deleteCharAt(rbuf.length() - 1);
        rbuf.append(")");
        return rbuf.toString();
    }
}
