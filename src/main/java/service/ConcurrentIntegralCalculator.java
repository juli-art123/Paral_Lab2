package service;

import calc.CallableIntegralCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.DoubleUnaryOperator;

public class ConcurrentIntegralCalculator {
    public record CalculationResult(double result, long duration) {}

    public CalculationResult calculate(double a, double b, long n, DoubleUnaryOperator f, int threads) {
        long startTime = System.currentTimeMillis();
        double delta = (b - a) / threads;
        List<Future<Double>> futureList = new ArrayList<>();
        try (ExecutorService es = Executors.newFixedThreadPool(threads)) {
            for (int i = 0; i < threads; i++) {
                double ai = a + i * delta;
                double bi = ai + delta;
                long ni = n / threads;
                CallableIntegralCalculator calculator = new CallableIntegralCalculator(ai, bi, ni, f);
                futureList.add(es.submit(calculator));
            }
            es.shutdown();
        }
        try {
            double totalSum = 0;
            for (Future<Double> future : futureList) {
                totalSum += future.get();
            }
            long endTime = System.currentTimeMillis();
            return new CalculationResult(totalSum, endTime - startTime);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
