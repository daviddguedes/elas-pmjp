package br.gov.pb.joaopessoa.elaspmjp;

public class Amigo {

    public static final String TABELA = "amigos";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String TELEFONE = "telefone";

    private int id;
    private String nomeAmigo;
    private String telefoneAmigo;

    public Amigo(int id, String nome, String telefone) {
        this.id = id;
        this.nomeAmigo = nome;
        this.telefoneAmigo = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeAmigo() {
        return nomeAmigo;
    }

    public void setNomeAmigo(String nomeAmigo) {
        this.nomeAmigo = nomeAmigo;
    }

    public String getTelefoneAmigo() {
        return telefoneAmigo;
    }

    public void setTelefoneAmigo(String telefoneAmigo) {
        this.telefoneAmigo = telefoneAmigo;
    }
}
