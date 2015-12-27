package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Database.ParseDatabase;


/**
 * Created by toms on 06/12/15.
 */

/**
 * interface permettant d'interagir avec Parse
 */
public interface IParsable {

    public boolean checkParseDatabase(Object... elements);
    public void addElementInDatabase(Object[] elements);
    public void removeElementFromParse();
}
