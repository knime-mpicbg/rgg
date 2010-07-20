package at.ac.arcs.rgg;

import at.ac.arcs.rgg.builder.RGGPanelBuilder;
import at.ac.arcs.rgg.factories.RGGFactory;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author ilhami
 * @author Holger Brandl
 */
public class RGG {

    public static boolean debug;

    private static CompositeConfiguration config;

//    private static Log log = LogFactory.getLog(RGG.class);
    private HashMap<String, Object> idMap = new HashMap<String, Object>();
    private HashMap<String, Object> rggProperties = new HashMap<String, Object>();
    private RGGModel rggModel;
    private RGGPanelModel rggPanelModel;
    private String setName;


    private static void initRGG() {
        if (config == null) {
            try {
                config = new CompositeConfiguration();

                config.addConfiguration(
                        new PropertiesConfiguration(
                                RGG.class.getResource("/at/ac/arcs/rgg/config/elementfactory.properties")));
                config.addConfiguration(
                        new PropertiesConfiguration(
                                RGG.class.getResource("/at/ac/arcs/rgg/config/rgg-attributes.properties")));
            } catch (ConfigurationException ex) {
                Logger.getLogger(RGG.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }


    public static RGG createInstance(File rggFile)
            throws ParserConfigurationException,
            SAXException, IOException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(rggFile));

        RGG rgg = createInstance(inputStream);

        rgg.setSetName(rggFile.getName()); // used in dialog-headers and so on        
        return rgg;
    }


    public static RGG createInstance(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        if (config == null) {
            initRGG();
        }


        RGG rgg = new RGG();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);

        Element rggElement = null;
        for (int i = 0; i < document.getChildNodes().getLength(); i++) {
            if (document.getChildNodes().item(i).getNodeType() == Element.ELEMENT_NODE) {
                rggElement = (Element) document.getChildNodes().item(i);
                debug = rggElement.getAttribute("debug").equalsIgnoreCase("true");
            }
        }

        rgg.setRggModel(RGGFactory.createRGGModel(rggElement, rgg));

        rgg.setRggPanelModel(new RGGPanelModel(rgg.getRggModel()));

        return rgg;
    }


    public static Configuration getConfiguration() {
        if (config == null) {
            throw new IllegalStateException("Please call first initRGG() method of this class!");
        }
        return config;
    }


    public String generateRScript() {
        return rggModel.generateRScript();
    }


    public JPanel buildPanel(boolean useDefaultDialogBorder, boolean debug) {
        return new RGGPanelBuilder().buildPanel(rggPanelModel, useDefaultDialogBorder, RGG.debug);
    }


    public void addObject(String id, Object obj) {
        idMap.put(id, obj);
    }


    public Object getObject(String id) {
        return idMap.get(id);
    }


    public void setProperty(String key, Object value) {
        rggProperties.put(key, value);
    }


    public Object getProperty(String key) {
        return rggProperties.get(key);
    }


    public RGGModel getRggModel() {
        return rggModel;
    }


    public void setRggModel(RGGModel rggModel) {
        this.rggModel = rggModel;
    }


    public RGGPanelModel getRggPanelModel() {
        return rggPanelModel;
    }


    public void setRggPanelModel(RGGPanelModel rggPanelModel) {
        this.rggPanelModel = rggPanelModel;
    }


    public File getBaseDirForFileDialogs() {
        return new File(System.getProperty("user.home")); // this is not set, so it wont' contain any meaningful values
    }


    public String getRGGName() {
        return setName;
    }


    public void setSetName(String setName) {
        this.setName = setName;
    }
}
