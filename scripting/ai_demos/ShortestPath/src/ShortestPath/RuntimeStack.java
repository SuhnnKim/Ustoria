/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ShortestPath;

import java.util.*;

/**
 *
 * @author R. N. Robson
 */
public class RuntimeStack {
    Vector<StackFrame> stack = new Vector<StackFrame>();
    
    public RuntimeStack()
    {
    }

    public void clear()
    {
        stack.clear();
    }
    
    public int size()
    {
        return stack.size();
    }

    public boolean empty()
    {
        return stack.size() == 0;
    }
    
    public boolean contains(String cty)
    {
        Iterator it = stack.iterator();
        while(it.hasNext())
        {
            StackFrame f = (StackFrame)it.next();
            if(f.city.name.equals(cty)) return true;   
        }
        return false;
    }

    public void push(String cty)
    {
        stack.add(new StackFrame(cty));
    }

    public void pop()
    {
        if(stack.size() > 0 )
            stack.removeElementAt(stack.size() - 1);
    }

    public String nextConnection()
    {
        String result = null;

        if(stack.size() > 0)
        {
            StackFrame top = stack.elementAt(stack.size() - 1);
            top.activeConn++;
            if(top.activeConn < top.numConnections())
            {
                result = top.city.connections.elementAt(top.activeConn);
                if(top.isConnectionUsed(result))
                {
                    result = nextConnection();
                }
            }
        }

        return result;
    }

    public void resetConnections()
    {
        if(stack.size() > 0)
        {
            StackFrame top = stack.elementAt(stack.size() - 1);
            top.activeConn = -1;
        }
    }

    public void addUsedConnection(String conn)
    {

        if(stack.size() > 0)
        {
            StackFrame top = stack.elementAt(stack.size() - 1);
            top.addUsedConnection(conn);
        }
    }

    public City topCity()
    {
        City result = null;
        if(stack.size() > 0)
        {
            StackFrame top = stack.elementAt(stack.size() - 1);
            result = top.city;
        }
        return result;
    }

    public boolean hasMoreConnections()
    {
        boolean result = false;
        if(stack.size() > 0)
        {
            StackFrame top = stack.elementAt(stack.size() - 1);
            result = (top.activeConn + 1) >= top.numConnections();
        }
        return result;
    }

    class StackFrame
    {
        City city;
        int activeConn;
        Vector<String> usedConnections = new Vector<String>();

        public StackFrame(String cty)
        {
            city = City.cityByName(cty);
            activeConn = -1;
        }

        public int numConnections()
        {
            return city.connections.size();
        }

        public void addUsedConnection(String conn)
        {
            usedConnections.add(conn);
        }

        public boolean isConnectionUsed(String conn)
        {
            return usedConnections.contains(conn);
        }

        public void clearUsedConnections()
        {
            usedConnections.clear();
        }
    }

}
