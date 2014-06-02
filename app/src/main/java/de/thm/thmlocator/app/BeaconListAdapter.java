package de.thm.thmlocator.app;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.radiusnetworks.ibeacon.IBeacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import de.thm.thmlocator.app.Entity.Event;
import de.thm.thmlocator.app.Entity.Room;

/**
 * Created by atabaksahraei on 12.05.14.
 */
public class BeaconListAdapter extends BaseAdapter {

    protected ArrayList<IBeacon> beacons;
    protected Activity myActivity;

    public BeaconListAdapter(Activity myActivity, Collection<IBeacon> beacons){
        this.beacons = new ArrayList<IBeacon>();
        this.beacons.addAll(beacons);
        this.myActivity = myActivity;

     }

    @Override
    public int getCount() {
        if(beacons == null)
            return 0;

        return beacons.size();
    }

    @Override
    public Object getItem(int position) {
        if(beacons == null)
            return null;

        return beacons.get(position);

    }

    @Override
    public long getItemId(int position) {
        if(beacons == null)
            return 0;

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        IBeacon beacon = (IBeacon) getItem(position);
        Room room = DataManager.getRoomByBeaconID(beacon.getMinor());
        View view = convertView;

        if(view == null || !(view instanceof LinearLayout))
        {
            view = myActivity.getLayoutInflater().inflate(R.layout.row_item, parent, false);
        }

        TextView titel, subTitel;
        ImageView circle, arrow;

        titel = (TextView) view.findViewById(R.id.list_item_data_row_TextView_Titel);
        subTitel = (TextView) view.findViewById(R.id.list_item_data_row_TextView_SubTitel);
        circle = (ImageView) view.findViewById(R.id.list_item_data_row_ImageView_Icon);
        arrow = (ImageView) view.findViewById(R.id.list_item_data_row_ImageView_next);

        if(beacon.getAccuracy() >= 1.0)
        {
            circle.setAlpha((float) 0.1);
        }else if(beacon.getAccuracy() >= 0.5 )
        {
            circle.setAlpha((float) 0.4);
        }else {
            circle.setAlpha((float) 1.0);
        }


        if(room != null) {
            titel.setText(room.getRoomName());
            arrow.setVisibility(View.VISIBLE);

            Event event = room.getEventByTime(new Date());

            if (event != null) {
                subTitel.setText(event.getEventName());
            } else {
                subTitel.setText(myActivity.getString(R.string.currentlyNoEvent));
            }
        }else{
            titel.setText("Beacon " + beacon.getMinor());
            subTitel.setText("Proximity: " + beacon.getAccuracy());
            arrow.setVisibility(View.INVISIBLE);
        }

        return view;
    }
}
