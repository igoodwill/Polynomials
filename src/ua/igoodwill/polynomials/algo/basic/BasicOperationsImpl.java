package ua.igoodwill.polynomials.algo.basic;

import ua.igoodwill.polynomials.model.Polynomial;

import java.util.stream.IntStream;

public class BasicOperationsImpl implements BasicOperations {

    @Override
    public Polynomial add(Polynomial p1, Polynomial p2) {
        return new Polynomial(
                IntStream
                        .range(0, getDegreeForSum(p1, p2) + 1)
                        .mapToDouble(i -> addCoefficients(p1, p2, i))
                        .toArray()
        );
    }

    @Override
    public Polynomial subtract(Polynomial minuend, Polynomial subtrahend) {
        return add(minuend, new Polynomial(
                IntStream
                        .range(0, subtrahend.getDegree() + 1)
                        .mapToDouble(subtrahend::getCoefficient)
                        .map(coefficient -> -coefficient)
                        .toArray()
        ));
    }

    @Override
    public Polynomial multiply(Polynomial p1, Polynomial p2) {
        int degree1 = p1.getDegree();
        int degree2 = p2.getDegree();

        return new Polynomial(
                IntStream
                        .range(0, degree1 + degree2 + 1)
                        .mapToDouble(
                                i -> IntStream
                                        .range(0, i + 1)
                                        .mapToDouble(
                                                j -> p1.getCoefficient(j) * p2.getCoefficient(i - j)
                                        )
                                        .sum()
                        )
                        .toArray()
        );
    }

    private int getDegreeForSum(Polynomial p1, Polynomial p2) {
        int degree1 = p1.getDegree();
        int degree2 = p2.getDegree();

        if (degree1 == degree2) {
            for (int i = degree1; i > 0; i--) {
                if (addCoefficients(p1, p2, i) != 0) {
                    return i;
                }
            }

            return 0;
        }

        return Math.max(degree1, degree2);
    }

    private double addCoefficients(Polynomial p1, Polynomial p2, int index) {
        return p1.getCoefficient(index) + p2.getCoefficient(index);
    }
}
