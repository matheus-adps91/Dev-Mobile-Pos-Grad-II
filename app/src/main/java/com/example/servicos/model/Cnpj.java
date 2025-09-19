package com.example.servicos.model;

public class Cnpj {
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private String naturezaJuridica;
    private String capitalSocial;
    private String dataInicio;
    private String porte;
    private String  tipo;
    private String telefone1;
    private String telefone2;
    private String email;
    private Situacao situacao;
    private Endereco endereco;

    public Cnpj(
            String cnpj,
            String razaoSocial,
            String nomeFantasia,
            String naturezaJuridica,
            String capitalSocial,
            String dataInicio,
            String porte,
            String tipo,
            String telefone1,
            String telefone2,
            String email,
            Situacao situacao,
            Endereco endereco)
    {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.naturezaJuridica = naturezaJuridica;
        this.capitalSocial = capitalSocial;
        this.dataInicio = dataInicio;
        this.porte = porte;
        this.tipo = tipo;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.email = email;
        this.situacao = situacao;
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Cnpj{" +
                "cnpj='" + cnpj + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", naturezaJuridica='" + naturezaJuridica + '\'' +
                ", capitalSocial='" + capitalSocial + '\'' +
                ", dataInicio='" + dataInicio + '\'' +
                ", porte='" + porte + '\'' +
                ", tipo='" + tipo + '\'' +
                ", telefone1='" + telefone1 + '\'' +
                ", telefone2='" + telefone2 + '\'' +
                ", email='" + email + '\'' +
                ", situacao=" + situacao +
                ", endereco=" + endereco +
                '}';
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getNaturezaJuridica() {
        return naturezaJuridica;
    }

    public String getCapitalSocial() {
        return capitalSocial;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getPorte() {
        return porte;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public String getEmail() {
        return email;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
