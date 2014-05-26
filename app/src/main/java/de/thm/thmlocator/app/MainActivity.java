package de.thm.thmlocator.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.thm.thmlocator.app.Entity.Room;


public class MainActivity extends Activity {
    protected static final String TAG = "MainActivity";
    RoomListAdapter myListAdapter;
    final static String BEACON_ID = "beacon.id";
    final static String ROOM_ID = "room.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Beim ersten Launch Testdaten erstellen
        //--------------------------------------
        boolean firstStart = false;
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        firstStart = settings.getBoolean("FIRST_RUN", false);
        if (!firstStart) {
            DataManager.createTestData(this);
            settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();
        }
        //--------------------------------------

        Intent startServiceIntent = new Intent(this, BeaconService.class);
        startService(startServiceIntent);

        ArrayList<Room> myRooms = new ArrayList<Room>();
        Room first = new Room(0, 0, null, "H.01.01", null);
        Room second = new Room(1, 1, null, "H.01.02", null);
        myRooms.add(first);
        myRooms.add(second);

        myListAdapter = new RoomListAdapter(this, myRooms);

        ListView myList = (ListView) findViewById(R.id.listViewRooms);
        View emptyList = (View) findViewById(R.id.list_empty);
        myList.setAdapter(myListAdapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(ROOM_ID, ((Room) MainActivity.this.myListAdapter.getItem(0)).getId());
                startActivity(intent);
            }
        });
        if(myListAdapter.getCount()>0)
        {
            myList.setVisibility(View.VISIBLE);
            emptyList.setVisibility(View.GONE);
        }else
        {
            myList.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);

        }

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
