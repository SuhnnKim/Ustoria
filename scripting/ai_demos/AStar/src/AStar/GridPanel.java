/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AStar;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author R. N. Robson
 */
public class GridPanel
    extends JPanel
{
    int rows, cols;
    int cellBorderWd = 2;
    CellProperties[][] grid;
    Color regularColor = new Color(200, 200, 200);
    Color nonTraversableColor = Color.BLACK;
    Color destinationColor = Color.BLUE;
    Color characterColor = Color.RED;
    Color possibleColor = Color.YELLOW;

    Coords characterCoords = null;
    Coords destinationCoords = null;
    Vector<Coords>  possibleMoves = new Vector<Coords>();

    Font font = new Font("SansSerif", Font.PLAIN, 10);
    NumberFormat formatter = new DecimalFormat("#0.0");

    public GridPanel(int wd, int ht, int rows, int cols)
    {
        
        this.rows = rows;
        this.cols = cols;
        grid = new CellProperties[rows][cols];
        for(int r=0; r< rows; r++)
        {
            for(int c = 0; c < cols; c++)
            {
                grid[r][c] = new CellProperties();
            }
        }
        this.setPreferredSize(new Dimension(wd, ht));
    }

    public void setCharacter(int r, int c)
    {
        if(characterCoords != null)
        {
            grid[characterCoords.row][characterCoords.col].character = false;
        }
        grid[r][c].character = true;
        characterCoords = new Coords(r,c);
        this.repaint();
    }

    public void setDestination(int r, int c)
    {
        grid[r][c].destination = true;
        destinationCoords = new Coords(r, c);
    }

    public void setNonTraversable(int r, int c)
    {
        grid[r][c].traversable = false;
    }

    public void resetGrid()
    {
       for(int r=0; r< rows; r++)
        {
            for(int c = 0; c < cols; c++)
            {
                grid[r][c].destination = false;
                grid[r][c].character = false;
                grid[r][c].traversable = true;
                grid[r][c].showDistance = false;
            }
        }
       this.repaint();
    }

    public Coords getDestination()
    {
        return destinationCoords;
    }

    public void clearPossibleMoves()
    {
        Iterator<Coords> iter = possibleMoves.iterator();
        while(iter.hasNext())
        {
            Coords c = iter.next();
            grid[c.row][c.col].showDistance = false;
        }
        possibleMoves.clear();
        repaint();
    }

    public Vector<Coords> showPossibleMoves()
    {
        

        int r = characterCoords.row;
        int c = characterCoords.col;
        if(r >= 1)  // top side
        {
            if(grid[r-1][c].traversable) {
                Coords tmp = new Coords(r-1,c);
                possibleMoves.add(tmp);
                grid[r-1][c].showDistance = true;
                grid[r-1][c].distToDestination = tmp.distance(destinationCoords);
            }
        }
        if(r < (rows - 1))  // bottom side
        {
            if(grid[r+1][c].traversable) {
                Coords tmp = new Coords(r+1,c);
                possibleMoves.add(tmp);
                grid[r+1][c].showDistance = true;
                grid[r+1][c].distToDestination = tmp.distance(destinationCoords);
            }
        }

        if(c >= 1)  // left side
        {
            if(grid[r][c-1].traversable) {
                Coords tmp = new Coords(r,c-1);
                possibleMoves.add(tmp);
                grid[r][c-1].showDistance = true;
                grid[r][c-1].distToDestination = tmp.distance(destinationCoords);
            }
        }
        if(c < (cols - 1))  // right side
        {
            if(grid[r][c+1].traversable) {
                Coords tmp = new Coords(r,c+1);
                possibleMoves.add(tmp);
                grid[r][c+1].showDistance = true;
                grid[r][c+1].distToDestination = tmp.distance(destinationCoords);
            }
        }
        this.repaint();
        return possibleMoves;
    }

    public Coords findBestMove()
    {
        Coords result = null;
        double dist = 100000.0;
        Iterator<Coords> iter = possibleMoves.iterator();
        while(iter.hasNext())
        {
            Coords c = iter.next();
            if(grid[c.row][c.col].distToDestination < dist)
            {
                dist = grid[c.row][c.col].distToDestination;
                result = c;
            }
        }
        return result;
    }

    class CellProperties
    {
        public boolean destination = false;
        public boolean character    = false;
        public boolean traversable  = true;
        public boolean showDistance = false;
        double distToDestination = 0.0;
    }

    public void paintComponent(Graphics g)
    {
        int ht = this.getHeight();
        int wd = this.getWidth();
        int cellSize = wd / cols;

        g.clearRect(0,0, wd, ht);
        g.setColor(Color.BLACK);
        for(int i = 0; i <= rows; i++)
        {
            g.drawLine(0, cellSize * i, wd, cellSize * i);
            g.drawLine(cellSize * i, 0, cellSize * i, wd);
        }

        for(int r=0; r < rows; r++)
        {
            for(int c=0; c < cols; c++)
            {

                if(grid[r][c].character)
                {
                    g.setColor(characterColor);
                }
                else if(grid[r][c].destination)
                {
                    g.setColor(destinationColor);
                }
                else if(grid[r][c].showDistance)
                {
                    g.setColor(possibleColor);
                }
                else if(grid[r][c].traversable)
                {
                    g.setColor(regularColor);
                }               
                else
                {
                    g.setColor(nonTraversableColor);
                }

                g.fillRect(c * cellSize + cellBorderWd, r * cellSize + cellBorderWd,
                        cellSize - 2 * cellBorderWd, cellSize - 2 * cellBorderWd);
                if(grid[r][c].showDistance)
                {
                    g.setColor(Color.BLACK);
                    String d = formatter.format(grid[r][c].distToDestination);
                    g.drawString(d,  c * cellSize + 3, r * cellSize + 16);
                }
            }
        }
        g.setColor(Color.white);
    }
}
