/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smoothastar;

import java.util.*;

/**
 *
 * @author robson
 */
public class SortedLinkedList
{
    LinkedList<Comparable>      dataList = new LinkedList();
    
    public void SortedLinkedList()
    {
        
    }
    
    public Iterator iterator()
    {
        return dataList.iterator();
    }
    
    public void insert(Comparable c)
    {
        ListIterator<Comparable> iter = dataList.listIterator();
        int pos = 0;
        while(iter.hasNext())
        {
            Comparable curr = iter.next();
            if(c.compareTo(curr) < 0)
            {  
                if(curr.equals(c)) return;
                break;
            }
            pos++;
        }
        dataList.add(pos, c);
    }
    
    public int size()
    {
        return dataList.size();
    }
    
    public boolean empty()
    {
        return dataList.size() == 0;
    }
    
    public void clear()
    {
        dataList.clear();
    }
    
    public Comparable removeHead()
    {
        Comparable result = 0;
        if(dataList.size() > 0)
        {
            result = dataList.removeFirst();
        }
        return result;
    }
    
    public void remove(Comparable c)
    {
        dataList.remove(c);
    }
}
