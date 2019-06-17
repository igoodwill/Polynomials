package ua.igoodwill.polynomials;

import ua.igoodwill.polynomials.algo.basic.BasicOperations;
import ua.igoodwill.polynomials.algo.basic.BasicOperationsImpl;
import ua.igoodwill.polynomials.algo.division.Division;
import ua.igoodwill.polynomials.algo.division.DivisionImpl;
import ua.igoodwill.polynomials.algo.division.DivisionResult;
import ua.igoodwill.polynomials.io.basic.BasicReader;
import ua.igoodwill.polynomials.io.basic.BasicWriter;
import ua.igoodwill.polynomials.io.basic.ConsoleReader;
import ua.igoodwill.polynomials.io.basic.ConsoleWriter;
import ua.igoodwill.polynomials.io.polynomials.PolynomialsReader;
import ua.igoodwill.polynomials.io.polynomials.PolynomialsReaderImpl;
import ua.igoodwill.polynomials.model.Polynomial;
import ua.igoodwill.polynomials.util.locale.MessageUtil;
import ua.igoodwill.polynomials.util.locale.MessageUtilImpl;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BasicWriter cw = new ConsoleWriter();
        BasicReader cr = new ConsoleReader();
        MessageUtil messageUtil = MessageUtilImpl.getInstance();
        PolynomialsReader pr = new PolynomialsReaderImpl(cr, messageUtil);

        cw.write("F: ");
        Polynomial f = pr.readPolynomial();
        cw.write("G: ");
        Polynomial g = pr.readPolynomial();

        BasicOperations bo = new BasicOperationsImpl(messageUtil);
        Division division = new DivisionImpl(messageUtil, bo);

        DivisionResult divisionResult = division.divide(f, g);
        cw.writeLine(divisionResult.toString());
    }
}
