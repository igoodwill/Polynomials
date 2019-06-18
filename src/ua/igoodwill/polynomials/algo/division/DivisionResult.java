package ua.igoodwill.polynomials.algo.division;

import ua.igoodwill.polynomials.model.Polynomial;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DivisionResult {

    private final Polynomial[] quotients;
    private final Polynomial remainder;

    public DivisionResult(Polynomial[] quotients, Polynomial remainder) {
        this.quotients = quotients;
        this.remainder = remainder;
    }

    public Polynomial[] getQuotients() {
        return quotients;
    }

    public Polynomial getRemainder() {
        return remainder;
    }

    @Override
    public String toString() {
        return "Quotients:\n" +
                Arrays
                        .stream(quotients)
                        .map(Polynomial::toString)
                        .collect(Collectors.joining("\n"))
                + "\nR: " + remainder;
    }
}
