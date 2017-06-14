package br.gov.pb.joaopessoa.elaspmjp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PerfilFragment extends Fragment {

    private int idUsuario;
    private String nomeUsuario;
    private String nomeUsuarioUpdate;
    private EditText edtNome;
    private Button btnUpdateNome;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        edtNome = (EditText) view.findViewById(R.id.edtPerfilNomeUsuario);
        btnUpdateNome = (Button) view.findViewById(R.id.btnPerfilUpdateNomeUsuario);

        final DatabaseController dbCtrl = new DatabaseController(getContext());
        Cursor cursor = dbCtrl.carregaDadosUsuario();

        if (cursor != null && cursor.moveToFirst()) {
            int indexId = cursor.getColumnIndex(Usuario.ID);
            int indexNomeUsual = cursor.getColumnIndex(Usuario.NOME);

            idUsuario = cursor.getInt(indexId);
            nomeUsuario = cursor.getString(indexNomeUsual);
            edtNome.setText(nomeUsuario);
            cursor.close();
        }

        btnUpdateNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNome.length() > 0) {
                    boolean update = dbCtrl.updateUsuario(idUsuario, edtNome.getText().toString());
                    if (update) {
                        Toast.makeText(getContext(), "Nome alterado com sucesso!",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Não foi possível alterar no momento.",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Precisa digitar um nome.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
