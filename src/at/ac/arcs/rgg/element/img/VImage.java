/*
 * VImage.java
 *
 * Created on 16. November 2006, 20:16
 */
package at.ac.arcs.rgg.element.img;

import java.io.File;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;


/**
 * @author ilhami
 */
public class VImage extends VisualComponent {

    private JLabel label;
    private File imgsrc;
    private JComponent[][] swingMatrix;


    /**
     * Creates a new instance of VImage
     */
    public VImage(File imgsrc) {
        label = new JLabel();
        this.imgsrc = imgsrc;
        try {
            ImageIcon img = new ImageIcon(imgsrc.toURI().toURL());
            label.setIcon(img);
            label.setIconTextGap(0);
            label.setBorder(null);
            label.setText(null);
            label.setOpaque(false);
        } catch (MalformedURLException ex) {
            label.setText("<html><font size='3' color='red'>ERROR!</font></html>");
        }
        swingMatrix = new JComponent[][]{{label}};
    }


    public JComponent[][] getSwingComponents() {
        return swingMatrix;
    }


    public File getSrc() {
        return imgsrc;
    }


    public void setHorizontalAlignment(final int alignment) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                label.setHorizontalAlignment(alignment);
            }
        });
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(label, colspan);
        }
    }
}
