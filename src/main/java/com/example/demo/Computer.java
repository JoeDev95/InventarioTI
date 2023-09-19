package com.example.demo;

import java.time.LocalDate;

public class Computer {
    private int id;
    private String setor;
    private String descricao;
    private double valor;
    private String condicoes;
    private String observacao;
    private LocalDate ultimaManutencao;
    private LocalDate previsaoProximaManutencao;

    public Computer(
            int id,
            String setor,
            String descricao,
            double valor,
            String condicoes,
            String observacao,
            LocalDate ultimaManutencao,
            LocalDate previsaoProximaManutencao
    ) {
        this.id = id;
        this.setor = setor;
        this.descricao = descricao;
        this.valor = valor;
        this.condicoes = condicoes;
        this.observacao = observacao;
        this.ultimaManutencao = ultimaManutencao;
        this.previsaoProximaManutencao = previsaoProximaManutencao;
    }

    public int getId() {
        return id;
    }

    public String getSetor() {
        return setor;
    }

    public String getDescricao() {
        return descricao;
    }


    public double getValor() {
        return valor;
    }

    public String getCondicoes() {
        return condicoes;
    }

    public String getObservacao() {
        return observacao;
    }

    public LocalDate getUltimaManutencao() {
        return ultimaManutencao;
    }

    public LocalDate getPrevisaoProximaManutencao() {
        return previsaoProximaManutencao;
    }

    public String getFabricante() {
        return null;
    }
}
