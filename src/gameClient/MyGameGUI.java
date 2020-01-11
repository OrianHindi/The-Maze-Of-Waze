package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import elements.Fruit;
import elements.Robot;
import gui.Graph_GUI;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import java.util.*;
import java.util.List;

public class MyGameGUI implements Runnable {
    private Graph_GUI Ggui;
    private DGraph Ggraph;
    private ArrayList<Robot> players;
    private ArrayList<Fruit> foodss ;
    private Fruit toAddFruit= new Fruit();
    private Robot toaddRobot= new Robot();
    private game_service game1;
    private Thread t1;


    public MyGameGUI() {
    //    StdDraw.mgg = this;
    }

    public void startGame(int senario){
        game_service game = Game_Server.getServer(senario); // you have [0,23] games
        this.game1=game;
        String g = game.getGraph();
        DGraph gg = new DGraph();
        gg.init(g);
        this.Ggraph=gg;
        this.Ggui = new Graph_GUI(this.Ggraph);
        String info = game.toString();
        List<String> fruits = this.game1.getFruits();
        this.foodss= new ArrayList<>(fruits.size());
        System.out.println(fruits.size());
        for (String fruit:fruits) {
            System.out.println(fruit);
            this.foodss.add(toAddFruit.initFromJson(fruit));
        }
        ArrayList<Fruit> copied = toAddFruit.copy(this.foodss);
        int numRobs=0;
        try {
            JSONObject game_info = new JSONObject(info);
            JSONObject robots = game_info.getJSONObject("GameServer");
            numRobs = robots.getInt("robots");
            this.players= new ArrayList<>(numRobs);
        }catch(Exception e){e.printStackTrace();}
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
        List<String> robList = game.getRobots();
        int indexForPic=0;
        for (String rob:robList) {
            this.players.add(toaddRobot.initFromJson(rob,indexForPic++));
        }
        game.startGame();
        while(game.isRunning()){
            updateFruits();
            updateRobots();
            moveRobots(this.game1,this.Ggraph);
            this.Ggui.printGraph();
            toaddRobot.printRobots(this.players);
            toAddFruit.printFruit(this.foodss);
            StdDraw.show();
        }
        System.out.println("game is over");
    }

