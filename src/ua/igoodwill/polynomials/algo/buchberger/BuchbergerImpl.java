package ua.igoodwill.polynomials.algo.buchberger;

import ua.igoodwill.polynomials.algo.division.Division;
import ua.igoodwill.polynomials.algo.s_polynomial.SPolynomial;
import ua.igoodwill.polynomials.model.HasMonomials;
import ua.igoodwill.polynomials.model.Polynomial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
            for (int j = 0; j < i; j++) {
                Polynomial p1 = result.get(i);
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
}
