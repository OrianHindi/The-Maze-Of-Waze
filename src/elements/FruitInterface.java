package elements;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

import java.util.ArrayList;
import java.util.List;

public interface FruitInterface {


    /**
     * this function get a string and build a Fruit object.
     * @return the fruit that have benn built.
     */
    public Fruit initFromJson(String JsonString);

    /**
     * this function build arraylist of fruits from strings
     * @param arr the list of strings that represent the fruits.
     * @return the arraylist that have benn built.
     */
    public ArrayList<Fruit> fillFruitList(List<String> arr);

    /**
     * this function return where we should go with the robot to eat the fruits.
     * @return
     */
    public edge_data getFruitEdge(graph g, Fruit f);

}
