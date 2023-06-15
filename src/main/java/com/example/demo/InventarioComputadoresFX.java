package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class InventarioComputadoresFX extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventário de Computadores");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Criar o ImageView para a logo
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

        Label fabricanteLabel = new Label("Fabricante:");
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

        Button salvarButton = new Button("Salvar");
        gridPane.add(salvarButton, 1, 11);

        salvarButton.setOnAction(event -> {
            int id = Integer.parseInt(idTextField.getText());
            String setor = setorTextField.getText();
            String descricao = descricaoTextField.getText();
            boolean garantia = garantiaCheckBox.isSelected();
            String validadeGarantia = garantia ? validadeDatePicker.getValue().toString() : null;
            String dataCompra = dataCompraDatePicker.getValue().toString();
            String fabricante = fabricanteTextField.getText();
            double valor = Double.parseDouble(valorTextField.getText());
            String condicoes = condicoesTextArea.getText();
            String observacao = observacaoTextArea.getText();

            // Agora você pode usar os dados inseridos para fazer o que for necessário, como salvar em um banco de dados

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
        });

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true); // Permite redimensionamento da janela
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
