package co.edu.pdam.eci.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.edu.pdam.eci.chatapp.R;

public class LoginActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{

    FirebaseAuth firebaseAuth;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        loginButton = (Button) findViewById(R.id.loginButton);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if ( user != null )
        {
            startActivity( new Intent( this, MainActivity.class ) );
            finish();
        }
    }


    public void onLoginClicked( View view )
    {
        loginButton.setEnabled( false );
        firebaseAuth.signInAnonymously();
    }


}
