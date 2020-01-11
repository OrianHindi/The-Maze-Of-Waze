package elements;

import dataStructure.graph;
import utils.Point3D;

import java.util.List;

public interface RobotInterface {
    /**
     * this function built a Robot Object from JSON string.
     * @param str the JSON string that represent a robot.
     * @return new Robot .
     */
    public Robot initFromJson(String str,int i);

    /**
     * this function print robots.
     */
    public void printRobots(List<Robot> RobotArr);

    /**
     * this function return where should the robot move next.
     * if automatic by algorithms or by client wish.
     * @return the node key the we should move to.
     */
    public int getNextNode(graph g, List<Fruit> arr);

}
