package elements;

import algorithms.Graph_Algo;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gui.Graph_GUI;
import javafx.print.Collation;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Fruit implements FruitInterface {
    private Point3D pos;
    private String img;
    private double value;
    private int type;
    private int tag;
    private static double EPSILON = 0.0000001;

    public Fruit(){
        this.value=0;
        this.img=img;
        this.pos=null;
        this.value=0;
        this.type=0;
        this.tag=0;
    }
    public Fruit(Fruit c){
        this.type=c.type;
        this.pos=new Point3D(c.pos);
        this.img=c.img;
        this.value=c.value;
    }

    public void update(String str){
        try {
            JSONObject fruit = new JSONObject(str);
            JSONObject fruitt = fruit.getJSONObject("Fruit");
            this.type = fruitt.getInt("type");
            this.value = fruitt.getDouble("value");
            String pos = fruitt.getString("pos");
            this.pos = new Point3D(pos);
        }catch (Exception e){e.printStackTrace();}

    }


    @Override
    public Fruit initFromJson(String str) {
        Fruit temp = new Fruit();
        try{
            JSONObject fruit = new JSONObject(str);
            JSONObject fruitt = fruit.getJSONObject("Fruit");
            temp.type= fruitt.getInt("type");
            temp.value= fruitt.getDouble("value");
            String pos = fruitt.getString("pos");
            temp.pos= new Point3D(pos);
            if(temp.type== -1) temp.img="coin.png";
            else temp.img= "coin.png";
        }catch (Exception e){ e.printStackTrace();}
        return temp;
    }

    public ArrayList<Fruit> fillFruitList(List<String> arr){
        ArrayList<Fruit> temp = new ArrayList<>();
        for (String fruit:arr) {
            temp.add(initFromJson(fruit));
        }
        return temp;
    }
    @Override
    public void printFruit(List<Fruit> fruitArr) {
        for (Fruit fruit: fruitArr) {
            StdDraw.picture(fruit.pos.x(),fruit.pos.y(),fruit.img,0.001,0.001);
        }

    }

    /**
     * this function return which node the robot should move, to the closest fruit.
     * @param g the graph we working on
     * @param fruit the fruit that we want to check.
     * @return the node id we need go to.
     */
    public edge_data getFruitEdge(graph g, Fruit fruit) {
        Collection<node_data> gNodes = g.getV();
        for(node_data node: gNodes){
            Collection<edge_data> edges =g.getE(node.getKey());
            if(edges!=null){
                for (edge_data edge:edges) {
                    if(isOnEdge(g,edge,fruit)){
                        System.out.println("edge is:" + edge);
                        return edge;
                    }
                }
            }
        }
        return null;
    }

    /**
     * this function check if the fruit on specific edge.
     * @param g the graph we are workin on
     * @param edge the edge we want to check if the fruit on it.
     * @param f the fruit
     * @return yes if the fruit on the edge false if not.
     */
    private boolean isOnEdge(graph g,edge_data edge,Fruit f){
        node_data src = g.getNode(edge.getSrc());
        node_data dst = g.getNode(edge.getDest());
        double first =Math.sqrt(Math.pow(src.getLocation().x()-f.pos.x(),2)+Math.pow(src.getLocation().y()-f.pos.y(),2));
        double second = Math.sqrt(Math.pow(dst.getLocation().x()-f.pos.x(),2)+Math.pow(dst.getLocation().y()-f.pos.y(),2));
        double third = Math.sqrt(Math.pow(src.getLocation().x()-dst.getLocation().x(),2)+Math.pow(src.getLocation().y()-dst.getLocation().y(),2));
        if (first + second > third -EPSILON && first+second<third+EPSILON) return true;
        return false;
    }
    public int getType(){
        return this.type;
    }
    public double getValue(){
        return  this.value;
    }

    public ArrayList<Fruit> copy(List<Fruit> copy){
        ArrayList<Fruit> ans = new ArrayList<>(copy.size());
        for (Fruit fruit:copy) {
            ans.add(new Fruit(fruit));

        }
        return ans;
    }
    public void setTag(int t){
        this.tag=t;
    }
    public int getTag(){
        return this.tag;
    }

}
