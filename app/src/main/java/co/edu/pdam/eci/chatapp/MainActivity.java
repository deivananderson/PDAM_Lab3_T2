package co.edu.pdam.eci.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.edu.pdam.eci.chatapp.Model.Message;

public class MainActivity
    extends AppCompatActivity
{

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    DatabaseReference databaseReference = database.getReference( "messages" );

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }

    public void onSendClicked(View view) {
        Message mensaje = new Message();
        EditText name = (EditText) findViewById(R.id.sender);
        EditText mes = (EditText) findViewById(R.id.message);
        mensaje.setMessage(mes.getText().toString());
        mensaje.setUser(name.getText().toString());
        databaseReference.push().setValue(mensaje);
    }
}
