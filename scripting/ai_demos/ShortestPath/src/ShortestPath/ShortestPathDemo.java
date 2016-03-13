/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ShortestPath;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author R. N. Robson
 */
public class ShortestPathDemo 
    extends JApplet {
    static DemoPanel demoPanel = new DemoPanel();
    static MapPanel map = new MapPanel();

    public ShortestPathDemo()
    {

    }

    public void init()
    {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }

    public void start()
    {

    }

    public void stop()
    {

    }

    void createGUI()
    {
        demoPanel.setOpaque(true);
        setContentPane(demoPanel);
        demoPanel.getDemoAreaPanel().add(map, BorderLayout.CENTER);
    }

    public static void main(String args[])
    {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Shortest Path Demo");
        mainFrame.addWindowListener(new CloseListener());

        demoPanel.setOpaque(true);
        mainFrame.getContentPane().add(demoPanel, BorderLayout.CENTER);
        demoPanel.getDemoAreaPanel().add(map, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    static class CloseListener
            extends WindowAdapter
    {
        public void windowClosing(WindowEvent evt)
        {
            System.exit(0);
        }

        public void windowClosed(WindowEvent evt)
        {
            System.exit(0);
        }

    }
}
