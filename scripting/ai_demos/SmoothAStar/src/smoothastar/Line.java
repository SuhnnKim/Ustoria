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
public class Line {
    final double FUZZ = 0.000000001;
    double x1, y1, x2, y2;
    double m, b;
    boolean vertical = false;
    
    public Line(double x1, double y1, double x2, double y2 )
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        m = computeSlope();
        b = y1 -m * x1;
    }
    
    public String toString()
    {
        return "(" + x1 + ", " + y1 + ")->(" + x2 + ", " + y2 +")";
    }
    
    public boolean isVertical()
    {
        return vertical;
    }
    
    public double getVerticalX()
    {
        return x1;
    }
    
    private boolean approximatelyEqual(double d1, double d2)
    {
        double diff = d1 - d2;
        diff = (diff < 0.0)? (1.0 * diff) : diff;
        return diff >= FUZZ;
    }
    
    private double computeSlope()
    {
        double result = 0.0;
        double dx = x2 - x1;
        if(dx == 0.0)
        {
            vertical = true;
        }
        else
        {
            result = (y2 - y1) / dx;
        }
        return result;
    }
    
    public double getSlope()
    {
        return m;
    }
    
    public double getYIntercept()
    {
        return b;
    }
    
    public boolean parallel(Line l1)
    {
        boolean result = false;
        if(vertical || l1.vertical)
        {
            result = vertical && l1.vertical;
        }
        else
        {
            result = approximatelyEqual(m, l1.m);
        }
        return result;
    }
    
    /**
     * Determine the intersection point of two lines. This assumes these lines are
     * of infinite length.
     * @param l1 The line to intersect with this.
     * @return A point of intersection or null if they are parallel
     */
    public Point findIntersection(Line l1)
    {
        Point result = null;
        if(! parallel(l1))
        {
            double x = 0.0;
            double y = 0.0;
            if(vertical || l1.vertical)
            {
                if(vertical)
                {
                    x = x1;
                    y = l1.m * x + l1.b;
                }
                else
                {
                    x = l1.x1;
                    y = m * x + b;
                }
                result = new Point(x, y);
            }
            else
            {
                x = (l1.b - b) / (m - l1.m);
                y = m * x + b;
                result = new Point(x, y);
            }
        }
        
        return result;
    }
    
    /**
     * Return true if the intersection point passed is within this line when it is
     * treated as a line segment bounded by its end points.
     * @param intersection
     * @return The intersection point or null if it is outside the line segment
     */
    public Point findIntersectionInRange(Point intersection)
    {
        if(intersection != null)
        {
            double xmin = (x1 < x2)? x1 : x2;
            double ymin = (y1 < y2)? y1 : y2;
            double xmax = (x1 > x2)? x1 : x2;
            double ymax = (y1 > y2)? y1 : y2;
            if(intersection.x > xmax ||
                    intersection.x < xmin ||
                    intersection.y > ymax ||
                    intersection.y < ymin)
            {
                intersection = null;
            }
            
        }
        return intersection;
    }
    
    /**
     * Return the intersection point of the 2 line segments or null if they do not
     * intersect at a point within both line segments.
     * @param l1
     * @return 
     */
    public Point findIntersectionInRange(Line l1)
    {
        Point intersection = findIntersection(l1);
        
        intersection = findIntersectionInRange(intersection);
        if(intersection != null) intersection = l1.findIntersectionInRange(intersection);
        return intersection;
    }
    
    public boolean isPointOnLine(Point p)
    {
        boolean result = false;
        if(vertical)
        {
            result = approximatelyEqual(x1, p.x);
        }
        else
        {
            double y = m * p.x + b;
            result = approximatelyEqual(y, p.y);
        }
        return result;
    }
    
}
