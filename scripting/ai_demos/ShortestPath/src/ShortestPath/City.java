/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ShortestPath;

import java.awt.*;
import java.util.*;
import java.text.*;

/**
 *
 * @author R. N. Robson
 */
public class City {
    final static int MAX_CITIES = 20;
    static Hashtable<String, City>  cityTable = new Hashtable<String, City>();
    static int maxIndex = 0;
    static int distanceTable[][] = new int[MAX_CITIES][MAX_CITIES];
    static City cityArray[] = new City[MAX_CITIES];
    static Color linkColor[][] = new Color[MAX_CITIES][MAX_CITIES];


    static
    {
        init();
    }

    String name = null;
    Color   color   = Color.BLACK;
    int     index = 0;
    int     x   = 0;
    int     y   = 0;
    Vector<String> connections = new Vector<String>();

    public City(String nm, int xx, int yy)
    {
        name = nm;
        x = xx;
        y = yy;
        index = maxIndex;
        cityArray[maxIndex] = this;
        maxIndex++;
        cityTable.put(nm, this);
    }

    public static int getNumCities()
    {
        return maxIndex;
    }

    public static int cityIndex(String cty)
    {
        int result = 0;
        City c = cityTable.get(cty);
        if(c != null) result = c.index;
        return result;
    }

    public static int distanceBetween(String cty1, String cty2)
    {
        return distanceTable[cityIndex(cty1)][cityIndex(cty2)];
    }

    public static Iterator cityIterator()
    {
        return cityTable.values().iterator();
    }

    public static  void computeConnections()
    {
        Vector<String> result = new Vector<String>();

        for(int idx = 0; idx < maxIndex; idx++)
        {
            for(int i = 0; i < maxIndex; i++)
            {
                if(distanceTable[idx][i] > 0)
                {
                    cityArray[idx].connections.add(cityArray[i].name);
                }
            }
        }
    }

    public Vector<String> getConnections()
    {
        return connections;
    }

    public static void setLinkColor(String cty1, String cty2, Color c)
    {
        int c1 = cityIndex(cty1);
        int c2 = cityIndex(cty2);
        linkColor[c1][c2] = c;
        linkColor[c2][c1] = c;
    }

    

    public static Color getLinkColor(String cty1, String cty2)
    {
        return linkColor[cityIndex(cty1)][cityIndex(cty2)];
    }

    public static City cityByName(String cty)
    {
        return cityTable.get(cty);
    }

    public static void reset()
    {
        for(int i = 0; i < MAX_CITIES; i++)
        {
            if(cityArray[i] != null) cityArray[i].color = Color.BLACK;
            for(int j = 0; j < MAX_CITIES; j++)
            {
                linkColor[i][j] = Color.BLACK;
            }
        }
    }

    public static void init()
    {
        new City("London", 5, 50);
        new City("Kitchener", 25, 30);
        new City("Hamilton", 30, 70);
        new City("St. Catherines", 40, 90);
        new City("Toronto", 50, 45);
        new City("Barrie", 50, 10);
        new City("Peterborough", 70, 40);
        
        reset();

        setDistance("London", "Kitchener", 100);
        setDistance("London", "Hamilton", 125);
        setDistance("Kitchener", "Hamilton", 60);
        setDistance("St. Catherines", "Hamilton", 50);
        setDistance("Barrie", "Kitchener", 180);
        setDistance("Hamilton", "Barrie", 145);
        setDistance("Hamilton", "Toronto", 70);
        setDistance("Toronto", "Barrie", 90);
        setDistance("Toronto", "Peterborough", 139);
        setDistance("Barrie", "Peterborough", 160);

        setDistance("Toronto", "St. Catherines", -50);
        setDistance("London", "Barrie", -210);
        setDistance("London", "St. Catherines", -170);
        setDistance("London", "Peterborough", -280);
        setDistance("London", "Toronto", -240);
        setDistance("Kitchener", "Toronto", -90);
        setDistance("Kitchener", "St. Catherines", -105);
        setDistance("Kitchener", "Peterborough", -200);
        setDistance("St. Catherines", "Barrie", -140);
        setDistance("Hamilton", "Peterborough", -175);
        setDistance("St. Catherines", "Peterborough", -147);

        computeConnections();
        //printDistanceTable();
    }

    static void setDistance(String cty1, String cty2, int d)
    {
        int c1 = cityIndex(cty1);
        int c2 = cityIndex(cty2);
        //System.out.println(cty1 + " to " + cty2 + "[" + c1 + ", " + c2 + "] = " + d);
        distanceTable[c1][c2] = d;
        distanceTable[c2][c1] = d;
    }

    static public void printDistanceTable()
    {
        NumberFormat df = new DecimalFormat("####");
        df.setMinimumIntegerDigits(5);

        for(int i = 0; i < maxIndex; i++)
        {
            System.out.print(cityArray[i].name);
            int nb = 20 - cityArray[i].name.length();
            for(int  j = 0; j < nb; j++) System.out.print(" ");
            for(int j = 0; j < maxIndex; j++)
            {
                System.out.print( " " + df.format(distanceTable[i][j]));
            }
            System.out.println();
        }
    }

}
