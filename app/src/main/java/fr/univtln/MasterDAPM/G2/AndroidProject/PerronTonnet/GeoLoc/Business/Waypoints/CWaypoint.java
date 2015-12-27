package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Waypoints;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.CExcursion;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users.CUser;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Database.ParseDatabase.IParsable;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;

/**
 * Created by imnotfood on 08/12/15.
 */
public class CWaypoint implements IParsable {
    private static int id;
    private String title;
    private LatLng latLng;
    //private double altitude;
    //private double angle;
    private CUser createdBy;
    private CExcursion waypExcursion;
    private String description;
    private EWaypointType type;

    /**
     * constructeurs

     */



    public CWaypoint(LatLng latLng, CUser createdBy, CExcursion waypExcursion) {
        this.latLng = latLng;
        this.createdBy = createdBy;
        this.waypExcursion = waypExcursion;
        this.title = "";
        this.description = "";
    }

    public CWaypoint(String title, LatLng latLng, CUser createdBy, EWaypointType type, String description) {
        this.title = title;
        this.latLng = latLng;
        this.createdBy = createdBy;
        this.type = type;
        this.description = description;
    }

    public CWaypoint(String title, LatLng latLng, double altitude, double angle, CUser createdBy, EWaypointType type, String description) {
        this.title = title;
        this.latLng = latLng;
        //this.altitude = altitude;
        //this.angle = angle;
        this.createdBy = createdBy;
        this.type = type;
        this.description = description;
        id++;
    }

    //getters and setters

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        CWaypoint.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public CUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CUser createdBy) {
        this.createdBy = createdBy;
    }

    public CExcursion getWaypExcursion() {
        return waypExcursion;
    }

    public void setWaypExcursion(CExcursion waypExcursion) {
        this.waypExcursion = waypExcursion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean checkParseDatabase(Object... elements) {
        return false;
    }

    public ParseObject exc;
    public ParseObject creatr;

    /**
     * ajoute le wqaypoint dans la base de donnees
     * @param elements
     */
    @Override
    public void addElementInDatabase(Object[] elements) {
        exc = new ParseObject("Excursion");
        creatr = new ParseObject("Userr");

        ParseQuery<ParseObject> currentExcursionQuery = new ParseQuery<ParseObject>("Excursion");
        currentExcursionQuery.whereEqualTo("excursionName", CMyApplication.getCurrentExcursion().getName());

        ParseQuery<ParseObject> currentUserQuery = new ParseQuery<ParseObject>("Userr");
        currentUserQuery.whereEqualTo("phoneNumber", CMyApplication.getCurrentUser().getPhoneNumber());

        try {
            exc = currentExcursionQuery.getFirst();
            creatr = currentUserQuery.getFirst();
        } catch (ParseException e) {
            Log.d("abc", "DDDD:");
        }

        ParseObject waypointTable = new ParseObject("Waypoints");

        waypointTable.put("waypointName", this.title);
        waypointTable.put("waypointLatitude", this.latLng.latitude);
        waypointTable.put("waypointLongitude", this.latLng.longitude);
        waypointTable.put("waypointDescription", this.description);

        waypointTable.put("waypExcursion", exc);
        waypointTable.put("waypCreator", creatr);

        exc.add("WaypointList", waypointTable);
        CMyApplication.getCurrentExcursion().addWaypoint(this);

        waypointTable.saveInBackground();
    }

    @Override
    public void removeElementFromParse() {

    }
}
