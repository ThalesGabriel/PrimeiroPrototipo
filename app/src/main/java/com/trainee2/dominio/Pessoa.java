package com.trainee2.dominio;

public class Usuario {

    private String nome;
    private String cidade;
    private long id;

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String toString() {
        return this.nome;
    }
    public String getCidade (){
        return cidade;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public String getNome() {
        return this.nome;
    }
}
