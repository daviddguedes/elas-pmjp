package br.gov.pb.joaopessoa.elaspmjp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

class DatabaseController {
    private SQLiteDatabase db;
    private CreateDb dbElas;

    public DatabaseController(Context context) {
        dbElas = new CreateDb(context);
    }

    public String createUser(String nome, String data_nascimento, String genero, Boolean termos) {
        ContentValues valores;
        long resultado;

        db = dbElas.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CreateDb.USER_NOME, nome);
        valores.put(CreateDb.USER_DATA_NASCIMENTO, data_nascimento);
        valores.put(CreateDb.USER_GENERO, genero);
        valores.put(CreateDb.USER_TERMOS, termos);

        resultado = db.insert(CreateDb.USER_TABELA, null, valores);
        db.close();

        if (resultado == -1)
            return "Desculpe. Houve um erro desconhecido.";
        else
            return "success";
    }

    public Cursor carregaDadosUsuario() {
        Cursor cursor;
        String[] campos = {CreateDb.USER_ID, CreateDb.USER_NOME, CreateDb.USER_DATA_NASCIMENTO, CreateDb.USER_GENERO, CreateDb.USER_TERMOS};
        db = dbElas.getReadableDatabase();
        cursor = db.query(CreateDb.USER_TABELA, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public boolean haveUser() {
        Cursor cursor;
        String[] campos = {CreateDb.USER_ID, CreateDb.USER_NOME, CreateDb.USER_GENERO, CreateDb.USER_TERMOS};
        db = dbElas.getReadableDatabase();
        cursor = db.query(CreateDb.USER_TABELA, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            boolean result = cursor.getCount() > 0;
            cursor.close();
            db.close();
            return result;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean updateUsuario(int id, String nome) {
        ContentValues valores;
        int resultado;

        db = dbElas.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CreateDb.USER_NOME, nome);

        resultado = db.update(CreateDb.USER_TABELA, valores, CreateDb.USER_ID + "=" + id, null);
        Log.d("Resultado: ", Integer.toString(id) + " - " + nome);
        db.close();

        return resultado != 0;
    }

    // ======================= AMIGOS =========================

    public String createAmigo(String nome, String telefone) {
        ContentValues valores;
        long resultado;

        db = dbElas.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CreateDb.AMIGO_NOME, nome);
        valores.put(CreateDb.AMIGO_TELEFONE, telefone);

        resultado = db.insert(CreateDb.AMIGO_TABELA, null, valores);
        db.close();

        if (resultado == -1)
            return "Desculpe. Houve um erro desconhecido.";
        else
            return "success";
    }

    public Cursor carregaDadosAmigos() {
        Cursor cursor;
        String[] campos = {CreateDb.AMIGO_ID, CreateDb.AMIGO_NOME, CreateDb.AMIGO_TELEFONE};
        db = dbElas.getReadableDatabase();
        cursor = db.query(CreateDb.AMIGO_TABELA, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void deletaAmigo(int id) {
        String where = CreateDb.AMIGO_ID + "=" + id;
        db = dbElas.getReadableDatabase();
        db.delete(CreateDb.AMIGO_TABELA, where, null);
        db.close();
    }

    public boolean haveAmigos() {
        Cursor cursor;
        String[] campos = {CreateDb.AMIGO_ID, CreateDb.AMIGO_NOME, CreateDb.AMIGO_TELEFONE};
        db = dbElas.getWritableDatabase();
        cursor = db.query(CreateDb.AMIGO_TABELA, campos, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            boolean response = cursor.getCount() > 0;
            cursor.close();
            db.close();
            return response;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

}


