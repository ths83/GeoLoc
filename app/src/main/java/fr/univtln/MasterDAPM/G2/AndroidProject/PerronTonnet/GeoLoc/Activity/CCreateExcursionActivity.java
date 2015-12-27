package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.CExcursion;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.EExcursionType;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Statistics.CCurrentStatistics;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Map.CExcursionMap;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users.CUser;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Waypoints.CWaypoint;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;


public class CCreateExcursionActivity extends AppCompatActivity {

    public static final String C_EXCURSION = "fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.ExcursionName";

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar mToolbar;

    CheckBox privateActivity;


    TextView activityTypeToChoose;

    private RadioButton selectPedestrialExcursion;
    private RadioButton selectRunningExcursion;
    private RadioButton selectCarExcursion;

    private EditText excursionName;

    private EExcursionType eExcursionType;

    private boolean accessExcursion;

    Intent userHistoryActivity;
    Intent intentSelectPublicAct;
    Intent ar;
    Intent type;
    Intent setUserStatisticActivity;
    Intent map;


    private String putExcursionName;
    private ArrayList<CWaypoint> cWaypoints;
    private ArrayList<CUser> cUsers;



    //String constants
    public static final String CREATE_EXCURSION = "Créer une excursion";
    public static final String EXCURSION_PRIVATE = "L'excursion sera privée";
    public static final String EXCURSION_PUBLIC = "L'excursion sera publique";
    public static final String PEDESTRIAL = "Pedestrial Selected";
    public static final String RUNNING = "Running Selected";
    public static final String CAR = "Car Selected";
    public static final String SPECIFY_EXCURSION_NAME = "Vous devez spécifier un nom!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_excursion);

        excursionName = (EditText) findViewById(R.id.excursion_name);

        selectPedestrialExcursion = (RadioButton) findViewById(R.id.pedestrial_way);
        selectRunningExcursion = (RadioButton) findViewById(R.id.running_way);
        selectCarExcursion = (RadioButton) findViewById(R.id.car_way);


        setUserStatisticActivity = new Intent(this,CUserStatisticsActivity.class);
        userHistoryActivity = new Intent(this, CHistoryActivity.class);
        intentSelectPublicAct = new Intent(this , CJoinExcursionActivity.class);
        ar = new Intent(this,CARActivity.class);
        map = new Intent(this,CExcursionMap.class);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(CREATE_EXCURSION);

        activityTypeToChoose = (TextView) findViewById(R.id.activitytype);

        cWaypoints = new ArrayList<>();
        cUsers = new ArrayList<>();

        //visibilité de l'excursion
        privateActivity = (CheckBox) findViewById(R.id.privateCheckBox);
        privateActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (privateActivity.isChecked()) {
                    accessExcursion = true;
                    Toast.makeText(getBaseContext(), EXCURSION_PRIVATE, Toast.LENGTH_SHORT).show();
                } else {
                    accessExcursion = false;
                    Toast.makeText(getBaseContext(), EXCURSION_PUBLIC, Toast.LENGTH_SHORT).show();
                }

            }
        });

        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(mToolbar);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.drawerToggle = new ActionBarDrawerToggle(this,this.drawerLayout,0,0);
        this.drawerLayout.setDrawerListener(this.drawerToggle);


    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // synchroniser le drawerToggle après la restauration via onRestoreInstanceState
        drawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * permet l'affichage et l'utlisation du drawer
     */

    private void toDragAndDropDrawer() {
        if(mToolbar.getAlpha() == 1){ //si alpha==1 alors elle est affichee

            //cacher
            mToolbar.animate()
                    .alpha(0) //la rendre invisible
                    .translationY(-mToolbar.getHeight()) //la déplacer vers le haut
                    .start();
        }
        else{ //si alpha==0 alors elle est cachee

            //afficher
            mToolbar.animate()
                    .alpha(1) //la rendre visible
                    .translationY(0) //retour à la position d'origine
                    .start();
        }
    }


    //type d'excursion

    /**
     * sélection du type marche de l'excursion
     * @param view
     * @return
     */
    public EExcursionType selectPedestrialExcursion(View view){
        eExcursionType = EExcursionType.PEDESTRIAL;
        Toast.makeText(getBaseContext(), PEDESTRIAL, Toast.LENGTH_SHORT).show();
        return eExcursionType;
    }

    /**
     * sélection du type course à pied de l'excursion
     * @param view
     * @return
     */
    public EExcursionType selectRunningExcursion(View view){
        eExcursionType = EExcursionType.RUNNING;
        Toast.makeText(getBaseContext(), RUNNING, Toast.LENGTH_SHORT).show();
        return eExcursionType;
    }

    /**
     * sélection du type Véhicule
     * @param view
     * @return
     */

    public  EExcursionType selectCarExcursion(View view){
        eExcursionType = EExcursionType.CAR;
        Toast.makeText(getBaseContext(), CAR, Toast.LENGTH_SHORT).show();
        return eExcursionType;
    }



    // intent

    /**
     * permet l'ouverture de l'historique
     * @param view
     */

    public void testHistory(View view){
        startActivity(userHistoryActivity);
        this.finish();
    }

    /**
     * un clic sur le bouton de validation permet d'afficher la carte
     * et de sauvegarder les données en globales dans le fichier CMyApplication.java
     * @param view
     */

    public void startNewExcursion(View view){
        putExcursionName = excursionName.getText().toString();
        if(putExcursionName.length() > 0) {
            CMyApplication.setCurrentExcursion(new CExcursion(putExcursionName, eExcursionType, accessExcursion, cUsers, cWaypoints, CMyApplication.getCurrentUser()));
            CMyApplication.getCurrentExcursion().addElementInDatabase(new Object[]{putExcursionName, eExcursionType, accessExcursion, cUsers, cWaypoints});
            CMyApplication.setCurrentStatistics(new CCurrentStatistics(CMyApplication.getCurrentUser(), CMyApplication.getCurrentExcursion()));
            CMyApplication.getCurrentStatistics().newStatsInDatabase();
            startActivity(map);
            this.finish();
        }
        else
            Toast.makeText(getBaseContext(), SPECIFY_EXCURSION_NAME, Toast.LENGTH_SHORT).show();
    }



    /**
     * permet l'ouverture de la sélection d'excursion à rejoindre
     * @param view
     */
    public void testSelectAct(View view) {
        startActivity(intentSelectPublicAct);
        this.finish();
    }

    /**
     * permet l'ouverture de la creation d'excursion
     * @param view
     */
    public void test_type_create_excursion(View view){
        startActivity(type);
        this.finish();
    }


    /**
     * deconnexion de l'utlisateur lors de la selection dans le drawer de "se deconnecter"
     * @param view
     */

    public void toDisconnectUser(View view) {
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
