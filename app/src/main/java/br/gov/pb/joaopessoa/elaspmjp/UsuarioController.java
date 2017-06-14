//package br.gov.pb.joaopessoa.elas;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//class UsuarioController {
//    private SQLiteDatabase db;
//    private CreateDb dbElas;
//
//    public UsuarioController(Context context) {
//        dbElas = new CreateDb(context);
//    }
//
//    public String createUser(String nome, String data_nascimento, String genero, Boolean termos) {
//        ContentValues valores;
//        long resultado;
//
//        db = dbElas.getWritableDatabase();
//        valores = new ContentValues();
//        valores.put(Usuario.NOME, nome);
//        valores.put(Usuario.DATA_NASCIMENTO, data_nascimento);
//        valores.put(Usuario.GENERO, genero);
//        valores.put(Usuario.TERMOS, termos);
//
//        resultado = db.insert(Usuario.TABELA, null, valores);
//        db.close();
//
//        if (resultado == -1)
//            return "Desculpe. Houve um erro desconhecido.";
//        else
//            return "success";
//    }
//
//    public Cursor carregaDadosUsuario() {
//        Cursor cursor;
//        String[] campos = {Usuario.ID, Usuario.NOME, Usuario.DATA_NASCIMENTO, Usuario.GENERO, Usuario.TERMOS};
//        db = dbElas.getReadableDatabase();
//        cursor = db.query(Usuario.TABELA, campos, null, null, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        db.close();
//        return cursor;
//    }
//
//    public boolean haveUser() {
//        Cursor cursor;
//        String[] campos = {Usuario.ID, Usuario.NOME, Usuario.GENERO, Usuario.TERMOS};
//        db = dbElas.getReadableDatabase();
//        cursor = db.query(Usuario.TABELA, campos, null, null, null, null, null, null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//            return cursor.getCount() > 0;
//        }else {
//            return false;
//        }
//    }
//
//}
//
