package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc;

import android.app.Application;
import android.content.res.Configuration;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.CExcursion;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Statistics.CCurrentStatistics;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users.CUser;

/**
 * Created by Lois on 11/12/15.
 */
public class CMyApplication extends Application {
    private static CUser currentUser;
    private static CExcursion currentExcursion;
    private static CCurrentStatistics currentStatistics;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public static CCurrentStatistics getCurrentStatistics() {
        return currentStatistics;
    }

    /**
     * mise à jour des statistiques courantes de l'utlisateur
     * @param currentStatistics
     */
    public static void setCurrentStatistics(CCurrentStatistics currentStatistics) {
        CMyApplication.currentStatistics = currentStatistics;
    }

    // getters and setters
    public static CUser getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(CUser newUser){
        currentUser = newUser;
    }

    public static CExcursion getCurrentExcursion() {
        return currentExcursion;
    }

    public static void setCurrentExcursion(CExcursion newExcursion) {
        currentExcursion = newExcursion;
    }

    /**
     * permet de retourner le pointeur d'une statistique pour tous les fichiers
     * de l'application
     * @return
     */
    public static String statsPointer(){
        final String[] id = new String[1];
        ParseQuery parseQuery = ParseQuery.getQuery("UserStats");
        parseQuery.whereEqualTo("user", CMyApplication.userPointer());
        parseQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                id[0] = object.getObjectId();
            }

        });
        return id[0];
    }


    /**
     * retourne le pointeur de l'utlisateur courant
     * @return
     */
    public static String userPointer(){
        final String[] id = new String[1];
        String ids;
        ParseQuery parseQuery = ParseQuery.getQuery("Userr");
        parseQuery.whereEqualTo("phoneNumber", CMyApplication.getCurrentUser().getPhoneNumber());
        parseQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                id[0] = object.getObjectId();
            }

        });
        return id[0];
    }


    /**
     * appele lors de la modififcation des parametres de
     * l'utlisateur, elle permet la mise a jour des valeurs
     * dans la table
     * @param username
     * @param passwd
     * @param cpasswd
     * @return
     */
    public static boolean modifyParameters(final String username, final String passwd, final String cpasswd) {

        ParseQuery parseQuery = ParseQuery.getQuery("Userr");
        parseQuery.whereEqualTo("phoneNumber", CMyApplication.getCurrentUser().getPhoneNumber());
        parseQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (!username.equals(object.getString("userName")) && !username.equals(null))
                    currentUser.setUsername(username);
                object.put("userName", username);
                if (!passwd.equals(object.getString("password")) && passwd.equals(cpasswd))
                    object.put("password", passwd);
                object.saveInBackground();
            }
        });
        return true;
    }

    /**
     * si la deconnexion est choisie , toutes variables sont reinitialisees
     */
    public static void resetAttributes() {
        currentUser = new CUser();
        currentExcursion = new CExcursion();
        currentStatistics = new CCurrentStatistics();
    }
}
