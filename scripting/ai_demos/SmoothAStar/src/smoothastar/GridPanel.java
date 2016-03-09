/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smoothastar;

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
    Color triedColor = Color.PINK;
    Color openColor = Color.GREEN;

    Coords characterCoords = null;
    Coords destinationCoords = null;
    Vector<Coords>  possibleMoves = new Vector<Coords>();
    SortedLinkedList    openList = new SortedLinkedList();
    Vector<Coords>  path = new Vector();
    Vector<Coords>  nonTraversable = new Vector();

    Font font = new Font("SansSerif", Font.PLAIN, 10);
    NumberFormat formatter = new DecimalFormat("#0.0");
    double cellDimension = 10.0;
    boolean drawLinePath = false;

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
        boolean pathSaved = false;
        if(characterCoords != null)
        {
            grid[characterCoords.row][characterCoords.col].character = false;
            grid[characterCoords.row][characterCoords.col].previouslyTried = true;
            path.add(characterCoords);
            pathSaved = true;
        }
        grid[r][c].character = true;
        characterCoords = new Coords(r,c);
        if(! pathSaved) path.add(characterCoords);
        openList.remove(characterCoords);
        this.repaint();
    }

    public void setDestination(int r, int c)
    {
        grid[r][c].destination = true;
        destinationCoords = new Coords(r, c);
    }
    
    public void setPreviouslyTried()
    {
        if(characterCoords != null)
            grid[characterCoords.row][characterCoords.col].previouslyTried = true;
    }

    public void setNonTraversable(int r, int c)
    {
        grid[r][c].traversable = false;
        nonTraversable.add(new Coords(r,c));
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
                grid[r][c].previouslyTried = false;
            }
        }
       path.clear();
       drawLinePath = false;
       this.repaint();
    }
    
    public void showGridPath(boolean showOrHide)
    {
        Iterator<Coords> iter = path.iterator();
        while(iter.hasNext())
        {
            Coords c = iter.next();
            grid[c.row][c.col].previouslyTried = showOrHide;
        }
        if(showOrHide) drawLinePath = true;
    }
    
    private boolean walkable(int r1, int c1, int r2, int c2)
    {
        boolean result = true;
        double cd = cellDimension;
        double halfSz = cd / 2.0;
        Line testLine = new Line(r1 * cd  + halfSz,
            c1 * cd + halfSz,
            r2 * cd + halfSz,
            c2 * cd + halfSz);
        Iterator<Coords> iter = nonTraversable.iterator();
        while(iter.hasNext())
        {
            Coords posn = iter.next();
            Square s = new Square(posn.row, posn.col, cellDimension);
            if(s.findIntersection(testLine) != null)
            {
                result = false;
                System.out.println("line from ("+ r1 + ", " + c1 + ") to (" +
                        r2 + ", " + c2 + ") intersects square " + posn);
                System.out.println("line " + testLine + " intersects square " +
                        s );
                break;
            }           
        }
        
        return result;
    }
    
    void printPath()
    {
        for(int i = 0; i < path.size(); i++)
        {
            System.out.println(path.elementAt(i)+ ", ");
        }
    }
    
    public boolean smoothPath()
    {
        Vector<Coords> newPath = new Vector();
        boolean pathChanged = false;
        int oldPathSize = path.size();
        if(path.size() > 2)
        {
            Coords lastCoord = path.firstElement(), prevCoord = path.firstElement(), currCoord = path.elementAt(1);
            newPath.add(lastCoord);
            int last=0, prev=0, curr=1;
            while(curr <path.size())
            {
                currCoord = path.elementAt(curr);
                if(! walkable(lastCoord.row, lastCoord.col, currCoord.row, currCoord.col))
                {
                    prevCoord = path.elementAt(prev);
                    newPath.add(prevCoord);
                    last = prev;
                    lastCoord = path.elementAt(prev);
                    pathChanged = true;
                }
                else
                {
                   prev = curr;
                    curr++; 
                }
                
            }
            if(pathChanged)
            {
                newPath.add(path.lastElement());
                path = newPath;
            }
            
        }
        
        //System.out.println("returning from smoothPath");
        //printPath();
        return oldPathSize != path.size();
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
    
    boolean isInGrid(Coords crd)
    {
        return (crd.row >= 0 && crd.row < rows) &&
                (crd.col >= 0 && crd.col < cols);
    }

    public Vector<Coords> showPossibleMoves()
    {
        

        int r = characterCoords.row;
        int c = characterCoords.col;
        
        
        Coords[] coordsToCheck = new Coords[8];
        int numToCheck = 0;
        coordsToCheck [numToCheck++] = new Coords(r-1, c-1);
        coordsToCheck [numToCheck++] = new Coords(r-1, c);
        coordsToCheck [numToCheck++] = new Coords(r-1, c+1);
        coordsToCheck [numToCheck++] = new Coords(r, c-1);
        coordsToCheck [numToCheck++] = new Coords(r, c+1);
        coordsToCheck [numToCheck++] = new Coords(r+1, c-1);
        coordsToCheck [numToCheck++] = new Coords(r+1, c);
        coordsToCheck [numToCheck++] = new Coords(r+1, c+1);
        for(int i = 0; i < numToCheck; i++)  
        {
            if(isInGrid(coordsToCheck[i]) && grid[coordsToCheck[i].row][coordsToCheck[i].col].traversable 
                    && (! grid[coordsToCheck[i].row][coordsToCheck[i].col].previouslyTried))
            {
                    possibleMoves.add(coordsToCheck[i]);
                    grid[coordsToCheck[i].row][coordsToCheck[i].col].showDistance = true;
                    grid[coordsToCheck[i].row][coordsToCheck[i].col].distToDestination = coordsToCheck[i].distanceToDest = coordsToCheck[i].distance(destinationCoords);
                    openList.insert(coordsToCheck[i]);                  
            }
        }
       
        this.repaint();
        return possibleMoves;
    }

    public Coords findBestMove()
    {
        Coords result = (Coords)openList.removeHead();
        return result;
    }

    class CellProperties
    {
        public boolean destination = false;
        public boolean character    = false;
        public boolean traversable  = true;
        public boolean showDistance = false;
        public boolean previouslyTried = false;
        public boolean open             = false;
        double distToDestination = 0.0;
    }

    public void paintComponent(Graphics g)
    {
        int ht = this.getHeight();
        int wd = this.getWidth();
        int cellSize = wd / cols;
        cellDimension = cellSize;
        for(int r=0; r < rows; r++)
        {
            for(int c=0; c < cols; c++)
            {
               grid[r][c].open = false; 
            }
        }
        Iterator<Coords> openIter = openList.iterator();
        while(openIter.hasNext())
        {
            Coords c = openIter.next();
            grid[c.row][c.col].open = true;
        }

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
                else if(grid[r][c].previouslyTried)
                {
                    g.setColor(triedColor);
                } 
                else if(grid[r][c].open)
                {
                    g.setColor(openColor);
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
        if(drawLinePath)
        {
            g.setColor(Color.BLACK);
            double half = cellDimension / 2.0;
            Coords last = null;
            Iterator<Coords> iter = path.iterator();
            while(iter.hasNext())
            {
                Coords c = iter.next();
                if(last != null)
                {
                    g.drawLine((int)(last.col * cellDimension + half),
                            (int)(last.row * cellDimension + half),
                            (int)(c.col * cellDimension + half), 
                            (int)(c.row * cellDimension + half));
                }
                last = c;
            }
        }
        g.setColor(Color.white);
    }
}
