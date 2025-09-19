package com.example.servicos.model;

public class Situacao {
    private String nome;
    private String data;
    private String motivo;

    public Situacao(String nome, String data, String motivo) {
        this.nome = nome;
        this.data = data;
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "Situacao{" +
                "nome='" + nome + '\'' +
                ", data='" + data + '\'' +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
