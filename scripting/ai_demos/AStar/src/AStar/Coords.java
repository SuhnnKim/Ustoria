/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AStar;

/**
 *
 * @author R. N. Robson
 */
public class Coords
{
    public int row = 0;
    public int col = 0;

    public Coords(int r, int c)
    {
        row = r;
        col = c;
    }

    public double distance(Coords c)
    {
        double result = 0.0;

        int dy = row - c.row;
        int dx = col - c.col;

        result = Math.sqrt(dx * dx + dy * dy);

        return result;
    }

    public double distance(int r, int c)
    {
        double result = 0.0;

        int dy = row - r;
        int dx = col - c;

        result = Math.sqrt(dx * dx + dy * dy);

        return result;
    }

    public String toString()
    {
        return "(" + String.valueOf(row) + ", " + String.valueOf(col) + ")";
    }

    public boolean equals(Object o)
    {
        boolean result = false;
        if(o instanceof Coords)
        {
            Coords c = (Coords)o;
            result = row == c.row && col == c.col;
        }
        return result;
    }
}
