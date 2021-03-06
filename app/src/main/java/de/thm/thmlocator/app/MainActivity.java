package de.thm.thmlocator.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.radiusnetworks.ibeacon.IBeacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import de.thm.thmlocator.app.Entity.Room;


public class MainActivity extends Activity implements IBeaconView {
    protected static final String TAG = "MainActivity";
    BeaconListAdapter myListAdapter;
    final static String BEACON_ID = "beacon.id";
    final static String ROOM_ID = "room.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataManager.registForBeaconListChanges(this);
        //Beim ersten Launch Testdaten erstellen
        //--------------------------------------
        boolean firstStart;
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        firstStart = settings.getBoolean("FIRST_RUN", true);
        if (firstStart) {
            DataManager.createTestData(this);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", false);
            editor.apply();
        }
        //--------------------------------------

        Intent startServiceIntent = new Intent(this, BeaconService.class);
        startService(startServiceIntent);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateBeaconList(Collection<IBeacon> iBeacons) {
        Log.d(TAG, "COLLECTION_SIZE: "+iBeacons.size());

        myListAdapter = new BeaconListAdapter(this, iBeacons);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView myList = (ListView) findViewById(R.id.listViewRooms);
                View emptyList = (View) findViewById(R.id.list_empty);
                myList.setAdapter(myListAdapter);
                myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        IBeacon beacon = (IBeacon) MainActivity.this.myListAdapter.getItem(i);

                        if(DataManager.getRoomByBeaconID(UUID.fromString(beacon.getProximityUuid())) == null)
                        {
                            Toast.makeText(MainActivity.this, "Keine Daten in der Datenbank enthalten!", Toast.LENGTH_SHORT).show();
                        }else {
                            intent.putExtra(ROOM_ID, (DataManager.getRoomByBeaconID(UUID.fromString(beacon.getProximityUuid())).getId()));
                            startActivity(intent);
                        }
                    }
                });
                if (myListAdapter.getCount() > 0) {
                    myList.setVisibility(View.VISIBLE);
                    emptyList.setVisibility(View.GONE);
                } else {
                    myList.setVisibility(View.GONE);
                    emptyList.setVisibility(View.VISIBLE);

                }
            }
        });

    }
}
