package br.gov.pb.joaopessoa.elaspmjp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        setTitle("Primeiro Acesso");

        if (getUsuario()) {
            finish();
            Intent intent = new Intent(getBaseContext(), AdicionarAmigosActivity.class);
            startActivityForResult(intent, 0);
        }

        final Button button = (Button) findViewById(R.id.btnInitialAvancar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CadastroActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    public boolean getUsuario() {
        DatabaseController crud = new DatabaseController(getBaseContext());
        Boolean user = crud.haveUser();
        return user;
    }
}
