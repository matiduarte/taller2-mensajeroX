package com.dk.mensajero.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dk.mensajero.Entities.User;
import com.dk.mensajero.R;

import static android.widget.AdapterView.*;
import static com.dk.mensajero.Activities.UserLoggerActivity.country.*;


public class UserLoggerActivity extends ActionBarActivity implements OnItemSelectedListener {

    enum country{
        ARGENTINA,
        BRASIL,
        CHILE,
        PARAGUAY,
        URUGUAY,
        INVALID,
    }

    private static Button button_yes;
    private static Button button_edit;
    private TextView areaCode;
    private TextView phoneNumber;
    private TextView userPhone;
    private TextView areaCodeView;
    private RelativeLayout numberConfirmationMessage_rl;
    private TextView phoneNumberNotifacitionView;
    Spinner expList;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_logger);
        this.phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        this.areaCode = (TextView) findViewById(R.id.areaCode);
        this.numberConfirmationMessage_rl = (RelativeLayout) findViewById(R.id.numberConfirmationMessage);
        this.areaCodeView = (TextView) findViewById(R.id.areaCodeConfirmationMessage);
        this.phoneNumberNotifacitionView = (TextView) findViewById(R.id.phoneNumberNotification);
        this.expandableListCountries();
        this.onYesButtonClick();
        this.onEditButtonClick();
        this.onEditActionListener();

    }

    public void expandableListCountries(){
        expList = (Spinner)findViewById(R.id.expandableListCountries);
        this.adapter = ArrayAdapter.createFromResource(this, R.array.Paises, android.R.layout.simple_spinner_item);
        expList.setAdapter(adapter);
        expList.setOnItemSelectedListener(this);
        setArgCode();
    }

    /**
     * Create a Bundle with the user phone and pass it to the profileActivity
     */
    private void startRegisterActivity(){

        Intent registerIntent = new Intent(this, RegisterActivity.class);
        Bundle bundlePhone = new Bundle();
        bundlePhone.putString("phone",this.phoneNumber.getText().toString());
        registerIntent.putExtras(bundlePhone);
        startActivity(registerIntent);
    }

     public void onYesButtonClick(){
        button_yes = (Button)findViewById(R.id.yesButton);
        button_yes.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        startRegisterActivity();

                    }
                }
        );

    }

    public void onEditButtonClick(){
        button_edit = (Button)findViewById(R.id.editButton);
        button_edit.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        hideNumberConfirmationRL();
                        showAreaCode();
                        showPhoneNumber();
                        showPhoneNumberNotification();
                        showAreaCodeTextView();
                        showSpinner();
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_logger, menu);
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

    public void setArgCode(){
        areaCode.setText("+54");
    }

    public void setBraCode(){
        areaCode.setText("+55");
    }

    public void setChiCode(){
        areaCode.setText("+56");
    }

    public void setUruCode(){
        areaCode.setText("+598");
    }

    public void setParCode(){
        areaCode.setText("+595");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(30,144,255));
        ((TextView) parent.getChildAt(0)).setTextSize(18);
       switch (this.getCountry((int) id)) {
            case ARGENTINA:
                setArgCode();
                break;
            case BRASIL:
                setBraCode();
                break;
            case CHILE:
                setChiCode();
                break;
            case PARAGUAY:
                setParCode();
                break;
           case URUGUAY:
               setUruCode();
               break;
            default:
                break;
        }

    }

    private void showPhoneNumber(){
        this.phoneNumber.setVisibility(VISIBLE);
    }

    private void showAreaCode(){
        this.areaCode.setVisibility(VISIBLE);
    }

    private void hidePhoneNumber(){
        this.phoneNumber.setVisibility(INVISIBLE);
    }

    private void hideAreaCode(){
        this.areaCode.setVisibility(INVISIBLE);
    }


    private void showUserPhone(){
        this.showNumberConfirmationRL();
        this.userPhone = (TextView) findViewById(R.id.number);
        CharSequence code = areaCode.getText();
        CharSequence enterPhone = phoneNumber.getText();
        userPhone.setText(code + " " + enterPhone);
    }

    private void hideSpinner(){
        this.expList.setVisibility(INVISIBLE);
    }

    private void showSpinner(){
        this.expList.setVisibility(VISIBLE);
    }

    private void hidePhoneNumberNotification(){
        this.phoneNumberNotifacitionView.setAlpha((float) 0.3);
    }

    private void showPhoneNumberNotification(){
        this.phoneNumberNotifacitionView.setAlpha((float) 1);
    }

    private void showNumberConfirmationRL(){
        numberConfirmationMessage_rl.setVisibility(VISIBLE);
    }

    private void hideNumberConfirmationRL(){
        numberConfirmationMessage_rl.setVisibility(INVISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void hideAreaCodeTextView(){
        this.areaCodeView.setVisibility(INVISIBLE);
    }

    private void showAreaCodeTextView(){
        this.areaCodeView.setVisibility(VISIBLE);
    }

    private country getCountry(int id) {
        if (id == 0)
            return ARGENTINA;
        if (id == 1)
            return BRASIL;
        if (id == 2)
            return CHILE;
        if (id == 3)
            return PARAGUAY;
        if (id == 4)
            return URUGUAY;

        return INVALID;
    }

    public void onEditActionListener(){

        this.phoneNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //verifies what key is pressed, in our case verifies if the DONE key is pressed
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String phone = phoneNumber.getText().toString();
                    if (phone.equals("")){
                        showEmptyPhoneMessage();
                    } else {
                        showUserPhone();
                        hideAreaCode();
                        hidePhoneNumber();
                        hideSpinner();
                        hidePhoneNumberNotification();
                        hideAreaCodeTextView();
                    }
                }
                return false;
            }
        });

    }

    private void showEmptyPhoneMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(UserLoggerActivity.this);
        dialogBuilder.setMessage("No se ingreso ningun número");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }

}
