/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ShortestPath;

import java.util.*;
import java.awt.*;
import javax.swing.SwingUtilities;

/**
 *
 * @author R. N. Robson
 */
public abstract class AbstractAlgorithm {
    static final int STEP_TIME = 3000;
    static final Color VISIT_COLOR = Color.RED;
    DemoPanel demo = null;
    String startCity = null;
    String endCity = null;
    String currCity = null;
    Vector<String>  trialStartCity = new Vector<String>();
    Vector<String>  trialEndCity = new Vector<String>();

    RuntimeStack stack = new RuntimeStack();

    public AbstractAlgorithm(DemoPanel dp)
    {
        demo = dp;
    }

    public void setStartCity(String cty)
    {
        startCity = cty;
        reset();
    }

    public void setEndCity(String cty)
    {
        endCity = cty;
        reset();
    }

    public void reset()
    {
        currCity = startCity;
        stack.clear();
        trialStartCity.clear();
        trialEndCity.clear();
    }

    public void solve()
    {
        boolean more = true;
        while(more)
        {
            more = step();
        }
    }

    public boolean step()
    {
        boolean moreSteps = (startCity != null) && (endCity != null)  && (currCity != null)
                && (! startCity.equals(endCity));
        String minName = null;
        int minDist = 0;

        for(int i = 0; i < trialStartCity.size(); i++)
        {
            City.setLinkColor(trialStartCity.elementAt(i),
                    trialEndCity.elementAt(i), Color.BLACK);
        }
        trialStartCity.clear();
        trialEndCity.clear();

        if(moreSteps)
        {
            StringBuffer msg = new StringBuffer();

            if(stack.empty())
            {
                stack.push(currCity);
            }
           String connName = stack.nextConnection();
            while(connName != null)
            {                
                //System.out.println("path from " + connName + " to " + endCity + " = " + pathExists);
                if(! stack.contains(connName))
                {
                    City.setLinkColor(currCity, connName, Color.YELLOW);
                    trialStartCity.add(currCity);
                    trialEndCity.add(connName);
                    msg.append("from ");
                    msg.append(connName);
                    msg.append(" to ");
                    msg.append(endCity);
                    msg.append(" = ");

                    if(minName == null)
                    {
                        minName = connName;
                        minDist = heuristic(connName);

                        msg.append(minDist);
                        
                    }
                    else
                    {
                        int dist = heuristic(connName);
                        msg.append(dist);
                        
                        if(dist < minDist)
                        {
                            minName = connName;
                            minDist = dist;
                        }
                    }
                    //demo.getComment().setText(msg.toString());
                    demo.getOutputArea().append(msg.toString() + "\n");
                    msg.setLength(0);
                    
                }
                connName = stack.nextConnection();
            }

            if(minName != null)
            {
                City.cityByName(currCity).color = VISIT_COLOR;
                City.cityByName(minName).color = VISIT_COLOR;
                City.setLinkColor(currCity, minName, VISIT_COLOR);

                stack.addUsedConnection(minName);
                stack.push(minName);
                currCity = minName;
                demo.getOutputArea().append("selected " + minName + "\n");
                trialEndCity.remove(minName);
                trialStartCity.removeElementAt(0);
                moreSteps = (! currCity.equals(endCity));
            }
            else
            {
               if(! stack.empty())
               {
                   stack.pop();
                   stack.resetConnections();
                   demo.getOutputArea().append("back tracking to ");
                   demo.getOutputArea().append(stack.topCity().name);
                   demo.getOutputArea().append("\n");
                   City.setLinkColor(currCity, stack.topCity().name, Color.BLACK);
                   City.cityByName(currCity).color = Color.BLACK;
                   currCity = stack.topCity().name;
                   moreSteps = true;
               }
               else
               {
                   moreSteps = false;
               }
            }
            moreSteps = (minName == null) || (! currCity.equals(endCity));
            demo.repaint();
        }
        return moreSteps;
    }

    public abstract int heuristic(String dest);

    public abstract String getDescription();

   
}
