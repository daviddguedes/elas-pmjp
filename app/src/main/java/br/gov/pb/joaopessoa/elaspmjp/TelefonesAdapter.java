package br.gov.pb.joaopessoa.elaspmjp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TelefonesAdapter extends ArrayAdapter<Telefones> {

    private final Context context;
    private final Telefones[] elementos;

    public TelefonesAdapter(Context context, Telefones[] elementos) {
        super(context, R.layout.list_view_telefones, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_view_telefones, parent, false);

        TextView titulo = (TextView) rowView.findViewById(R.id.txtTitulo);
        TextView telefone1 = (TextView) rowView.findViewById(R.id.txtTelefone1);

        titulo.setText(elementos[position].getTitulo());
        telefone1.setText(elementos[position].getNumero());

        ImageButton deletar = (ImageButton) rowView.findViewById(R.id.btnFazerLigacao);
        deletar.setFocusable(false);
        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + elementos[position].getNumero()));
                try {
                    context.startActivity(callIntent);
                }catch (Exception e) {
                    Log.d("Erro: ", e.toString());
                }finally {
                    Log.d("Ligar: ", "Ligando...");
                }
            }
        });

        return rowView;
    }

}
