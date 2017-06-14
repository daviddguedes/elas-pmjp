package br.gov.pb.joaopessoa.elaspmjp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);// Connectivity

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();// Get

        if (networkInfo != null && networkInfo.isConnected()) {// Check if
            return true;// Internet is there
        } else {
            return false;// No internet
        }

    }
}
