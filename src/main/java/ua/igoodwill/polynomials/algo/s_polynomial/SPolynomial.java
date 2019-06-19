package ua.igoodwill.polynomials.algo.s_polynomial;

import ua.igoodwill.polynomials.model.HasMonomials;
import ua.igoodwill.polynomials.model.Polynomial;

public interface SPolynomial {

    Polynomial sPolynomial(HasMonomials p1, HasMonomials p2);
}
