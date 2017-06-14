package br.gov.pb.joaopessoa.elaspmjp;


import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements LocationListener {

    ImageButton btnHelpMe;
    final static ArrayList<Amigo> amigosArrayList = new ArrayList<Amigo>();
    String nomeUsuario = "";
    String dataNascimentoUsuario = "";
    String generoUsuario = "";
    String sendingSmsStatus = "SEND";
    PendingIntent send;
    String TELEFONE_ELAS = "8399999999";
    Double latitude;
    Double longitude;
    private BroadcastReceiver deliveryBroadcastReceiver;
    Location actualLocation;
    boolean checkGPS = false;
    boolean checkNetwork = false;
    Location loc;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;
    boolean canGetLocation = false;
    ProgressDialog progress;

    public HomeFragment() {

    }

    private void pedirPermissoes() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.INTERNET}, 1);
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

    public Location configurarServico() {
        try {
            locationManager = (LocationManager) getActivity()
                    .getSystemService(Context.LOCATION_SERVICE);

            checkGPS = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            checkNetwork = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!checkGPS && !checkNetwork) {
                Toast.makeText(getActivity(), "Localização está ativada? Você tem saldo para enviar SMS?.", Toast.LENGTH_SHORT).show();
            } else {
                this.canGetLocation = true;

                if (checkNetwork) {
                    try {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            loc = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        }

                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                        }
                    } catch (SecurityException e) {

                    }
                }
            }

            if (checkGPS) {
                if (loc == null) {
                    try {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            loc = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (loc != null) {
                                latitude = loc.getLatitude();
                                longitude = loc.getLongitude();
                            }
                        }
                    } catch (SecurityException e) {

                    }
                }
            }

        } catch (SecurityException ex) {
            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return loc;
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
        progress = new ProgressDialog(getContext());

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
                if (latitude != null && longitude != null) {
                    progress.setTitle("Enviando");
                    progress.setMessage("Enviando as mensagens...");
                    progress.setCancelable(false);
                    progress.show();

                    Log.d("Localizacao: ", "http://maps.google.com/maps?q=" + latitude + "," + longitude);
                    CharSequence agora = android.text.format.DateFormat.format("dd-MM-yyyy HH:mm:ss", new java.util.Date());

                    int index = amigosArrayList.size();
                    Log.d("index: ", Integer.toString(index));
                    send = PendingIntent.getBroadcast(getContext(), 0,
                            new Intent(sendingSmsStatus), 0);

                    String textoRelatorio = "#Elas - " + nomeUsuario + " - " + dataNascimentoUsuario + " - " + generoUsuario + " - http://maps.google.com/maps?q=" + latitude + "," + longitude;

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(TELEFONE_ELAS, null, textoRelatorio, null, null);

                    for (int i = 0; i < index; i++) {
                        Amigo amigo = amigosArrayList.get(i);
                        String nomeAmigo = amigo.getNomeAmigo();
                        Log.d("Nome: ", nomeAmigo);
                        String telefoneAmigo = amigo.getTelefoneAmigo();
                        String texto = nomeAmigo + ", estou em PERIGO. http://maps.google.com/maps?q=" + latitude + "," + longitude;
                        smsManager.sendTextMessage(telefoneAmigo, null, texto, send, null);
                    }

                    progress.dismiss();
                } else {
                    showSettingsAlert();
                    Toast.makeText(getActivity(), "É necessário ativar a Localização do seu dispositivo.", Toast.LENGTH_LONG).show();
                }
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

    public double getLongitude() {
        if (loc != null) {
            longitude = loc.getLongitude();
        }
        return longitude;
    }

    public double getLatitude() {
        if (loc != null) {
            latitude = loc.getLatitude();
        }
        return latitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Atenção!");
        alertDialog.setMessage("É necessário ativar a localização");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        getActivity().startActivity(intent);
    }


}



