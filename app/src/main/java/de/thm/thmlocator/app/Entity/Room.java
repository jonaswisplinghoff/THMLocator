package de.thm.thmlocator.app.Entity;

/**
 * Created by atabaksahraei on 12.05.14.
 */

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.util.UUID;

import de.thm.thmlocator.app.StaticLib;

public class Room implements Serializable {

    private int id;
    private UUID beaconID;
    private String roomPicture;
    private String roomName;
    private ArrayList<Event> roomEvents;

    public Room(int id, UUID beaconID,  String roomName,
                ArrayList<Event> roomEvents) {
        super();
        this.id = id;
        this.beaconID = beaconID;
        this.roomPicture = null;
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
        return StaticLib.decodeBase64(this.roomPicture);
    }
    public void setRoomPicture(Bitmap roomPicture) {
        this.roomPicture = StaticLib.encodeTobase64(roomPicture);

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

    public UUID getBeaconID() {
        return beaconID;
    }
    public void setBeaconID(UUID beaconID) {
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

        if(result==null){
            result = new Event("Keine Veranstaltung", "", "", "", new Date(), new Date(), 0, "", "", "");
        }
        return result;
    }

    public boolean isWithinRange(Date testDate, Date startDate, Date endDate) {
        return !(testDate.before(startDate) || testDate.after(endDate));
    }
}