package controller;

import calc.Function;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.ConcurrentIntegralCalculator;
import service.ConcurrentIntegralCalculator.CalculationResult;

public class MainController {
    @FXML
    private TextField nField;
    @FXML
    private TextField threadsField;
    @FXML
    private Label resultLabel;
    @FXML
    private Label timeLabel;
    private final ConcurrentIntegralCalculator calculator = new ConcurrentIntegralCalculator();

    public void calculate() {
        long n = Long.parseLong(nField.getText());
        int threads = Integer.parseInt(threadsField.getText());
        Thread.startVirtualThread(() -> {
            CalculationResult result = calculator.calculate(0, Math.PI / 2, n, Function::calculate, threads);
            Platform.runLater(() -> {
                resultLabel.setText("I = " + result.result());
                timeLabel.setText("Час виконання = " + result.duration() + " ms");
            });
        });
    }
}
