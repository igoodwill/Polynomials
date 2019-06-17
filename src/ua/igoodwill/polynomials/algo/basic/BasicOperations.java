package ua.igoodwill.polynomials.algo.basic;

import ua.igoodwill.polynomials.model.Polynomial;

public interface BasicOperations {

    Polynomial add(Polynomial p1, Polynomial p2);

    Polynomial subtract(Polynomial minuend, Polynomial subtrahend);

    Polynomial multiply(Polynomial p1, Polynomial p2);
}
