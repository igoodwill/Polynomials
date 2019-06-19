package ua.igoodwill.polynomials.algo;

import org.testng.annotations.BeforeMethod;
import ua.igoodwill.polynomials.algo.basic.BasicOperations;
import ua.igoodwill.polynomials.algo.basic.BasicOperationsImpl;
import ua.igoodwill.polynomials.model.Order;
import ua.igoodwill.polynomials.service.locale.FormatService;
import ua.igoodwill.polynomials.service.locale.NotationService;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class AlgorithmTest {

    protected BasicOperations basicOperations;

    @BeforeMethod
    public void setUp() {
        DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(4);
        FormatService.setDecimalFormat(decimalFormat);

        NotationService.setOrder(Order.GRLEX);
        NotationService.setVariableLetters("x", "y", "z");
        NotationService.setPowerSymbol("^");
        NotationService.setPositiveSign("+");
        NotationService.setNegativeSign("-");

        basicOperations = new BasicOperationsImpl();
    }
}
