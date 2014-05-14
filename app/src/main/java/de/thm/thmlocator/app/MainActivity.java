package de.thm.thmlocator.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import de.thm.thmlocator.app.Entity.Room;


public class MainActivity extends Activity {
    protected static final String TAG = "MainActivity";
    RoomListAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, BeaconService.class));

        ArrayList<Room> myRooms = new ArrayList<Room>();
        Room first = new Room();
        first.setEventName("First");
        first.setRoomName("H01.01");

        Room second = new Room();
        second.setEventName("second");
        second.setRoomName("H01.02");

        myRooms.add(first);
        myRooms.add(second);

        myListAdapter = new RoomListAdapter(this, myRooms);

        ListView myList = (ListView) findViewById(R.id.listViewRooms);
        myList.setAdapter(myListAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
