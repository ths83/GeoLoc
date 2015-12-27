package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Business.Users.CUser;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.CMyApplication;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;

public class CSignInActivity extends AppCompatActivity {


    Intent signUpActivity, userHistory;

    Button validateSignIn;

    EditText numberPhone;

    EditText passwd;

    TextView forgotPasswd;



    //String constants
    public static final String SENDING_MESSAGE = "Envoi d'un SMS en cours...";
    public static final String WRONG_INFORMATIONS = "Mauvaise combinaison!\nVeuillez saisir à nouveau vos identifiants.";



    // utilisation de Parse
    private ParseObject userTable = new ParseObject("UserTable");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        forgotPasswd = (TextView) findViewById(R.id.forgotten);
        forgotPasswd.setClickable(false);
        numberPhone = (EditText) findViewById(R.id.login);
        passwd = (EditText) findViewById(R.id.mdp);
        validateSignIn = (Button) findViewById(R.id.sign_in_button);

        //listener permettant l'acces au bouton de connexion
        //pardéfaut il n'est pas disponible
        numberPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!numberPhone.getText().toString().equals("")) {
                    if(!passwd.getText().toString().equals(""))
                        validateSignIn.setEnabled(true);
                    passwd.setEnabled(true);
                    forgotPasswd.setTextColor(Color.BLUE);
                    forgotPasswd.setClickable(true);
                }
                else {
                    forgotPasswd.setClickable(false);
                    forgotPasswd.setTextColor(Color.GRAY);
                    validateSignIn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //listener permettant l'acces au bouton de connexion
        //pardéfaut il n'est pas disponible
        passwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!passwd.getText().toString().equals("") && !numberPhone.getText().toString().equals(""))
                    validateSignIn.setEnabled(true);
                else
                    validateSignIn.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        signUpActivity = new Intent(this, CSignUpActivity.class);
        userHistory = new Intent(this,CHistoryActivity.class);
    }

    //intent

    /**
     * acceder a l'inscription
     * @param view
     */
    public void toConnect(View view){
        startActivity(signUpActivity);
    }


    /**
     * verification des donnees saisies par l'utilisation apres clic du bouton VALIDER
     * @param view
     */

    public void askConnection(View view){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Userr");
        query.whereEqualTo("phoneNumber", Integer.parseInt(numberPhone.getText().toString()));
        query.getFirstInBackground(new GetCallback<ParseObject>(){
            public void done(ParseObject object, ParseException e){
                if (object != null) {
                    if(object.getString("password").equals(passwd.getText().toString())) {
                        CMyApplication.setCurrentUser(new CUser(object.getString("userName"), object.getInt("phoneNumber")));
                        startActivity(userHistory);
                    }
                   else
                        Toast.makeText(getBaseContext(), WRONG_INFORMATIONS, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    /**
     * envoi d'un sms si le lien de mot de passe oublie est selectionne
     * recherche du mot de passe dans Parse via un pointeur
     * @param view
     */
    public void sendForgottenPassword(View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Userr");
        query.whereEqualTo("phoneNumber", Integer.parseInt(numberPhone.getText().toString()));
        Toast.makeText(getBaseContext(), String.valueOf(Integer.parseInt(numberPhone.getText().toString())), Toast.LENGTH_SHORT).show();
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                Log.d("abc", "bonjour");
                Log.d("abc", object.getString("password"));
                SmsManager.getDefault().sendTextMessage(numberPhone.getText().toString(),"GeoLoc", object.getString("password"), null, null);
            }
        });

    }
}