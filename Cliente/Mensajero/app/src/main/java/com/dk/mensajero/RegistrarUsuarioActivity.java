package com.dk.mensajero;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.dk.mensajero.R.id.exp_list;


public class RegistrarUsuarioActivity extends ActionBarActivity {

    HashMap<String, List<String>> paisesDesplegable;
    List<String> listaDePaises;
    ExpandableListView Exp_list;
    AdaptadorDePaises adaptador;
    private static Button button_sbm;
    private EditText numeroDeTelefono;
    private TextView codigoDeArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        panelExpandiblePaises();
        onClickButtonListener();
        mostrarCodigoDeArea();
    }

    public void panelExpandiblePaises(){
        Exp_list = (ExpandableListView) findViewById(exp_list);
        paisesDesplegable = ProveedorDatos.getInfo();
        listaDePaises = new ArrayList<String>(paisesDesplegable.keySet());
        adaptador = new AdaptadorDePaises(this, paisesDesplegable, listaDePaises);
        Exp_list.setAdapter(adaptador);
    }

    public void mostrarCodigoDeArea(){
        codigoDeArea = (TextView) findViewById(R.id.codArea);
        Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                switch (paisesDesplegable.get(listaDePaises.get(groupPosition)).get(childPosition)) {
                    case "Argentina":
                        codigoDeArea.setText("+54");
                        break;
                    case "Brasil":
                        codigoDeArea.setText("+55");
                        break;
                    case "Chile":
                        codigoDeArea.setText("+56");
                        break;
                    case "Uruguay":
                        codigoDeArea.setText("+598");
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    public void onClickButtonListener(){
        button_sbm = (Button)findViewById(R.id.button);
        button_sbm.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.dk.mensajero.ConfirmarDatosActivity");
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
}
