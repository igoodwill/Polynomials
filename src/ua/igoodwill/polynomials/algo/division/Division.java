package ua.igoodwill.polynomials.algo.division;

import ua.igoodwill.polynomials.model.HasMonomials;

public interface Division {

    DivisionResult divide(HasMonomials dividend, HasMonomials divisor);
}
