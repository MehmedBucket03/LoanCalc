package com.example.loancalc;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoanCalculator extends Application {

    //Declare controls for UI
    private TextField tfLoanAmount = new TextField();
    private TextField tfAnnualInterestRate = new TextField();
    private TextField tfNumberOfYears = new TextField();
    private Label lblMonthlyPayment = new Label();
    private Label lblTotalPayment = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //layout grid
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        //UI grid controls
        gridPane.add(new Label("Loan Amount:"), 0, 0);
        gridPane.add(tfLoanAmount, 1, 0);
        gridPane.add(new Label("Annual Interest Rate (%):"), 0, 1);
        gridPane.add(tfAnnualInterestRate, 1, 1);
        gridPane.add(new Label("Number of Years:"), 0, 2);
        gridPane.add(tfNumberOfYears, 1, 2);

        Button btnComputePayment = new Button("Compute Payment");
        gridPane.add(btnComputePayment, 1, 3);

        gridPane.add(new Label("Monthly Payment:"), 0, 4);
        gridPane.add(lblMonthlyPayment, 1, 4);
        gridPane.add(new Label("Total Payment:"), 0, 5);
        gridPane.add(lblTotalPayment, 1, 5);

        //event-driven for button
        btnComputePayment.setOnAction(e -> computeLoanPayment());

        // Create the scene and show the stage
        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setTitle("Loan Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Calculate and display payments
    private void computeLoanPayment() {
        try {
            //input receiver
            double loanAmount = Double.parseDouble(tfLoanAmount.getText());
            double annualInterestRate = Double.parseDouble(tfAnnualInterestRate.getText());
            int numberOfYears = Integer.parseInt(tfNumberOfYears.getText());

            //input validity
            if (loanAmount <= 0 || annualInterestRate <= 0 || numberOfYears <= 0) {
                showError("Please enter valid positive numbers.");
                return;
            }

            //monthly interest rate
            double monthlyInterestRate = annualInterestRate / 1200;

            //monthly payment formula
            double monthlyPayment = loanAmount * monthlyInterestRate /
                    (1 - Math.pow(1 + monthlyInterestRate, -numberOfYears * 12));

            //Calculate total payment
            double totalPayment = monthlyPayment * numberOfYears * 12;

            //display results
            lblMonthlyPayment.setText(String.format("$%.2f", monthlyPayment));
            lblTotalPayment.setText(String.format("$%.2f", totalPayment));
        } catch (NumberFormatException ex) {
            showError("Please enter valid numeric values.");
        }
    }

    //show error messages for invalid inputs
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
