package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users.CUser;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Waypoints.CWaypoint;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Database.ParseDatabase.IParsable;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;

public class CExcursion implements IParsable,Serializable{

    public static final String EXCURSION_TYPE = "fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.ExcursionType";

    private static int id;
    private static List<String> excursionList = new ArrayList<String>();
    private String name;
    private EExcursionType type;
    private boolean isPrivate;
    private List<CUser> participants;
    private List<CWaypoint> waypointsList;
    private CUser host;
    private int refreshRate; //ms

    private Object[] excursion ;

    public ParseObject newExcursion;
    public ParseObject currentUsr;


    /**
     * constructeur sans parametres
     */
    public CExcursion() {
        this.name = "";
        this.type = EExcursionType.PEDESTRIAL;
        this.isPrivate = true;
        this.participants = new ArrayList<CUser>();
        this.waypointsList = new ArrayList<CWaypoint>();
    }

    /**
     * constructeur permettant la creation d'un excursion avec les parametres ci dessous
     * @param name
     * @param type
     * @param isPrivate
     * @param participants
     * @param waypointsList
     * @param host
     */

    public CExcursion(String name, EExcursionType type, boolean isPrivate, List<CUser> participants, List<CWaypoint> waypointsList, CUser host) {
        this.name = name;
        this.type = type;
        this.isPrivate = isPrivate;
        this.participants = participants;
        this.waypointsList = waypointsList;
        this.host = host;

        /**
         * en fonction du type d'excursion choisi,
         * la frequence de rafraichissement des valeurs
         * dans la base est different
         */
        String exType ;
        int refreshAppearTime;

        if (type == EExcursionType.CAR){
            exType = EExcursionType.CAR.toString();
            refreshAppearTime = 15;
        }

        else if (type == EExcursionType.RUNNING){
            exType = EExcursionType.RUNNING.toString();
            refreshAppearTime = 30;
        }

        else {
            exType = EExcursionType.PEDESTRIAL.toString();
            refreshAppearTime = 45;
        }


    }

    /**
     *  * constructeur permettant la creation d'un excursion avec les parametres ci dessous
     * @param name
     * @param type
     * @param isPrivate
     * @param participants
     * @param waypointsList
     */

    public CExcursion(String name, EExcursionType type, boolean isPrivate, List<CUser> participants, List<CWaypoint> waypointsList) {
        this.name = name;
        this.type = type;
        this.isPrivate = isPrivate;
        this.participants = participants;
        this.waypointsList = waypointsList;


        String exType ;
        int refreshAppearTime;



        if (type == EExcursionType.CAR){
            exType = EExcursionType.CAR.toString();
            refreshAppearTime = 15;
        }

        else if (type == EExcursionType.RUNNING){
            exType = EExcursionType.RUNNING.toString();
            refreshAppearTime = 30;
        }

        else {
            exType = EExcursionType.PEDESTRIAL.toString();
            refreshAppearTime = 45;
        }


    }







    @Override
    public boolean checkParseDatabase(Object... elements) {
        return false;
    }


    /**
     * ajoute l'excursion dans la base de donnees
     * @param elements
     */
    @Override
    public void addElementInDatabase(final Object[] elements) {

        CUser user = CMyApplication.getCurrentUser();

        ParseObject parseHost = new ParseObject("Userr");

        ParseQuery<ParseObject> putHostQuery = new ParseQuery<ParseObject>("Userr");
        putHostQuery.whereEqualTo("phoneNumber", user.getPhoneNumber());
        try {
            parseHost = putHostQuery.getFirst();
        } catch (ParseException e) { Log.d("abc", "failed to get host"); }

        ParseObject excursionTable = new ParseObject("Excursion");
        excursionTable.put("excursionName", elements[0]);
        excursionTable.put("excursionType", elements[1].toString());
        excursionTable.put("isPrivate", elements[2]);
        excursionTable.put("ParticipantsList", elements[3]);
        excursionTable.put("WaypointList", elements[4]);
        excursionTable.put("excursionHost", parseHost);
        excursionTable.add("ParticipantsList", parseHost);
        excursionTable.saveInBackground();
    }


    /**
     * ajout d'un participant de l'excursion dans la base de donnees
     * @param user
     */

    public void addParticipant(CUser user) {
        ParseQuery<ParseObject> joinExcursionQuery = new ParseQuery<ParseObject>("Excursion");
        joinExcursionQuery.whereEqualTo("excursionName", this.name);

        ParseQuery<ParseObject> getUsr = new ParseQuery<ParseObject>("Userr");
        getUsr.whereEqualTo("userName", user.getUsername());

        newExcursion = new ParseObject("Excursion");
        currentUsr = new ParseObject("Userr");
        try {
            newExcursion = joinExcursionQuery.getFirst();
            currentUsr = getUsr.getFirst();
        } catch (ParseException e) {
        }
        participants.add(user);
        newExcursion.add("ParticipantsList", currentUsr);
        }

    /**
     * getters et setters
     * @return
     */
    @Override
    public String toString() {
        return this.name;
    }

    public void addAllParticipants(List<CUser> user) {
        participants.addAll(user);
    }

    public void addWaypoint(CWaypoint waypoint) {
        waypointsList.add(waypoint);
    }

    public void addAllWaypoint(List<CWaypoint> waypoint) {
        waypointsList.addAll(waypoint);
    }

    @Override
    public void removeElementFromParse() {

    }

    public String getName() {
        return name;
    }

    public EExcursionType getType() {
        return type;
    }
}

