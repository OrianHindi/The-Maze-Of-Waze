package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import elements.Fruit;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyGameAlgo {
    private MyGameGUI MyGG;


    public void startGame(int senario)  {
        game_service game = Game_Server.getServer(senario); // you have [0,23] games
        this.MyGG.setGame1(game);
        String g = game.getGraph();
        DGraph d = new DGraph();
        d.init(g);
        this.MyGG.setGgraph(d); // set our Graph to the graph that have been built from JSON that represent the graph of scenario.
        this.MyGG.findRange();  // get the Scale of the graph;
        String info = game.toString();
        System.out.println(info);
        List<String> fruits = this.MyGG.getGame1().getFruits();
        this.MyGG.setFoodss(this.MyGG.getToAddFruit().fillFruitList(fruits)); ;  //init the Fruits the our ArrayList fruit.


        ArrayList<Fruit> copied = toAddFruit.copy(this.foodss);
        int numRobs=0;
        try {   //get the number of robots from server.
            JSONObject game_info = new JSONObject(info);
            JSONObject robots = game_info.getJSONObject("GameServer");
            numRobs = robots.getInt("robots");
        }catch(Exception e){e.printStackTrace();}

        placeRobots(numRobs,copied);

        List<String> robList = game.getRobots();
        this.players=toaddRobot.fillRobotList(robList);
        this.game1.startGame();
        this.start();


    }

}
