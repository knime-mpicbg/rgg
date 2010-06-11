package at.ac.arcs.rgg.element.maimporter;

import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

/**
 *
 * @author ilhami
 */
public class RGGMAImporterFactory extends RElementFactory {

    private static Log log = LogFactory.getLog(RGGMAImporterFactory.class);

    @Override
    public RMAImporter createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE) {
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        /****************** initialize and set attributes values **************************************/
        }

        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String id = element.getAttribute(RGG.getConfiguration().getString("ID"));
        String othercolumns = element.getAttribute(RGG.getConfiguration().getString("OTHER-COLUMNS"));
        /***********************************************************************************************/
        RMAImporter rMAImporter = new RMAImporter();
        VMAImporter vMAImporter = null;
        if (StringUtils.isNotBlank(othercolumns)) {
            vMAImporter = new VMAImporter(rggInstance, StringUtils.split(othercolumns, ','));
        } else {
            vMAImporter = new VMAImporter(rggInstance, null);
        }

        if (StringUtils.isNotBlank(var)) {
            rMAImporter.setVar(var);
        }

        if (StringUtils.isNotBlank(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                vMAImporter.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN"))) {
                vMAImporter.setColumnSpan(LayoutInfo.FULL_SPAN);
            } else {
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN") + " seems not to be a number: " +
                        colspan + "nor a known keyword!");
            }
        }

        if (StringUtils.isNotBlank(id)) {
            rggInstance.addObject(id, vMAImporter);
        }

        rMAImporter.setVMAImporter(vMAImporter);
        
        return rMAImporter;
    }
}
