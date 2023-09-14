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
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ListaComputadoresFX extends Application {
    private DatabaseManager databaseManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lista de Computadores");

        databaseManager = new DatabaseManager();

        TableView<Computer> table = new TableView<>();

        TableColumn<Computer, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Computer, String> setorColumn = new TableColumn<>("Setor");
        setorColumn.setCellValueFactory(new PropertyValueFactory<>("setor"));

        TableColumn<Computer, String> descricaoColumn = new TableColumn<>("Descrição");
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        TableColumn<Computer, Boolean> garantiaColumn = new TableColumn<>("Garantia");
        garantiaColumn.setCellValueFactory(new PropertyValueFactory<>("garantia"));

        TableColumn<Computer, LocalDate> validadeGarantiaColumn = new TableColumn<>("Validade da Garantia");
        validadeGarantiaColumn.setCellValueFactory(new PropertyValueFactory<>("validadeGarantia"));

        validadeGarantiaColumn.setCellFactory(column -> {
            TableCell<Computer, LocalDate> cell = new TableCell<Computer, LocalDate>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(formatter.format(item));
                    }
                }
            };
            return cell;
        });

        // Outras colunas...

        table.getColumns().addAll(idColumn, setorColumn, descricaoColumn, garantiaColumn, validadeGarantiaColumn);

        ObservableList<Computer> computadores = FXCollections.observableArrayList();

        ResultSet resultSet = databaseManager.getAllComputadores();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String setor = resultSet.getString("setor");
                    String descricao = resultSet.getString("descricao");
                    boolean garantia = resultSet.getBoolean("garantia");
                    LocalDate validadeGarantia = resultSet.getDate("validade_garantia").toLocalDate();
                    // Faça o mesmo para outras datas: dataCompra, ultimaManutencao, previsaoProximaManutencao
                    String fabricante = resultSet.getString("fabricante");
                    double valor = resultSet.getDouble("valor");
                    String condicoes = resultSet.getString("condicoes");
                    String observacao = resultSet.getString("observacao");
                    LocalDate ultimaManutencao = resultSet.getDate("ultima_manutencao").toLocalDate();
                    LocalDate previsaoProximaManutencao = resultSet.getDate("previsao_proxima_manutencao").toLocalDate();

                    Computer computador = new Computer(id, setor, descricao, garantia, validadeGarantia,
                            fabricante, valor, condicoes, observacao, ultimaManutencao, previsaoProximaManutencao);
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

    public void exibirListaComputadores(Statement statement) {
    }

    public void show() {
    }
}
