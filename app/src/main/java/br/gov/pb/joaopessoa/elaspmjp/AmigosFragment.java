package br.gov.pb.joaopessoa.elaspmjp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AmigosFragment extends Fragment {

    private ListView lista;
    private TextView edtAmigoNome;
    private TextView edtAmigoTelefone;
    private Button button;
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

    public AmigosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amigos, container, false);

        edtAmigoNome = (TextView) view.findViewById(R.id.edtAmigoNome);
        edtAmigoTelefone = (TextView) view.findViewById(R.id.edtAmigoTelefone);
        button = (Button) view.findViewById(R.id.btnAmigoAdd);
        edtAmigoNome.addTextChangedListener(textWatcher);
        edtAmigoTelefone.addTextChangedListener(textWatcher);
        lista = (ListView) view.findViewById(R.id.listView);

        getAmigos();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getAmigos();
                if (amigosArrayList.size() < 5) {
                    amigoNome = edtAmigoNome.getText().toString();
                    amigoTelefone = edtAmigoTelefone.getText().toString();
                    DatabaseController crudAmigos = new DatabaseController(getContext());
                    String resultado;
                    resultado = crudAmigos.createAmigo(amigoNome, amigoTelefone);
                    if (resultado == "success") {
                        getAmigos();
                        edtAmigoNome.setText("");
                        edtAmigoTelefone.setText("");
                        amigoNome = "";
                        amigoTelefone = "";
                    } else {
                        Toast.makeText(getContext(), resultado, Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Você já cadastrou 5 amigos(as).", Toast.LENGTH_LONG).show();
                }
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        return view;
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
        DatabaseController crudAmigos = new DatabaseController(getContext());
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

        ArrayAdapter adapter = new AmigosAdapter(getActivity(), amigosArrayList);
        lista.setAdapter(adapter);
    }

    public boolean getUsuarioEamigos() {
        DatabaseController dbCtrl = new DatabaseController(getContext());
        Boolean amigos = dbCtrl.haveAmigos();
        return amigos;
    }

}
