package ua.igoodwill.polynomials.io.polynomials;

import ua.igoodwill.polynomials.model.Polynomial;

import java.io.IOException;

public interface PolynomialsReader {

    Polynomial readPolynomial() throws IOException;
}
