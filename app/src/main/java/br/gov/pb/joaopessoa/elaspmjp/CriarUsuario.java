package br.gov.pb.joaopessoa.elaspmjp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class CriarUsuario extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "elas.db";
    public static final String TABELA = "user";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String DATA_NASCIMENTO = "data_nascimento";
    public static final String GENERO = "genero";
    public static final String TERMOS = "termos";
    public static final int VERSAO = 1;

    public CriarUsuario(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement,"
                + NOME + " text,"
                + DATA_NASCIMENTO + " text,"
                + GENERO + " text,"
                + TERMOS + " boolean"
                + ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA);
        onCreate(db);
    }

}
