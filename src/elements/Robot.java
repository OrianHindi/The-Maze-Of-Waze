package elements;

import algorithms.Graph_Algo;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.MyGameGUI;
import gui.Graph_GUI;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Robot implements RobotInterface {
    private int ID;
    private Point3D pos;
    private String img;
    private double Rank;
    private int src;
    private int dest;


    /**
     * Default constractor.
     */
    public Robot(){
        this.ID=0;
        this.pos=null;
        this.img="";
        this.Rank=0;
        this.src=0;
        this.dest=0;

    }

    /**
     * this function update a robot from a String that have been sent from server.
     * @param str the JSON string that represent the changes in the robot.
     */
    public void update(String str){
        System.out.println("ROBOTS JSON IS = " + str);
        try {
            JSONObject robot = new JSONObject(str);
            JSONObject robott = robot.getJSONObject("Robot");
            this.ID = robott.getInt("id");
            this.src = robott.getInt("src");
            this.dest = robott.getInt("dest");
            String pos = robott.getString("pos");
            this.pos = new Point3D(pos);
            this.Rank= robott.getDouble("value");
        }catch (Exception e){e.printStackTrace();}

    }
    /**
     * this function intiliaze a robot from a JSON string.
     * @param str the JSON string that represent a robot.
     * @param i an index for the robot img.
     * @return the robot that have been built from the string.
     */
    public Robot initFromJson(String str,int i) {
        Robot temp = new Robot();
        try{
            JSONObject robot = new JSONObject(str);
            JSONObject robott =robot.getJSONObject("Robot");
            temp.ID=robott.getInt("id");
            temp.src=robott.getInt("src");
            temp.dest=robott.getInt("dest");
            String pos = robott.getString("pos");
            temp.pos= new Point3D(pos);
            temp.img="Robot" + i +".png";
        }catch (Exception e){e.printStackTrace();}
        return temp;
    }

    /**
     * this function built an arraylist of robots from list of json strings.
     * @param arr the list of strings that represent the robots.
     * @return the arraylist that have been built from arr.
     */
    public ArrayList<Robot> fillRobotList(List<String> arr){
        ArrayList<Robot> temp = new ArrayList<>();
        int indexForPic=0;
        for (String rob:arr) {
            temp.add(initFromJson(rob,indexForPic++));
        }
        return temp;
    }

    /**
     * Setters && Getters.
     */

    public String getImg(){
        return this.img;
    }
    public Point3D getPos(){
        return this.pos;
    }
    public int getSrc(){
        return this.src;
    }
    public int getDest(){return this.dest;}
    public double getValue(){
        return this.Rank;
    }
    public int getId(){return this.ID;}
    public void setId(int id){this.ID=id;}
    public void setImg(String p){this.img=p;}
    public void setSrc(int i){this.src=i;}
    public void setPos(Point3D p){this.pos=p;}
    public void setValue(double val){this.Rank=val;}

}
