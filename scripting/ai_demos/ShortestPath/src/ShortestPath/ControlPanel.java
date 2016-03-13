/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ShortestPath;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author R. N. Robson
 */
public class ControlPanel
    extends JPanel {
    DemoPanel   demo        = null;
    JComboBox   fromCB      = null;
    JComboBox   toCB        = null;
    JComboBox   algCB       = null;
    JButton     stepBut     = new JButton("Step");
    JButton     solveBut    = new JButton("Solve");
    JButton     resetBut    = new JButton("Reset");
    AbstractAlgorithm algorithm;
    AbstractAlgorithm   algList[] = new AbstractAlgorithm[2];
    String cityNames[];
    boolean moreSteps = true;


    public ControlPanel(DemoPanel dp)
    {
        demo = dp;
        Vector v = new Vector();
        Iterator it = City.cityIterator();
        while(it.hasNext())
        {
            v.add(((City)it.next()).name);
        }
        cityNames = new String[v.size()];
        for(int i = 0; i < v.size(); i++)
        {
            cityNames[i] = (String)v.elementAt(i);
        }

        algList[0] = new GreedyAlgorithm(dp);
        algList[1] = new AstarAlgorithm(dp);
        algorithm = algList[0];

        createContent();
    }

    void createContent()
    {
        fromCB = new JComboBox(cityNames);
        toCB = new JComboBox(cityNames);
        algCB = new JComboBox(algList);

        fromCB.addActionListener(new FromHandler());
        toCB.addActionListener(new ToHandler());
        algCB.addActionListener(new AlgHandler());

        stepBut.addActionListener(new StepHandler());
        solveBut.addActionListener(new SolveHandler());
        resetBut.addActionListener(new ResetHandler());

        JPanel cbPanel = new JPanel();
        cbPanel.setLayout(new GridLayout(2, 4));
        cbPanel.add(new JLabel("From"));
        cbPanel.add(fromCB);
        cbPanel.add(new JLabel("To"));
        cbPanel.add(toCB);
        cbPanel.add(new JLabel("Algorithm"));
        cbPanel.add(algCB);
        cbPanel.add(new JLabel(""));
        cbPanel.add(new JLabel(""));

        JPanel butPanel = new JPanel();
        butPanel.add(stepBut);
        butPanel.add(solveBut);
        butPanel.add(resetBut);

        setLayout(new BorderLayout());
        add(cbPanel, BorderLayout.NORTH);
        add(butPanel, BorderLayout.SOUTH);

        fromCB.setSelectedIndex(0);
        toCB.setSelectedIndex(0);
        algCB.setSelectedIndex(0);
    }

    class FromHandler
            implements ActionListener {
        public void actionPerformed(ActionEvent evt)
        {
            algorithm.setStartCity(fromCB.getSelectedItem().toString());
            stepBut.setEnabled(true);
            solveBut.setEnabled(true);
            City.reset();
            demo.getDemoAreaPanel().repaint();
        }
    }

    class AlgHandler
            implements ActionListener {
        public void actionPerformed(ActionEvent evt)
        {
            algorithm = (AbstractAlgorithm)algCB.getSelectedItem();
            algorithm.reset();
            algorithm.setStartCity(fromCB.getSelectedItem().toString());
            algorithm.setEndCity(toCB.getSelectedItem().toString());
            demo.getExplainArea().setText(algorithm.getDescription());
            stepBut.setEnabled(true);
            solveBut.setEnabled(true);
            City.reset();
            demo.getDemoAreaPanel().repaint();
        }
    }

    class ToHandler
            implements ActionListener {
        public void actionPerformed(ActionEvent evt)
        {
            algorithm.setEndCity(toCB.getSelectedItem().toString());
            stepBut.setEnabled(true);
            solveBut.setEnabled(true);
            City.reset();
            demo.getDemoAreaPanel().repaint();
        }
    }

    class StepHandler
            implements ActionListener {
        public void actionPerformed(ActionEvent evt)
        {
            stepBut.setEnabled(false);
            moreSteps = algorithm.step();
            stepBut.setEnabled(moreSteps);
            solveBut.setEnabled(moreSteps);
        }
    }

    class SolveHandler
            implements ActionListener {
        public void actionPerformed(ActionEvent evt)
        {
            algorithm.solve();
            stepBut.setEnabled(false);
            solveBut.setEnabled(false);
        }
    }

    class ResetHandler
            implements ActionListener {
        public void actionPerformed(ActionEvent evt)
        {
            algorithm.reset();
            stepBut.setEnabled(true);
            solveBut.setEnabled(true);
            demo.getOutputArea().setText("");
            demo.getComment().setText("");
            City.reset();
            demo.getDemoAreaPanel().repaint();
        }
    }

}
