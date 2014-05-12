package de.thm.thmlocator.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.thm.thmlocator.app.Entity.Room;

/**
 * Created by atabaksahraei on 12.05.14.
 */
public class RoomListAdapter extends BaseAdapter {

    protected List<Room> myList;
    public RoomListAdapter (Context context, List<Room> items){
        myList = items;
     }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