    public  void moveRobots(game_service game,DGraph g){
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
                    System.out.println("first dest is:" +dest);

                    if(dest == -1){
                        dest=toaddRobot.getNextNode(g,this.foodss);
                        System.out.println("dest is:" + dest);
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

    public static void main(String[] args) {
        MyGameGUI p = new MyGameGUI();
        p.startGame(2);

    }


    @Override
    public void run() {

    }

}
//
//    public void MyGameGUI(int GameSenario){
//        game_service game = Game_Server.getServer(GameSenario);
//        String stringGraph = game.getGraph();
//        this.Ggraph=new DGraph();
//        this.Ggraph.init(stringGraph);
//        this.Ggui= new Graph_GUI(this.Ggraph);
//        String info = game.toString();
//
//        System.out.println(info);
//        JSONObject infoGame;
//        try{
//            infoGame= new JSONObject(info);
//            JSONObject ROBOTS = infoGame.getJSONObject("GameServer");
//            int numrobs = ROBOTS.getInt("robots");
//            int numfruits = ROBOTS.getInt("fruits");
//            this.food= new ArrayList<>(numfruits);
//            this.players= new ArrayList<>(numrobs);
//            for (int i = 0; i <numrobs ; i++) {
//                game.addRobot(i);
//            }
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//        List<String> foods = game.getFruits();
//        for (String food:foods) {
//            try {
//                JSONObject fruitt = new JSONObject(food);
//                JSONObject fruittt= fruitt.getJSONObject("Fruit");
//                double value = fruittt.getDouble("value");
//                String pos =fruittt.getString("pos");
//                int type = fruittt.getInt("type");
//                Point3D ps = new Point3D(pos);
//                StdDraw.picture(ps.x(),ps.y(),"coin.png",0.001,0.001);
//            }
//            catch(Exception pe){
//
//            }
//        }
//
//
//        List<String> robots = game.getRobots();
//        int number=1;
//        for (String robot:robots) {
//            try {
//                System.out.println(robot);
//                JSONObject temp = new JSONObject(robot);
//                JSONObject robotPlace =temp.getJSONObject("Robot");
//                String locatiion = robotPlace.getString("pos");
//                int id = robotPlace.getInt("id");
//                Point3D rp = new Point3D(locatiion);
//                this.players.add(new Player(id,rp,"Robot"+ number + ".png"));
//                System.out.println(rp);
//                StdDraw.picture(rp.x(),rp.y(),"Robot1.png",0.001,0.001);
//                StdDraw.picture(rp.x()+0.005,rp.y()+0.005,"Audi.png",0.001,0.001);
//
//            }
//            catch (Exception p){
//
//            }
//        }
//
//        for (Player player:this.players) {
//            int id= player.getID();
//            Point3D pos = player.getLocation();
//            System.out.println("id is:" + id + "pos is: " + pos.toString());
//        }
//
//
//    }


//bboaz code


//    public void test1() {
//        int scenario_num = 4;
//        game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
//        this.game1=game;
//        String g = game.getGraph();
//        DGraph gg = new DGraph();
//        gg.init(g);
//        this.Ggraph=gg;
//        this.Ggui = new Graph_GUI(gg);
//        String info = game.toString();
//        JSONObject line;
//        try {
//            line = new JSONObject(info);
//            JSONObject ttt = line.getJSONObject("GameServer");
//            int rs = ttt.getInt("robots");
//            System.out.println(info);
//            System.out.println(g);
//            int src_node = 0;  // arbitrary node, you should start at one of the fruits
//            for(int a = 0;a<rs;a++) {
//                game.addRobot(src_node+a);
//            }
//        }
//        catch (JSONException e) {e.printStackTrace();}
//        game.startGame();
//        int check =0;
//        while (game.isRunning()){
//            this.Ggui.printGraph();
//            moveRobots(game,gg);
//            if(check==0){
//                this.t1 = new Thread(this);
//                this.t1.start();
//                check++;
//            }
//        }
//
//        String results = game.toString();
//        System.out.println("Game Over: "+results);
//    }
//    /**
//     * Moves each of the robots along the edge,
//     * in case the robot is on a node the next destination (next edge) is chosen (randomly).
//     * @param game
//     * @param gg
//    //* @param log
//     */
//    private static void moveRobots(game_service game, DGraph gg) {
//        List<String> log = game.move();
//        //   System.out.println("size of robots" +log.size());
//        if(log!=null) {
//            long t = game.timeToEnd();
//            for(int i=0;i<log.size();i++) {
//                String robot_json = log.get(i);
//                try {
//                    JSONObject line = new JSONObject(robot_json);
//                    JSONObject ttt = line.getJSONObject("Robot");
//                    int rid = ttt.getInt("id");
//                    int src = ttt.getInt("src");
//                    int dest = ttt.getInt("dest");
//                    String ps = ttt.getString("pos");
//                    Point3D pp = new Point3D(ps);
//                    StdDraw.picture(pp.x(),pp.y(),"Robot1.png",0.001,0.001);
//                    StdDraw.show();
//
//                    if(dest==-1) {
//                        dest = nextNode(gg, src);
//                        game.chooseNextEdge(rid, dest);
////                        System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
////                        System.out.println(ttt);
//                    }
//
//                }
//                catch (JSONException e) {e.printStackTrace();}
//            }
//        }
//    }
//    /**
//     * a very simple random walk implementation!
//     * @param g
//     * @param src
//     * @return
//     */
//    private static int nextNode(DGraph g, int src) {
//        int ans = -1;
//        Collection<edge_data> ee = g.getE(src);
//        Iterator<edge_data> itr = ee.iterator();
//        int s = ee.size();
//        int r = (int)(Math.random()*s);
//        int i=0;
//        while(i<r) {itr.next();i++;}
//        ans = itr.next().getDest();
//        return ans;
//    }


//thread


//while (game1.isRunning()) {
//        List<String> foods = game1.getFruits();
//        if (foods != null) {
//            System.out.println("num of fruits" + foods.size());
//            for (String food : foods) {
//                try {
//                    JSONObject fruitt = new JSONObject(food);
//                    JSONObject fruittt = fruitt.getJSONObject("Fruit");
//                    double value = fruittt.getDouble("value");
//                    String pos = fruittt.getString("pos");
//                    int type = fruittt.getInt("type");
//                    Point3D ps = new Point3D(pos);
//                    System.out.println("fruit pos" + pos);
//                    StdDraw.picture(ps.x(), ps.y(), "coin.png", 0.001, 0.001);
//
//                } catch (Exception pe) {
//
//                }
//            }
//            try {
//                t1.wait(300);
//            } catch (Exception psa) {
//
//            }
//        }
//    }
//
//}