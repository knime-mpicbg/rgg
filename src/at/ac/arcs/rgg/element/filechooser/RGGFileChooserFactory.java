/*
 * RGGFileChooserFactory.java
 *
 * Created on 29. Oktober 2006, 18:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package at.ac.arcs.rgg.element.filechooser;

import javax.swing.JFileChooser;
import org.apache.commons.lang.StringUtils;
import at.ac.arcs.rgg.RGG;
import at.ac.arcs.rgg.element.RElement;
import at.ac.arcs.rgg.factories.RElementFactory;
import at.ac.arcs.rgg.layout.LayoutInfo;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.w3c.dom.Element;

/**
 *
 * @author ilhami
 */
public class RGGFileChooserFactory extends RElementFactory {

    /** Creates a new instance of RGGFileChooserFactory */
    public RGGFileChooserFactory() {
    }

    public RElement createRGGElement(Element element, RGG rggInstance) {
        if (element.getNodeType() != Element.ELEMENT_NODE) {
            throw new IllegalArgumentException("elements node type must be ELEMENT_NODE");
        }
        RFileChooser rfilechooser = new RFileChooser();
        VFileChooser vfilechooser = new VFileChooser(rggInstance);
        rfilechooser.setVFileChooser(vfilechooser);

        /****************** initialize and set attributes values **************************************/
        String var = element.getAttribute(RGG.getConfiguration().getString("VAR"));
        String label = element.getAttribute(RGG.getConfiguration().getString("LABEL"));
        String colspan = element.getAttribute(RGG.getConfiguration().getString("COLUMN-SPAN"));
        String acceptedextensions = element.getAttribute(RGG.getConfiguration().getString("ACCEPTED-EXTENSIONS"));
        String description = element.getAttribute(RGG.getConfiguration().getString("DESCRIPTION"));
        String fileselectionmode = element.getAttribute(RGG.getConfiguration().getString("FILESELECTION-MODE"));
        String multiselectionenabled = element.getAttribute(RGG.getConfiguration().getString("MULTISELECTION-ENABLED"));
        String enabled = element.getAttribute(RGG.getConfiguration().getString("ENABLED"));
        /***********************************************************************************************/
        if (StringUtils.isNotBlank(var)) {
            rfilechooser.setVariable(var);
        }

        if (StringUtils.isNotBlank(label)) {
            vfilechooser.setLabel(label);
        }

        if (StringUtils.isNotBlank(acceptedextensions)) {
            if (!StringUtils.equals(acceptedextensions, "*")) {
                rfilechooser.setExtensions(StringUtils.split(acceptedextensions, ','));
                if (StringUtils.isNotBlank(description)) {
                    rfilechooser.setDescription(description);
                }
                vfilechooser.setExtensionFilter(rfilechooser.getDescription(), rfilechooser.getExtensions());
            }
        }

        if (StringUtils.isNotBlank(colspan) && StringUtils.isNumeric(colspan)) {
            if (StringUtils.isNumeric(colspan)) {
                vfilechooser.setColumnSpan(Integer.parseInt(colspan));
            } else if (StringUtils.equals(colspan, RGG.getConfiguration().getString("FULL-SPAN"))) {
                vfilechooser.setColumnSpan(LayoutInfo.FULL_SPAN);
            } else {
                throw new NumberFormatException(RGG.getConfiguration().getString("COLUMN-SPAN") 
                        + " seems not to be a number: " + colspan + "nor a known keyword!");
            }
        }

        if (StringUtils.isNotBlank(fileselectionmode)) {
            if (StringUtils.equalsIgnoreCase(fileselectionmode, RGG.getConfiguration().getString("FILES-ONLY"))) {
                vfilechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            } else if (StringUtils.equalsIgnoreCase(fileselectionmode, RGG.getConfiguration().getString("DIRECTORIES-ONLY"))) {
                vfilechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            } else if (StringUtils.equalsIgnoreCase(fileselectionmode, RGG.getConfiguration().getString("FILES-AND-DIRECTORIES"))) {
                vfilechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            }
        }

        if (StringUtils.isNotBlank(multiselectionenabled)) {
            if (StringUtils.equalsIgnoreCase(multiselectionenabled, "T") || StringUtils.equalsIgnoreCase(multiselectionenabled, "TRUE")) {
                vfilechooser.setMultiSelectionEnabled(true);
            } else if (StringUtils.equalsIgnoreCase(multiselectionenabled, "F") || StringUtils.equalsIgnoreCase(multiselectionenabled, "FALSE")) {
                vfilechooser.setMultiSelectionEnabled(false);
            }
        }

        if (StringUtils.isNotBlank(enabled)) {            
            if (util.match("/(\\w+)\\./", enabled)) {
                String id = util.group(1);
                enabled = util.substitute("s/" + id + "\\.//g", enabled);
                AutoBinding<Object, Object, Object, Object> binding =
                        Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, // one-way binding
                        rggInstance.getObject(id), // source of value
                        ELProperty.create(enabled), // the property to get
                        vfilechooser, // the "backing bean"
                        BeanProperty.create("enabled") // property to set
                        );
                binding.bind();
            }else if (StringUtils.equalsIgnoreCase(enabled, "F") || StringUtils.equalsIgnoreCase(enabled, "FALSE")) {
                vfilechooser.setEnabled(false);
            }else if (StringUtils.equalsIgnoreCase(enabled, "T") || StringUtils.equalsIgnoreCase(enabled, "TRUE")) {
                //Do nothing
            }
        }

        return rfilechooser;
    }
}
