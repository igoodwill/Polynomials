package ua.igoodwill.polynomials.algo.buchberger;

import ua.igoodwill.polynomials.model.HasMonomials;
import ua.igoodwill.polynomials.model.Polynomial;

public interface Buchberger {

    Polynomial[] findGrobnerBasis(HasMonomials... generators);
}
