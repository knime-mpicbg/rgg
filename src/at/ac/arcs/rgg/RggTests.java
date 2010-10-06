package at.ac.arcs.rgg;

import at.ac.arcs.rgg.runner.RGGRunner;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;


/**
 * Todo: convert this into a proper unittest suite
 *
 * @author Holger Brandl
 */
public class RggTests {

    public static void main(String[] args) throws IllegalAccessException, IOException, ParserConfigurationException, SAXException, InstantiationException, ClassNotFoundException {
        String templateText = RGGRunner.readFileAsString(new File("/Users/brandl/projects/knime/R4Knime/rggtester2.rgg"));

        InputStream xmlStream = new BufferedInputStream(new ByteArrayInputStream(templateText.getBytes()));

        RGG rgg = RGG.createInstance(xmlStream);

        System.out.println(rgg.generateRScript());
    }

}
