package at.ac.arcs.rgg.element.maimporter.ui.model;

import at.ac.arcs.rgg.element.maimporter.array.Array;
import at.ac.arcs.rgg.element.maimporter.array.ArrayDetectionException;
import at.ac.arcs.rgg.element.maimporter.array.ArrayInfo;
import at.ac.arcs.rgg.element.maimporter.array.ArrayRecognizers;
import at.ac.arcs.rgg.element.maimporter.array.TargetFile;
import at.ac.arcs.rgg.element.maimporter.array.TargetFileException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;

import org.jdesktop.swingworker.SwingWorker;


/**
 * @author ilhami
 */
public class MAImporterModel implements ArrayHeaderRowChangeListener {

    public static final String RGLISTTABLEMODELPROPERTY = "RGLISTTABLEMODELPROPERTY";
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private TargetFile targetFile;
    private TargetFileTableModel targetFileModel;
    private ArrayList<ArrayInfo> arrayInfos;
    private ArrayHeaderRowTableModel arrayHeaderRowTableModel;
    private RGListTableModel rGListTableModel;
    private String[] othercolumns;
    public static final String PROP_TargetFile = "targetFile";
    private boolean isMAImporterModelCreated = false;


    public static MAImporterModel createModelFromArrays(File[] arrays, String[] othercolumns)
            throws IOException, ArrayDetectionException {

        MAImporterModel model = new MAImporterModel();
        model.othercolumns = othercolumns;
        //create and set TargetFile
        model.setTargetFile(TargetFile.createTargetFile(arrays));
        model.setTargetFileModel(new TargetFileTableModel(model.getTargetFile()));
        //Recognize files
        model.setArrayInfos(recognize(model.getTargetFile()));
        //TODO check arrayinfos here. same array type,same layout...
        if (!model.getArrayInfos().get(0).getArraySource().equals("affymetrix")) {
            //create and set ArrayHeaderRowTableModel
            model.setArrayHeaderRowTableModel(
                    ArrayHeaderRowTableModel.createArrayHeaderRowTableModel(model.getArrayInfos().get(0)));
            Array array = model.getArrayInfos().get(0).getArrayCreator().makeArray(model.getArrayInfos().get(0));
            model.setRGListTableModel(new RGListTableModel(array, othercolumns));
        }
        model.setMAImporterModelCreated(true);
        return model;
    }


    public static MAImporterModel createModelFromTargetFile(File targetFile, String[] othercolumns)
            throws TargetFileException, ArrayDetectionException, IOException {
        MAImporterModel model = new MAImporterModel();
        model.othercolumns = othercolumns;
        //create and set TargetFile
        model.setTargetFile(TargetFile.createTargetFile(targetFile));
        model.setTargetFileModel(new TargetFileTableModel(model.getTargetFile()));
        //Recognize files
        model.setArrayInfos(recognize(model.getTargetFile()));
        //TODO check arrayinfos here. same array type,same layout...
        if (!model.getArrayInfos().get(0).getArraySource().equals("affymetrix")) {
            //create and set ArrayHeaderRowTableModel
            model.setArrayHeaderRowTableModel(
                    ArrayHeaderRowTableModel.createArrayHeaderRowTableModel(model.getArrayInfos().get(0)));
            Array array = model.getArrayInfos().get(0).getArrayCreator().makeArray(model.getArrayInfos().get(0));
            model.setRGListTableModel(new RGListTableModel(array, othercolumns));
        }
        model.setMAImporterModelCreated(true);
        return model;
    }


    private static ArrayList<ArrayInfo> recognize(TargetFile targetFile) throws ArrayDetectionException {
        return ArrayRecognizers.recognizeArraysInTargetFile(targetFile);
    }


    public TargetFile getTargetFile() {
        return targetFile;
    }


    public void setTargetFile(TargetFile targetFile) {
        this.targetFile = targetFile;
        changeSupport.firePropertyChange(PROP_TargetFile, null, targetFile);
    }


    public TargetFileTableModel getTargetFileModel() {
        return targetFileModel;
    }


    public void setTargetFileModel(TargetFileTableModel targetFileModel) {
        this.targetFileModel = targetFileModel;
    }


    public ArrayList<ArrayInfo> getArrayInfos() {
        return arrayInfos;
    }


    public void setArrayInfos(ArrayList<ArrayInfo> arrayInfos) {
        this.arrayInfos = arrayInfos;
    }


    public ArrayHeaderRowTableModel getArrayHeaderRowTableModel() {
        return arrayHeaderRowTableModel;
    }


    public void setArrayHeaderRowTableModel(ArrayHeaderRowTableModel arrayHeaderRowTableModel) {
        this.arrayHeaderRowTableModel = arrayHeaderRowTableModel;
        this.arrayHeaderRowTableModel.addArrayHeaderRowChangeListener(this);
    }


    public RGListTableModel getRGListTableModel() {
        return rGListTableModel;
    }


    public void setRGListTableModel(RGListTableModel rGListTableModel) {
        this.rGListTableModel = rGListTableModel;
    }


    public boolean isMAImporterModelCreated() {
        return isMAImporterModelCreated;
    }


    public void setMAImporterModelCreated(boolean isMAImporterModelCreated) {
        this.isMAImporterModelCreated = isMAImporterModelCreated;
    }


    public void stateChanged(ArrayHeaderChangedEvent evt) {
        for (ArrayInfo inf : arrayInfos) {
            inf.setHeaderLineNo(evt.getHeaderRow() + 1);
        }

        SwingWorker<RGListTableModel, Object> worker = new SwingWorker<RGListTableModel, Object>() {

            @Override
            protected RGListTableModel doInBackground() throws Exception {
                Array array = arrayInfos.get(0).getArrayCreator().makeArray(arrayInfos.get(0));
                return new RGListTableModel(array, othercolumns);
            }


            @Override
            protected void done() {
                try {
                    super.done();
                    RGListTableModel oldModel = MAImporterModel.this.rGListTableModel;
                    MAImporterModel.this.rGListTableModel = get();
                    changeSupport.firePropertyChange(RGLISTTABLEMODELPROPERTY, oldModel, rGListTableModel);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MAImporterModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(MAImporterModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        worker.execute();
    }


    public void stateChanged(ChangeEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        changeSupport.addPropertyChangeListener(listener);
    }


    public void removePropertyChangeListener(PropertyChangeListener listener) {
        if (listener == null) {
            return;
        }
        changeSupport.removePropertyChangeListener(listener);
    }
}
