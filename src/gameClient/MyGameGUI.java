package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import gui.Graph_GUI;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyGameGUI {
    private Graph_GUI Ggui;
    private DGraph Ggraph;


    public MyGameGUI(int GameSenario){
        game_service game = Game_Server.getServer(GameSenario);
        String stringGraph = game.getGraph();
        this.Ggraph=new DGraph();
        this.Ggraph.init(stringGraph);
        this.Ggui= new Graph_GUI(this.Ggraph);
        String info = game.toString();
        JSONObject infoGame;
        try{
            infoGame= new JSONObject(info);
            JSONObject ROBOTS = infoGame.getJSONObject("GameServer");
            int numrobs = ROBOTS.getInt("robots");
        }
        catch(Exception e){

        }

    }

    public static void main(String[] args) {
        MyGameGUI p = new MyGameGUI(8);
    }




}
