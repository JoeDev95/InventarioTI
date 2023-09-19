package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;

public class InventarioComputadoresFX extends Application {
    private DatabaseManager databaseManager;
    private TextField setorTextField;
    private TextField descricaoTextField;
    private DatePicker dataCompraDatePicker;
    private ChoiceBox<String> fabricanteChoiceBox;
    private TextField valorTextField;
    private TextArea condicoesTextArea;
    private TextArea observacaoTextArea;
    private DatePicker ultimaManutencaoDatePicker;
    private DatePicker previsaoProximaManutencaoDatePicker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventário de máquinas");

        databaseManager = DatabaseManager.getInstance(); // Obtém a instância única

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        // ImageView para a logo
        Image logoImage = new Image(getClass().getResourceAsStream("/logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        vbox.getChildren().add(logoImageView);

        vbox.getChildren().addAll(createComputerForm());


        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(10, 0, 0, 0));

        Button salvarButton = new Button("Salvar");
        Button listarButton = new Button("Listar Computadores");

        buttonsBox.getChildren().addAll(salvarButton, listarButton);

        vbox.getChildren().add(buttonsBox);

        salvarButton.setOnAction(event -> {
            Computer computer = getComputerFromForm();
            if (computer != null) {
                databaseManager.insertComputer(computer); // Usa o método do DatabaseManager
                clearFormFields();
            }
        });

        listarButton.setOnAction(event -> {
            ListaComputadoresFX listaComputadoresWindow = new ListaComputadoresFX();
            try {
                listaComputadoresWindow.exibirListaComputadores(databaseManager.getConnection().createStatement());
                listaComputadoresWindow.show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private GridPane createComputerForm() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        Label idLabel = new Label("ID:");
        TextField idTextField = new TextField();
        idTextField.setDisable(true);
        gridPane.addRow(0, idLabel, idTextField);

        Label setorLabel = new Label("Setor:");
        setorTextField = new TextField();
        gridPane.addRow(1, setorLabel, setorTextField);

        Label descricaoLabel = new Label("Descrição:");
        descricaoTextField = new TextField();
        gridPane.addRow(2, descricaoLabel, descricaoTextField);

        Label dataCompraLabel = new Label("Data da Compra:");
        dataCompraDatePicker = new DatePicker();
        gridPane.addRow(5, dataCompraLabel, dataCompraDatePicker);

        Label fabricanteLabel = new Label("Filial de Origem:");
        fabricanteChoiceBox = new ChoiceBox<>();
        fabricanteChoiceBox.getItems().addAll("Filial 1", "Filial 2", "Filial 3", "Filial 4", "Filial 5");
        fabricanteChoiceBox.setValue("Filial 1");
        gridPane.addRow(6, fabricanteLabel, fabricanteChoiceBox);

        Label valorLabel = new Label("Valor:");
        valorTextField = new TextField();
        gridPane.addRow(7, valorLabel, valorTextField);

        Label condicoesLabel = new Label("Condições:");
        condicoesTextArea = new TextArea();
        gridPane.addRow(8, condicoesLabel, condicoesTextArea);

        Label observacaoLabel = new Label("Observação:");
        observacaoTextArea = new TextArea();
        gridPane.addRow(9, observacaoLabel, observacaoTextArea);

        Label ultimaManutencaoLabel = new Label("Última Manutenção:");
        ultimaManutencaoDatePicker = new DatePicker();
        gridPane.addRow(10, ultimaManutencaoLabel, ultimaManutencaoDatePicker);

        Label previsaoProximaManutencaoLabel = new Label("Próxima Manutenção:");
        previsaoProximaManutencaoDatePicker = new DatePicker();
        gridPane.addRow(11, previsaoProximaManutencaoLabel, previsaoProximaManutencaoDatePicker);

        return gridPane;
    }

    private Computer getComputerFromForm() {
        try {
            int id = 0;
            String setor = setorTextField.getText();
            String descricao = descricaoTextField.getText();
            double valor = Double.parseDouble(valorTextField.getText());
            String condicoes = condicoesTextArea.getText();
            String observacao = observacaoTextArea.getText();
            LocalDate ultimaManutencao = ultimaManutencaoDatePicker.getValue();
            LocalDate previsaoProximaManutencao = previsaoProximaManutencaoDatePicker.getValue();

            return new Computer(id, setor, descricao, valor, condicoes, observacao, ultimaManutencao, previsaoProximaManutencao);
        } catch (Exception e) {
            System.out.println("Erro ao obter os dados do formulário: " + e.getMessage());
            return null;
        }
    }

    private void clearFormFields() {
        // Limpa os campos do formulário após a inserção bem-sucedida no banco de dados
        setorTextField.clear();
        descricaoTextField.clear();
        fabricanteChoiceBox.setValue("Filial 1");
        valorTextField.clear();
        condicoesTextArea.clear();
        observacaoTextArea.clear();
        ultimaManutencaoDatePicker.setValue(null);
        previsaoProximaManutencaoDatePicker.setValue(null);
    }

}
