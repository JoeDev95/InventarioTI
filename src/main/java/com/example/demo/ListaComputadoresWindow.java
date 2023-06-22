package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListaComputadoresWindow extends Stage {
    private ObservableList<String> computadores;

    public ListaComputadoresWindow() {
        computadores = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>(computadores);

        VBox vbox = new VBox(listView);
        Scene scene = new Scene(vbox, 400, 300);

        setTitle("Lista de Computadores");
        setScene(scene);
    }

    public void exibirListaComputadores(Statement statement) {
        computadores.clear();

        String sql = "SELECT * FROM computers";

        try (ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String setor = resultSet.getString("setor");
                String descricao = resultSet.getString("descricao");
                boolean garantia = resultSet.getBoolean("garantia");
                String validadeGarantia = resultSet.getString("validade_garantia");
                String dataCompra = resultSet.getString("data_compra");
                String fabricante = resultSet.getString("fabricante");
                double valor = resultSet.getDouble("valor");
                String condicoes = resultSet.getString("condicoes");
                String observacao = resultSet.getString("observacao");

                String item = "ID: " + id +
                        ", Setor: " + setor +
                        ", Descrição: " + descricao +
                        ", Garantia: " + garantia +
                        ", Validade da Garantia: " + validadeGarantia +
                        ", Data da Compra: " + dataCompra +
                        ", Fabricante: " + fabricante +
                        ", Valor: " + valor +
                        ", Condições: " + condicoes +
                        ", Observação: " + observacao;

                computadores.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar os computadores: " + e.getMessage());
        }

        showAndWait();
    }
}
