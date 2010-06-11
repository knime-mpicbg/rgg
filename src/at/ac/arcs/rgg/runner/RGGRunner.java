/*
 * Created by JFormDesigner on Fri Jun 11 11:00:19 CEST 2010
 */

package at.ac.arcs.rgg.runner;

import at.ac.arcs.rgg.RGG;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author Holger Brandl
 */
public class RGGRunner extends JFrame{

    public File curRGGFile;
    public RGG rgg;


    public static void main(String[] args) {
        new RGGRunner()

        if(args.length == 1){

        }

    }


    public RGGRunner() {
        initComponents();
    }


    private void quitMenuItemActionPerformed() {
        System.exit(0);
    }


    private void openRGGMenuItemActionPerformed() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("rgg-script files", "rgg"));
        chooser.showOpenDialog(this);

        File rggFile = chooser.getSelectedFile();
        if (rggFile == null || !rggFile.isFile())
            return;


        // rebuild the content of the panel
        curRGGFile = rggFile;
        refresh(curRGGFile);
    }


    private void refresh(final File rggFile) {
        try {
            rgg = RGG.createInstance(rggFile);
            final JPanel rggPanel = rgg.buildPanel(true, false);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiPanel.removeAll();
                    guiPanel.add(rggPanel, BorderLayout.CENTER);


                    sourceArea.setText(readFileAsString(rggFile));
                }
            });

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    private static String readFileAsString(File file) {
        try {
            StringBuffer fileData = new StringBuffer(1000);
            BufferedReader reader = new BufferedReader(
                    new FileReader(file));
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Could not read file";
    }


    private void refreshMenuActionPerformed() {
        if (curRGGFile != null) {
            refresh(curRGGFile);
        }
    }


    private void runMenuActionPerformed() {
        if (rgg != null) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    genScriptArea.setText(rgg.generateRScript());
                }
            });
        }
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Open Source Project license - Sphinx-4 (cmusphinx.sourceforge.net/sphinx4/)
        panel2 = new JPanel();
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

        //======== panel2 ========
        {
            panel2.setLayout(new BorderLayout());

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
            panel2.add(menuBar1, BorderLayout.NORTH);

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
            panel2.add(tabPanel, BorderLayout.CENTER);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Open Source Project license - Sphinx-4 (cmusphinx.sourceforge.net/sphinx4/)
    private JPanel panel2;
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
