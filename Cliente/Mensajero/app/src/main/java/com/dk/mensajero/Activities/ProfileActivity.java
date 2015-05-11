package com.dk.mensajero.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.dk.mensajero.DB.DbHelper;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.R;

import java.io.FileDescriptor;
import java.io.IOException;

public class ProfileActivity extends ActionBarActivity {
    private static final int SELECT_PICTURE = 1;
    private String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        DbHelper db = new DbHelper(this);
        User user = db.getUser();

        //Obtengo el telefono del userLogger
        this.getUserPhone();
        //nombre
        EditText name = (EditText)findViewById(R.id.profileUserName);
        name.setHint(user.getName());

        //foto de perfil
        ImageView picture = (ImageView) findViewById(R.id.imageButtonProfile);
        String profilePicturePath = user.getProfilePicture();
        if (profilePicturePath.equals("")) {
            picture.setImageDrawable(getResources().getDrawable(R.drawable.profile_default));
        } else {
            picture.setImageDrawable(Drawable.createFromPath(user.getProfilePicture()));
        }

        //estado
        Button state = (Button) findViewById(R.id.profileUserState);
        //TODO: pedir el estado al servidor
        state.setText("conectado");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void selectPicture(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Seleccione una foto de perfil: "), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    ImageView picture = (ImageView)findViewById(R.id.imageButtonProfile);
                    try{
                        picture.setImageBitmap(getBitmapFromUri(selectedImageUri));
                        DbHelper db = new DbHelper(this);
                        User user = db.getUser();
                        user.setProfilePicture(getPath(selectedImageUri));
                        db.updateUser(user);
                    }catch (IOException e) {
                        Log.i("WARNING: ",e.getMessage());
                    }

                }
            }
        }
    }


    public String getPath(Uri uri) {

        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    public void changeState(View view) {
        Button state = (Button)findViewById(R.id.profileUserState);
        CharSequence actualState =  state.getText();
        if(actualState.equals("Conectado"))
            state.setText(R.string.profile_state_disconnected);
        else
            state.setText(R.string.profile_state_connected);
    }

    public void saveProfile(View view){
        DbHelper db = new DbHelper(this);
        User user = db.getUser();

        //guardo el nombre
        EditText editText = (EditText) findViewById(R.id.profileUserName);
        String sName = editText.getText().toString();
        if (!sName.matches("")) {
            user.setName(sName);
        }

        //TODO: actualizar el usuario en el server

        //actualizo en la base de datos local
        db.updateUser(user);

        //vuelvo a la pantalla de settings.
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);


    }


    private void getUserPhone(){

        Intent intentExtras = getIntent();
        Bundle phoneBundle;
        if (intentExtras.hasExtra("phone")){
            phoneBundle = intentExtras.getExtras();
            this.phoneNumber = phoneBundle.getString("phone");
        }
    }
}
