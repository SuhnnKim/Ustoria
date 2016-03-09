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
public class GreedyAlgorithm 
    extends AbstractAlgorithm {

    public GreedyAlgorithm(DemoPanel dp)
    {
        super(dp);
    }

    public int heuristic(String dest)
    {
        int dist = City.distanceBetween(dest, endCity);
        dist = (dist < 0)? (-dist): dist;
        return dist;
    }

    public String getDescription()
    {
        String result = "The greedy algorithm select the next node in the path";
        result += " by picking the city with the minimum straight-line distance ";
        result += " to the final city.";
        return result;
    }

    public String toString()
    {
        return "Greedy";
    }

}
