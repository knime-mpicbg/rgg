package at.ac.arcs.rgg.element.maimporter;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.element.maimporter.array.ArrayChannelInfo;
import at.ac.arcs.rgg.element.maimporter.array.ArrayColorInfo;
import at.ac.arcs.rgg.element.maimporter.ui.MAImporterPanel;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;


/**
 * @author ilhami
 */
public class RMAImporter extends RElement {

    private String var;
    private VMAImporter vMAImporter;
    private VisualComponent[][] visualcomponents;


    public RMAImporter() {
    }


    @Override
    public String getRCode() {
        //Target file creation
        MAImporterPanel mapanel = vMAImporter.getMAImporterPanel();

        StringBuilder rbuilder = new StringBuilder();

        if (StringUtils.isNotBlank(var)) {
            rbuilder.append(var);
            rbuilder.append(" = ");
        }

        if (!mapanel.isMAImporterModelSet()) {
            rbuilder.append("NA");
            return rbuilder.toString();
        }

        //start of list
        rbuilder.append(" list(");

        //targetfile
        rbuilder.append("targetfile=");
        rbuilder.append(mapanel.getMAModel().getTargetFile().toRCode());
        //source
        rbuilder.append(",\nsource=\"");
        rbuilder.append(mapanel.getArraySource());
        //path
        rbuilder.append("\", path=\"");
        rbuilder.append(StringUtils.replace(mapanel.getMAModel().getTargetFile().getPath().getAbsolutePath(), "\\", "/") + "\"");

        if (!vMAImporter.isAffymetrix()) {
            //columns
            rbuilder.append(",\ncolumns=list(");
            if (mapanel.getArrayChannelInfo() == ArrayChannelInfo.TWOCHANNEL) {
                rbuilder.append("G=\"" + mapanel.getGHeader() + "\"");
                rbuilder.append(", Gb=\"" + mapanel.getGbHeader() + "\"");
                rbuilder.append(", R=\"" + mapanel.getRHeader() + "\"");
                rbuilder.append(", Rb=\"" + mapanel.getRbHeader() + "\")");

                //channelinfo & colorinfo
                rbuilder.append(",channel=2,color=\"RG\"");

            } else if (mapanel.getArrayChannelInfo() == ArrayChannelInfo.ONECHANNEL) { //ONECHANNEL
                if (mapanel.getArrayColorInfo() == ArrayColorInfo.G) {
                    rbuilder.append("G=\"" + mapanel.getGHeader() + "\"");
                    rbuilder.append(", Gb=\"" + mapanel.getGbHeader() + "\"");
                    rbuilder.append(", R=\"" + mapanel.getGHeader() + "\"");
                    rbuilder.append(", Rb=\"" + mapanel.getGbHeader() + "\")");

                    //channelinfo & colorinfo
                    rbuilder.append(",channel=1,color=\"G\"");
                } else { //Red
                    rbuilder.append("G=\"" + mapanel.getRHeader() + "\"");
                    rbuilder.append(", Gb=\"" + mapanel.getRbHeader() + "\"");
                    rbuilder.append(", R=\"" + mapanel.getRHeader() + "\"");
                    rbuilder.append(", Rb=\"" + mapanel.getRbHeader() + "\")");
                    //channelinfo & colorinfo
                    rbuilder.append(",channel=1,color=\"R\"");
                }
            } else { //GENERIC TYPE ==> UNKNOWN
                rbuilder.append("G=\"" + mapanel.getGHeader() + "\"");
                rbuilder.append(", Gb=\"" + mapanel.getGbHeader() + "\"");
                rbuilder.append(", R=\"" + mapanel.getRHeader() + "\"");
                rbuilder.append(", Rb=\"" + mapanel.getRbHeader() + "\")");

                //channelinfo & colorinfo
                rbuilder.append(",channel=NA,color=NA");
            }

            //annotation
            rbuilder.append(",\nannotation = ");
            if (mapanel.getAnnotationHeaders().size() > 0) {
                rbuilder.append("list(");
                for (String header : mapanel.getAnnotationHeaders()) {
                    rbuilder.append("\"" + header + "\",");
                }
                rbuilder.deleteCharAt(rbuilder.length() - 1);
                rbuilder.append(")");
            } else {
                rbuilder.append("character(0)");
            }

            //other.columns
            rbuilder.append(",other.columns = ");
            if (mapanel.getOtherColumnHeaders().size() > 0) {
                rbuilder.append("list(");
                for (int i = 0; i < mapanel.getOtherColumnHeaders().size(); i++) {
                    rbuilder.append(vMAImporter.othercolumns[i] + "=\"" + mapanel.getOtherColumnHeaders().get(i) + "\",");
                }
                rbuilder.deleteCharAt(rbuilder.length() - 1);
                rbuilder.append(")");
            } else {
                rbuilder.append("character(0)");
            }
        }
        //end of list
        rbuilder.append(")");

        return rbuilder.toString();
    }


    public void setVMAImporter(VMAImporter vMAImporter) {
        this.vMAImporter = vMAImporter;
    }


    @Override
    public boolean hasVisualComponents() {
        return true;
    }


    @Override
    public VisualComponent[][] getVisualComponents() {
        if (visualcomponents == null) {
            visualcomponents = new VisualComponent[][]{{vMAImporter}};
        }
        return visualcomponents;
    }


    @Override
    public boolean isChildAddable() {
        return false;
    }


    public void setVar(String var) {
        this.var = var;
    }


    public String getVar() {
        return var;
    }


    @Override
    public JComponent[][] getSwingComponentMatrix() {
        return vMAImporter.getSwingComponents();
    }

}
