package de.thm.thmlocator.app;

import com.radiusnetworks.ibeacon.IBeacon;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by atabaksahraei on 30.05.14.
 */
public interface IBeaconView {
    public void updateBeaconList (Collection<IBeacon> iBeacons);
}
