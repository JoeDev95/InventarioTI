package com.example.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ListaComputadoresFX extends Application {
    private DatabaseManager databaseManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lista de Computadores");

        databaseManager = new DatabaseManager();

        TableView<Computador> table = new TableView<>();

        TableColumn<Computador, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Computador, String> setorColumn = new TableColumn<>("Setor");
        setorColumn.setCellValueFactory(new PropertyValueFactory<>("setor"));

        TableColumn<Computador, String> descricaoColumn = new TableColumn<>("Descrição");
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Computador, Boolean> garantiaColumn = new TableColumn<>("Garantia");
        garantiaColumn.setCellValueFactory(new PropertyValueFactory<>("garantia"));

        TableColumn<Computador, String> validadeGarantiaColumn = new TableColumn<>("Validade da Garantia");
        validadeGarantiaColumn.setCellValueFactory(new PropertyValueFactory<>("validadeGarantia"));

        TableColumn<Computador, String> dataCompraColumn = new TableColumn<>("Data da Compra");
        dataCompraColumn.setCellValueFactory(new PropertyValueFactory<>("dataCompra"));

        TableColumn<Computador, String> fabricanteColumn = new TableColumn<>("Fabricante");
        fabricanteColumn.setCellValueFactory(new PropertyValueFactory<>("fabricante"));

        TableColumn<Computador, Double> valorColumn = new TableColumn<>("Valor");
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valor"));

        TableColumn<Computador, String> condicoesColumn = new TableColumn<>("Condições");
        condicoesColumn.setCellValueFactory(new PropertyValueFactory<>("condicoes"));

        TableColumn<Computador, String> observacaoColumn = new TableColumn<>("Observação");
        observacaoColumn.setCellValueFactory(new PropertyValueFactory<>("observacao"));

        table.getColumns().addAll(idColumn, setorColumn, descricaoColumn, garantiaColumn, validadeGarantiaColumn,
                dataCompraColumn, fabricanteColumn, valorColumn, condicoesColumn, observacaoColumn);

        ObservableList<Computador> computadores = FXCollections.observableArrayList();

        ResultSet resultSet = databaseManager.getAllComputadores();
        if (resultSet != null) {
            try {
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

                    Computador computador = new Computador(id, setor, descricao, garantia, validadeGarantia,
                            dataCompra, fabricante, valor, condicoes, observacao);
                    computadores.add(computador);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        table.setItems(computadores);

        VBox vbox = new VBox(table);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        databaseManager.closeConnection();
    }
}
