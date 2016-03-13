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
public class Square {
    Line[] sides = new Line[4];
    
    public Square(int r, int c, double sz)
    {
        
        double tlx = r * sz;
        double tly = c * sz;
        sides[0] = new Line(tlx+1, tly+1, tlx+1, tly + sz-1);
        sides[1] = new Line(tlx+1, tly+1, tlx + sz-1, tly+1);
        sides[2] = new Line(tlx+ sz-1, tly+1, tlx + sz-1, tly + sz-1);
        sides[3] = new Line(tlx + sz-1, tly + sz-1, tlx+1, tly + sz-1);
    }
    
    public String toString()
    {
        StringBuffer result = new StringBuffer();
        result.append("[");
        for(int i = 0; i < 4; i++)
        {
            result.append(sides[i].toString());
        }
        result.append("}");
        return result.toString();
    }
    
    public Point findIntersection(Line l1)
    {
        Point p = null;
        for(int i = 0; i < 4; i++)
        {
            p = sides[i].findIntersectionInRange(l1);
            if(p != null) break;
        }
        return p;
    }
    
}
