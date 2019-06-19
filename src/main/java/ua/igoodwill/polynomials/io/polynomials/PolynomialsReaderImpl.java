package ua.igoodwill.polynomials.io.polynomials;

import ua.igoodwill.polynomials.io.basic.BasicReader;
import ua.igoodwill.polynomials.model.Polynomial;

import java.io.IOException;

public class PolynomialsReaderImpl implements PolynomialsReader {

    private final BasicReader basicReader;

    public PolynomialsReaderImpl(BasicReader basicReader) {
        this.basicReader = basicReader;
    }

    @Override
    public Polynomial readPolynomial() throws IOException {
        String line = basicReader.readLine();
        return Polynomial.from(line);
    }
}
