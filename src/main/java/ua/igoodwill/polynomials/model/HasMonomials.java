package ua.igoodwill.polynomials.model;

public interface HasMonomials {

    double getCoefficient(int... degrees);

    Monomial[] getMonomials();

    Monomial getLeadingMonomial();

    boolean isZero();
}
