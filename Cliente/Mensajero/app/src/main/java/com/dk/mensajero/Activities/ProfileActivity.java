package com.dk.mensajero.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.dk.mensajero.Entities.User;
import com.dk.mensajero.Interfaces.UpdateProfileCallback;
import com.dk.mensajero.R;
import com.dk.mensajero.Service.Service;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;

public class ProfileActivity extends ActionBarActivity {
    private static final int SELECT_PICTURE = 1;
    private static final int widthProfileSize = 280;
    private static final int heightProfileSize = 400;
    private boolean defaultPicture;
    EditText etName, etPassword;
    Button bState;
    ImageButton ibPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        User user = User.getUser(this);

        //nombre
        etName = (EditText)findViewById(R.id.profile_etName);
        etName.setText(user.getName());

        //contraseña
        etPassword = (EditText)findViewById(R.id.profile_etPassword);
        etPassword.setHint(R.string.newPassword);

        //foto de perfil
        ibPicture = (ImageButton) findViewById(R.id.profile_ibPicture);
        String profilePicture = user.getProfilePicture();
        defaultPicture = profilePicture.equals("default");
        if (defaultPicture) {
            ibPicture.setImageDrawable(getResources().getDrawable(R.drawable.profile_default));
        } else {
            ibPicture.setImageBitmap(stringToBitmap(profilePicture));
        }

        //estado
        bState = (Button) findViewById(R.id.profile_bState);

        if (user.getState())
            bState.setText(R.string.profile_state_connected);
        else
            bState.setText(R.string.profile_state_disconnected);
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
                    ImageView picture = (ImageView)findViewById(R.id.profile_ibPicture);
                    try{
                        Bitmap profileBitmap = getBitmapFromUri(selectedImageUri);
                        profileBitmap = Bitmap.createScaledBitmap(profileBitmap,widthProfileSize,heightProfileSize,true);
                        picture.setImageBitmap(profileBitmap);
                        defaultPicture = false;
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
        Button state = (Button)findViewById(R.id.profile_bState);
        CharSequence actualState =  state.getText();
        if(actualState.equals("Conectado"))
            state.setText(R.string.profile_state_disconnected);
        else
            state.setText(R.string.profile_state_connected);
    }


    public void saveProfile(View view) {
        final User user = User.getUser(this);

        //guardo el nombre
        etName = (EditText) findViewById(R.id.profile_etName);
        String sName = etName.getText().toString();
        if (!sName.matches("")) {
            user.setName(sName);
        }

        //guardo la contraseña
        etPassword = (EditText) findViewById(R.id.profile_etPassword);
        String sPassword = etPassword.getText().toString();
        if (!sPassword.matches("")) {
            user.setPassword(sPassword);
        }

        //guardo el estado
        bState = (Button) findViewById(R.id.profile_bState);
        String sState = (String) bState.getText();
        if (sState.equals("Conectado")) user.setState(true);
        else user.setState(false);

        //guardo la foto de perfil
        if (!defaultPicture) {
            ibPicture = (ImageButton) findViewById(R.id.profile_ibPicture);
            Bitmap bitmapPicture = ((BitmapDrawable) ibPicture.getDrawable()).getBitmap();
            user.setProfilePicture(bitmapToString(bitmapPicture));
        }



        final Service serviceRequest = new Service(this);
        serviceRequest.updateUserProfileInBackground(user, new UpdateProfileCallback() {
                    @Override
                    public void done(boolean connectionResult) {
                        if (connectionResult) {
                            //actualizo el usuario en la base de datos local
                            user.save(ProfileActivity.this);
                            startActivity(new Intent(ProfileActivity.this, ConversationsListActivity.class));
                        }else{
                            serviceRequest.showFailConnectionServerMessage(ProfileActivity.this);
                        }
                    }
                }
        );

    }

    /**
     * Convierte un bitmap en string.
     * @param bitmap la imagen a ser convertida.
     * @return String la imagen codificada en base64.
     */
    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    /**
     * Convierte un string en bitmap.
     * @param encodedString el string a transformar en bitmap.
     * (precondition: encodedString debería estar en base64)
     * @return bitmap (convertido desde el string recibido.)
     */
    private Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            Bitmap image_default = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.profile_default);
            return image_default;
        }
    }

}
