package ua.igoodwill.polynomials.model;

import ua.igoodwill.polynomials.service.locale.FormatService;
import ua.igoodwill.polynomials.service.locale.MessageService;
import ua.igoodwill.polynomials.service.locale.NotationService;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static ua.igoodwill.polynomials.util.regex.RegexUtil.matchesFully;
import static ua.igoodwill.polynomials.util.regex.RegexUtil.wrapWithAnchors;

public class Monomial implements HasMonomials, Comparable<Monomial> {

    private static final String CONSTANT_PATTERN = "?\\d+(?:\\.\\d+)?";
    private static final int PRECISION = 10_000;

    private final double coefficient;
    private final int[] degrees;

    public Monomial(double coefficient, int[] degrees) {
        if (degrees == null || degrees.length != NotationService.getNumberOfVariables()) {
            throw new IllegalArgumentException(
                    MessageService.getUtil().wrongParam("degrees")
            );
        }

        this.coefficient = coefficient;
        this.degrees = degrees;
    }

    public static Monomial from(String monomial) {
        final String variablePattern = "([" +
                Pattern.quote(String.join("", NotationService.getVariableLetters())) + // Variable
                "])" +
                "(?:" + Pattern.quote(NotationService.getPowerSymbol()) + "(\\d+))?"; // Degree

        final String monomialPattern = wrapWithAnchors(
                "(\\d+(?:.\\d+)?)?" + // Coefficient
                        "((?:" + variablePattern + ")+)"
        );

        monomial = monomial.replaceAll("\\s", "");

        double coefficient = 1;
        if (monomial.startsWith(NotationService.getPositiveSign())) {
            monomial = monomial.substring(1);
        } else if (monomial.startsWith(NotationService.getNegativeSign())) {
            monomial = monomial.substring(1);
            coefficient = -1;
        }

        int[] degrees = new int[NotationService.getNumberOfVariables()];
        if (matchesFully(CONSTANT_PATTERN, monomial)) {
            coefficient *= Double.parseDouble(monomial);
        } else {
            Matcher monomialMatcher = Pattern
                    .compile(monomialPattern)
                    .matcher(monomial);

            if (!monomialMatcher.matches()) {
                throw new IllegalArgumentException(
                        MessageService.getUtil().wrongParam("polynomial")
                );
            }

            String coefficientString = monomialMatcher.group(1);
            String variableString = monomialMatcher.group(2);

            if (coefficientString != null) {
                coefficient *= Double.parseDouble(coefficientString);
            }

            Matcher variableMatcher = Pattern
                    .compile(variablePattern)
                    .matcher(variableString);
            while (variableMatcher.find()) {
                String variable = variableMatcher.group(1);
                String degreeString = variableMatcher.group(2);

                int index = NotationService.getVariableIndex(variable);
                if (degreeString != null) {
                    degrees[index] = Integer.parseInt(degreeString);
                } else {
                    degrees[index] = 1;
                }
            }
        }

        return new Monomial(coefficient, degrees);
    }

    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public double getCoefficient(int... degrees) {
        for (int i = 0; i < degrees.length; i++) {
            if (degrees[i] != this.degrees[i]) {
                return 0;
            }
        }

        return getCoefficient();
    }

    @Override
    public Monomial[] getMonomials() {
        return new Monomial[]{this};
    }

    @Override
    public Monomial getLeadingMonomial() {
        return this;
    }

    @Override
    public boolean isZero() {
        return Math.round(coefficient * PRECISION) == 0;
    }

    public int getTotalDegree() {
        return Arrays.stream(degrees).sum();
    }

    public boolean isConstant() {
        return getTotalDegree() == 0;
    }

    public int getDegree(int index) {
        return degrees[index];
    }

    public int[] getDegrees() {
        return degrees;
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        }

        if (isConstant()) {
            return FormatService.format(coefficient);
        }

        StringBuilder result = new StringBuilder();

        if (Math.abs(coefficient) != 1) {
            result.append(FormatService.format(coefficient));
        } else {
            if (coefficient == -1) {
                result.append(NotationService.getNegativeSign());
            }
        }

        IntStream
                .range(0, degrees.length)
                .filter(index -> degrees[index] != 0)
                .forEach(index -> {
                    result.append(NotationService.getVariableLetter(index));

                    int degree = degrees[index];
                    if (degree != 1) {
                        result.append(NotationService.getPowerSymbol()).append(degree);
                    }
                });

        return result.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Monomial monomial = (Monomial) other;
        return Arrays.equals(degrees, monomial.degrees);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(degrees);
    }

    @Override
    public int compareTo(Monomial other) {
        Order order = NotationService.getOrder();
        int cmp;
        if (order.isGraded()) {
            cmp = Integer.compare(getTotalDegree(), other.getTotalDegree());
            if (cmp != 0) {
                return cmp;
            }
        }

        return Arrays.compare(degrees, other.degrees);
    }

    public static Monomial zero() {
        return constant(0);
    }

    public static Monomial constant(double coefficient) {
        return new Monomial(coefficient, new int[NotationService.getNumberOfVariables()]);
    }
}
