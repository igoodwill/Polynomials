package ua.igoodwill.polynomials.algo.buchberger;

import ua.igoodwill.polynomials.algo.division.Division;
import ua.igoodwill.polynomials.algo.s_polynomial.SPolynomial;
import ua.igoodwill.polynomials.model.HasMonomials;
import ua.igoodwill.polynomials.model.Monomial;
import ua.igoodwill.polynomials.model.Polynomial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuchbergerImpl implements Buchberger {

    private final Division division;
    private final SPolynomial sPolynomial;

    public BuchbergerImpl(Division division, SPolynomial sPolynomial) {
        this.division = division;
        this.sPolynomial = sPolynomial;
    }

    @Override
    public Polynomial[] findGrobnerBasis(HasMonomials... generators) {
        List<Polynomial> result = Arrays
                .stream(generators)
                .map(Polynomial::new)
                .collect(Collectors.toList());

        for (int i = 1; i < result.size(); i++) {
            Polynomial p1 = result.get(i);
            for (int j = 0; j < i; j++) {
                Polynomial p2 = result.get(j);

                Polynomial s = sPolynomial.sPolynomial(p1, p2);
                Polynomial remainder = division.divide(s, result.toArray(new Polynomial[0])).getRemainder();
                if (!remainder.isZero()) {
                    result.add(remainder);
                }
            }
        }

        return result.toArray(new Polynomial[0]);
    }

    @Override
    public Polynomial[] findMinimalGrobnerBasis(HasMonomials... generators) {
        List<Polynomial> result = Arrays
                .stream(findGrobnerBasis(generators))
                .map(generator ->
                        division.divide(
                                generator, Monomial.constant(generator.getLeadingMonomial().getCoefficient())
                        ).getQuotients()[0]
                )
                .collect(Collectors.toList());

        for (int i = 0; i < result.size(); i++) {
            Polynomial p1 = result.get(i);
            for (int j = i + 1; j < result.size(); j++) {
                Polynomial p2 = result.get(j);

                Monomial leadingMonomial1 = p1.getLeadingMonomial();
                Monomial leadingMonomial2 = p2.getLeadingMonomial();

                if (division.divide(leadingMonomial1, leadingMonomial2).getRemainder().isZero()) {
                    result.remove(i);
                    i--;
                    break;
                } else if (division.divide(leadingMonomial2, leadingMonomial1).getRemainder().isZero()) {
                    result.remove(j);
                    j--;
                }
            }
        }

        return result.toArray(new Polynomial[0]);
    }

    @Override
    public Polynomial[] findReducedGrobnerBasis(HasMonomials... generators) {
        Polynomial[] result = findMinimalGrobnerBasis(generators);

        for (int i = 0; i < result.length; i++) {
            Polynomial polynomial = result[i];
            result[i] = division.divide(
                    polynomial,
                    Stream
                            .concat(Arrays.stream(result, 0, i),
                                    Arrays.stream(result, i + 1, result.length))
                            .toArray(HasMonomials[]::new)
            ).getRemainder();
        }

        return result;
    }
}
