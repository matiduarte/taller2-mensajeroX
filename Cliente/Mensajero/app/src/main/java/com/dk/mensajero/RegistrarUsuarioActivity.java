package com.dk.mensajero;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dk.mensajero.R.id.exp_list;


public class RegistrarUsuarioActivity extends ActionBarActivity {

    HashMap<String, List<String>> paisesDesplegable;
    List<String> listaDePaises;
    ExpandableListView Exp_list;
    AdaptadorDePaises adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        Exp_list = (ExpandableListView) findViewById(exp_list);
        paisesDesplegable = ProveedorDatos.getInfo();
        listaDePaises = new ArrayList<String>(paisesDesplegable.keySet());
        adaptador = new AdaptadorDePaises(this, paisesDesplegable, listaDePaises);
        Exp_list.setAdapter(adaptador);
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
}
