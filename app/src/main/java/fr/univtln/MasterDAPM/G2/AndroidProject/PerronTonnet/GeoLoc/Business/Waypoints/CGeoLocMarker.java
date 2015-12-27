package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Waypoints;



import android.util.Log;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;

/**
 * Created by toms on 16/12/2015.
 */
public class CGeoLocMarker {

    private Marker marker;


    /**
     * constructeur creant un marker sur la carte
     * le constructeur de CWaypoint est appele a chaque appel de celui-ci
     * @param googleMap
     * @param latLng
     * @param bitmapDescriptor
     */
    public CGeoLocMarker(GoogleMap googleMap, LatLng latLng, BitmapDescriptor bitmapDescriptor) {
        marker = googleMap.addMarker
                (new MarkerOptions().position(latLng).icon(bitmapDescriptor));

        marker.setDraggable(true);

        Log.d("abc", "avant pose wayp");
        CWaypoint wayp = new CWaypoint(latLng, CMyApplication.getCurrentUser(), CMyApplication.getCurrentExcursion());
        wayp.addElementInDatabase(new Object[0]);
        Log.d("abc", "apres pose wayp");
    }

    //getters
    public Marker getMarker() {
        return marker;
    }
}
