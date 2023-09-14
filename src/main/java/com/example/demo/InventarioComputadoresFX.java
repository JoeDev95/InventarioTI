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
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=123";

    private Connection connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventário de máquinas");

        createConnection();

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        // ImageView para a logo
        Image logoImage = new Image(getClass().getResourceAsStream("/logo.png"));
        ImageView logoImageView = new ImageView(logoImage);
        vbox.getChildren().add(logoImageView);

        // Criação de controles para coleta de informações sobre o computador
        vbox.getChildren().addAll(createComputerForm());

        // Botões para Salvar e Listar
        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(10, 0, 0, 0));

        Button salvarButton = new Button("Salvar");
        Button listarButton = new Button("Listar Computadores");

        buttonsBox.getChildren().addAll(salvarButton, listarButton);

        vbox.getChildren().add(buttonsBox);

        salvarButton.setOnAction(event -> {
            Computer computer = getComputerFromForm();
            if (computer != null) {
                insertComputer(computer);
                clearFormFields();
            }
        });

        listarButton.setOnAction(event -> {
            ListaComputadoresFX listaComputadoresWindow = new ListaComputadoresFX();
            try {
                listaComputadoresWindow.exibirListaComputadores(connection.createStatement());
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

    private void createConnection() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            if (connection != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
                createTable();
            } else {
                System.out.println("A conexão com o banco de dados não pôde ser estabelecida.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS computers (" +
                "id SERIAL PRIMARY KEY," +
                "setor TEXT," +
                "descricao TEXT," +
                "garantia BOOLEAN," +
                "validade_garantia DATE," +
                "data_compra DATE," +
                "fabricante TEXT," +
                "valor REAL," +
                "condicoes TEXT," +
                "observacao TEXT," +
                "ultima_manutencao DATE," +
                "previsao_proxima_manutencao DATE" +
                ")";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
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
        TextField setorTextField = new TextField();
        gridPane.addRow(1, setorLabel, setorTextField);

        Label descricaoLabel = new Label("Descrição:");
        TextField descricaoTextField = new TextField();
        gridPane.addRow(2, descricaoLabel, descricaoTextField);

        Label garantiaLabel = new Label("Garantia:");
        CheckBox garantiaCheckBox = new CheckBox();
        gridPane.addRow(3, garantiaLabel, garantiaCheckBox);

        Label validadeLabel = new Label("Validade da Garantia:");
        DatePicker validadeDatePicker = new DatePicker();
        gridPane.addRow(4, validadeLabel, validadeDatePicker);

        Label dataCompraLabel = new Label("Data da Compra:");
        DatePicker dataCompraDatePicker = new DatePicker();
        gridPane.addRow(5, dataCompraLabel, dataCompraDatePicker);

        Label fabricanteLabel = new Label("Filial de Origem:");
        ChoiceBox<String> fabricanteChoiceBox = new ChoiceBox<>();
        fabricanteChoiceBox.getItems().addAll("Filial 1", "Filial 2", "Filial 3", "Filial 4", "Filial 5");
        fabricanteChoiceBox.setValue("Filial 1");
        gridPane.addRow(6, fabricanteLabel, fabricanteChoiceBox);

        Label valorLabel = new Label("Valor:");
        TextField valorTextField = new TextField();
        gridPane.addRow(7, valorLabel, valorTextField);

        Label condicoesLabel = new Label("Condições:");
        TextArea condicoesTextArea = new TextArea();
        gridPane.addRow(8, condicoesLabel, condicoesTextArea);

        Label observacaoLabel = new Label("Observação:");
        TextArea observacaoTextArea = new TextArea();
        gridPane.addRow(9, observacaoLabel, observacaoTextArea);

        Label ultimaManutencaoLabel = new Label("Última Manutenção:");
        DatePicker ultimaManutencaoDatePicker = new DatePicker();
        gridPane.addRow(10, ultimaManutencaoLabel, ultimaManutencaoDatePicker);

        Label previsaoProximaManutencaoLabel = new Label("Próxima Manutenção:");
        DatePicker previsaoProximaManutencaoDatePicker = new DatePicker();
        gridPane.addRow(11, previsaoProximaManutencaoLabel, previsaoProximaManutencaoDatePicker);

        return gridPane;
    }

    private Computer getComputerFromForm() {
        try {
            int id = 0;
            String setor = "";
            String descricao = "";
            boolean garantia = false;
            LocalDate validadeGarantia = null;
            String fabricante = "";
            double valor = 0;
            String condicoes = "";
            String observacao = "";
            LocalDate ultimaManutencao = null;
            LocalDate previsaoProximaManutencao = null;

            // Obter valores dos campos do formulário
            // Você deve adicionar a lógica para obter os valores corretamente dos campos

            return new Computer(id, setor, descricao, garantia, validadeGarantia, fabricante, valor, condicoes, observacao, ultimaManutencao, previsaoProximaManutencao);
        } catch (Exception e) {
            System.out.println("Erro ao obter os dados do formulário: " + e.getMessage());
            return null;
        }
    }

    private void clearFormFields() {
        // Limpar os campos do formulário após a inserção bem-sucedida
    }

    private void insertComputer(Computer computer) {
        String sql = "INSERT INTO computers (setor, descricao, garantia, validade_garantia, data_compra, fabricante, valor, condicoes, observacao, ultima_manutencao, previsao_proxima_manutencao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, computer.getSetor());
            statement.setString(2, computer.getDescricao());
            statement.setBoolean(3, computer.isGarantia());
            statement.setString(6, computer.getFabricante());
            statement.setDouble(7, computer.getValor());
            statement.setString(8, computer.getCondicoes());
            statement.setString(9, computer.getObservacao());
            statement.setObject(10, computer.getUltimaManutencao());
            statement.setObject(11, computer.getPrevisaoProximaManutencao());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Computador inserido com sucesso. ID gerado: " + generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir o computador no banco de dados: " + e.getMessage());
        }
    }
}
