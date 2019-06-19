package ua.igoodwill.polynomials.model;

import ua.igoodwill.polynomials.service.locale.MessageService;
import ua.igoodwill.polynomials.service.locale.NotationService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial implements HasMonomials {

    private static final String TERM_PATTERN = "([+-]?[^-+]+)";

    private final MonomialsContainer monomials;

    public Polynomial(MonomialsContainer monomials) {
        if (monomials == null) {
            throw new IllegalArgumentException(
                    MessageService.getUtil().wrongParam("monomials")
            );
        }

        this.monomials = new MonomialsContainer(monomials);
    }

    public Polynomial(Monomial[] monomials) {
        if (monomials == null) {
            throw new IllegalArgumentException(
                    MessageService.getUtil().wrongParam("monomials")
            );
        }

        this.monomials = new MonomialsContainer(monomials);
    }

    public Polynomial(HasMonomials source) {
        monomials = new MonomialsContainer();
        Arrays
                .stream(source.getMonomials())
                .forEach(this.monomials::add);
    }

    public static Polynomial from(String polynomial) {
        Matcher termMatcher = Pattern
                .compile(TERM_PATTERN)
                .matcher(polynomial);

        MonomialsContainer monomials = new MonomialsContainer();
        while (termMatcher.find()) {
            String monomialString = termMatcher.group(1);

            Monomial monomial = Monomial.from(monomialString);
            monomials.add(monomial);
        }

        return new Polynomial(monomials);
    }

    public static Polynomial zero() {
        MonomialsContainer monomials = new MonomialsContainer(Monomial.zero());

        return new Polynomial(monomials);
    }

    @Override
    public double getCoefficient(int... degrees) {
        return monomials.get(degrees).getCoefficient();
    }

    @Override
    public Monomial[] getMonomials() {
        return monomials.toArray();
    }

    @Override
    public Monomial getLeadingMonomial() {
        return monomials.leading();
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
