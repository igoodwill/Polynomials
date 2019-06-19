package ua.igoodwill.polynomials.model;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.Stream;

public class MonomialsContainer {

    private final TreeMap<Monomial, Monomial> monomials;

    public MonomialsContainer() {
        monomials = new TreeMap<>();
    }

    public MonomialsContainer(Monomial... monomials) {
        this();
        Arrays
                .stream(monomials)
                .filter(monomial -> !monomial.isZero())
                .forEach(this::add);

        if (this.monomials.size() == 0) {
            add(Monomial.zero());
        }
    }

    public MonomialsContainer(MonomialsContainer monomials) {
        this(monomials.toArray());
    }

    public void add(Monomial monomial) {
        Monomial currentMonomial = monomials.get(monomial);
        if (currentMonomial != null) {
            int[] degrees = monomial.getDegrees();
            double coefficient = monomial.getCoefficient();
            double currentCoefficient = currentMonomial.getCoefficient();
            monomial = new Monomial(coefficient + currentCoefficient, degrees);
        }

        monomials.put(monomial, monomial);
    }

    public Monomial get(int... degrees) {
        return get(new Monomial(0, degrees));
    }

    public Monomial get(Monomial monomial) {
        return monomials.get(monomial);
    }

    public Monomial leading() {
        return monomials.lastEntry().getValue();
    }

    public Monomial[] toArray() {
        return monomials.values().toArray(new Monomial[0]);
    }

    public Stream<Monomial> stream() {
        return monomials.values().stream();
    }

    public int size() {
        return monomials.size();
    }
}
