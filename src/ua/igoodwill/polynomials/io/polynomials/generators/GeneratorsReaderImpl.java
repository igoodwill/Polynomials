package ua.igoodwill.polynomials.io.polynomials.generators;

import ua.igoodwill.polynomials.io.polynomials.PolynomialsReader;
import ua.igoodwill.polynomials.model.Polynomial;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneratorsReaderImpl implements GeneratorsReader {

    private final PolynomialsReader polynomialsReader;

    public GeneratorsReaderImpl(PolynomialsReader polynomialsReader) {
        this.polynomialsReader = polynomialsReader;
    }

    @Override
    public Polynomial[] readGenerators() throws IOException {
        List<Polynomial> generators = new ArrayList<>();
        Polynomial generator;
        while (!(generator = polynomialsReader.readPolynomial()).isZero()) {
            generators.add(generator);
        }

        return generators.toArray(new Polynomial[0]);
    }
}
