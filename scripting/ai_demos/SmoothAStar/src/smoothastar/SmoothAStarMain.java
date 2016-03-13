/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smoothastar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author R. N. Robson
 */
public class SmoothAStarMain 
    extends JApplet {

    SmoothAStarPanel astarPanel = null;

    public SmoothAStarMain()
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
        createPanel();
        astarPanel.setOpaque(true);
        setContentPane(astarPanel);

    }

    void createPanel()
    {
        astarPanel = new SmoothAStarPanel(500,500, 15,15);

        
    }

    void createDesktop()
    {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("A* Demo");
        mainFrame.addWindowListener(new CloseListener());

        createPanel();
        mainFrame.getContentPane().add(astarPanel, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void main(String args[])
    {
        SmoothAStarMain fdm = new SmoothAStarMain();
        fdm.createDesktop();
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
