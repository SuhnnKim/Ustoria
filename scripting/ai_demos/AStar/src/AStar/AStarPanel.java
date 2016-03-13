/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AStar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author R. N. Robson
 */
public class AStarPanel
    extends JPanel
{
    GridPanel gridPanel = null;
    JButton     stepBut = new JButton("Step");
    JButton     resetBut = new JButton("Reset");
    JLabel      msgLabel = new JLabel();
    boolean findPossibles = true;

    public AStarPanel(int wd, int ht, int rows, int cols)
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
        butPanel.add(resetBut);
        controlPanel.add(msgLabel, BorderLayout.SOUTH);
        controlPanel.add(butPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        this.add(gridPanel, BorderLayout.CENTER);

        stepBut.addActionListener(new StepHandler());
        resetBut.addActionListener(new ResetHandler());
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
                    if(gridPanel.getDestination().equals(nxt))
                    {
                        stepBut.setEnabled(false);
                    }
                }
                findPossibles = true;
            }
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
