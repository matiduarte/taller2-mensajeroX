package com.dk.mensajero.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dk.mensajero.R;
import com.dk.mensajero.Service.Service;
import com.dk.mensajero.Utilities.IpHandler;

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
                String ip = etIp.getText().toString();
                String port = etPort.getText().toString();

                if (ip.isEmpty() || port.isEmpty()){
                    showEmptyFieldMessage();
                } else {
                    setIpPort(ip, port);
                }
                break;

        }
    }

    private void setIpPort(String ip, String port) {
        StringBuilder sB = new StringBuilder();
        sB.append("http://" + ip + ":" + port + "/");

        IpHandler ipHandler = new IpHandler(ip,port);
        ipHandler.save(this);

        Service.setIp(sB.toString());
        startActivity(new Intent(this, AuthenticationActivity.class));
    }

    private void showEmptyFieldMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(IpPortActivity.this);
        dialogBuilder.setMessage("No puede haber campos vacios");
        dialogBuilder.setPositiveButton("Reintentar",null);
        dialogBuilder.show();
    }
}
