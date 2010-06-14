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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * @author Holger Brandl
 */
public class RGGRunner extends JFrame {

    public File curRGGFile;
    public RGG rgg;


    public static void main(String[] args) {
        RGGRunner rggRunner = new RGGRunner();
        rggRunner.setBounds(100, 100, 500, 600);

        rggRunner.setVisible(true);

        if (args.length == 1) {
            rggRunner.curRGGFile = new File(args[0]);
            rggRunner.refresh();
        }


//        rggRunner.rgg.getRggModel().restoreState(Collections.singletonMap("Barcode Selection", (Object) Arrays.asList("babla", "neubau")));
    }


    public RGGRunner() {
        initComponents();
        getContentPane().add(mainPanel);

        addWindowFocusListener(new WindowAdapter() {

            @Override
            public void windowGainedFocus(WindowEvent windowEvent) {
                if (autoRefreshCheckbox.isSelected()) {
                    refresh();
                }
            }
        });
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
        refresh();
    }


    private void refresh() {
        if (curRGGFile == null) {
            throw new RuntimeException("could not find/read file");
        }

        try {
            rgg = RGG.createInstance(curRGGFile);
            final JPanel rggPanel = rgg.buildPanel(true, false);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    guiPanel.removeAll();
                    guiPanel.add(rggPanel, BorderLayout.CENTER);


                    sourceArea.setText(readFileAsString(curRGGFile));
                    mainPanel.invalidate();
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
        refresh();
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
        mainPanel = new JPanel();
        menuBar1 = new JMenuBar();
        fileMenu = new JMenu();
        openRGGMenuItem = new JMenuItem();
        quitMenuItem = new JMenuItem();
        actionMenu = new JMenu();
        refreshMenuItem = new JMenuItem();
        generateMenuItem = new JMenuItem();
        autoRefreshCheckbox = new JCheckBoxMenuItem();
        tabPanel = new JTabbedPane();
        guiPanel = new JPanel();
        sourceTabPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        sourceArea = new JTextArea();
        scriptPanel = new JPanel();
        scrollPane2 = new JScrollPane();
        genScriptArea = new JTextArea();

        //======== mainPanel ========
        {
            mainPanel.setLayout(new BorderLayout());

            //======== menuBar1 ========
            {

                //======== fileMenu ========
                {
                    fileMenu.setText("File");

                    //---- openRGGMenuItem ----
                    openRGGMenuItem.setText("Open");
                    openRGGMenuItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            openRGGMenuItemActionPerformed();
                        }
                    });
                    fileMenu.add(openRGGMenuItem);
                    fileMenu.addSeparator();

                    //---- quitMenuItem ----
                    quitMenuItem.setText("Quit");
                    quitMenuItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            quitMenuItemActionPerformed();
                        }
                    });
                    fileMenu.add(quitMenuItem);
                }
                menuBar1.add(fileMenu);

                //======== actionMenu ========
                {
                    actionMenu.setText("Actions");

                    //---- refreshMenuItem ----
                    refreshMenuItem.setText("Refresh");
                    refreshMenuItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            refreshMenuActionPerformed();
                        }
                    });
                    actionMenu.add(refreshMenuItem);

                    //---- generateMenuItem ----
                    generateMenuItem.setText("Generate");
                    generateMenuItem.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            runMenuActionPerformed();
                        }
                    });
                    actionMenu.add(generateMenuItem);

                    //---- autoRefreshCheckbox ----
                    autoRefreshCheckbox.setText("Auto Refresh");
                    autoRefreshCheckbox.setToolTipText("Refresh the ui automatically when the window becomes activated");
                    actionMenu.add(autoRefreshCheckbox);
                }
                menuBar1.add(actionMenu);
            }
            mainPanel.add(menuBar1, BorderLayout.NORTH);

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
            mainPanel.add(tabPanel, BorderLayout.CENTER);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Open Source Project license - Sphinx-4 (cmusphinx.sourceforge.net/sphinx4/)
    private JPanel mainPanel;
    private JMenuBar menuBar1;
    private JMenu fileMenu;
    private JMenuItem openRGGMenuItem;
    private JMenuItem quitMenuItem;
    private JMenu actionMenu;
    private JMenuItem refreshMenuItem;
    private JMenuItem generateMenuItem;
    private JCheckBoxMenuItem autoRefreshCheckbox;
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
