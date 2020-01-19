package Tests;

import dataStructure.graph;
import elements.Fruit;
import elements.Robot;
import org.junit.Test;
import utils.Point3D;

import java.util.List;

import static org.junit.Assert.*;

public class FruitTest {

    @Test
    public void setTag() {
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
    }

    @Test
    public void copy() {
    }


    //    @Test
//    public void update() {
//    }
//
//    @Test
//    public void initFromJson() {
//    }
//
//    @Test
//    public void fillFruitList() {
//    }
}