package ua.igoodwill.polynomials.algo.basic;

import ua.igoodwill.polynomials.model.HasMonomials;
import ua.igoodwill.polynomials.model.Monomial;
import ua.igoodwill.polynomials.model.MonomialsContainer;
import ua.igoodwill.polynomials.model.Polynomial;

import java.util.Arrays;
import java.util.stream.IntStream;

public class BasicOperationsImpl implements BasicOperations {

    @Override
    public Polynomial add(HasMonomials p1, HasMonomials p2) {
        Monomial[] monomials = p1.getMonomials();
        Monomial[] monomials2 = p2.getMonomials();

        MonomialsContainer result = new MonomialsContainer();
        Arrays
                .stream(new Monomial[][]{monomials, monomials2})
                .flatMap(Arrays::stream)
                .forEach(result::add);

        return new Polynomial(result);
    }

    @Override
    public Polynomial subtract(HasMonomials minuend, HasMonomials subtrahend) {
        return add(minuend, new Polynomial(Arrays
                .stream(subtrahend.getMonomials())
                .map(monomial -> {
                    double coefficient = monomial.getCoefficient();
                    int[] degrees = monomial.getDegrees();

                    return new Monomial(-coefficient, degrees);
                })
                .toArray(Monomial[]::new)));
    }

    @Override
    public Polynomial multiply(HasMonomials p1, HasMonomials p2) {
        Monomial[] monomials = p1.getMonomials();
        Monomial[] monomials2 = p2.getMonomials();

        MonomialsContainer result = new MonomialsContainer();
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
                                result.add(newMonomial);
                            });
                });

        return new Polynomial(result);
    }
}
