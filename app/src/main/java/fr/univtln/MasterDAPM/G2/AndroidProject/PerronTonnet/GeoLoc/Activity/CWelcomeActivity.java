package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.parse.Parse;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Database.ParseDatabase.SCheckDatabase;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;

public class CWelcomeActivity extends AppCompatActivity {

    private Intent welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "dKHr2pmXDmwRmqtsJe6PIUGpVRqRMqEs4fNQP3fF", "RnvjFfOklkICBTAkQOEg2kRNux4YIgGrqiVPGHGo");


        // initialisation du singleton pour afficher les excursions courantes dans l'activite associee
        SCheckDatabase.getInstance().check();

        welcome = new Intent(this,CSelectChoiceActivity.class);
    }


    /**
     * acces à la deuxieme activite de l'application permettant
     * le choix de s'inscrire ou de se connecter
     * @param view
     */
    public void welcomeHere(View view){
        startActivity(welcome);
    }


    /**
     * blocage du bouton de retour de l'appareil
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
