package ua.igoodwill.polynomials.algo.s_polynomial;

import ua.igoodwill.polynomials.algo.basic.BasicOperations;
import ua.igoodwill.polynomials.algo.division.Division;
import ua.igoodwill.polynomials.algo.monomial.lcm.MonomialLcm;
import ua.igoodwill.polynomials.model.HasMonomials;
import ua.igoodwill.polynomials.model.Monomial;
import ua.igoodwill.polynomials.model.Polynomial;

public class SPolynomialImpl implements SPolynomial {

    private final BasicOperations basicOperations;
    private final Division division;
    private final MonomialLcm monomialLcm;

    public SPolynomialImpl(BasicOperations basicOperations, Division division, MonomialLcm monomialLcm) {
        this.basicOperations = basicOperations;
        this.division = division;
        this.monomialLcm = monomialLcm;
    }

    @Override
    public Polynomial sPolynomial(HasMonomials p1, HasMonomials p2) {
        Monomial leadingMonomial1 = p1.getLeadingMonomial();
        Monomial leadingMonomial2 = p2.getLeadingMonomial();

        Monomial lcm = monomialLcm.lcm(leadingMonomial1, leadingMonomial2);

        Polynomial quotient1 = division.divide(lcm, leadingMonomial1).getQuotient();
        Polynomial quotient2 = division.divide(lcm, leadingMonomial2).getQuotient();

        return basicOperations.subtract(
                basicOperations.multiply(p1, quotient1),
                basicOperations.multiply(p2, quotient2)
        );
    }
}
