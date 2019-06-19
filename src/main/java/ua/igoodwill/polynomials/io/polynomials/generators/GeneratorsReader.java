package ua.igoodwill.polynomials.io.polynomials.generators;

import ua.igoodwill.polynomials.model.Polynomial;

import java.io.IOException;

public interface GeneratorsReader {

    Polynomial[] readGenerators() throws IOException;
}
