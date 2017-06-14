package br.gov.pb.joaopessoa.elaspmjp;


import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment_original extends Fragment {

    ImageButton btnHelpMe;
    final static ArrayList<Amigo> amigosArrayList = new ArrayList<Amigo>();
    String nomeUsuario = "";
    String dataNascimentoUsuario = "";
    String generoUsuario = "";
    String sendingSmsStatus = "SEND";
    PendingIntent send;
    String TELEFONE_ELAS = "83999999999";
    Double latitude;
    Double longitude;
    private BroadcastReceiver deliveryBroadcastReceiver;
    Location actualLocation;

    public HomeFragment_original() {

    }

    private void pedirPermissoes() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS}, 1);
        } else
            configurarServico();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configurarServico();
                } else {
                    Toast.makeText(getActivity(), "Elas precisa de permissão para enviar SMS e acessar sua localização.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    public void configurarServico() {
        try {
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    atualizar(location);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) { }

                public void onProviderEnabled(String provider) { }

                public void onProviderDisabled(String provider) { }
            };

            actualLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (actualLocation != null) {
                Log.d("Location: ", "GPS ligado.");
                latitude = actualLocation.getLatitude();
                longitude = actualLocation.getLongitude();
            } else {
                Log.d("Location: ", "GPS está desligado.");
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Toast.makeText(getActivity(), "É necessário ativar a localização do dispositivo", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (SecurityException ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void atualizar(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnHelpMe = (ImageButton) view.findViewById(R.id.btnHelpMe);
        DatabaseController dbCtrl = new DatabaseController(getContext());

        Cursor cursorUsuario = dbCtrl.carregaDadosUsuario();
        Cursor cursorAmigos = dbCtrl.carregaDadosAmigos();

        if (cursorUsuario != null && cursorUsuario.moveToFirst()) {
            int indexNomeUsual = cursorUsuario.getColumnIndex(Usuario.NOME);
            int indexDataNascentUsual = cursorUsuario.getColumnIndex(Usuario.DATA_NASCIMENTO);
            int indexGenaroUsual = cursorUsuario.getColumnIndex(Usuario.GENERO);

            nomeUsuario = cursorUsuario.getString(indexNomeUsual);
            dataNascimentoUsuario = cursorUsuario.getString(indexDataNascentUsual);
            generoUsuario = cursorUsuario.getString(indexGenaroUsual);
            cursorUsuario.close();
        }

        if (cursorAmigos != null && cursorAmigos.moveToFirst()) {
            amigosArrayList.clear();
            int nomeColumn = cursorAmigos.getColumnIndex(Amigo.NOME);
            int telefoneColumn = cursorAmigos.getColumnIndex(Amigo.TELEFONE);
            int idColumn = cursorAmigos.getColumnIndex(Amigo.ID);

            do {
                int thisId = cursorAmigos.getInt(idColumn);
                String thisNome = cursorAmigos.getString(nomeColumn);
                String thisTelefone = cursorAmigos.getString(telefoneColumn);

                amigosArrayList.add(new Amigo(thisId, thisNome, thisTelefone));
            }
            while (cursorAmigos.moveToNext());

            cursorAmigos.close();
        }

        btnHelpMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirPermissoes();
                return;
//                if (latitude != null && longitude != null) {
//                    Log.d("Localizacao: ", "http://maps.google.com/maps?q=" + latitude + "," + longitude);
//                    CharSequence agora = android.text.format.DateFormat.format("dd-MM-yyyy HH:mm:ss", new java.util.Date());
//
//                    int index = amigosArrayList.size();
//                    Log.d("index: ", Integer.toString(index));
//                    send = PendingIntent.getBroadcast(getContext(), 0,
//                            new Intent(sendingSmsStatus), 0);
//
//                    String textoRelatorio = "#Elas - " + nomeUsuario + " - " + dataNascimentoUsuario + " - " + generoUsuario + " - http://maps.google.com/maps?q=" + latitude + "," + longitude;
//
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(TELEFONE_ELAS, null, textoRelatorio, null, null);
//
//                    for (int i = 0; i < index; i++) {
//                        Amigo amigo = amigosArrayList.get(i);
//                        String nomeAmigo = amigo.getNomeAmigo();
//                        Log.d("Nome: ", nomeAmigo);
//                        String telefoneAmigo = amigo.getTelefoneAmigo();
//                        String texto = nomeAmigo + ", estou em PERIGO. http://maps.google.com/maps?q=" + latitude + "," + longitude;
//                        smsManager.sendTextMessage(telefoneAmigo, null, texto, send, null);
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "É necessário ativar a Localização do seu dispositivo.", Toast.LENGTH_LONG).show();
//                }
            }
        });

        deliveryBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getContext(), "Mensagens enviadas com sucesso!",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getContext(), "É necessário ter bônus. Erro: genérico",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getContext(), "Você está sem serviço de SMS no momento.",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getContext(), "Houve um erro. Erro: Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getContext(), "Houve um erro. Erro: Off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        };

        return view;
    }

    @Override
    public void onResume() {
        pedirPermissoes();
        getContext().registerReceiver(deliveryBroadcastReceiver, new IntentFilter(sendingSmsStatus));
        super.onResume();
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(deliveryBroadcastReceiver);
        super.onPause();
    }


}



