package de.thm.thmlocator.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import java.util.UUID;

import de.thm.thmlocator.app.Entity.Room;

/**
 * Created by jonaswisplinghoff on 14.05.14.
 */
public class BeaconService extends Service implements IBeaconConsumer {

    protected static final String TAG = "BeaconService";
    protected static final String BEACON_ID = "THMBeacon";

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

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onIBeaconServiceConnect() {
        beaconManager.setMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.i(TAG, "I just saw an iBeacon for the first time!");
                try {
                    beaconManager.startRangingBeaconsInRegion(new Region(BEACON_ID, null , null, null));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an iBeacon");
                try {
                    beaconManager.stopRangingBeaconsInRegion(new Region(BEACON_ID, null, null, null));
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

                if(iBeacons.size()>0){

                    for(IBeacon beacon: iBeacons){
                        Log.d(TAG, "Beacon found: " + beacon.getMajor() + ":" + beacon.getMinor());
                    }

                    DataManager.NotifyBeacons(iBeacons);

                    IBeacon closest = iBeacons.iterator().next();
                    if(closest.getProximity()==IBeacon.PROXIMITY_IMMEDIATE){
                        sendNotification(UUID.fromString(closest.getProximityUuid()));
                    }
                }
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region(BEACON_ID, null, null, null));
        } catch (RemoteException e) {   }
    }

    public void sendNotification(UUID beaconId){
        Room currentRoom = DataManager.getRoomByBeaconID(beaconId);

        if(currentRoom != null){
            Log.v(TAG, "sending Notification");

            int notificationId = 1;

            // Build intent for notification content
            Intent viewIntent = new Intent(this, DetailActivity.class);
            Room room = DataManager.getRoomByBeaconID(beaconId);
            viewIntent.putExtra(MainActivity.ROOM_ID, room.getId());

            PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            currentRoom.getRoomPicture();

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.logo)
                            .setLargeIcon(currentRoom.getRoomPicture())
                            .setContentTitle(currentRoom.getRoomName())
                            .setContentText(currentRoom.getEventByTime(new Date()).getEventName())
                            .setContentIntent(viewPendingIntent);

            NotificationCompat.BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
            secondPageStyle.setBigContentTitle(currentRoom.getEventByTime(new Date()).getEventName())
                    .bigText(currentRoom.getEventByTime(new Date()).getEventProf());

            Notification secondPageNotification =
                    new NotificationCompat.Builder(this)
                            .setStyle(secondPageStyle)
                            .build();


            Notification twoPageNotification =
                    new NotificationCompat.WearableExtender()
                            .addPage(secondPageNotification)
                            .extend(notificationBuilder)
                            .build();


            // Get an instance of the NotificationManager service
            NotificationManagerCompat notificationManager =
                    NotificationManagerCompat.from(this);

            // Build the notification and issues it with notification manager.
            notificationManager.notify(notificationId, twoPageNotification);
        }
    }
}