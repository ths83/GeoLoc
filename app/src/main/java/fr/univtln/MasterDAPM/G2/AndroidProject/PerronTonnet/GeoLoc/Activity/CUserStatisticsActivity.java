package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Map.CExcursionMap;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;

public class CUserStatisticsActivity extends AppCompatActivity {

    private TextView excursionType;

    private TextView excursionTime;

    private TextView excursionDistance;

    private TextView averageSpeed;

    private TextView excursionName;

    private TextView excursionStartedDate;

    private Intent historyIntent;
    private Intent intent;

    private Object[]detailsUserActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_statistics_details);

        excursionName = (TextView) findViewById(R.id.name);
        excursionStartedDate = (TextView) findViewById(R.id.date);
        excursionType = (TextView) findViewById(R.id.excursionType);
        excursionTime = (TextView) findViewById(R.id.excursionTime);
        excursionDistance = (TextView) findViewById(R.id.excursionDistance);
        averageSpeed = (TextView) findViewById(R.id.excursionAverageSpeed);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        historyIntent = new Intent(this, CHistoryActivity.class);

        detailsUserActivity =  getIntent().getStringArrayExtra(CExcursionMap.USER_DISTANCE);

        excursionName.setText(String.valueOf(detailsUserActivity[0]));
        excursionStartedDate.setText(String.valueOf(detailsUserActivity[1]));
        excursionDistance.setText(String.valueOf(detailsUserActivity[2]));
        excursionType.setText(String.valueOf(detailsUserActivity[3]));
        excursionTime.setText(String.valueOf(detailsUserActivity[4]));
        averageSpeed.setText(String.valueOf((detailsUserActivity[5])));


        Toast.makeText(getBaseContext(), String.valueOf(detailsUserActivity[2]), Toast.LENGTH_SHORT).show();

    }

    /**
     * fin de l'excursion et basculement sur la page des statistiques de l'excursion
     * de l'utilisateur
     * @param view
     */
    public void closeActivity(View view) {
        startActivity(historyIntent);
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
