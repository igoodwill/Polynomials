package ua.igoodwill.polynomials;

import ua.igoodwill.polynomials.algo.basic.BasicOperations;
import ua.igoodwill.polynomials.algo.basic.BasicOperationsImpl;
import ua.igoodwill.polynomials.algo.division.Division;
import ua.igoodwill.polynomials.algo.division.DivisionImpl;
import ua.igoodwill.polynomials.algo.division.DivisionResult;
import ua.igoodwill.polynomials.algo.monomial.lcm.MonomialLcm;
import ua.igoodwill.polynomials.algo.monomial.lcm.MonomialLcmImpl;
import ua.igoodwill.polynomials.algo.s_polynomial.SPolynomial;
import ua.igoodwill.polynomials.algo.s_polynomial.SPolynomialImpl;
import ua.igoodwill.polynomials.io.basic.BasicReader;
import ua.igoodwill.polynomials.io.basic.BasicWriter;
import ua.igoodwill.polynomials.io.basic.ConsoleReader;
import ua.igoodwill.polynomials.io.basic.ConsoleWriter;
import ua.igoodwill.polynomials.io.polynomials.PolynomialsReader;
import ua.igoodwill.polynomials.io.polynomials.PolynomialsReaderImpl;
import ua.igoodwill.polynomials.io.polynomials.order.OrderReader;
import ua.igoodwill.polynomials.io.polynomials.order.OrderReaderImpl;
import ua.igoodwill.polynomials.model.Polynomial;
import ua.igoodwill.polynomials.service.locale.FormatService;
import ua.igoodwill.polynomials.service.locale.MessageService;
import ua.igoodwill.polynomials.service.locale.NotationService;
import ua.igoodwill.polynomials.util.locale.MessageUtilImpl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Main {

    private BasicWriter basicWriter;
    private BasicReader basicReader;

    private PolynomialsReader polynomialsReader;
    private OrderReader orderReader;

    private BasicOperations basicOperations;
    private Division division;
    private MonomialLcm monomialLcm;
    private SPolynomial sPolynomial;

    public static void main(String[] args) throws IOException {
        Main instance = new Main();
        instance.init();

        instance.basicWriter.write("Order (\"x y z\" means x > y > z order): ");
        String[] order = instance.orderReader.readOrder();
        NotationService.setVariableLetters(order);

        instance.basicWriter.write("F: ");
        Polynomial f = instance.polynomialsReader.readPolynomial();
        instance.basicWriter.write("G: ");
        Polynomial g = instance.polynomialsReader.readPolynomial();

//        DivisionResult divisionResult = instance.division.divide(f, g);
//        instance.basicWriter.writeLine(divisionResult.toString());

        Polynomial result = instance.sPolynomial.sPolynomial(f, g);
        instance.basicWriter.writeLine(result.toString());
    }

    private void init() {
        MessageService.setUtil(MessageUtilImpl.getInstance());
        NotationService.setPowerSymbol("^");
        NotationService.setPositiveSign("+");
        NotationService.setNegativeSign("-");

        DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(4);
        FormatService.setDecimalFormat(decimalFormat);

        basicWriter = new ConsoleWriter();
        basicReader = new ConsoleReader();

        polynomialsReader = new PolynomialsReaderImpl(basicReader);
        orderReader = new OrderReaderImpl(basicReader);

        basicOperations = new BasicOperationsImpl();
        division = new DivisionImpl(basicOperations);
        monomialLcm = new MonomialLcmImpl();
        sPolynomial = new SPolynomialImpl(basicOperations, division, monomialLcm);
    }
}
