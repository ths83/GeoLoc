package fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;

import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.Applicative.Sign.Sign.CSignUp;
import fr.univtln.MasterDAPM.G2.AndroidProject.PerronTonnet.GeoLoc.R;

public class CSignUpActivity extends AppCompatActivity {

    private EditText numberPhone;
    private EditText password;
    private EditText passwordConfirmed;
    private EditText userName;

    private Button newUser;
    private Button connection;

    private ParseObject userTable = new ParseObject("UserTable");
    private Toolbar mToolbar;



    //String constants
    public static final String SIGN_IN = "Inscription";
    public static final String WRONG_NUMBER = "Veuillez entrer un numéro valide!";
    public static final String WRONG_PASSWORD = "Veuillez entrer un mot de passe valide!";
    public static final String WRONG_CONFIRMATION = "Les mots de passe doivent correspondre!";
    public static final String WRONG_NAME = "Veuillez entrer un nom valide!";
    public static final String WELCOME = "Bienvenue sur GeoLoc!\nPour vous connecter, rendez-vous sur la page de connexion!";
    public static final String USER_ALREADY_EXISTS = "Ce numéro a déjà un compte!";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        numberPhone = (EditText) findViewById(R.id.e_sign_up_phone);
        password = (EditText) findViewById(R.id.e_sign_up_password);
        passwordConfirmed = (EditText) findViewById(R.id.e_sign_up_pwconfirmation);
        userName = (EditText) findViewById(R.id.e_sign_up_name);
        newUser = (Button) findViewById(R.id.b_sign_up_ok);
        connection = (Button) findViewById(R.id.connexion);

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toConnect(v);
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(SIGN_IN);

        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(mToolbar);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    /**
     * permet de verifier que tous les champs sont remplis par l'utilisateur
     * et indique via des toast si ce n'est pas le cas
     * enregistre les valeurs dans la base de donnees Parse
     * @param view
     */
    public void signUpListener(View view) {
        if (numberPhone.getText().toString().equals(""))
            Toast.makeText(getBaseContext(), WRONG_NUMBER, Toast.LENGTH_SHORT).show();
        else if (password.getText().toString().equals(""))
            Toast.makeText(getBaseContext(), WRONG_PASSWORD, Toast.LENGTH_SHORT).show();
        else if (!password.getText().toString().equals(passwordConfirmed.getText().toString()))
            Toast.makeText(getBaseContext(), WRONG_CONFIRMATION, Toast.LENGTH_SHORT).show();
        else if (userName.getText().toString().equals(""))
            Toast.makeText(getBaseContext(), WRONG_NAME, Toast.LENGTH_SHORT).show();
        else {
            CSignUp CSignUp = new CSignUp();
            Object[] signUpElements = new Object[3];
            signUpElements[0] = Integer.parseInt(numberPhone.getText().toString());
            signUpElements[1] = userName.getText().toString();
            signUpElements[2] = password.getText().toString();
            CSignUp.addElementInDatabase(signUpElements);

            Toast.makeText(getBaseContext(), WELCOME, Toast.LENGTH_LONG).show();

        }
    }

    /**
     * se rendre a l'activite de connexion
     * @param view
     */
    public void toConnect(View view){
        startActivity(new Intent(this,CSignInActivity.class));
    }

}
