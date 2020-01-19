package gameClient;


import de.micromata.opengis.kml.v_2_2_0.*;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import elements.Fruit;
import elements.Robot;
import utils.StdDraw;

import javax.swing.*;
import java.io.File;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class KML_Logger {
    public void makeKML() throws ParseException, InterruptedException {
        System.out.println("IM HERE!!!! KML");
        Kml kmlDoc = new Kml();
        Document doc = kmlDoc.createAndSetDocument();
        int change=0;
        if(StdDraw.mgg!=null &&StdDraw.mgg.getGame1()!=null){
            while(StdDraw.mgg.getGame1().isRunning()){
                Thread.sleep(100);
                change++;
                ArrayList<Fruit> fruit = StdDraw.mgg.getFoodss();
                ArrayList<Robot>  players= StdDraw.mgg.getPlayers();

                for (Robot robot: players) {
                    Placemark robotMark = doc.createAndAddPlacemark();
                    Icon robIcon = new Icon();

                    robIcon.setHref("http://pngimg.com/uploads/alien/alien_PNG71.png");
                    robIcon.setViewBoundScale(1);
                    robIcon.setViewRefreshTime(1);
                    robIcon.withRefreshInterval(1);
                    IconStyle robIconeStyle = new IconStyle();
                    robIconeStyle.setScale(1);
                    robIconeStyle.setHeading(1);
                    robIconeStyle.setColor("ff007db3");
                    robIconeStyle.setIcon(robIcon);
                    robotMark.createAndAddStyle().setIconStyle(robIconeStyle);
                    robotMark.withDescription("Mac: " + "\nType: Robot").withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(robot.getPos().x(),robot.getPos().y());
                    String robTime1 = MillisToString(StringToMillis(TimeNow())+change*1000);
                    String robTime2 = MillisToString(StringToMillis(TimeNow())+(change +1)*1000);
                    String[] timeArr =robTime1.split(" ");
                    robTime1=splitArr(timeArr);
                    String[] timeArr2 = robTime2.split(" ");
                    robTime2=splitArr(timeArr2);
                    TimeSpan robSpan = robotMark.createAndSetTimeSpan();
                    robSpan.setBegin(robTime1);
                    robSpan.setEnd(robTime2);
                }
                for(Fruit f :fruit){
                    Placemark fruitMark = doc.createAndAddPlacemark();
                    Icon fruitIcon = new Icon();

                    fruitIcon.setHref("http://pngimg.com/uploads/star/star_PNG1597.png");
                    fruitIcon.setViewBoundScale(1);
                    fruitIcon.setViewRefreshTime(1);
                    fruitIcon.withRefreshInterval(1);
                    IconStyle fruitIconStyle = new IconStyle();
                    fruitIconStyle.setScale(1);
                    fruitIconStyle.setHeading(1);
                    fruitIconStyle.setColor("ff007db3");
                    fruitIconStyle.setIcon(fruitIcon);
                    fruitMark.createAndAddStyle().setIconStyle(fruitIconStyle);
                    fruitMark.withDescription("Mac: " + "\nType: Fruit").withOpen(Boolean.TRUE).createAndSetPoint().addToCoordinates(f.getPos().x(),f.getPos().y());
                    String fruitTime1 = MillisToString(StringToMillis(TimeNow())+change*1000);
                    String fruitTime2 = MillisToString(StringToMillis(TimeNow())+(change+1)*1000);
                    String[] fruitArr= fruitTime1.split(" ");
                    fruitTime1=splitArr(fruitArr);
                    String[] fruitArr2 = fruitTime2.split(" ");
                    fruitTime2=splitArr(fruitArr2);
                    TimeSpan fruitSpan = fruitMark.createAndSetTimeSpan();
                    fruitSpan.setBegin(fruitTime1);
                    fruitSpan.setEnd(fruitTime2);

                }

            }

        }
        try{
            int s = JOptionPane.showConfirmDialog(null,"Save game to KML?","Please choose Yes/No",JOptionPane.YES_NO_OPTION);
            if(s==1) StdDraw.saveToKML=false;
            else StdDraw.saveToKML=true;
            System.out.println(StdDraw.saveToKML + " at KML");
            if(StdDraw.saveToKML) kmlDoc.marshal(new File("kmlFile.kml"));
        }catch (Exception e){e.printStackTrace();}
    }

    public String MillisToString(Long millis)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(millis));
    }


    public long StringToMillis(String TimeAsString) throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = format.parse(TimeAsString.toString());
        long millis = date.getTime();
        return millis;
    }

    public String TimeNow()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public String splitArr(String[] arr){
        String temp= arr[0] + "T" + arr[1] + "Z";
        return temp;
    }

}