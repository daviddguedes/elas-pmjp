//package br.gov.pb.joaopessoa.elas;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//class AmigosController {
//    private SQLiteDatabase db;
//    private CreateDb dbElas;
//
//    public AmigosController(Context context) {
//        dbElas = new CreateDb(context);
//    }
//
//    public String createAmigo(String nome, String telefone) {
//        ContentValues valores;
//        long resultado;
//
//        db = dbElas.getWritableDatabase();
//        valores = new ContentValues();
//        valores.put(Amigo.NOME, nome);
//        valores.put(Amigo.TELEFONE, telefone);
//
//        resultado = db.insert(Amigo.TABELA, null, valores);
//        db.close();
//
//        if (resultado == -1)
//            return "Desculpe. Houve um erro desconhecido.";
//        else
//            return "success";
//    }
//
//    public Cursor carregaDadosAmigos() {
//        Cursor cursor;
//        String[] campos = {Amigo.ID, Amigo.NOME, Amigo.TELEFONE};
//        db = dbElas.getReadableDatabase();
//        cursor = db.query(Amigo.TABELA, campos, null, null, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        db.close();
//        return cursor;
//    }
//
//    public void deletaAmigo(int id) {
//        String where = Amigo.ID + "=" + id;
//        db = dbElas.getReadableDatabase();
//        db.delete(Amigo.TABELA, where, null);
//        db.close();
//    }
//
//    public boolean haveAmigos() {
//        Cursor cursor;
//        String[] campos = {Amigo.ID, Amigo.NOME, Amigo.TELEFONE};
//        db = dbElas.getWritableDatabase();
//        cursor = db.query(Amigo.TABELA, campos, null, null, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//            return cursor.getCount() > 0;
//        }else {
//            return false;
//        }
//    }
//}
