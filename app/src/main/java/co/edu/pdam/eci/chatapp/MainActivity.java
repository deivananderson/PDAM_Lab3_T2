package co.edu.pdam.eci.chatapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import co.edu.pdam.eci.chatapp.Model.Message;
import co.edu.pdam.eci.chatapp.adapter.MessagesAdapter;

public class MainActivity
    extends AppCompatActivity
{
    private static final  int REQUEST_IMAGE_CAPTURE = 0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    FirebaseStorage storage = FirebaseStorage.getInstance();

    DatabaseReference databaseReference = database.getReference( "messages" );

    MessagesAdapter messagesAdapter = new MessagesAdapter(this);

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        ChildEventListener messagesListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addChildEventListener( messagesListener );

    }

    public void onSendClicked(View view) {
        Message mensaje = new Message();
        EditText name = (EditText) findViewById(R.id.sender);
        EditText mes = (EditText) findViewById(R.id.message);
        mensaje.setMessage(mes.getText().toString());
        mensaje.setUser(name.getText().toString());
        databaseReference.push().setValue(mensaje);
    }

    public void onSendPhotoClicked( View view )
    {
        Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        if ( takePictureIntent.resolveActivity( getPackageManager() ) != null )
        {
            startActivityForResult( takePictureIntent, REQUEST_IMAGE_CAPTURE );
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK )
        {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get( "data" );
            UploadPostTask uploadPostTask = new UploadPostTask();
            uploadPostTask.execute( imageBitmap );
        }
    }


    @SuppressWarnings("VisibleForTests")
    private class UploadPostTask
            extends AsyncTask<Bitmap, Void, Void>
    {

        @Override
        protected Void doInBackground( Bitmap... params )
        {
            StorageReference storageRef = storage.getReferenceFromUrl("gs://pdamlab3.appspot.com/");

            Bitmap bitmap = params[0];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress( Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream );
            storageRef.child( UUID.randomUUID().toString() + "jpg" ).putBytes(
                    byteArrayOutputStream.toByteArray() ).addOnSuccessListener(
                    new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess( UploadTask.TaskSnapshot taskSnapshot )
                        {
                            if ( taskSnapshot.getDownloadUrl() != null )
                            {
                                String imageUrl = taskSnapshot.getDownloadUrl().toString();
                                final Message message = new Message( imageUrl );
                                runOnUiThread( new Runnable()
                                {
                                    @Override
                                    public void run()
                                    {
                                        messagesAdapter.addMessage( message );
                                    }
                                } );
                            }
                        }
                    } );

            return null;
        }
    }


}
