/*
 * VTextField.java
 *
 * Created on 06. August 2006, 12:59
 */
package at.ac.arcs.rgg.element.slider;

import at.ac.arcs.rgg.component.VisualComponent;
import at.ac.arcs.rgg.layout.LayoutInfo;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;


/**
 * @author ilhami
 */
public class VSlider extends VisualComponent {

    private JSlider slider;
    private JLabel sliderValueLabel;
    private JLabel label;
    private JPanel sliderPanel;
    private boolean enabled;
    private javax.swing.event.ChangeListener l;
    private JComponent[][] swingComponents;


    /**
     * Creates a new instance of VTextField
     */
    public VSlider() {
        initializeComponents();
    }


    private void initializeComponents() {
        label = new JLabel();
        sliderValueLabel = new JLabel("50");
        slider = new JSlider();

        slider.addChangeListener(l = new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderValueLabel.setText("" + slider.getValue());
            }
        });

        CellConstraints cc = new CellConstraints();
        sliderPanel = new JPanel();
        sliderPanel.setLayout(new FormLayout("center:pref", "min,pref"));
        sliderPanel.add(sliderValueLabel, cc.xy(1, 1));
        sliderPanel.add(slider, cc.xy(1, 2));
//        
//        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
//        sliderPanel.add("sliderValueLabel", sliderValueLabel);
//        sliderPanel.add("slider", slider);
    }


    public boolean isVisualComponent() {
        return true;
    }


    public JComponent[][] getSwingComponents() {
        if (swingComponents == null) {
            if (label.getText().length() > 0) {
                swingComponents = new JComponent[][]{{label, sliderPanel}};
            } else {
                swingComponents = new JComponent[][]{{sliderPanel}};
            }
        }
        return swingComponents;
    }


    public String getLabelText() {
        return label.getText();
    }


    public void setLabelText(final String labeltext) {
        label.setText(labeltext);
    }


    public int getValue() {
        return slider.getValue();
    }


    public void setValue(int value) {
        slider.setValue(value);
    }


    public int getMinumum() {
        return slider.getMinimum();
    }


    public void setMinumum(int value) {
        slider.setMinimum(value);
    }


    public int getMaximum() {
        return slider.getMaximum();
    }


    public void setMaximum(int value) {
        slider.setMaximum(value);
    }


    public void setColumnSpan(int colspan) {
        if (colspan > 0) {
            LayoutInfo.setComponentColumnSpan(sliderPanel, colspan);
        }
    }


    public void setPaintValue(boolean b) {
        if (!b) {
            sliderPanel.remove(0);
            slider.removeChangeListener(l);
        }
    }


    public void setPaintTicks(boolean b) {
        slider.setPaintTicks(b);
    }


    public void setPaintTrack(boolean b) {
        slider.setPaintTrack(b);
    }


    public void setPaintLabels(boolean b) {
        slider.setPaintLabels(b);
    }


    public void setMajorTickSpacing(int n) {
        slider.setMajorTickSpacing(n);
    }


    public void setMinorTickSpacing(int n) {
        slider.setMinorTickSpacing(n);
    }


    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;

        slider.setEnabled(enabled);
        sliderValueLabel.setEnabled(enabled);

    }
}
