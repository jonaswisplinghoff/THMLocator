package de.thm.thmlocator.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.MonitorNotifier;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

import java.util.Collection;

/**
 * Created by jonaswisplinghoff on 14.05.14.
 */
public class BeaconService extends Service implements IBeaconConsumer {

    protected static final String TAG = "BeaconService";
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
            }

            @Override
            public void didExitRegion(Region region) {
                Log.i(TAG, "I no longer see an iBeacon");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.i(TAG, "I have just switched from seeing/not seeing iBeacons: "+state);
            }
        });

        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons, Region region) {
                Log.i(TAG, "The first iBeacon I see is about "+iBeacons.iterator().next().getAccuracy()+" meters away.");
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
            beaconManager.startRangingBeaconsInRegion(new Region("myMonitoringUniqueId", null, null, null));
        } catch (RemoteException e) {   }
    }
}