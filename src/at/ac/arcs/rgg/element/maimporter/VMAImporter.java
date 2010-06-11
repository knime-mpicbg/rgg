package at.ac.arcs.rgg.element.maimporter;

import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.maimporter.array.Array;
import at.ac.arcs.rgg.element.maimporter.array.TargetFile;
import at.ac.arcs.rgg.element.maimporter.ui.MAImporterPanel;
import at.ac.arcs.rgg.element.maimporter.ui.model.MAImporterModel;
import at.ac.arcs.rgg.layout.LayoutInfo;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author ilhami
 */
public class VMAImporter extends VisualComponent implements PropertyChangeListener{

    private String[] columns;
    private String[] annotation;
    private String[] targetfileheader;
    private String arraysource;
    private boolean affymetrix;
    String[] othercolumns;
    
    
    private MAImporterPanel mapanel;
    private RGG rggInstance;
    private JComponent[][] swingComponents;

    /**
     * Creates a new instance of VTextField
     */
    public VMAImporter(RGG rggInstance,String[] othercolumns) {        
        this.rggInstance = rggInstance;
        this.othercolumns = othercolumns;
        initializeComponents(othercolumns);
    }

    private void initializeComponents(String[] othercolumns) {
        mapanel = new MAImporterPanel(othercolumns);
        mapanel.addPropertyChangeListener(this);
    }

    public boolean isVisualComponent() {
        return true;
    }

    public JComponent[][] getSwingComponents() {
        if (swingComponents == null) {
            swingComponents = new JComponent[][]{{mapanel}};
        }
        return swingComponents;
    }

    public MAImporterPanel getMAImporterPanel() {
        return mapanel;
    }

    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(mapanel, colspan);
        }
    }

    public MAImporterModel getMAModel() {
        return mapanel.getMAModel();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Array.PROP_Annotation)){
            annotation = (String[]) evt.getNewValue();
            changeSupport.firePropertyChange("annotation", evt.getOldValue(), evt.getNewValue());
        }else if(evt.getPropertyName().equals(Array.PROP_OtherColumns)){
            
        }else if(evt.getPropertyName().equals(MAImporterModel.PROP_TargetFile)){
            List<String> headers = ((TargetFile)evt.getNewValue()).getHeader();
            String[] old = targetfileheader;
            targetfileheader = new String[headers.size()];
            for(int i=0;i<headers.size();i++){
                targetfileheader[i]=headers.get(i);
            }
            changeSupport.firePropertyChange("targetfileheader", old, this.targetfileheader);
        }else if(evt.getPropertyName().equalsIgnoreCase("arraysource")){
            arraysource = (String)evt.getNewValue();
            changeSupport.firePropertyChange("affymetrix",affymetrix,isAffymetrix());
            //TODO move this quick fix to the one of the models.
            if(isAffymetrix()){
                String[] oldvalue = annotation;
                annotation = null;
                changeSupport.firePropertyChange("annotation", oldvalue, annotation);
            }
        }
    }

    public String[] getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String[] annotation) {
        this.annotation = annotation;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getTargetfileheader() {
        return targetfileheader;
    }

    public void setTargetfileheader(String[] targetfileheader) {
        this.targetfileheader = targetfileheader;
    }

    public boolean isAffymetrix(){
        if(arraysource == null)
            return false;
        return arraysource.equals("affymetrix");
    }
    
    public String getArraysource() {
        return arraysource;
    }

    public void setArraysource(String arraysource) {
        this.arraysource = arraysource;
    }
    
}
