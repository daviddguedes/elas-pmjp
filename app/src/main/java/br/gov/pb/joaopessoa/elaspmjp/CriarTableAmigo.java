package br.gov.pb.joaopessoa.elaspmjp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class CriarTableAmigo extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "elas.db";
    public static final String TABELA = "amigos";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String TELEFONE = "telefone";
    public static final int VERSAO = 1;

    public CriarTableAmigo(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement,"
                + NOME + " text,"
                + TELEFONE + " text"
                + ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
    }
}
