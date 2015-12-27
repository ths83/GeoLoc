package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;

public class CHistoryActivity extends AppCompatActivity {

    TextView textView;
    Intent userStat;
    private Intent userHistory;
    private Intent joinExcursion;
    private Intent ar;
    private Intent createActivity;

    private Intent userParameters;
    private Toolbar mToolbar;



    //String constants
    public static final String HISTORY = "History";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        textView = (TextView) findViewById(R.id.test1);
        userStat = new Intent(this, CUserStatisticsActivity.class);
        userHistory = new Intent(this, CHistoryActivity.class);
        joinExcursion = new Intent(this, CJoinExcursionActivity.class);
        ar = new Intent(this, CARActivity.class);
        createActivity = new Intent(this, CCreateExcursionActivity.class);
        userParameters = new Intent(this, CUserParametersActivity.class);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(HISTORY);

        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(mToolbar);
    }


    //intent

    public void testContactsDetails(View view){
        startActivity(userStat);
        this.finish();
    }

    /**
     * visualiser les statistiques de l'utilisateur
     * @param view
     */
    public void goToUserStatistics(View view){
        startActivity(userHistory);
        this.finish();
    }

    /**
     * se rendre à la page de choix d'excursion
     * @param view
     */
    public void testSelectAct(View view) {
        startActivity(joinExcursion);
        this.finish();
    }


    /**
     * se rendre a la page de creation d'activite
     * @param view
     */
    public void test_type_create_excursion(View view){
        startActivity(createActivity);
        this.finish();
    }

    /**
     * se rendre à la page de modification des param de l'utilisateur
     * @param view
     */
    public void setUserParameters(View view){
        startActivity(userParameters);
        this.finish();
    }

    /**
     * se déconnecter
     * @param view
     */
    public void disconnectUser(View view) {
        CMyApplication.resetAttributes();
        this.finish();
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
