package Tests;

import algorithms.Graph_Algo;
import gameClient.KML_Logger;
import gameClient.MyGameGUI;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class KML_LoggerTest {

    @Test
    public void makeKML()  {
        KML_Logger kml = new KML_Logger();
        boolean checkSave = true;
        try {
            kml.makeKML();
        }catch (ParseException| InterruptedException e){checkSave=false;}

        assertEquals(true,checkSave);

    }
}