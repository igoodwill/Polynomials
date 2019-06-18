package ua.igoodwill.polynomials.algo.basic;

import ua.igoodwill.polynomials.model.HasMonomials;
import ua.igoodwill.polynomials.model.Monomial;
import ua.igoodwill.polynomials.model.Polynomial;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class BasicOperationsImpl implements BasicOperations {

    @Override
    public Polynomial add(HasMonomials p1, HasMonomials p2) {
        Monomial[] monomials = p1.getMonomials();
        Monomial[] monomials2 = p2.getMonomials();

        TreeMap<Monomial, Monomial> result = new TreeMap<>();
        Arrays
                .stream(new Monomial[][]{monomials, monomials2})
                .flatMap(Arrays::stream)
                .forEach(monomial -> {
                    Monomial currentMonomial = result.get(monomial);
                    if (currentMonomial != null) {
                        int[] degrees = monomial.getDegrees();
                        double coefficient = monomial.getCoefficient();
                        double currentCoefficient = currentMonomial.getCoefficient();
                        monomial = new Monomial(currentCoefficient + coefficient, degrees);
                    }

                    result.put(monomial, monomial);
                });

        return new Polynomial(result);
    }

    @Override
    public Polynomial subtract(HasMonomials minuend, HasMonomials subtrahend) {
        Monomial[] minuendMonomials = minuend.getMonomials();
        Monomial[] subtrahendMonomials = subtrahend.getMonomials();

        TreeMap<Monomial, Monomial> result = new TreeMap<>();
        Arrays
                .stream(minuendMonomials)
                .forEach(monomial -> result.put(monomial, monomial));

        Arrays
                .stream(subtrahendMonomials)
                .forEach(monomial -> {
                    Monomial currentMonomial = result.get(monomial);
                    if (currentMonomial != null) {
                        int[] degrees = monomial.getDegrees();
                        double coefficient = monomial.getCoefficient();
                        double currentCoefficient = currentMonomial.getCoefficient();
                        monomial = new Monomial(currentCoefficient - coefficient, degrees);
                    }

                    result.put(monomial, monomial);
                });

        return new Polynomial(result);
    }

    @Override
    public Polynomial multiply(HasMonomials p1, HasMonomials p2) {
        Monomial[] monomials = p1.getMonomials();
        Monomial[] monomials2 = p2.getMonomials();

        TreeMap<Monomial, Monomial> result = new TreeMap<>();
        Arrays
                .stream(monomials)
                .forEach(monomial -> {
                    Arrays
                            .stream(monomials2)
                            .forEach(monomial2 -> {
                                int[] degrees = monomial.getDegrees();
                                int[] degrees2 = monomial2.getDegrees();

                                double newCoefficient = monomial.getCoefficient() * monomial2.getCoefficient();

                                if (newCoefficient == 0) {
                                    return;
                                }

                                int[] newDegrees = IntStream
                                        .range(0, degrees.length)
                                        .map(index -> degrees[index] + degrees2[index])
                                        .toArray();

                                Monomial newMonomial = new Monomial(newCoefficient, newDegrees);
                                Monomial currentMonomial = result.get(newMonomial);
                                if (currentMonomial != null) {
                                    double currentCoefficient = currentMonomial.getCoefficient();
                                    newMonomial = new Monomial(currentCoefficient + newCoefficient, newDegrees);
                                }

                                result.put(newMonomial, newMonomial);
                            });
                });

        return new Polynomial(result);
    }
}
