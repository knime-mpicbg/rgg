/*
 * Created by JFormDesigner on Fri Jun 11 11:19:20 CEST 2010
 */

package at.ac.arcs.rgg.runner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Holger Brandl
 */
public class RGGTabContainer extends JPanel {
    public RGGTabContainer() {
        initComponents();
    }

    private void openRGGMenuItemActionPerformed() {
        // TODO add your code here
    }

    private void quitMenuItemActionPerformed() {
        // TODO add your code here
    }

    private void refreshMenuActionPerformed() {
        // TODO add your code here
    }

    private void runMenuActionPerformed() {
        // TODO add your code here
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Open Source Project license - Sphinx-4 (cmusphinx.sourceforge.net/sphinx4/)
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        openRGGMenuItem = new JMenuItem();
        quitMenuItem = new JMenuItem();
        refreshMenu = new JMenu();
        runMenu = new JMenu();
        tabPanel = new JTabbedPane();
        guiPanel = new JPanel();
        sourceTabPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        sourceArea = new JTextArea();
        scriptPanel = new JPanel();
        scrollPane2 = new JScrollPane();
        genScriptArea = new JTextArea();

        //======== this ========
        setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("File");

                //---- openRGGMenuItem ----
                openRGGMenuItem.setText("Open");
                openRGGMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        openRGGMenuItemActionPerformed();
                    }
                });
                menu1.add(openRGGMenuItem);
                menu1.addSeparator();

                //---- quitMenuItem ----
                quitMenuItem.setText("Quit");
                quitMenuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        quitMenuItemActionPerformed();
                    }
                });
                menu1.add(quitMenuItem);
            }
            menuBar1.add(menu1);

            //======== refreshMenu ========
            {
                refreshMenu.setText("Refresh");
                refreshMenu.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        refreshMenuActionPerformed();
                    }
                });
            }
            menuBar1.add(refreshMenu);

            //======== runMenu ========
            {
                runMenu.setText("Run");
                runMenu.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        runMenuActionPerformed();
                    }
                });
            }
            menuBar1.add(runMenu);
        }
        add(menuBar1, BorderLayout.NORTH);

        //======== tabPanel ========
        {

            //======== guiPanel ========
            {
                guiPanel.setLayout(new BorderLayout());
            }
            tabPanel.addTab("GUI", guiPanel);


            //======== sourceTabPanel ========
            {
                sourceTabPanel.setLayout(new BorderLayout());

                //======== scrollPane1 ========
                {

                    //---- sourceArea ----
                    sourceArea.setEnabled(false);
                    scrollPane1.setViewportView(sourceArea);
                }
                sourceTabPanel.add(scrollPane1, BorderLayout.CENTER);
            }
            tabPanel.addTab("Source", sourceTabPanel);


            //======== scriptPanel ========
            {
                scriptPanel.setLayout(new BorderLayout());

                //======== scrollPane2 ========
                {
                    scrollPane2.setViewportView(genScriptArea);
                }
                scriptPanel.add(scrollPane2, BorderLayout.CENTER);
            }
            tabPanel.addTab("Script", scriptPanel);

        }
        add(tabPanel, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Open Source Project license - Sphinx-4 (cmusphinx.sourceforge.net/sphinx4/)
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem openRGGMenuItem;
    private JMenuItem quitMenuItem;
    private JMenu refreshMenu;
    private JMenu runMenu;
    private JTabbedPane tabPanel;
    private JPanel guiPanel;
    private JPanel sourceTabPanel;
    private JScrollPane scrollPane1;
    private JTextArea sourceArea;
    private JPanel scriptPanel;
    private JScrollPane scrollPane2;
    private JTextArea genScriptArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
