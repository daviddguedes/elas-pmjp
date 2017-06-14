package br.gov.pb.joaopessoa.elaspmjp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdicionarAmigosActivity extends AppCompatActivity {

    private ListView lista;
    private TextView edtAmigoNome;
    private TextView edtAmigoTelefone;
    private Button button;
    private Button btnConcluir;
    private ImageButton deletar;
    private String amigoNome;
    private String amigoTelefone;
    final static ArrayList<Amigo> amigosArrayList = new ArrayList<Amigo>();

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
        setContentView(R.layout.activity_adicionar_amigos);
        setTitle("Cadastrar amigos(as)");

        edtAmigoNome = (TextView) findViewById(R.id.edtAmigoNome);
        edtAmigoTelefone = (TextView) findViewById(R.id.edtAmigoTelefone);
        button = (Button) findViewById(R.id.btnAmigoAdd);
        btnConcluir = (Button) findViewById(R.id.btnConcluir);
        edtAmigoNome.addTextChangedListener(textWatcher);
        edtAmigoTelefone.addTextChangedListener(textWatcher);
        lista = (ListView) findViewById(R.id.listView);

        getAmigos();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getAmigos();
                if (amigosArrayList.size() < 5) {
                    amigoNome = edtAmigoNome.getText().toString();
                    amigoTelefone = edtAmigoTelefone.getText().toString();
                    DatabaseController crudAmigos = new DatabaseController(getBaseContext());
                    String resultado;
                    resultado = crudAmigos.createAmigo(amigoNome, amigoTelefone);
                    if (resultado == "success") {
                        getAmigos();
                        edtAmigoNome.setText("");
                        edtAmigoTelefone.setText("");
                        amigoNome = "";
                        amigoTelefone = "";
                    } else {
                        Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Você já cadastrou 5 amigos(as).", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getUsuarioEamigos()) {
                    Toast.makeText(getApplicationContext(),  "Adicione pelo menos um(a) amigo(a).", Toast.LENGTH_LONG).show();
                }else {
                    finish();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    public void enableButtonSubmit() {
        boolean nome = edtAmigoNome.getText().toString().length() > 1;
        boolean telefone = edtAmigoTelefone.getText().toString().length() > 5;

        if (nome && telefone) {
            button.setAlpha(1f);
            button.setEnabled(true);
        } else {
            button.setAlpha(0.3f);
            button.setEnabled(false);
        }
    }

    public void getAmigos() {
        amigosArrayList.clear();
        DatabaseController crudAmigos = new DatabaseController(getBaseContext());
        Cursor cursor = crudAmigos.carregaDadosAmigos();

        if (cursor != null && cursor.moveToFirst()) {
            int nomeColumn = cursor.getColumnIndex(CriarTableAmigo.NOME);
            int telefoneColumn = cursor.getColumnIndex(CriarTableAmigo.TELEFONE);
            int idColumn = cursor.getColumnIndex(CriarTableAmigo.ID);

            do {
                int thisId = cursor.getInt(idColumn);
                String thisNome = cursor.getString(nomeColumn);
                String thisTelefone = cursor.getString(telefoneColumn);

                amigosArrayList.add(new Amigo(thisId, thisNome, thisTelefone));
            }
            while (cursor.moveToNext());

            cursor.close();
        }

        ArrayAdapter adapter = new AmigosAdapter(this, amigosArrayList);
        lista.setAdapter(adapter);
    }

    public boolean getUsuarioEamigos() {
        DatabaseController dbCtrl = new DatabaseController(getBaseContext());
        Boolean user = dbCtrl.haveUser();
        Boolean amigos = dbCtrl.haveAmigos();
        return user && amigos;
    }


}
