package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Applicative.Sign.Sign;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users.CUser;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Database.ParseDatabase.IParsable;

public class CSignUp implements IParsable{


    @Override
    public boolean checkParseDatabase(Object[] elements) {
        return false;
    }


    /**
     * cree un nouvel utilisateur aprs l'inscription, et l'ajoute dans la base de donnees
     * @param elements
     */
    @Override
    public void addElementInDatabase(Object[] elements) {
        final CUser USER = new CUser((int) elements[0], (String) elements[1], (String) elements[2]);

        ParseQuery<ParseObject> signInQuery = ParseQuery.getQuery("Userr");
        signInQuery.whereEqualTo("phoneNumber", USER.getPhoneNumber());
        signInQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object == null)
                    newUserInDatabase(USER);

            }
        });
    }


    @Override
    public void removeElementFromParse() {

    }

    /**
     * ajoute dans la table USERR le nouvel utlisateur
     * @param user
     */
    public void newUserInDatabase(CUser user) {
        final ParseObject userTable = new ParseObject("Userr");
        userTable.put("userId", user.getId());
        userTable.put("phoneNumber", user.getPhoneNumber());
        userTable.put("userName", user.getUsername());
        userTable.put("password", user.getPassword());
        userTable.saveInBackground();
    }

}
