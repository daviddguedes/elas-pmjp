package br.gov.pb.joaopessoa.elaspmjp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDb extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "elas.db";
    public static final int VERSAO = 1;

    public static final String USER_TABELA = "user";
    public static final String USER_ID = "_id";
    public static final String USER_NOME = "nome";
    public static final String USER_DATA_NASCIMENTO = "data_nascimento";
    public static final String USER_GENERO = "genero";
    public static final String USER_TERMOS = "termos";

    public static final String AMIGO_TABELA = "amigos";
    public static final String AMIGO_ID = "_id";
    public static final String AMIGO_NOME = "nome";
    public static final String AMIGO_TELEFONE = "telefone";

    String sqlTableUser = "CREATE TABLE " + USER_TABELA + "("
            + USER_ID + " integer primary key autoincrement,"
            + USER_NOME + " text,"
            + USER_DATA_NASCIMENTO + " text,"
            + USER_GENERO + " text,"
            + USER_TERMOS + " boolean"
            + ")";

    String sqlTableAmigos = "CREATE TABLE " + AMIGO_TABELA + "("
            + AMIGO_ID + " integer primary key autoincrement,"
            + AMIGO_NOME + " text,"
            + AMIGO_TELEFONE + " text"
            + ")";

    public CreateDb(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlTableUser);
        db.execSQL(sqlTableAmigos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + USER_TABELA);
        db.execSQL("DROP TABLE IF EXISTS" + AMIGO_TABELA);
        onCreate(db);
    }
}

