package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:C:\\Users\\Joelson\\Documents\\inventario.db";
    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS computers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "setor TEXT," +
                "descricao TEXT," +
                "garantia INTEGER," +
                "validade_garantia TEXT," +
                "data_compra TEXT," +
                "fabricante TEXT," +
                "valor REAL," +
                "condicoes TEXT," +
                "observacao TEXT" +
                ")";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao criar a tabela: " + e.getMessage());
        }
    }

    public void insertComputador(Computador computador) {
        String sql = "INSERT INTO computers (setor, descricao, garantia, validade_garantia, " +
                "data_compra, fabricante, valor, condicoes, observacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, computador.getSetor());
            statement.setString(2, computador.getDescricao());
            statement.setBoolean(3, computador.isGarantia());
            statement.setString(4, computador.getValidadeGarantia());
            statement.setString(5, computador.getDataCompra());
            statement.setString(6, computador.getFabricante());
            statement.setDouble(7, computador.getValor());
            statement.setString(8, computador.getCondicoes());
            statement.setString(9, computador.getObservacao());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir o computador no banco de dados: " + e.getMessage());
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
