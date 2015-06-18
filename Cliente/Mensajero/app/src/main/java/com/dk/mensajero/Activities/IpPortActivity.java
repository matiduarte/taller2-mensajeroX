package com.dk.mensajero.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dk.mensajero.R;

public class IpPortActivity extends ActionBarActivity implements View.OnClickListener {

    Button bOk;
    EditText etIp, etPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_port);

        etIp = (EditText) findViewById(R.id.etIp);
        etPort = (EditText) findViewById(R.id.etPort);
        bOk = (Button) findViewById(R.id.bOk);

        bOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bOk:
                startActivity(new Intent(this, AuthenticationActivity.class));
                break;

        }
    }
}
