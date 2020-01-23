package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import elements.Fruit;
import elements.Robot;
import org.json.JSONObject;
import utils.StdDraw;

import java.awt.*;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MyGameAlgo extends  Thread {
    private MyGameGUI MyGG;
    private KML_Logger kml = new KML_Logger();
    private Thread r;


    /**
     * this function start the automatic game.
     *
     * @param senario the scenario the client choosed.
     */
    public void startGame(int senario) {
        game_service game = Game_Server.getServer(senario); // you have [0,23] games
        this.MyGG.setGame1(game);
        String g = game.getGraph();
        DGraph d = new DGraph();
        d.init(g);
        this.MyGG.setGgraph(d); // set our Graph to the graph that have been built from JSON that represent the graph of scenario.
        this.MyGG.findRange();  // get the Scale of the graph;
        String info = game.toString();
        List<String> fruits = this.MyGG.getGame1().getFruits();
        this.MyGG.setFoodss(this.MyGG.getToAddFruit().fillFruitList(fruits));
        ;  //init the Fruits to our ArrayList fruit.


        ArrayList<Fruit> copied = this.MyGG.getToAddFruit().copy(this.MyGG.getFoodss());
        int numRobs = MyGG.numOfRobs(info);
        placeRobots(numRobs, copied);  //place the robots in the server.

        List<String> robList = game.getRobots();
        this.MyGG.setPlayers(this.MyGG.getToaddRobot().fillRobotList(robList)); //init the Robots to our Arraylist robots.
        this.MyGG.getGame1().startGame();
        //  ThreadMove();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    kml.makeKML();
                } catch (ParseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        this.start();


    }

    /**
     * the thread the in charge to do all this each 10 milisec.
     * update fruit and robots from the server move all the robots and then print all the changes.
     */
    public void run() {
        while (this.MyGG.getGame1().isRunning()) {
            this.MyGG.getGame1().move();
            this.MyGG.updateFruits();
            this.MyGG.updateRobots();
            moveRobots(this.MyGG.getGame1(), this.MyGG.getGgraph());
            this.MyGG.printGraph();
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.text(MyGG.getxRange().get_max(), MyGG.getyRange().get_max() + 0.0015, "Time to End: " + MyGG.getGame1().timeToEnd() / 1000);
            int score = 0;
            try {
                String info = MyGG.getGame1().toString();
                JSONObject p = new JSONObject(info);
                JSONObject pp = p.getJSONObject("GameServer");
                score = pp.getInt("grade");
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            StdDraw.text(MyGG.getxRange().get_min(), MyGG.getyRange().get_max() + 0.0015, "Score:" + score);
            this.MyGG.printRobots(this.MyGG.getPlayers());
            this.MyGG.printFruit(this.MyGG.getFoodss());
            StdDraw.show();
            try {
                sleep(sleepTime(this.MyGG.getGgraph(),this.MyGG.getFoodss(),this.MyGG.getPlayers()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        System.out.println("game is over" + this.MyGG.getGame1().toString());

    }

    /**
     * this function place the robot on the node that have been choosen by the fruit places.
     *
     * @param numRobs the amout of robots.
     * @param copied  the list of the fruits that we want to put robot near each fruit.
     */
    public void placeRobots(int numRobs, ArrayList<Fruit> copied) {
        for (int i = 0; i < numRobs; i++) {
            edge_data p = this.MyGG.getToAddFruit().getFruitEdge(this.MyGG.getGgraph(), copied.get(0));
            if (copied.get(0).getType() == -1) {
                this.MyGG.getGame1().addRobot(Math.max(p.getDest(), p.getSrc()));
            } else if (copied.get(0).getType() == 1) {
                this.MyGG.getGame1().addRobot(Math.min(p.getDest(), p.getSrc()));
            }
            copied.remove(0);
        }
    }

    /**
     * this function move the robots automaticlly.
     *
     * @param game the game that the client have benn chosen.
     * @param g    the graph that we get from the server .
     */
    public void moveRobots(game_service game, DGraph g) {
        List<String> log = game.getRobots();
        if (log != null) {
            ArrayList<Fruit> copied = this.MyGG.getToAddFruit().copy(this.MyGG.getFoodss());
            long t = game.timeToEnd();
            for (int i = 0; i < log.size(); i++) {
                String robot_json = log.get(i);
                try {
                    JSONObject line = new JSONObject(robot_json);
                    JSONObject tomove = line.getJSONObject("Robot");
                    int robID = tomove.getInt("id");
                    int src = tomove.getInt("src");
                    int dest = tomove.getInt("dest");
                    int nodetoGO = getNextNode(this.MyGG.getPlayers().get(i), g, copied);


                    if (dest == -1) {
                        dest = nodetoGO;
                        game.chooseNextEdge(robID, dest);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }


    public void setMyGG(MyGameGUI p) {
        this.MyGG = p;
    }

    /**
     * this function return where should the robot move next.
     * if automatic by algorithms or by client wish.
     * @return the node key the we should move to.
     */
    public int getNextNode(Robot r , graph g, List<Fruit> arr ) {
        Graph_Algo p = new Graph_Algo(g);
        edge_data temp = null;
        double min = Integer.MAX_VALUE;
        double disFromRob = 0;
        int whereTo=-1;
        int finalWhereTo =-1;
        for (Fruit fruit: arr) {
            if (fruit.getTag() == 0) {
                temp = fruit.getFruitEdge(g, fruit);
                if (fruit.getType() == -1) {
                    if (temp.getDest() > temp.getSrc()) {
                        disFromRob = p.shortestPathDist(r.getSrc(), temp.getDest());
                        whereTo = temp.getSrc();
                    } else if (temp.getSrc() > temp.getDest()) {
                        disFromRob = p.shortestPathDist(r.getSrc(), temp.getSrc());
                        whereTo = temp.getDest();
                    }
                    if(r.getSrc()==temp.getSrc()) {
                        fruit.setTag(1);
                        return temp.getDest();
                    }
                    if(r.getSrc()==temp.getDest()) {
                        fruit.setTag(1);
                        return temp.getSrc();
                    }
                    if (disFromRob < min) {
                        min = disFromRob;
                        finalWhereTo = whereTo;
                    }

                } else if (fruit.getType() == 1) {
                    if (temp.getDest() < temp.getSrc()) {
                        disFromRob = p.shortestPathDist(r.getSrc(), temp.getDest());
                        whereTo = temp.getDest();
                    } else if (temp.getSrc() < temp.getDest()) {
                        disFromRob = p.shortestPathDist(r.getSrc(), temp.getSrc());
                        whereTo = temp.getSrc();
                    }
                    if(r.getSrc()==temp.getSrc()) {
                        fruit.setTag(1);
                        return temp.getDest();
                    }
                    if(r.getSrc()==temp.getDest()) {
                        fruit.setTag(1);
                        return temp.getSrc();
                    }
                    if (disFromRob < min) {
                        min = disFromRob;
                        finalWhereTo = whereTo;
                    }

                }

            }

        }

        List<node_data> ans = p.shortestPath(r.getSrc(), finalWhereTo);
        for (Fruit fruit: arr) {
            temp = fruit.getFruitEdge(g,fruit);
            if(temp.getDest()==finalWhereTo || temp.getSrc()==finalWhereTo){
                fruit.setTag(1);
                break;
            }
        }
        if (ans.size() == 1) {
            List<node_data> ans2 = p.shortestPath(r.getSrc(), (finalWhereTo + 15) % 11);

            return ans2.get(1).getKey();
        }
        return ans.get(1).getKey();


    }

    private int sleepTime(graph g,ArrayList<Fruit> arrF,ArrayList<Robot> arrR){
        int ans =90;
        for (Robot rob: arrR) {
            for (Fruit fruit: arrF) {
                edge_data temp = fruit.getFruitEdge(g,fruit);
                if(temp.getSrc()==rob.getSrc() || temp.getDest()==rob.getSrc()){
                    return 45;
                }
            }
        }
        return ans;
    }

}





