package de.thm.thmlocator.app;

/**
 * Created by robinwiegand on 19.05.14.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import de.thm.thmlocator.app.Entity.Room;


public class DataManager {
    private static String filename = "res/raw/rooms.fmi";
    FileInputStream fis;
    FileOutputStream fileout;

    public Room getRoomByID(int id){
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

    //Daten von der SD-Karte lesen
    private ArrayList<Room> getRooms(){
        ArrayList<Room> result = new ArrayList<Room>();
        File file = new File(filename);
        try{
            if (file.exists()) {
                fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                result = (ArrayList<Room>) ois.readObject();
            }
        } catch (Exception e){
            result = null;
        }
        return result;
    }

    //Daten abspeichern
    private boolean setRooms(ArrayList<Room> rooms){
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
}

