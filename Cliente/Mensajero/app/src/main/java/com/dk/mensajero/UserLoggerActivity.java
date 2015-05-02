package com.dk.mensajero;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import static android.widget.AdapterView.*;
import static com.dk.mensajero.UserLoggerActivity.country.*;


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
    private static Button button_ok;
    private TextView areaCode;
    private TextView phoneNumber;
    private TextView userPhone;
    private TextView areaCodeView;
    private RelativeLayout numberConfirmationMessage_rl;
    private RelativeLayout mirror_rl;
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
        this.mirror_rl = (RelativeLayout) findViewById(R.id.mirror);
        this.areaCodeView = (TextView) findViewById(R.id.areaCodeConfirmationMessage);
        this.phoneNumberNotifacitionView = (TextView) findViewById(R.id.phoneNumberNotification);
        this.expandableListCountries();
        this.onOkButtonClick();
        this.onYesButtonClick();
        this.onEditButtonClick();
    }

    public void expandableListCountries(){
        expList = (Spinner)findViewById(R.id.expandableListCountries);
        this.adapter = ArrayAdapter.createFromResource(this, R.array.Paises, android.R.layout.simple_spinner_item);
        expList.setAdapter(adapter);
        expList.setOnItemSelectedListener(this);
        setArgCode();
    }

    public void onOkButtonClick(){
        button_ok = (Button) findViewById(R.id.buttonOk);
        button_ok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideTextsViews();
                    }
                }
        );

    }

     public void onYesButtonClick(){
        button_yes = (Button)findViewById(R.id.yesButton);
        button_yes.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.dk.mensajero.AjustesActivity");
                        startActivity(intent);

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
                        numberConfirmationMessage_rl.setVisibility(GONE);

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

    private void hideTextsViews(){
        this.userPhone = (TextView) findViewById(R.id.number);
        this.areaCodeView.setAlpha((float) 0.1);
        this.button_ok.setAlpha((float) 0.1);
        this.phoneNumberNotifacitionView.setAlpha((float) 0.1);
        CharSequence code = areaCode.getText();
        CharSequence enterPhone = phoneNumber.getText();
        userPhone.setText(code + " " + enterPhone);
        //numberConfirmationMessage_rl.setBackgroundColor(Color.RED);
        mirror_rl.setVisibility(VISIBLE);
        numberConfirmationMessage_rl.setBackgroundColor(Color.LTGRAY);
        numberConfirmationMessage_rl.setVisibility(VISIBLE);
        //numberConfirmationMessage_rl.setVisibility(VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
}
