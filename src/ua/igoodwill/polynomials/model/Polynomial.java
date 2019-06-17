package ua.igoodwill.polynomials.model;

import ua.igoodwill.polynomials.util.locale.MessageUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static ua.igoodwill.polynomials.util.regex.RegexUtil.matchesFully;
import static ua.igoodwill.polynomials.util.regex.RegexUtil.wrapWithAnchors;

public class Polynomial {

    private static final String VARIABLE_LETTER = "x";
    private static final String POWER_SYMBOL = "^";
    private static final String POSITIVE_SIGN = "+";
    private static final String POSITIVE_SEPARATOR = " + ";
    private static final String NEGATIVE_SIGN = "-";
    private static final String NEGATIVE_SEPARATOR = " - ";
    private static final String TERM_PATTERN = "([+-]?[^-+]+)";
    private static final String CONSTANT_PATTERN = "?\\d+(?:\\.\\d+)?";
    private static final String MONOMIAL_PATTERN =
            "(\\d+(?:.\\d+)?)?" + // Coefficient
                    VARIABLE_LETTER + // Variable
                    "(?:" + Pattern.quote(POWER_SYMBOL) + "(\\d+))?"; // Degree

    private static final DecimalFormat df;

    static {
        df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(4);
    }

    private final MessageUtil messageUtil;

    private final double[] coefficients;

    public Polynomial(MessageUtil messageUtil, double[] coefficients) {
        this.messageUtil = messageUtil;
        if (coefficients == null ||
                coefficients.length == 0 ||
                (coefficients.length > 1 && coefficients[coefficients.length - 1] == 0)) {
            throw new IllegalArgumentException(messageUtil.wrongParam("coefficients"));
        }

        this.coefficients = coefficients;
    }

    public Polynomial(MessageUtil messageUtil, Polynomial source) {
        this.messageUtil = messageUtil;
        coefficients = Arrays.copyOf(source.coefficients, source.coefficients.length);
    }

    public static Polynomial from(MessageUtil messageUtil, String polynomial) {
        Matcher termMatcher = Pattern
                .compile(TERM_PATTERN)
                .matcher(polynomial);

        ArrayList<Double> coefficients = new ArrayList<>();
        while (termMatcher.find()) {
            String monomial = termMatcher.group(1);
            monomial = monomial.replaceAll("\\s", "");

            double coefficient = 1;
            if (monomial.startsWith(POSITIVE_SIGN)) {
                monomial = monomial.substring(1);
            } else if (monomial.startsWith(NEGATIVE_SIGN)) {
                monomial = monomial.substring(1);
                coefficient = -1;
            }

            int degree;
            if (matchesFully(CONSTANT_PATTERN, monomial)) {
                coefficient *= Double.parseDouble(monomial);
                degree = 0;
            } else {
                Matcher monomialMatcher = Pattern
                        .compile(wrapWithAnchors(MONOMIAL_PATTERN))
                        .matcher(monomial);

                if (!monomialMatcher.matches()) {
                    throw new IllegalArgumentException(messageUtil.wrongParam("polynomial"));
                }

                String coefficientString = monomialMatcher.group(1);
                String degreeString = monomialMatcher.group(2);

                if (coefficientString != null) {
                    coefficient *= Double.parseDouble(coefficientString);
                }

                if (degreeString != null) {
                    degree = Integer.parseInt(degreeString);
                } else {
                    degree = 1;
                }
            }

            coefficients.ensureCapacity(degree + 1);
            for (int i = coefficients.size(); i <= degree; i++) {
                coefficients.add(0.0);
            }

            Double currentCoefficient = coefficients.get(degree);
            coefficients.set(degree, coefficient + currentCoefficient);
        }

        return new Polynomial(
                messageUtil,
                coefficients
                        .stream()
                        .mapToDouble(Double::doubleValue)
                        .toArray()
        );
    }

    public static Polynomial zero(MessageUtil messageUtil) {
        return new Polynomial(messageUtil, new double[]{0});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Polynomial that = (Polynomial) o;
        return Arrays.equals(coefficients, that.coefficients);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coefficients);
    }

    @Override
    public String toString() {
        if (coefficients.length == 1) {
            return getConstantTerm();
        }

        StringBuilder result = new StringBuilder();
        IntStream
                .range(0, coefficients.length)
                .filter(i -> getCoefficient(i) != 0)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .map(this::getMonomial)
                .forEach(monomial -> {
                    if (result.length() != 0) {
                        if (monomial.startsWith(NEGATIVE_SIGN)) {
                            result.append(NEGATIVE_SEPARATOR);
                            monomial = monomial.substring(1);
                        } else {
                            result.append(POSITIVE_SEPARATOR);
                        }
                    }

                    result.append(monomial);
                });

        return result.toString();
    }

    private String getMonomial(int index) {
        if (index == 0) {
            return getConstantTerm();
        }

        double coefficient = coefficients[index];
        StringBuilder result = new StringBuilder();

        if (Math.abs(coefficient) != 1) {
            result.append(df.format(coefficient));
        } else {
            if (coefficient == -1) {
                result.append(NEGATIVE_SIGN);
            }
        }

        result.append(VARIABLE_LETTER);

        if (index != 1) {
            result.append(POWER_SYMBOL).append(index);
        }

        return result.toString();
    }

    private String getConstantTerm() {
        return df.format(getCoefficient(0));
    }

    public double getLeadingCoefficient() {
        return getCoefficient(getDegree());
    }

    public boolean isZero() {
        return getDegree() == 0 && getCoefficient(0) == 0;
    }

    public double getCoefficient(int index) {
        return coefficients.length > index ? coefficients[index] : 0;
    }

    public int getDegree() {
        return coefficients.length - 1;
    }
}
