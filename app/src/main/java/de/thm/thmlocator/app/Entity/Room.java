package de.thm.thmlocator.app.Entity;

/**
 * Created by atabaksahraei on 12.05.14.
 */
public class Room {

    protected Integer id;

    protected String roomName;

    protected String eventName;

    public Integer getid()
       {
           return id;
       }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

}