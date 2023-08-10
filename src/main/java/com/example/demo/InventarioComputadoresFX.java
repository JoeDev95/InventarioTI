package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

public class InventarioComputadoresFX<Categoria> extends Application {
    private static final String DATABASE_URL = "jdbc:sqlite:/home/joe/inventario.db";


    private Connection connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventário de maquinas");

        createConnection();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // ImageView para a logo
        Image logoImage = new Image(getClass().getResourceAsStream("/logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        gridPane.add(logoImageView, 0, 0, 2, 1);

        Label idLabel = new Label("ID:");
        TextField idTextField = new TextField();
        gridPane.add(idLabel, 0, 1);
        gridPane.add(idTextField, 1, 1);

        Label setorLabel = new Label("Setor:");
        TextField setorTextField = new TextField();
        gridPane.add(setorLabel, 0, 2);
        gridPane.add(setorTextField, 1, 2);

        Label descricaoLabel = new Label("Descrição:");
        TextField descricaoTextField = new TextField();
        gridPane.add(descricaoLabel, 0, 3);
        gridPane.add(descricaoTextField, 1, 3);

        Label garantiaLabel = new Label("Garantia:");
        CheckBox garantiaCheckBox = new CheckBox();
        gridPane.add(garantiaLabel, 0, 4);
        gridPane.add(garantiaCheckBox, 1, 4);

        Label validadeLabel = new Label("Validade da Garantia:");
        DatePicker validadeDatePicker = new DatePicker();
        gridPane.add(validadeLabel, 0, 5);
        gridPane.add(validadeDatePicker, 1, 5);

        Label dataCompraLabel = new Label("Data da Compra:");
        DatePicker dataCompraDatePicker = new DatePicker();
        gridPane.add(dataCompraLabel, 0, 6);
        gridPane.add(dataCompraDatePicker, 1, 6);

        Label fabricanteLabel = new Label("Filial de origem:");
        TextField fabricanteTextField = new TextField();
        gridPane.add(fabricanteLabel, 0, 7);
        gridPane.add(fabricanteTextField, 1, 7);

        Label valorLabel = new Label("Valor:");
        TextField valorTextField = new TextField();
        gridPane.add(valorLabel, 0, 8);
        gridPane.add(valorTextField, 1, 8);

        Label condicoesLabel = new Label("Condições:");
        TextArea condicoesTextArea = new TextArea();
        gridPane.add(condicoesLabel, 0, 9);
        gridPane.add(condicoesTextArea, 1, 9);

        Label observacaoLabel = new Label("Observação:");
        TextArea observacaoTextArea = new TextArea();
        gridPane.add(observacaoLabel, 0, 10);
        gridPane.add(observacaoTextArea, 1, 10);


        Label ultimaManutencaoLabel = new Label("Última Manutenção:");
        DatePicker ultimaManutencaoDatePicker = new DatePicker();
        gridPane.add(ultimaManutencaoLabel, 0, 11);
        gridPane.add(ultimaManutencaoDatePicker, 1, 11);

        Label previsaoProximaManutencaoLabel = new Label("Próxima Manutenção:");
        DatePicker previsaoProximaManutencaoDatePicker = new DatePicker();
        gridPane.add(previsaoProximaManutencaoLabel, 0, 12);
        gridPane.add(previsaoProximaManutencaoDatePicker, 1, 12);


        Button salvarButton = new Button("Salvar");
        gridPane.add(salvarButton, 1, 13);


        salvarButton.setOnAction(event -> {
            int id;
            try {
                id = Integer.parseInt(idTextField.getText());
            } catch (NumberFormatException e) {
                // Tratar o caso em que o valor não é um número válido
                // Por exemplo, exibir uma mensagem de erro ao usuário
                System.out.println("O valor do ID não é um número válido");
                return; // ou qualquer outra ação adequada ao seu caso
            }

            String setor = setorTextField.getText();
            String descricao = descricaoTextField.getText();
            boolean garantia = garantiaCheckBox.isSelected();
            String validadeGarantia = garantia ? validadeDatePicker.getValue().toString() : null;
            String dataCompra = dataCompraDatePicker.getValue() != null ? dataCompraDatePicker.getValue().toString() : null;
            String fabricante = fabricanteTextField.getText();
            double valor = Double.parseDouble(valorTextField.getText());
            String condicoes = condicoesTextArea.getText();
            String observacao = observacaoTextArea.getText();

            String ultimaManutencao = ultimaManutencaoDatePicker.getValue() != null
                    ? ultimaManutencaoDatePicker.getValue().toString()
                    : null;
            String previsaoProximaManutencao = previsaoProximaManutencaoDatePicker.getValue() != null
                    ? previsaoProximaManutencaoDatePicker.getValue().toString()
                    : null;

            insertComputer(id, setor, descricao, garantia, validadeGarantia, dataCompra,
                    fabricante, valor, condicoes, observacao, ultimaManutencao, previsaoProximaManutencao);


            // Exemplo de exibição dos dados recebidos
            System.out.println("Dados inseridos:");
            System.out.println("ID: " + id);
            System.out.println("Setor: " + setor);

            System.out.println("Descrição: " + descricao);
            System.out.println("Garantia: " + garantia);
            if (garantia) {
                System.out.println("Validade da Garantia: " + validadeGarantia);
            }
            System.out.println("Data da Compra: " + dataCompra);
            System.out.println("Fabricante: " + fabricante);
            System.out.println("Valor: " + valor);
            System.out.println("Condições: " + condicoes);
            System.out.println("Observação: " + observacao);
            System.out.println("Última manuntenção: " + ultimaManutencao);
            System.out.println("Próxima manuntenção: " + previsaoProximaManutencao);

        });

        Button listarButton = new Button("Listar Computadores");
        gridPane.add(listarButton, 1, 15);

        listarButton.setOnAction(event -> {
            ListaComputadoresFX listaComputadoresWindow = new ListaComputadoresFX();
            try {
                listaComputadoresWindow.exibirListaComputadores(connection.createStatement());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true); // Permite redimensionamento da janela mais à esquerda
        primaryStage.show();
    }

    private void createConnection() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            createTable();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS computers (" +
                "id INTEGER PRIMARY KEY," +
                "setor TEXT," +
                "descricao TEXT," +
                "garantia BOOLEAN," +
                "validade_garantia TEXT," +
                "data_compra TEXT," +
                "fabricante TEXT," +
                "valor REAL," +
                "condicoes TEXT," +
                "observacao TEXT," +
                "ultima_manutencao TEXT," +
                "previsao_proxima_manutencao TEXT" +
                ")";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela no banco de dados: " + e.getMessage());
        }
    }

    private void insertComputer(int id, String setor, String descricao, boolean garantia, String validadeGarantia,
                                String dataCompra, String fabricante, double valor, String condicoes,
                                String observacao, String ultimaManutencao, String previsaoProximaManutencao) {
        String sql = "INSERT INTO computers (id, setor, descricao, garantia, validade_garantia, data_compra, " +
                "fabricante, valor, condicoes, observacao, ultima_manutencao, previsao_proxima_manutencao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, setor);
            statement.setString(3, descricao);
            statement.setBoolean(4, garantia);
            statement.setString(5, validadeGarantia);
            statement.setString(6, dataCompra);
            statement.setString(7, fabricante);
            statement.setDouble(8, valor);
            statement.setString(9, condicoes);
            statement.setString(10, observacao);
            statement.setString(11, ultimaManutencao);
            statement.setString(12, previsaoProximaManutencao);

            statement.executeUpdate();
            System.out.println("Dados inseridos com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir os dados no banco de dados: " + e.getMessage());
        }
    }
}
