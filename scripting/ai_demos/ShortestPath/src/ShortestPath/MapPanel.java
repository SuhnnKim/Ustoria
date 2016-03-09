/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ShortestPath;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author R. N. Robson
 */
public class MapPanel
    extends JPanel {
    
    public MapPanel()
    {
        this.setPreferredSize(new Dimension(500, 300));
    }

    public void paintComponent(Graphics g)
    {
        Iterator it = City.cityIterator();
        while(it.hasNext())
        {
            City c = (City)it.next();
            g.setColor(c.color);
            g.fillArc(xloc(c.x), yloc(c.y), 8, 8, 0, 360);
            g.drawString(c.name, xloc(c.x) + 7, yloc(c.y) -7);

            Iterator connIter = City.cityIterator();
            
            while(connIter.hasNext())
            {

                City dest = (City)connIter.next();
                if(c.name.equals(dest.name))continue;
                g.setColor(City.getLinkColor(c.name, dest.name));
                int dist = City.distanceBetween(c.name, dest.name);
                if(dist < 0) {
                    g.setColor(Color.LIGHT_GRAY);
                    dist = -dist;
                    //System.out.println("grey from " + c.name + " to " + dest.name);
                }
                g.drawLine(xloc(c.x)+7, yloc(c.y)+7, xloc(dest.x)+7, yloc(dest.y)+7);
                


                int xx = xloc(dest.x) - xloc(c.x);
                int yy = yloc(dest.y) - yloc(c.y);
                xx = (xx < 0) ? (-xx): xx;
                yy = (yy < 0) ? (-yy): yy;
                int startx = xloc((c.x < dest.x)? c.x: dest.x);
                int starty = yloc((c.y < dest.y)? c.y: dest.y);
                g.drawString(String.valueOf(dist),
                        xx/2+ 7 + startx,
                        yy/2+ 7 + starty);
                //System.out.println("from " + c.name + " to " + dest.name +
                  //      " halfx=" + xx + " halfy=" + yy +
                    //    " startx=" + startx + " starty=" + starty);
            }
        }
    }

    int xloc(int xx)
    {
        return (int)(xx/100.0 * this.getWidth());
    }

    int yloc(int yy)
    {
        return (int)(yy/100.0 * this.getHeight());
    }

}
