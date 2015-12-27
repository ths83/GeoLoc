package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.Serializable;
import java.util.List;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.CExcursion;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Statistics.CCurrentStatistics;

/**
 * Created by imnotfood on 08/12/15.
 */
public class CUser implements Serializable{
    private static int idCounter = 0;
    private int id;
    private int phoneNumber;
    private String username;
    private String password;
    private List<CUser> friendList;
    private List<CCurrentStatistics> history;

    /**
     * constructerus
     */

    public CUser(){}

    public CUser(String username) {
        this.id = ++idCounter;
        this.username = username;
    }

    public CUser(String username, int phoneNumber) {
        this.username = username;
        this.phoneNumber = phoneNumber;
    }


    public CUser(int phoneNumber, String username, String password) {
        this.id = ++idCounter;

        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
    }

    public CUser(int phoneNumber, String password) {
        this.id = ++idCounter;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public CUser(int phoneNumber, String username, String password, List<CUser> friendList, List<CCurrentStatistics> history) {
        this.id = ++idCounter;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.friendList = friendList;
        this.history = history;

    }

    public ParseObject usrTabl;
    public ParseObject excTabl;

    /**
     * mise a jour dans CMyApplication de l'excursion de l'utilisateur
     * @param excursion
     */
   public void setCurrentExcursion(CExcursion excursion) {
        usrTabl = new ParseObject("Userr");
        ParseQuery<ParseObject> currentUserQuery = new ParseQuery<ParseObject>("Userr");
        currentUserQuery.whereEqualTo("phoneNumber", this.phoneNumber);

        excTabl = new ParseObject("Excursion");
        ParseQuery<ParseObject> currentExcursionQuery = new ParseQuery<ParseObject>("Excursion");
        currentExcursionQuery.whereEqualTo("excursionName", excursion.getName());

        try {
            usrTabl = currentUserQuery.getFirst();
            excTabl = currentExcursionQuery.getFirst();
        } catch (ParseException e) {
            Log.d("abc", "fail");
        }

        usrTabl.put("currentExcursion", excTabl);
    }

    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CUser> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<CUser> friendList) {
        this.friendList = friendList;
    }

    public List<CCurrentStatistics> getHistory() {
        return history;
    }

    public void setHistory(List<CCurrentStatistics> history) {
        this.history = history;
    }


}
