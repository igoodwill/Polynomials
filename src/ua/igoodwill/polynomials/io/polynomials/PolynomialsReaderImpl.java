package ua.igoodwill.polynomials.io.polynomials;

import ua.igoodwill.polynomials.io.basic.BasicReader;
import ua.igoodwill.polynomials.model.Polynomial;
import ua.igoodwill.polynomials.util.locale.MessageUtil;

import java.io.IOException;

public class PolynomialsReaderImpl implements PolynomialsReader {

    private final BasicReader basicReader;
    private final MessageUtil messageUtil;

    public PolynomialsReaderImpl(BasicReader basicReader, MessageUtil messageUtil) {
        this.basicReader = basicReader;
        this.messageUtil = messageUtil;
    }

    @Override
    public Polynomial readPolynomial() throws IOException {
        String line = basicReader.readLine();
        return Polynomial.from(messageUtil, line);
    }
}
