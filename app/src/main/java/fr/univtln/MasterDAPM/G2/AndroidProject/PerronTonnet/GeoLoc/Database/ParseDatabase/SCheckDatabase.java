package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Database.ParseDatabase;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.CExcursion;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.EExcursionType;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users.CUser;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Waypoints.CWaypoint;

/**
 * Created by toms on 12/15/15.
 */
public class SCheckDatabase {

    private static SCheckDatabase ourInstance = new SCheckDatabase();

    public static SCheckDatabase getInstance() {
        return ourInstance;
    }

    public List<CExcursion> excursions = null;


    /**
     * singleton permettant de recuperer les excursions
     * publiques et de les afficher dans une listview
     * des l'ouvreture de l'application
     */

    public void check(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Excursion");
        query.include("excursionHost");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                excursions = new ArrayList<CExcursion>();

                if (objects != null) {
                    if (e == null) {
                        Log.d("error:", "ok");
                        for (ParseObject po : objects) {


                            List<CUser> tempUser = new ArrayList<CUser>();
                            tempUser = po.getList("ParticipantsList");


                            List<CWaypoint> tempWaypoint = new ArrayList<CWaypoint>();
                            tempWaypoint = po.getList("WaypointList");


                            ParseObject a = po.getParseObject("excursionHost");
                            CUser user = new CUser(a.getString("userName"), a.getInt("phoneNumber"));
                            excursions.add(new CExcursion(po.getString("excursionName"),
                                    EExcursionType.valueOf(po.getString("excursionType").toUpperCase()), po.getBoolean("isPrivate"),
                                    tempUser, tempWaypoint, user));
                        }
                    }
                } else {
                    Log.d("error:", "vide");
                }
            }
        });


}
}
