package elements;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

import java.util.List;

public interface FruitInterface {


    /**
     * this function get a string and build a Fruit object.
     * @return the fruit that have benn built.
     */
    public Fruit initFromJson(String JsonString);

    /**
     * this function print all the fruits on the graph.
     */
    public void printFruit(List<Fruit> arr);

    /**
     * this function return where we should go with the robot to eat the fruits.
     * @return
     */
    public edge_data getFruitEdge(graph g, Fruit f);

}
