package br.gov.pb.joaopessoa.elaspmjp;

public class Telefones {

    private String titulo;
    private String numero;

    public Telefones(String titulo, String numero) {
        this.titulo = titulo;
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
