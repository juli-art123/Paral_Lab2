package calc;

import java.util.concurrent.Callable;
import java.util.function.DoubleUnaryOperator;

public class CallableIntegralCalculator implements Callable<Double> {
    private final IntegralCalculator calculator;

    public CallableIntegralCalculator(double a, double b, long n, DoubleUnaryOperator f) {
        this.calculator = new IntegralCalculator(a, b, n, f);
    }

    @Override
    public Double call() throws Exception {
        return calculator.calculate();
    }
}
