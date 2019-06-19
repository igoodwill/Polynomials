package ua.igoodwill.polynomials.service.locale;

import java.text.DecimalFormat;

public class FormatService {

    private static FormatService instance = new FormatService();

    private DecimalFormat decimalFormat;

    private static FormatService getInstance() {
        return instance;
    }

    private FormatService() {
    }

    public static String format(double number) {
        return getInstance().decimalFormat.format(number);
    }

    public static void setDecimalFormat(DecimalFormat decimalFormat) {
        getInstance().decimalFormat = decimalFormat;
    }
}
