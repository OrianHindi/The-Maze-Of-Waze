package Tests;

import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import elements.Fruit;
import elements.Robot;
import org.junit.Test;
import utils.Point3D;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FruitTest {
    static String JSONSTRING ="{\"Fruit\":{\"value\":8,\"type\":-1,\"pos\":\"35.4,32.3,0.0\"}}";


    @Test
    public void setTag() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        Fruit f3 = new Fruit();
        f1.setTag(1);
        f2.setTag(0);
        f3.setTag(0);
        assertEquals(0,f3.getTag());
        assertEquals(0,f2.getTag());
        assertEquals(1,f1.getTag());
    }

    @Test
    public void getTag() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        Fruit f3 = new Fruit();
        f1.setTag(1);
        f2.setTag(0);
        f3.setValue(0);
        assertEquals(0,f3.getTag());
        assertEquals(0,f2.getTag());
        assertEquals(1,f1.getTag());
    }

    @Test
    public void setPos() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        Fruit f3 = new Fruit();
        Point3D p1 = new Point3D(1,2,0);
        Point3D p2 = new Point3D(2,4,0);
        Point3D p3 = new Point3D(5,6,0);
        f1.setPos(p1);
        f2.setPos(p2);
        f3.setPos(p3);
        assertEquals(p3,f3.getPos());
        assertEquals(p2,f2.getPos());
        assertEquals(p1,f1.getPos());
    }

    @Test
    public void getPos() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        Fruit f3 = new Fruit();
        Point3D p1 = new Point3D(1,2,0);
        Point3D p2 = new Point3D(2,4,0);
        Point3D p3 = new Point3D(5,6,0);
        f1.setPos(p1);
        f2.setPos(p2);
        f3.setPos(p3);
        assertEquals(p3,f3.getPos());
        assertEquals(p2,f2.getPos());
        assertEquals(p1,f1.getPos());
    }

    @Test
    public void setImg() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        String img1 = "fruit1";
        String img2 = "Fruit2";
        f1.setImg(img1);
        f2.setImg(img2);;
        assertEquals(img1,f1.getImg());
        assertEquals(img2,f2.getImg());
    }

    @Test
    public void getImg() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        String img1 = "fruit1";
        String img2 = "Fruit2";
        f1.setImg(img1);
        f2.setImg(img2);;
        assertEquals(img1,f1.getImg());
        assertEquals(img2,f2.getImg());
    }

    @Test
    public void setType() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        Fruit f3 = new Fruit();
        f1.setType(1);
        f2.setType(-1);
        f3.setType(1);
        assertEquals(1,f1.getType());
        assertEquals(-1,f2.getType());
        assertEquals(1,f3.getType());
    }

    @Test
    public void getType() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        Fruit f3 = new Fruit();
        f1.setType(1);
        f2.setType(-1);
        f3.setType(1);
        assertEquals(1,f1.getType());
        assertEquals(-1,f2.getType());
        assertEquals(1,f3.getType());
    }

    @Test
    public void setValue() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        Fruit f3 = new Fruit();
        f1.setValue(1.0);
        f2.setValue(2.0);
        f3.setValue(3.0);
        assertEquals(3.0,f3.getValue(),0.0001);
        assertEquals(2.0,f2.getValue(), 0.0001);
        assertEquals(1.0,f1.getValue(), 0.0001);
    }

    @Test
    public void getValue() {
        Fruit f1 = new Fruit();
        Fruit f2 = new Fruit();
        Fruit f3 = new Fruit();
        f1.setValue(1.0);
        f2.setValue(2.0);
        f3.setValue(3.0);
        assertEquals(3.0,f3.getValue(),0.0001);
        assertEquals(2.0,f2.getValue(), 0.0001);
        assertEquals(1.0,f1.getValue(), 0.0001);
    }

    @Test
    public void getFruitEdge() {
        DGraph d = new DGraph();
        Node a = new Node(new Point3D(1,2));
        Node b = new Node(new Point3D(2,4));
        d.addNode(a);
        d.addNode(b);
        d.connect(a.getKey(),b.getKey(),12);
        Fruit r = new Fruit();
        r.setPos(new Point3D(1.5,3));
        edge_data temp = r.getFruitEdge(d,r);

        assertEquals(temp.getSrc(),a.getKey());
        assertEquals(temp.getDest(),b.getKey());



    }

    @Test
    public void copy() {
        ArrayList<Fruit> arr = new ArrayList<>();
        Fruit r = new Fruit();
        r.setType(1);
        r.setImg("abc");
        r.setPos(new Point3D(1,1,1));
        r.setValue(20);
        arr.add(r);
        ArrayList<Fruit> p =r.copy(arr);
        Fruit rr = p.get(0);
        assertEquals(r.getType(),rr.getType());
        assertEquals(r.getPos(),rr.getPos());
        assertEquals(r.getValue(),rr.getValue(),0.0001);
        assertEquals(r.getImg(),rr.getImg());
    }
        @Test
    public void update() {
        Point3D p = new Point3D(35.4,32.3);
        Fruit r = new Fruit();
        r.update(JSONSTRING);
        assertEquals(8,r.getValue(),0.001);
        assertEquals(-1,r.getType());
        assertEquals(p,r.getPos());

    }
    @Test
    public void initFromJson() {
        Point3D pp = new Point3D(35.4,32.3);
        Fruit temp = new Fruit();
        Fruit p =temp.initFromJson(JSONSTRING);
        assertEquals(8.0,p.getValue(),0.001);
        assertEquals(-1,p.getType());
        assertEquals(pp,p.getPos());
    }
}