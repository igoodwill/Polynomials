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
import ua.igoodwill.polynomials.service.locale.MessageService;
import ua.igoodwill.polynomials.service.locale.NotationService;
import ua.igoodwill.polynomials.util.locale.MessageUtilImpl;

import java.io.IOException;

public class Main {

    private BasicWriter basicWriter;
    private BasicReader basicReader;

    private PolynomialsReader polynomialsReader;

    private BasicOperations basicOperations;
    private Division division;

    public static void main(String[] args) throws IOException {
        Main instance = new Main();
        instance.init();

        instance.basicWriter.write("F: ");
        Polynomial f = instance.polynomialsReader.readPolynomial();
        instance.basicWriter.write("G: ");
        Polynomial g = instance.polynomialsReader.readPolynomial();

        DivisionResult divisionResult = instance.division.divide(f, g);
        instance.basicWriter.writeLine(divisionResult.toString());
    }

    private void init() {
        MessageService.setUtil(MessageUtilImpl.getInstance());
        NotationService.setVariableLetter("x");
        NotationService.setPowerSymbol("^");
        NotationService.setPositiveSign("+");
        NotationService.setNegativeSign("-");

        basicWriter = new ConsoleWriter();
        basicReader = new ConsoleReader();

        polynomialsReader = new PolynomialsReaderImpl(basicReader);

        basicOperations = new BasicOperationsImpl();
        division = new DivisionImpl(basicOperations);
    }
}
