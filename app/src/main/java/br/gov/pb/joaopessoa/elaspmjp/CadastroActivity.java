package br.gov.pb.joaopessoa.elaspmjp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtDataNnascimento;
    private RadioGroup rdgGenero;
    private CheckBox chkTermos;
    private String nome;
    private String data_nascimento;
    private String genero;
    private Boolean termos;
    private Boolean termosCheck;
    private RadioButton radioButton;
    private Button button;
    DatabaseController dbCtrl = new DatabaseController(getBaseContext());

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            enableButtonSubmit();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setTitle("Cadastro");

        dbCtrl = new DatabaseController(getBaseContext());
        if (dbCtrl.haveUser()) {
            Intent in = new Intent(this, AdicionarAmigosActivity.class);
            finish();
            startActivity(in);
        }

        txtNome = (TextView) findViewById(R.id.edtNomeUsuario);
        txtDataNnascimento = (TextView) findViewById(R.id.edtNascimento);
        rdgGenero = (RadioGroup) findViewById(R.id.radioGenero);
        chkTermos = (CheckBox) findViewById(R.id.chkTermos);
        button = (Button) findViewById(R.id.btnCadastroAvancar);

        txtNome.addTextChangedListener(textWatcher);
        txtDataNnascimento.addTextChangedListener(textWatcher);
        rdgGenero.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                enableButtonSubmit();
            }
        });
        chkTermos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableButtonSubmit();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int selectedId = rdgGenero.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                termosCheck = chkTermos.isChecked();

                nome = txtNome.getText().toString();
                data_nascimento = txtDataNnascimento.getText().toString();
                genero = radioButton.getText().toString();
                termos = termosCheck.booleanValue();

                DatabaseController crud = new DatabaseController(getBaseContext());
                String resultado;
                resultado = crud.createUser(nome, data_nascimento, genero, termos);
                if (resultado == "success") {
                    v.setWillNotCacheDrawing(true);
                    Intent intent = new Intent(v.getContext(), AdicionarAmigosActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btnMostrarTermos = (Button) findViewById(R.id.btnShowTermos);
        btnMostrarTermos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext(), R.style.Theme_AppCompat_Light);
                alert.setView(R.layout.fragment_termos);
                alert.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });

    }

    public void enableButtonSubmit() {
        boolean nome = txtNome.getText().toString().length() > 2;
        boolean data_nascimento = txtDataNnascimento.getText().toString().length() > 5;
        boolean genero = rdgGenero.getCheckedRadioButtonId() != -1;
        boolean termos = chkTermos.isChecked();

        if (nome && data_nascimento && genero && termos) {
            button.setAlpha(1f);
            button.setEnabled(true);
        } else {
            button.setAlpha(0.3f);
            button.setEnabled(false);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (dbCtrl.haveUser()) {
            Intent in = new Intent(this, AdicionarAmigosActivity.class);
            finish();
            startActivity(in);
        }
    }

}
