package de.thm.thmlocator.app.Entity;

/**
 * Created by atabaksahraei on 12.05.14.
 */

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Date;

public class Room {



    private int id;
    private int beaconID;
    private Bitmap roomPicture;
    private String roomName;
    private ArrayList<Event> roomEvents;

    public Room(int id, int beaconID, Bitmap roomPicture, String roomName,
                ArrayList<Event> roomEvents) {
        super();
        this.id = id;
        this.beaconID = beaconID;
        this.roomPicture = roomPicture;
        this.roomName = roomName;
        this.roomEvents = roomEvents;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Bitmap getRoomPicture() {
        return roomPicture;
    }
    public void setRoomPicture(Bitmap roomPicture) {
        this.roomPicture = roomPicture;
    }
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public ArrayList<Event> getRoomEvents() {
        return roomEvents;
    }

    public void setRoomEvents(ArrayList<Event> roomEvents) {
        this.roomEvents = roomEvents;
    }

    public int getBeaconID() {
        return beaconID;
    }
    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }


    public Event getEventByTime(Date actualTime){
        Event result = null;
        if(roomEvents != null){
            for(Event e : roomEvents) {
                if(isWithinRange(actualTime, e.getStartDate(), e.getEndDate())){
                    result = e;
                }
            }
        }
        return result;
    }

    public boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
        return !(testDate.before(startDate) || testDate.after(endDate));
    }

}

