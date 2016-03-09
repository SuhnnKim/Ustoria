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
public class SmoothAStarPanel
    extends JPanel
{
    GridPanel gridPanel = null;
    JButton     stepBut = new JButton("Step");
    JButton     resetBut = new JButton("Reset");
    JButton     smoothBut = new JButton("Smooth");
    JLabel      msgLabel = new JLabel();
    boolean findPossibles = true;

    public SmoothAStarPanel(int wd, int ht, int rows, int cols)
    {
        gridPanel = new GridPanel(wd, ht, rows, cols);
        createContent();
        initializeGrid();
    }

    void createContent()
    {
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel();
        JPanel butPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        butPanel.add(stepBut);
        butPanel.add(smoothBut);
        butPanel.add(resetBut);
        controlPanel.add(msgLabel, BorderLayout.SOUTH);
        controlPanel.add(butPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        this.add(gridPanel, BorderLayout.CENTER);

        stepBut.addActionListener(new StepHandler());
        smoothBut.addActionListener(new SmoothHandler());
        resetBut.addActionListener(new ResetHandler());
        
        smoothBut.setEnabled(false);
    }

    void initializeGrid()
    {
        gridPanel.setDestination(10, 10);
        gridPanel.setCharacter(2,2);
        gridPanel.setNonTraversable(4,4);
        gridPanel.setNonTraversable(5,4);
        gridPanel.setNonTraversable(6,4);
        gridPanel.setNonTraversable(7,4);
        gridPanel.setNonTraversable(7,5);
        gridPanel.setNonTraversable(7,6);
        gridPanel.setNonTraversable(7,7);
        gridPanel.setNonTraversable(6,7);
        gridPanel.setNonTraversable(5,7);
        gridPanel.setNonTraversable(4,7);
        gridPanel.setNonTraversable(3,7);
        gridPanel.setNonTraversable(3,8);
        gridPanel.setNonTraversable(3,9);
        gridPanel.setNonTraversable(3, 6);
    }

    class StepHandler
            implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            if(findPossibles)
            {
                gridPanel.showPossibleMoves();
                findPossibles = false;
                
            }
            else
            {
                Coords nxt = gridPanel.findBestMove();
                if(nxt != null)
                {
                    gridPanel.clearPossibleMoves();
                    gridPanel.setCharacter(nxt.row, nxt.col);
                    //gridPanel.setPreviouslyTried();
                    if(gridPanel.getDestination().equals(nxt))
                    {
                        stepBut.setEnabled(false);
                        smoothBut.setEnabled(true);
                    }
                }
                findPossibles = true;
            }
        }
    }
    
    class SmoothHandler
            implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            gridPanel.showGridPath(false);
            smoothBut.setEnabled(gridPanel.smoothPath());
            gridPanel.showGridPath(true);
            gridPanel.repaint();
        }
    }

    class ResetHandler
            implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            gridPanel.resetGrid();
            gridPanel.clearPossibleMoves();
            initializeGrid();
            stepBut.setEnabled(true);
            findPossibles = true;
        }
    }

}
