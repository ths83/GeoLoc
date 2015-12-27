package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Map.CExcursionMap;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;

public class CUserParametersActivity extends AppCompatActivity {

    private TextView modifyNumberphone;

    private EditText modifyUsername;

    private EditText modifyPassword;

    private EditText confirmNewPasswd;

    private Button validateModification;

    private Intent userStats;

    private Intent userHistory;
    private Intent joinActivity;
    private Intent test_intent_settings;
    private Intent ar;
    private Intent test_intent_type;

    private Intent userParameters;
    private Intent map;



    //String constants
    public static final String USER_SETTINGS = "Paramètres de l'utilisateur";
    public static final String CHANGE_OK = "Modification effectuée";
    public static final String PASSWORD_DOES_NOT_MATCH = "Les mots de passe ne sont pas les mêmes!";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_user_parameters);


        userStats = new Intent(this,CUserStatisticsActivity.class);
        userHistory = new Intent(this, CHistoryActivity.class);
        joinActivity = new Intent(this , CJoinExcursionActivity.class);
        ar = new Intent(this,CARActivity.class);
        map = new Intent(this,CExcursionMap.class);

        modifyNumberphone = (TextView) findViewById(R.id.modifynumberphone);
        modifyUsername = (EditText) findViewById(R.id.modifyusername);
        modifyPassword = (EditText) findViewById(R.id.modifypasswd);
        confirmNewPasswd = (EditText) findViewById(R.id.confirmpasswd);
        validateModification = (Button) findViewById(R.id.ok);

        //envoi des donnees dans Mypplication.java
        modifyNumberphone.setText(String.valueOf(CMyApplication.getCurrentUser().getPhoneNumber()));
        modifyUsername.setText(String.valueOf(CMyApplication.getCurrentUser().getUsername()));

        //verifie que les nouveaux mots de passe saisies soient identiques
        //et effectue la modification dans Parse
        validateModification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modifyPassword.getText().toString().length() > 0 && confirmNewPasswd.getText().toString().equals(modifyPassword.getText().toString())) {
                    CMyApplication.modifyParameters
                            (modifyUsername.getText().toString(),
                                    modifyPassword.getText().toString(), confirmNewPasswd.getText().toString());
                    Toast.makeText(getBaseContext(), CHANGE_OK,
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), PASSWORD_DOES_NOT_MATCH,
                            Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    //intent
    public void testContactsDetails(View view){
        startActivity(userStats);
        this.finish();
    }

    /**
     * acces a l'historique de l'utilisateur
     * @param view
     */
    public void testHistory(View view){
        startActivity(userHistory);
        this.finish();
    }

    /**
     * acces aux excursions publiques
     * @param view
     */
    public void joinActivity(View view) {
        startActivity(joinActivity);
        this.finish();
    }

    public void test_type_create_excursion(View view){
        startActivity(test_intent_type);
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
