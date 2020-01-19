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

    /**
     * Defualt constractor.
     */
    public Fruit(){
        this.value=0;
        this.img=img;
        this.pos=null;
        this.value=0;
        this.type=0;
        this.tag=0;
    }

    /**
     * Copy constractor.
     * @param c the fruit we want to copy.
     */
    public Fruit(Fruit c){
        this.type=c.type;
        this.pos=new Point3D(c.pos);
        this.img=c.img;
        this.value=c.value;
    }

    /**
     * this functin update a fruit from a json string that we get from the server.
     * @param str the JSON string that represent the changes of the fruit.
     */
    public void update(String str){
        try {
            JSONObject fruit = new JSONObject(str);
            JSONObject fruitt = fruit.getJSONObject("Fruit");
            this.type = fruitt.getInt("type");
            this.value = fruitt.getDouble("value");
            String pos = fruitt.getString("pos");
            this.pos = new Point3D(pos);
            this.setTag(0);
        }catch (Exception e){e.printStackTrace();}

    }

    /**
     * this function intilaize a fruit from JSON string.
     * @param str the JSON string that represent the fruit.
     * @return the fruit that have been built from str.
     */
    @Override
    public Fruit initFromJson(String str) {
        System.out.println("STRING JSON FRUIT =" + str);
        Fruit temp = new Fruit();
        try{
            JSONObject fruit = new JSONObject(str);
            JSONObject fruitt = fruit.getJSONObject("Fruit");
            temp.type= fruitt.getInt("type");
            temp.value= fruitt.getDouble("value");
            String pos = fruitt.getString("pos");
            temp.pos= new Point3D(pos);
            if(temp.type== -1) temp.img="redStar.png";
            else temp.img= "yellowStar.png";
        }catch (Exception e){ e.printStackTrace();}
        return temp;
    }

    /**
     * this function get list of string and built from this list a list of fruit.
     * @param arr the list of strings that represent the fruits.
     * @return the list of fruit that have been built from arr.
     */
    public ArrayList<Fruit> fillFruitList(List<String> arr){
        ArrayList<Fruit> temp = new ArrayList<>();
        for (String fruit:arr) {
            temp.add(initFromJson(fruit));
        }
        return temp;
    }
    @Override

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

    /**
     * this function get array list and copy this list.
     * @param copy the arraylist we want to copy.
     * @return the copied arraylist.
     */
    public ArrayList<Fruit> copy(List<Fruit> copy){
        ArrayList<Fruit> ans = new ArrayList<>(copy.size());
        for (Fruit fruit:copy) {
            ans.add(new Fruit(fruit));

        }
        return ans;
    }

    /**
     * Setters && Getters.
     */
    public void setTag(int t){
        this.tag=t;
    }
    public int getTag(){
        return this.tag;
    }
    public Point3D getPos(){return this.pos;}
    public String getImg(){return this.img;}
    public int getType(){
        return this.type;
    }
    public double getValue(){
        return  this.value;
    }
    public void setValue(double value){this.value=value;}
    public void setPos(Point3D p){this.pos=p;}
    public void setImg(String img){this.img=img;}
    public void setType(int type){this.type=type;}



}
