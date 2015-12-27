package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;

public class CSelectChoiceActivity extends AppCompatActivity {

    private Intent logIn;
    private Intent suscribeTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_choice);

        logIn = new Intent(this,CSignInActivity.class);
        suscribeTo = new Intent(this,CSignUpActivity.class);

    }

    //intent

    /**
     * methode permettant la demande de connexion apres clic sur VALIDER
     * @param view
     */
    public void toLogIn(View view){
        startActivity(logIn);
    }

    /**
     * methode permettant l'ouverture de l'inscription

     * @param view
     */
    public void toSuscribeIn(View view){
        startActivity(suscribeTo);
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
