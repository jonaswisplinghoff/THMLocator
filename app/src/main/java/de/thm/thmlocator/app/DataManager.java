package de.thm.thmlocator.app;

/**
 * Created by robinwiegand on 19.05.14.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;

import com.radiusnetworks.ibeacon.IBeacon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import de.thm.thmlocator.app.Entity.Event;
import de.thm.thmlocator.app.Entity.Room;


public class DataManager {
    private static IBeaconView myCurrentbeaconView;
    private static String filename = Environment.getExternalStorageDirectory().toString() +  "/rooms.fmi";
    private static FileInputStream fis;
    private static FileOutputStream fileout;

    public static void registForBeaconListChanges(IBeaconView beaconView)
    {
        myCurrentbeaconView = beaconView;
    }

    public static void NotifyBeacons(Collection<IBeacon> beacons)
    {
        if(myCurrentbeaconView != null)
        {
            myCurrentbeaconView.updateBeaconList(beacons);
        }
    }

    public static Room getRoomByID(int id){
        ArrayList<Room> rooms = new ArrayList<Room>();
        rooms = getRooms();
        Room result = null;

        for(Room r : rooms){
            if(r.getId() == id){
                result = r;
            }
        }

        return result;
    }

    public static Room getRoomByBeaconID(UUID beaconId){
        ArrayList<Room> rooms = new ArrayList<Room>();
        rooms = getRooms();
        Room result = null;

        for(Room r : rooms){
            if(r.getBeaconID().equals(beaconId)){
                result = r;
            }
        }

        return result;
    }


    //Daten von der SD-Karte lesen
    private static ArrayList<Room> getRooms(){
        ArrayList<Room> result = new ArrayList<Room>();
        File file = new File(filename);
        try{
            if (file.exists()) {
                fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                result = (ArrayList<Room>) ois.readObject();
            }
            else{
                throw new FileNotFoundException();
            }
        } catch (Exception e){
            result = null;
        }
        return result;
    }

    //Daten abspeichern
    private static boolean setRooms(ArrayList<Room> rooms){
        boolean result;
        try
        {
            File file = new File(filename);
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create file");
                }
                //else { //evtl. Fragen ob ueberschrieben werden soll }
            }
            fileout = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(rooms);
            out.close();
            result = true;
        }
        catch (Exception ex)
        {
            result = false;
        }

        return result;
    }


    public static void createTestData(Context ctxt){
        ArrayList<Room> rooms = new ArrayList<Room>();
        ArrayList<Event> events = new ArrayList<Event>();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = formatter.parse("30/06/2014 18:00");
            endDate = formatter.parse("25/07/2014 20:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Event e1 = new Event("FMI", "Prof. Dr. Dominik Schultes", "dominik.schultes@iem.thm.de","https://moodle.thm.de/course/view.php?id=1420", startDate, endDate, 120, "https://arsnova.eu/mobile/#id/77201101", "Vorlesung", "Medieninformatik M. Sc.");
        events.add(e1);

        //Bitmap testBild = BitmapFactory.decodeResource(ctxt.getResources(), R.drawable.raum_test);
        Room r1 = new Room(1, UUID.fromString("73676723-7400-0000-ffff-0000ffff0000"), "H0101", events);
        Room r2 = new Room(2, UUID.fromString("73676723-7400-0000-ffff-0000ffff0001"), "J.03.27c", events);
        Room r3 = new Room(3, UUID.fromString("73676723-7400-0000-ffff-0000ffff0002"), "J.03.25b", events);

        r1.setRoomPicture(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.room_h0101));
        r2.setRoomPicture(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.room_j0327c));
        r2.setRoomPicture(BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.room_j0325b));

        rooms.add(r1);
        rooms.add(r2);
        rooms.add(r3);

        setRooms(rooms);
    }
}

