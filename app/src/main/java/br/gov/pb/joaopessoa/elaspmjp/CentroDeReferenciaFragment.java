package br.gov.pb.joaopessoa.elaspmjp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class CentroDeReferenciaFragment extends Fragment {

    public static final String telefoneCentro = "0800 283 3883";
    public static final String emailCentro = "crebezerra@hotmail.com";
    Button btnLigacao;
    Button btnEmail;

    public CentroDeReferenciaFragment() {
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

        View view = inflater.inflate(R.layout.fragment_centro_de_referencia, container, false);

        btnLigacao = (Button) view.findViewById(R.id.btnLigar);
        btnEmail = (Button) view.findViewById(R.id.btnEmail);

        btnLigacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirPermissoes();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + telefoneCentro));
                try {
                    getActivity().startActivity(callIntent);
                } catch (Exception e) {

                } finally {

                }
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirPermissoes();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailCentro });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contato");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, ""));
            }
        });

        return view;
    }

}
