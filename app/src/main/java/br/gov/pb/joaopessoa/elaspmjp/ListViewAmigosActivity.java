package br.gov.pb.joaopessoa.elaspmjp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListViewAmigosActivity extends AppCompatActivity {
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amigos_layout);
    }
}
