package de.thm.thmlocator.app.Entity;

/**
 * Created by robinwiegand on 19.05.14.
 */

import java.util.Date;
import java.io.Serializable;

public class Event implements Serializable {


    private String eventName;
    private String eventProf;
    private Date startDate;
    private Date endDate;
    private int eventDurationMinutes;
    private String arsnovaURL;
    private String eventType;
    private String course;

    public Event(String eventName, String eventProf, Date startDate,
                 Date endDate, int eventDurationMinutes, String arsnovaURL,
                 String eventType, String course) {
        super();
        this.eventName = eventName;
        this.eventProf = eventProf;
        this.startDate = startDate;
        this.endDate = endDate;
        this.eventDurationMinutes = eventDurationMinutes;
        this.arsnovaURL = arsnovaURL;
        this.eventType = eventType;
        this.course = course;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getArsnovaURL() {
        return arsnovaURL;
    }
    public void setArsnovaURL(String arsnovaURL) {
        this.arsnovaURL = arsnovaURL;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getEventProf() {
        return eventProf;
    }
    public void setEventProf(String eventProf) {
        this.eventProf = eventProf;
    }
    public int getEventDurationMinutes() {
        return eventDurationMinutes;
    }
    public void setEventDurationMinutes(int eventDurationMinutes) {
        this.eventDurationMinutes = eventDurationMinutes;
    }
}
