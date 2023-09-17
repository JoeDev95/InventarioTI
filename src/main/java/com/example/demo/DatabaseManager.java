package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=123";
    private static DatabaseManager instance;

    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
            createTable();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS computers (" +
                "id INTEGER PRIMARY KEY," +
                "setor TEXT," +
                "descricao TEXT," +
                "fabricante TEXT," +
                "valor REAL," +
                "condicoes TEXT," +
                "observacao TEXT," +
                "ultima_manutencao TEXT," +
                "previsao_proxima_manutencao TEXT" +
                ")";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }

    public void insertComputer(Computer computador) {
        String sql = "INSERT INTO computers (id, setor, descricao, " +
                "fabricante, valor, condicoes, observacao, ultima_manutencao, previsao_proxima_manutencao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, computador.getSetor());
            statement.setString(2, computador.getDescricao());
            statement.setString(3, computador.getFabricante());
            statement.setDouble(4, computador.getValor());
            statement.setString(5, computador.getCondicoes());
            statement.setString(6, computador.getObservacao());
            statement.setString(7, computador.getPrevisaoProximaManutencao());
            statement.setString(8,computador.getUltimaManutencao());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir o bem no banco de dados: " + e.getMessage());
        }
    }

    public ResultSet getAllComputadores() {
        String sql = "SELECT * FROM computers";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Erro ao obter todos os computadores do banco de dados: " + e.getMessage());
        }

        return null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão com o banco de dados: " + e.getMessage());
        }
    }
}
