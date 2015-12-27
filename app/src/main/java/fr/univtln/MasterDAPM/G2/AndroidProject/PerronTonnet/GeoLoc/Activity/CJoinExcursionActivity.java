package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Excursion.CExcursion;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Database.ParseDatabase.SCheckDatabase;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;

public class CJoinExcursionActivity extends AppCompatActivity {

    Intent userStats;
    private Intent userHistory;
    private Intent joinExcursion;
    private Intent ar;
    private Intent createActivity;

    private Toolbar mToolbar;

    private CExcursion[] listExcursions;

    private Intent userParameters;

    //String constants
    public static final String JOIN_EXCURSION = "Rejoindre une Excursion";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_excursion);

        while (SCheckDatabase.getInstance().excursions == null);

        //extraction des données et mise en place dans un tableau de String
        Object[] o = SCheckDatabase.getInstance().excursions.toArray();

        listExcursions = new CExcursion[o.length];

        for (int i =0 ; i<o.length ; i++)
            listExcursions[i] = (CExcursion)o[i];

        // affichage des données
        ListView list = (ListView)findViewById(R.id.excursionView);

        ArrayAdapter<CExcursion> adapter = new ArrayAdapter<CExcursion>(this, android.R.layout.simple_list_item_1, listExcursions);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CMyApplication.setCurrentExcursion((CExcursion) parent.getItemAtPosition(position));
                CMyApplication.getCurrentExcursion().addParticipant(CMyApplication.getCurrentUser());
                CMyApplication.getCurrentUser().setCurrentExcursion(CMyApplication.getCurrentExcursion());
            }
        });

        //intent

        userStats = new Intent(this,CUserStatisticsActivity.class);
        userHistory = new Intent(this, CHistoryActivity.class);
        joinExcursion = new Intent(this , CJoinExcursionActivity.class);
        ar = new Intent(this,CARActivity.class);
        createActivity = new Intent(this, CCreateExcursionActivity.class);
        userParameters = new Intent(this,CUserParametersActivity.class);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(JOIN_EXCURSION);

        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(mToolbar);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void testContactsDetails(View view){
        startActivity(userStats);
    }

    public void testHistory(View view){
        startActivity(userHistory);
    }


    public void testSelectAct(View view) { startActivity(joinExcursion); }
    public void disconnectUser(View view) {
        CMyApplication.resetAttributes();
        this.finish();
    }

    public void testtest(View view){startActivity(ar);}
    public void test_type_create_excursion(View view){startActivity(createActivity);}

    public void setUserParameters(View view){
        startActivity(userParameters);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
