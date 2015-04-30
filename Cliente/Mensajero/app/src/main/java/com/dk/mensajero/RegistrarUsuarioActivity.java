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
import android.widget.Spinner;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.widget.AdapterView.*;
import static com.dk.mensajero.RegistrarUsuarioActivity.country.*;


public class RegistrarUsuarioActivity extends ActionBarActivity implements OnItemSelectedListener {

    enum country{
        ARGENTINA,
        BRASIL,
        CHILE,
        PARAGUAY,
        URUGUAY,
        INVALID,
    }
    private static Button button_sbm;
    private TextView areaCode;
    private TextView phoneNumber;
    Spinner expList;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        this.phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        this.areaCode = (TextView) findViewById(R.id.areaCode);
        this.expandableListCountries();
        this.onClickButtonListener();
    }

    public void expandableListCountries(){
        expList = (Spinner)findViewById(R.id.expandableListCountries);
        this.adapter = ArrayAdapter.createFromResource(this, R.array.Paises, android.R.layout.simple_spinner_item);
        expList.setAdapter(adapter);
        expList.setOnItemSelectedListener(this);
        setArgCode();
    }

    public void onClickButtonListener(){
        button_sbm = (Button)findViewById(R.id.button);
        button_sbm.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.dk.mensajero.AjustesActivity");
                        startActivity(intent);

                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registrar_usuario, menu);
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
