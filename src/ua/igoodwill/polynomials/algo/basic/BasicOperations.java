package ua.igoodwill.polynomials.algo.basic;

import ua.igoodwill.polynomials.model.HasMonomials;
import ua.igoodwill.polynomials.model.Polynomial;

public interface BasicOperations {

    Polynomial add(HasMonomials p1, HasMonomials p2);

    Polynomial subtract(HasMonomials minuend, HasMonomials subtrahend);

    Polynomial multiply(HasMonomials p1, HasMonomials p2);
}
