package de.thm.thmlocator.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.thm.thmlocator.app.Entity.Room;

/**
 * Created by atabaksahraei on 12.05.14.
 */
public class RoomListAdapter extends BaseAdapter {

    protected ArrayList<Room> myList;
    protected Activity myActivity;

    public RoomListAdapter (Activity myActivity, ArrayList<Room> items){
        myList = items;
        this.myActivity = myActivity;

        if(myList == null)
            myList = new ArrayList<Room>();
     }

    @Override
    public int getCount() {
        if(myList == null)
            return 0;

        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        if(myList == null)
            return null;

        return myList.get(position);

    }

    @Override
    public long getItemId(int position) {
        if(myList == null)
            return 0;

        return ((Room) myList.get(position)).getid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Room item = (Room) getItem(position);
        View view = convertView;

        if(view == null || !(view instanceof LinearLayout))
        {
            view = myActivity.getLayoutInflater().inflate(R.layout.row_item, parent, false);
        }

        TextView titel, subTitel;

        titel = (TextView) myActivity.findViewById(R.id.list_item_data_row_TextView_Titel);
        subTitel = (TextView) myActivity.findViewById(R.id.list_item_data_row_TextView_SubTitel);

        titel.setText(item.getRoomName());
        subTitel.setText(item.getEventName());

        return view;
    }
}
