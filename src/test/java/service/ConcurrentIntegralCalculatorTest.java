package service;

import calc.Function;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcurrentIntegralCalculatorTest {
    private static final double EPS = 1e-5;
    ConcurrentIntegralCalculator calculator;

    @ParameterizedTest
    @CsvSource({"100, 10", "100, 50", "100, 180",
            "100_000, 10", "100_000, 50", "100_000, 180",
            "100_000_000, 10", "100_000_000, 50", "100_000_000, 180",
            "1_000_000_000, 10", "1_000_000_000, 50", "1_000_000_000, 180"
    })
    void calculateTest(long n, long threads) throws InterruptedException {
        calculator = new ConcurrentIntegralCalculator();
        double expected = 50000.0;
        double actual = calculator.calculate(EPS, Math.PI / 2 - EPS, n, Function::calculate, (int) threads).result();
        double relativeError = Math.abs((expected - actual) / expected);
        assertEquals(1e-9, relativeError, EPS);
    }
}
