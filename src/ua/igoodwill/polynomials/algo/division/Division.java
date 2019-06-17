package ua.igoodwill.polynomials.algo.division;

import ua.igoodwill.polynomials.model.Polynomial;

public interface Division {

    DivisionResult divide(Polynomial dividend, Polynomial divisor);
}
