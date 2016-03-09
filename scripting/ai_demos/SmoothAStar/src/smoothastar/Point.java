/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smoothastar;

/**
 *
 * @author robson
 */
public class Point {
    public double x;
    public double y;
    
    public Point(double xx, double yy)
    {
        x = xx;
        y = yy;
    }
    
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
    
    public boolean equals(Object o)
    {
        boolean result = false;
        if(o instanceof Point)
        {
            Point oo = (Point)o;
            result = x == oo.x && y == oo.y;
        }
        return result;
    }
}
