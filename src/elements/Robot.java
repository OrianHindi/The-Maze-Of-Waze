package elements;

import algorithms.Graph_Algo;
import dataStructure.edge_data;
import dataStructure.graph;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import java.util.List;

public class Robot implements RobotInterface {
    private int ID;
    private Point3D pos;
    private String img;
    private double Rank;
    private int src;
    private int dest;


    public Robot(){
        this.ID=0;
        this.pos=null;
        this.img="";
        this.Rank=0;
        this.src=0;
        this.dest=0;

    }


    public void update(String str){
        try {
            JSONObject robot = new JSONObject(str);
            JSONObject robott = robot.getJSONObject("Robot");
            this.ID = robott.getInt("id");
            this.src = robott.getInt("src");
            this.dest = robott.getInt("dest");
            String pos = robott.getString("pos");
            this.pos = new Point3D(pos);
        }catch (Exception e){e.printStackTrace();}

    }
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

    @Override
    public void printRobots(List<Robot> RobotArr) {
        for (Robot robot: RobotArr) {
            StdDraw.picture(robot.pos.x(),robot.pos.y(),robot.img,0.001,0.001);
        }

    }


    public int getNextNode(graph g, List<Fruit> arr ) {
        Graph_Algo p = new Graph_Algo(g);
        double distRsrcToDest = -1;
        int rSrc = this.src;
        int edgeDest = -1;
        int edgeSrc = -1;
        int ans2 = -1;
        Fruit temp = null;
        edge_data check = null;
        edge_data check2 = null;
        int nodeid = -1;
        double min = Integer.MAX_VALUE;
        for (Fruit fruit : arr) {
            check = fruit.getFruitEdge(g, fruit);
            edgeDest = check.getDest();
            edgeSrc = check.getSrc();
            if (fruit.getType() == -1) {
                ans2 = Math.max(edgeDest, edgeSrc);
                distRsrcToDest = p.shortestPathDist(rSrc, ans2);
                if (distRsrcToDest == 0) {
                    if (ans2 == edgeDest) nodeid = edgeSrc;
                    nodeid = edgeDest;
                    check2 = check;
                    temp = fruit;
                }
                if (distRsrcToDest < min) {
                    min = distRsrcToDest;
                    nodeid = ans2;
                    check2 = check;
                    temp = fruit;
                }
            } else if (fruit.getType() == 1) {
                ans2 = Math.min(edgeDest, edgeSrc);
                distRsrcToDest = p.shortestPathDist(rSrc, ans2);
                if (distRsrcToDest == 0) {
                    if (ans2 == edgeSrc) nodeid = edgeDest;
                    else nodeid = edgeSrc;
                    check2 = check;
                    temp = fruit;
                }
                if (distRsrcToDest < min) {
                    min = distRsrcToDest;
                    nodeid = ans2;
                    check2 = check;
                    temp = fruit;
                }
            }
        }
        edgeDest = check2.getDest();
        edgeSrc = check2.getSrc();

        if (temp.getType() == -1) {
            ans2 = Math.max(edgeDest, edgeSrc);
            if (p.shortestPathDist(nodeid, ans2) == 0 && ans2 == edgeDest) return edgeSrc;
            else return edgeDest;
        }
        if (temp.getType() == 1) {
            ans2 = Math.min(edgeDest, edgeSrc);
            if (p.shortestPathDist(nodeid, ans2) == 0 && ans2 == edgeDest) return edgeSrc;
            else return edgeDest;
        }
        return nodeid;
    }
    public String getImg(){
        return this.img;
    }
    public Point3D getPos(){
        return this.pos;
    }

}
