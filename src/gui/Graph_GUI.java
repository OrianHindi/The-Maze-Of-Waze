package gui;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import elements.Fruit;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Graph_GUI extends Thread {
    public static DGraph graph= new DGraph();
    public static Graph_Algo graph_algo= new Graph_Algo();
    private static Range xRange= new Range(0,0);
    private static Range yRange= new Range(0,0);
    private int ModeCount=0;


    public Graph_GUI (){
        this.openWindow();
    }
    public Graph_GUI(DGraph graph){
        StdDraw.clear();
        this.graph = graph;
        graph_algo.init(graph);
        StdDraw.g=this;
        this.openCanvas();
        ModeCount=graph.getMC();
        this.start();
    }

    public node_data findNode(double x, double y){
        Collection<node_data> temp = graph.getV();
        for (node_data node: temp) {
            if(x>=node.getLocation().x()-0.4 && x<= node.getLocation().x()+0.4 && y>=node.getLocation().y()-0.4 && y<=node.getLocation().y()+0.4) return node;
        }
        return null;
    }


    public Range findRangeX(){
        if(graph.nodeSize()!=0) {
            double min = Integer.MAX_VALUE;
            double max = Integer.MIN_VALUE;
            Collection<node_data> V = graph.getV();
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
        if(graph.nodeSize()!=0) {
            double min = Integer.MAX_VALUE;
            double max = Integer.MIN_VALUE;
            Collection<node_data> V = graph.getV();
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
    public void openCanvas(){
        StdDraw.setCanvasSize(1024,512);
        Range x = findRangeX();
        Range y = findRangeY();
        System.out.println(x.get_min() + "," + x.get_max());
        System.out.println(y.get_min() + "," + y.get_max());
        StdDraw.setXscale(x.get_min()-0.002,x.get_max()+0.002);
        StdDraw.setYscale(y.get_min()-0.002,y.get_max()+0.002);
        StdDraw.enableDoubleBuffering();
        printGraph();

    }
    public void openWindow(){
        StdDraw.setCanvasSize(1024,512);
        StdDraw.clear(Color.BLUE);
        StdDraw.setYscale(-51,50);
        StdDraw.setXscale(-51,50);
        StdDraw.picture(0,0,"Maze.png");
    }
    /**
     * this function prints the graph
     *
     */
    public void printGraph(){

        double rightScaleX = ((xRange.get_max()-xRange.get_min())*0.04);
        double rightScaleY =  ((yRange.get_max()-yRange.get_min())*0.04);
        StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.15);
        DGraph d = this.graph;
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

    public List<node_data> TSP(List<Integer> targets){
        graph_algo.init(graph);
        return graph_algo.TSP(targets);
    }
    public void setGraph(DGraph d){
        graph=d;
    }
}
