package at.ac.arcs.rgg;

import at.ac.arcs.rgg.runner.RGGRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Todo: convert this into a proper unittest suite
 *
 * @author Holger Brandl
 */
public class RggTests {

    public static void main2(String[] args) throws IllegalAccessException, IOException, ParserConfigurationException, SAXException, InstantiationException, ClassNotFoundException {
        String templateText = RGGRunner.readFileAsString(new File("/Users/brandl/projects/knime/R4Knime/rggtester2.rgg"));

        InputStream xmlStream = new BufferedInputStream(new ByteArrayInputStream(templateText.getBytes()));

        RGG rgg = RGG.createInstance(xmlStream);

        System.out.println(rgg.generateRScript());
    }


    public static void main(String[] args) throws IllegalAccessException, IOException, ParserConfigurationException, SAXException, InstantiationException, ClassNotFoundException {
        String templateText = RGGRunner.readFileAsString(new File("/Users/brandl/projects/knime/R4Knime/rggtester.rgg"));

        InputStream xmlStream = new BufferedInputStream(new ByteArrayInputStream(templateText.getBytes()));

        RGG rgg = RGG.createInstance(xmlStream);

        Map<String, Object> dialogConfig = new HashMap<String, Object>();
        dialogConfig.put("Peaks", "peak");
        dialogConfig.put("Cell column pattern", "test23");
        dialogConfig.put("Detect troughs instead of peaks", true);

        rgg.getRggModel().restoreState(dialogConfig);


        System.out.println(rgg.generateRScript());
    }

}
