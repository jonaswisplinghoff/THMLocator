package de.thm.thmlocator.app;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.support.v4.app.NotificationManagerCompat;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.MonitorNotifier;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

import java.util.Collection;
import java.util.Date;

import de.thm.thmlocator.app.Entity.Room;

/**
 * Created by jonaswisplinghoff on 14.05.14.
 */
public class BeaconService extends Service implements IBeaconConsumer {

    protected static final String TAG = "BeaconService";
    protected static final String BEACON_ID = "THMBeacon";
    protected static final String BEACON_UUID = "9DEFDC97-38DB-4FE3-A7C1-45BCE2A27A87";
    protected static final String EXTRA_ROOM_ID = "notification_extra_room_id";

    private IBeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = IBeaconManager.getInstanceForApplication(this);
        beaconManager.bind(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        beaconManager.unBind(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Started BeaconService from "+intent.toString());

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onIBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an iBeacon for the first time!");
                try {
                    beaconManager.startRangingBeaconsInRegion(new Region(BEACON_ID, BEACON_UUID , null, null));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an iBeacon");
                try {
                    beaconManager.stopRangingBeaconsInRegion(new Region(BEACON_ID, BEACON_UUID, null, null));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing iBeacons: "+state);
            }
        });

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons, Region region) {

                for(IBeacon beacon: iBeacons){
                    Log.d(TAG, "Beacon found: " + beacon.getMajor() + ":" + beacon.getMinor());
                }

                DataManager.NotifyBeacons(iBeacons);
                sendNotification(iBeacons.iterator().next().getMinor());

            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region(BEACON_ID, BEACON_UUID, null, null));
        } catch (RemoteException e) {   }
    }

    public void sendNotification(int beaconId){


        Room currentRoom = DataManager.getRoomByBeaconID(beaconId);

        if(currentRoom != null){
            Log.v(TAG, "sending Notification");

            int notificationId = 1;

            // Build intent for notification content
            Intent viewIntent = new Intent(this, DetailActivity.class);
            viewIntent.putExtra(EXTRA_ROOM_ID, beaconId);
            PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, 0);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.logo)
                            .setLargeIcon(currentRoom.getRoomPicture())
                            .setContentTitle(currentRoom.getRoomName())
                            .setContentText(currentRoom.getEventByTime(new Date()).getEventName())
                            .setContentIntent(viewPendingIntent);

            // Get an instance of the NotificationManager service
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this);

            // Build the notification and issues it with notification manager.
            notificationManager.notify(notificationId, notificationBuilder.build());
        }
    }
}