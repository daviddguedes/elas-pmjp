package br.gov.pb.joaopessoa.elaspmjp;

public class Usuario {

    public static final String TABELA = "user";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String DATA_NASCIMENTO = "data_nascimento";
    public static final String GENERO = "genero";
    public static final String TERMOS = "termos";

    private int id;
    private String nomeUsuario;
    private String telefoneUsuario;
    private String dataNascimentoUsuario;
    private String generoUsuario;
    private Boolean termosUsuario;

    public Usuario(int id, String nomeUsuario, String telefoneUsuario, String dataNascimentoUsuario, String generoUsuario, Boolean termosUsuario) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.telefoneUsuario = telefoneUsuario;
        this.dataNascimentoUsuario = dataNascimentoUsuario;
        this.generoUsuario = generoUsuario;
        this.termosUsuario = termosUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public String getDataNascimentoUsuario() {
        return dataNascimentoUsuario;
    }

    public void setDataNascimentoUsuario(String dataNascimentoUsuario) {
        this.dataNascimentoUsuario = dataNascimentoUsuario;
    }

    public String getGeneroUsuario() {
        return generoUsuario;
    }

    public void setGeneroUsuario(String generoUsuario) {
        this.generoUsuario = generoUsuario;
    }

    public Boolean getTermosUsuario() {
        return termosUsuario;
    }

    public void setTermosUsuario(Boolean termosUsuario) {
        this.termosUsuario = termosUsuario;
    }
}
