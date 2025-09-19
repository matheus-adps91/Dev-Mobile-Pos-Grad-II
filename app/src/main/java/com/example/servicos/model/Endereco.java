package com.example.servicos.model;

public class Endereco {
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String uf;
    private String municipio;

    public Endereco(
            String tipoLogradouro,
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cep,
            String uf,
            String municipio)
    {
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.uf = uf;
        this.municipio = municipio;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "tipoLogradouro='" + tipoLogradouro + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numero='" + numero + '\'' +
                ", complemento='" + complemento + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cep='" + cep + '\'' +
                ", uf='" + uf + '\'' +
                ", municipio='" + municipio + '\'' +
                '}';
    }
}
