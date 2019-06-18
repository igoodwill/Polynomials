package ua.igoodwill.polynomials.model;

import ua.igoodwill.polynomials.service.locale.MessageService;
import ua.igoodwill.polynomials.service.locale.NotationService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial implements HasMonomials {

    private static final String TERM_PATTERN = "([+-]?[^-+]+)";

    private final TreeMap<Monomial, Monomial> monomials;

    public Polynomial(TreeMap<Monomial, Monomial> monomials) {
        if (monomials == null) {
            throw new IllegalArgumentException(
                    MessageService.getUtil().wrongParam("monomials")
            );
        }

        this.monomials = new TreeMap<>();
        monomials
                .values()
                .stream()
                .filter(monomial -> !monomial.isZero())
                .forEach(monomial -> this.monomials.put(monomial, monomial));

        if (this.monomials.size() == 0) {
            Monomial zero = Monomial.zero();
            this.monomials.put(zero, zero);
        }
    }

    public Polynomial(HasMonomials source) {
        monomials = new TreeMap<>();
        Arrays
                .stream(source.getMonomials())
                .forEach(monomial -> this.monomials.put(monomial, monomial));
    }

    public static Polynomial from(String polynomial) {
        Matcher termMatcher = Pattern
                .compile(TERM_PATTERN)
                .matcher(polynomial);

        TreeMap<Monomial, Monomial> monomials = new TreeMap<>();
        while (termMatcher.find()) {
            String monomialString = termMatcher.group(1);

            Monomial monomial = Monomial.from(monomialString);
            Monomial currentMonomial = monomials.get(monomial);
            if (currentMonomial != null) {
                int[] degrees = monomial.getDegrees();
                double coefficient = monomial.getCoefficient();
                double currentCoefficient = currentMonomial.getCoefficient();
                monomial = new Monomial(coefficient + currentCoefficient, degrees);
            }

            monomials.put(monomial, monomial);
        }

        return new Polynomial(monomials);
    }

    public static Polynomial zero() {
        TreeMap<Monomial, Monomial> monomials = new TreeMap<>();

        Monomial zero = Monomial.zero();
        monomials.put(zero, zero);

        return new Polynomial(monomials);
    }

    @Override
    public double getCoefficient(int... degrees) {
        return monomials.get(new Monomial(0, degrees)).getCoefficient();
    }

    @Override
    public Monomial[] getMonomials() {
        return monomials.values().toArray(new Monomial[0]);
    }

    @Override
    public Monomial getLeadingMonomial() {
        return monomials.lastEntry().getValue();
    }

    @Override
    public boolean isZero() {
        return monomials.size() == 1 && getMonomials()[0].isZero();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Polynomial that = (Polynomial) other;
        return monomials.equals(that.monomials);
    }

    @Override
    public int hashCode() {
        return monomials.hashCode();
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        monomials
                .values()
                .stream()
                .filter(monomial -> !monomial.isZero())
                .sorted(Comparator.reverseOrder())
                .map(Monomial::toString)
                .forEach(monomial -> {
                    if (result.length() != 0) {
                        result.append(" ");
                        if (monomial.startsWith(NotationService.getNegativeSign())) {
                            result.append(NotationService.getNegativeSign());
                            monomial = monomial.substring(1);
                        } else {
                            result.append(NotationService.getPositiveSign());
                        }
                        result.append(" ");
                    }

                    result.append(monomial);
                });

        return result.toString();
    }
}
