package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Applicative.Sign.Sign;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Database.ParseDatabase.IParsable;

public class CSignIn implements IParsable {

    @Override
    public boolean checkParseDatabase(Object[] elements) {
        /*CUser user = new CUser((int)elements[0], (String)elements[1]);
        ParseQuery<ParseObject> userLogIn = ParseQuery.getQuery("Userr");
        userLogIn.whereEqualTo("phoneNumber", user.getPhoneNumber());
        userLogIn.getFirstInBackground(new GetCallback<ParseObject>(){
            @Override
            public void done(ParseObject object, ParseException e){
                if(object != null)
            }
        }*/
        return false;
    }

    @Override
    public void addElementInDatabase(Object ... elements) {
    }

    @Override
    public void removeElementFromParse() {

    }


}
