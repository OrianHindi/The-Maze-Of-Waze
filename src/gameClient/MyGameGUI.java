package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import elements.Fruit;
import elements.Robot;
import gui.Graph_GUI;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MyGameGUI extends Thread {
    private DGraph Ggraph;
    private ArrayList<Robot> players;
    private ArrayList<Fruit> foodss ;
    private Fruit toAddFruit= new Fruit();
    private Robot toaddRobot= new Robot();
    private game_service game1;
    private static Range xRange= new Range(0,0);
    private static Range yRange= new Range(0,0);



    public MyGameGUI() {
        StdDraw.mgg=this;
        openWindow();
    }

    public MyGameGUI (int x){
        StdDraw.mgg=this;

    }


    private void placeRobots(int numRobs, ArrayList<Fruit> copied) {
        for (int i = 0; i <numRobs; i++) {
            edge_data p = toAddFruit.getFruitEdge(this.Ggraph,copied.get(0));
            if(copied.get(0).getType()==-1){
                this.game1.addRobot(Math.max(p.getDest(),p.getSrc()));
            }
            else if(copied.get(0).getType()==1){
                this.game1.addRobot(Math.min(p.getDest(),p.getSrc()));
            }
            copied.remove(0);
        }
    }

    public void moveRobots(game_service game,DGraph g){
        List<String> log = game.move();
        if(log!=null){
            long t = game.timeToEnd();
            for (int i = 0; i <log.size() ; i++) {
                String robot_json = log.get(i);
                try{
                    JSONObject line = new JSONObject(robot_json);
                    JSONObject tomove = line.getJSONObject("Robot");
                    int robID = tomove.getInt("id");
                    int src = tomove.getInt("src");
                    int dest = tomove.getInt("dest");


                    if(dest == -1){
                        dest=this.players.get(i).getNextNode(g,this.foodss);
                        game.chooseNextEdge(robID,dest);
                    }

                }catch(Exception e){e.printStackTrace();}

            }
        }
    }

    public void updateRobots(){
        List<String> robots = this.game1.getRobots();
        if(robots!=null){
            for (int i = 0; i <this.players.size() ; i++) {
                this.players.get(i).update(robots.get(i));
            }
        }
    }
    public void updateFruits() {
        List<String> foods = this.game1.getFruits();
        if (foods != null) {
            for (int i = 0; i <this.foodss.size() ; i++) {
                this.foodss.get(i).update(foods.get(i));
            }
        }
    }
    public void finishGame(){
        StdDraw.setCanvasSize(1024,512);
        StdDraw.clear(Color.BLUE);
        StdDraw.setYscale(-51,50);
        StdDraw.setXscale(-51,50);
        StdDraw.picture(0,0,"Maze.png");
        StdDraw.show();
    }
    private void placeRobots_Manual(int[] arr) {
        for (int i = 0; i <arr.length ; i++) {
            this.game1.addRobot(arr[i]);
        }
    }





    @Override
    public void run() {
        while(this.game1.isRunning()){
            updateFruits();
            updateRobots();
            moveRobots(this.game1,this.Ggraph);
            this.printGraph();
            printRobots(this.players);
            printFruit(this.foodss);
            StdDraw.show();
            try{
                sleep(10);
            }catch (Exception e){e.printStackTrace();}

        }
        System.out.println("game is over" + this.game1.toString());

    }
    public Range findRangeX(){
        if(this.Ggraph.nodeSize()!=0) {
            double min = Integer.MAX_VALUE;
            double max = Integer.MIN_VALUE;
            Collection<node_data> V = this.Ggraph.getV();
            for (node_data node : V) {
                if (node.getLocation().x() > max) max = node.getLocation().x();
                if (node.getLocation().x() < min) min = node.getLocation().x();
            }
            Range ans = new Range(min, max);
            xRange = ans;
            return ans;
        }
        else{
            Range Default = new Range(-100,100);
            xRange=Default;
            return Default;
        }
    }
    public Range findRangeY(){
        if(this.Ggraph.nodeSize()!=0) {
            double min = Integer.MAX_VALUE;
            double max = Integer.MIN_VALUE;
            Collection<node_data> V = this.Ggraph.getV();
            for (node_data node : V) {
                if (node.getLocation().y() > max) max = node.getLocation().y();
                if (node.getLocation().y() < min) min = node.getLocation().y();
            }
            Range ans = new Range(min, max);
            yRange = ans;
            return ans;
        }
        else{
            Range Default = new Range(-100,100);
            yRange=Default;
            return Default;
        }
    }
    /**
     * open the window of the graph printed
     *
     */
    public void findRange(){
        Range x = findRangeX();
        Range y = findRangeY();
        StdDraw.setXscale(x.get_min()-0.002,x.get_max()+0.002);
        StdDraw.setYscale(y.get_min()-0.002,y.get_max()+0.002);

    }
    public void openWindow(){
        StdDraw.setCanvasSize(1024,512);
        StdDraw.clear(Color.BLUE);
        StdDraw.setYscale(-51,50);
        StdDraw.setXscale(-51,50);
        StdDraw.picture(0,0,"Maze.png");
        int senario=0;
        String senarioString = JOptionPane.showInputDialog(null,"Please choose a Game Senario");
        try{
            senario=Integer.parseInt(senarioString);
        }catch(Exception e1){e1.printStackTrace();}
        String[] chooseGame = {"Manually Game","Auto Game"};
        Object selctedGame = JOptionPane.showInputDialog(null,"Choose a Game mode","Message",JOptionPane.INFORMATION_MESSAGE,null,chooseGame,chooseGame[0]);
        if(selctedGame=="Auto Game") {
            StdDraw.clear();
            StdDraw.enableDoubleBuffering();
            startGame(senario);
        }
        else{
            String list = JOptionPane.showInputDialog(null,"Please enter Nodes for robots.","Shape of x,y,z,w",1);
            String[] arr = list.split(",");
            int[] keys= new int[arr.length];
            StdDraw.clear();
            StdDraw.enableDoubleBuffering();


        }



    }

    /**
     * this function prints the graph
     *
     */
    public void printGraph(){
        StdDraw.clear();
        double rightScaleX = ((xRange.get_max()-xRange.get_min())*0.04);
        double rightScaleY =  ((yRange.get_max()-yRange.get_min())*0.04);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.15);
        DGraph d = this.Ggraph;
        if(d!=null) {
            Iterator it = d.getV().iterator();
            while (it.hasNext()) {
                node_data temp = (node_data)it.next();
                Point3D p = temp.getLocation();
                StdDraw.filledCircle(p.x(), p.y(),rightScaleX*0.1);
                StdDraw.text(p.x(), p.y() +rightScaleX*0.3, "" + temp.getKey());
            }
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.003);
            Iterator it1 = d.getV().iterator();
            while(it1.hasNext()){
                node_data temp1 = (node_data)it1.next();
                if(d.getE(temp1.getKey())!=null){
                    Iterator it2 = d.getE(temp1.getKey()).iterator();
                    while(it2.hasNext()){
                        edge_data temp2 = (edge_data)it2.next();
                        if(temp2!=null){
                            StdDraw.setPenRadius(0.003);
                            StdDraw.setPenColor(Color.RED);
                            double weight = Math.round(temp2.getWeight()*100.0)/100.0;
                            node_data srcNode = d.getNode(temp2.getSrc());
                            node_data dstNode = d.getNode(temp2.getDest());
                            Point3D srcP = srcNode.getLocation();
                            Point3D dstP = dstNode.getLocation();
                            StdDraw.line(srcP.x(), srcP.y(), dstP.x(), dstP.y());

                            double x = 0.2*srcP.x()+0.8*dstP.x();
                            double y = 0.2*srcP.y() + 0.8*dstP.y();
                            StdDraw.setPenColor(Color.BLACK);
                            StdDraw.text(x,y, "" +weight);

                            StdDraw.setPenColor(Color.YELLOW);
                            StdDraw.setPenRadius(0.15);
                            double x1 = 0.1*srcP.x()+0.9*dstP.x();
                            double y1 = 0.1*srcP.y()+0.9*dstP.y();
                            StdDraw.filledCircle(x1,y1,rightScaleX*0.1);

                        }
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        MyGameGUI p = new MyGameGUI();
    }

    /**
     * this function print the fruits.
     * @param fruitArr the list of the fruits.
     */
    public void printFruit(List<Fruit> fruitArr) {
        for (Fruit fruit: fruitArr) {
            StdDraw.picture(fruit.getPos().x(),fruit.getPos().y(),fruit.getImg(),0.0009,0.0009);
        }

    }

    /**
     * this function print the robots.
     * @param RobotArr the list of the robots.
     */
    public void printRobots(List<Robot> RobotArr) {
        for (Robot robot: RobotArr) {
            StdDraw.picture(robot.getPos().x(),robot.getPos().y(),robot.getImg(),0.001,0.001);
        }

    }

    /**
     * Setter && Getters
     */
    public game_service getGame1(){return this.game1; }
    public void setGame1(game_service game){this.game1=game;}
    public void setGgraph(DGraph d){this.Ggraph=d;}
    public void setPlayers(ArrayList<Robot> arr){this.players=arr;}
    public void setFoodss(ArrayList<Fruit> arr){this.foodss=arr;}
    public ArrayList<Fruit> getFoodss(){return this.foodss;}
    public ArrayList<Robot> getPlayers(){return this.players;}
    public Robot getToaddRobot(){return this.toaddRobot;}
    public Fruit getToAddFruit(){return this.toAddFruit;}
    public DGraph getGgraph(){return this.Ggraph;}

}

