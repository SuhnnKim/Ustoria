/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ShortestPath;

/**
 *
 * @author R. N. Robson
 */
public class AstarAlgorithm
    extends AbstractAlgorithm {

    public AstarAlgorithm(DemoPanel dp)
    {
        super(dp);
    }

    public int heuristic(String dest)
    {
        int dist = City.distanceBetween(currCity, dest);
        dist = (dist < 0)? (-dist): dist;
        int dist1 = City.distanceBetween(dest, endCity);
        dist1 = (dist1 < 0)? (-dist1): dist1;
        return dist + dist1;
    }

    public String getDescription()
    {
        String result = "The A* algorithm selects the next node in the path";
        result += " by picking the city with the minimum straight-line distance ";
        result += " to the final city plus the minimum distance from the current city.";
        return result;
    }

    public String toString()
    {
        return "A*";
    }

}
