package br.gov.pb.joaopessoa.elaspmjp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AmigosAdapter extends ArrayAdapter<Amigo> {
    private final Context context;
    private final ArrayList<Amigo> elementos;

    public AmigosAdapter(Context context, ArrayList<Amigo> elementos) {
        super(context, R.layout.amigos_layout, elementos);
        this.context = context;
        this.elementos = elementos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.amigos_layout, parent, false);

        TextView nomeAmigo = (TextView) rowView.findViewById(R.id.nomeAmigo);
        TextView telefoneAmigo = (TextView) rowView.findViewById(R.id.telefoneAmigo);

        nomeAmigo.setText(elementos.get(position).getNomeAmigo());
        telefoneAmigo.setText(elementos.get(position).getTelefoneAmigo());
        final int i = elementos.get(position).getId();

        ImageButton deletar = (ImageButton) rowView.findViewById(R.id.btnDeleteAmigo);
        deletar.setFocusable(false);
        deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseController crud = new DatabaseController(context);
                Log.d("Delete: ", "Botao delete");
                crud.deletaAmigo(i);
                for (int q = 0; q < elementos.size(); q++) {
                    if (elementos.get(q).getId() == i) {
                        elementos.remove(elementos.get(q));
                        notifyDataSetChanged();
                    }
                }
            }
        });

        return rowView;
    }
}
