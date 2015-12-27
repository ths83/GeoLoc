package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Statistics;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.CExcursion;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.EExcursionType;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users.CUser;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Database.ParseDatabase.IParsable;

/**
 * Created by imnotfood on 08/12/15.
 */
public class CCurrentStatistics implements IParsable, Serializable {
    private List<LatLng> itineraryLatLng;
    private List<Double> itineraryAltitude;

    private LatLng currentPosition;
    private double currentAltitude;
    private double currentDistance;
    private double averageSpeed;

    private CUser relatedToUser;
    private CExcursion relatedToExcursion;
    private int refreshDatabaseTime;

    private Object[] toAddinDatabase;

    private ParseQuery<ParseObject> refreshParseCurrentStatistics = ParseQuery.getQuery("UserStats");

    /**
     * constructeur par defaut
     */
    public CCurrentStatistics() {
        this.itineraryLatLng = new ArrayList<LatLng>();
        this.currentPosition = new LatLng(0d, 0d);
        this.currentAltitude = 0;
        this.averageSpeed = 0;
        this.relatedToUser = new CUser();
        this.relatedToExcursion = new CExcursion();
        this.refreshDatabaseTime = 0;
    }

    /**
     * construteur avec parametres
     * @param relatedToUser
     * @param relatedToExcursion
     */
    public CCurrentStatistics(CUser relatedToUser, CExcursion relatedToExcursion) {
        itineraryLatLng = new ArrayList<>();
        itineraryAltitude = new ArrayList<>();
        currentPosition = new LatLng(0d, 0d);
        currentAltitude = 0d;
        currentDistance = 0d;
        averageSpeed = 0d;

        this.relatedToUser = relatedToUser;
        this.relatedToExcursion = relatedToExcursion;

        if (relatedToExcursion.getType() == EExcursionType.CAR) {
            refreshDatabaseTime = 15;
        } else if (relatedToExcursion.getType() == EExcursionType.RUNNING) {
            refreshDatabaseTime = 30;
        } else {
            refreshDatabaseTime = 45;
        }
    }


    /**
     * getters and setters
     * @return
     */
    public List<LatLng> getItineraryLatLng() {
        return itineraryLatLng;
    }

    public void setItineraryLatLng(List<LatLng> itineraryLatLng) {
        this.itineraryLatLng = itineraryLatLng;
    }

    public List<Double> getItineraryAltitude() {
        return itineraryAltitude;
    }

    public void setItineraryAltitude(List<Double> itineraryAltitude) {
        this.itineraryAltitude = itineraryAltitude;
    }

    public LatLng getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(LatLng currentPosition) {
        this.currentPosition = currentPosition;
    }

    public double getCurrentAltitude() {
        return currentAltitude;
    }

    public void setCurrentAltitude(double currentAltitude) {
        this.currentAltitude = currentAltitude;
    }

    public double getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(double currentDistance) {
        this.currentDistance = currentDistance;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }


    /**
     * ajoute les statistiques de l'utilisateur dans la table Excursion de la base de donnee
     * @param delayToSearchOtherUsersInDatabase
     * @param itineraryLatLng
     * @param itineraryAltitude
     * @param currentPosition
     * @param currentAltitude
     * @param currentDistance
     * @param averageSpeed
     */
    public void setAllCurrentStatistics(int delayToSearchOtherUsersInDatabase, final List<LatLng> itineraryLatLng,
                                        final List<Double> itineraryAltitude, final LatLng currentPosition,
                                        double currentAltitude, final double currentDistance, double averageSpeed) {
        this.setCurrentPosition(currentPosition);
        this.setCurrentAltitude(currentAltitude);
        this.setCurrentDistance(currentDistance);
        this.setItineraryAltitude(itineraryAltitude);
        this.setItineraryLatLng(itineraryLatLng);
        this.setAverageSpeed(averageSpeed);


        // if refreshTime every xx seconds
        if (delayToSearchOtherUsersInDatabase % refreshDatabaseTime == 0) {
            // put user pointer here
            refreshParseCurrentStatistics.getInBackground(CMyApplication.statsPointer(), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        object.put("currentLat", currentPosition.latitude);
                        object.put("currentLng", currentPosition.longitude);
                        object.put("itineraryLat", itineraryLatLng);
                        object.put("itineraryAlt", itineraryAltitude);
                        object.put("distanceTraveled", currentDistance);
                        object.put("duration", 23);
                        object.put("user", CMyApplication.userPointer());
                        object.saveInBackground();
                    }
                }
            });
        }
    }



    @Override
    public boolean checkParseDatabase(Object... elements) {
        return false;
    }

    @Override
    public void addElementInDatabase(Object[] elements) {

        ParseObject parseObject = new ParseObject("UserStats");
        parseObject.put("currentLat", currentPosition.latitude);
        parseObject.put("currentLng", currentPosition.longitude);
        parseObject.put("itineraryLat", itineraryLatLng);
        parseObject.put("itineraryAlt", itineraryAltitude);
        parseObject.put("distanceTraveled", 50);
        parseObject.put("duration", 23);
        parseObject.saveInBackground();
    }

    @Override
    public void removeElementFromParse() {
    }


    /**
     * met à jour les donnees de l'utlisateur dans la table Excursion
     */
    public void newStatsInDatabase() {
        ParseObject parseStat = new ParseObject("UserStats");

        ParseObject parseUser = new ParseObject("Userr");
        ParseObject parseExcursion = new ParseObject("Excursion");

        ParseQuery<ParseObject> getUser = new ParseQuery<ParseObject>("Userr");
        ParseQuery<ParseObject> getExcursion = new ParseQuery<ParseObject>("Excursion");
        getUser.whereEqualTo("phoneNumber", relatedToUser.getPhoneNumber());
        getExcursion.whereEqualTo("excursionName", relatedToExcursion.getName());

        try {
            parseUser = getUser.getFirst();
            parseExcursion = getExcursion.getFirst();
        } catch (ParseException e) {
            Log.d("abc", "sad fail");
        }


        parseStat.put("currentLat", currentPosition.latitude);
        parseStat.put("currentLng", currentPosition.longitude);
        parseStat.put("itineraryLat", currentPosition.latitude);
        parseStat.put("itineraryLng", currentPosition.longitude);
        parseStat.put("distanceTraveled", currentDistance);
        parseStat.put("relatedToUser", parseUser);
        parseStat.put("relatedToExcursion", parseExcursion);
        parseStat.saveInBackground();
    }
}