/*
 * VTextField.java
 *
 * Created on 06. August 2006, 12:59
 */
package at.ac.arcs.rgg.element.textfield;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;

/**
 *
 * @author ilhami
 */
public class VTextField extends VisualComponent {

    JTextField textfield;
    private JLabel label;
    private boolean isNumeric = false;
    private String defaultvalue;
    private JComponent[][] swingComponents;
    private boolean enabled;

    /**
     * Creates a new instance of VTextField
     */
    public VTextField() {
        enabled=true;
        initializeComponents();
    }

    private void initializeComponents() {
        textfield = new JTextField();
        textfield.setColumns(5);
    }

    public boolean isVisualComponent() {
        return true;
    }

    public JComponent[][] getSwingComponents() {
        if (label == null) {
            swingComponents = new JComponent[][]{{textfield}};
        } else {
            swingComponents = new JComponent[][]{{label, textfield}};
        }
        return swingComponents;
    }

    void setColumns(int columns) {
        textfield.setColumns(columns);
    }

    public String getLabelText() {
        return label.getText();
    }

    public void setLabelText(String text) {
        label = new JLabel(text);
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
        textfield.setText(defaultvalue);
    }

    public String getTextFieldValue() {
        return textfield.getText();
    }

    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(textfield, colspan);
        }
    }

    public void setNumeric(boolean isNumeric) {
        //        if(isNumeric){
        //            NumberFormatter nformatter = new NumberFormatter(new DecimalFormat("#,#.#E0"));
        //            nformatter.setAllowsInvalid(false);
        //            textfield.setFormatterFactory(new DefaultFormatterFactory(nformatter));
        //        }else if(this.isNumeric && !isNumeric){
        ////            textfield.setFormatterFactory(new DefaultFormatterFactory(new DefaultFormatter()));
        //            textfield.getFormatter().uninstall();
        //        }
        this.isNumeric = isNumeric;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        textfield.setEnabled(enabled);
        label.setEnabled(enabled);
    }

    public boolean isNumeric() {
        return isNumeric;
    }
}
