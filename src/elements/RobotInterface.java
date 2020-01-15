package elements;

import dataStructure.graph;
import utils.Point3D;

import java.util.ArrayList;
import java.util.List;

public interface RobotInterface {
    /**
     * this function built a Robot Object from JSON string.
     * @param str the JSON string that represent a robot.
     * @return new Robot .
     */
    public Robot initFromJson(String str,int i);

    /**
     * this functuin build ArrayList with robots
     * @param arr the list of strings that represent the robots.'
     * @return the arraylist that have benn built.
     */
    public ArrayList<Robot> fillRobotList(List<String> arr);


}
