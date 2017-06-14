package br.gov.pb.joaopessoa.elaspmjp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TelefonesFragment extends Fragment {

    private ListView lista;

    public TelefonesFragment() {
        // Required empty public constructor
    }

    private void pedirPermissoes() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    configurarServico();
                } else {
                    Toast.makeText(getActivity(), "Elas precisa de permissão.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_telefones, container, false);

        lista = (ListView) view.findViewById(R.id.listView);

        pedirPermissoes();

        Telefones tel1 = new Telefones("Secretaria de Políticas Públicas para as Mulheres", "(83) 3218-5628");
        Telefones tel2 = new Telefones("Centro de Referência da Mulher Ednalva Bezerra", "0800 283 3883");
        Telefones tel3 = new Telefones("Instituto Cândida Vargas – Setor de Violência Sexual", "(83) 3015-1500");
        Telefones tel4 = new Telefones("Centro de Cidadania LGBT", "(83) 3218-9246");
        Telefones tel5 = new Telefones("Central de Atendimento à Mulher em Situação de Violência", "180");
        Telefones tel6 = new Telefones("Polícia Militar", "190");
        Telefones tel7 = new Telefones("Disque Denúncia – Polícia Civil da PB", "197");
        Telefones tel8 = new Telefones("Disque 100 - Violação dos Direitos Humanos", "100");
        final Telefones[] TELEFONES = {tel1,tel2,tel3,tel4,tel5,tel6,tel7,tel8};

        ArrayAdapter adapter = new TelefonesAdapter(getContext(), TELEFONES);
        lista.setAdapter(adapter);

        return view;
    }

}
