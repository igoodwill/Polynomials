package ua.igoodwill.polynomials.algo.division;

import ua.igoodwill.polynomials.model.Polynomial;

public class DivisionResult {

    private final Polynomial quotient;
    private final Polynomial remainder;

    public DivisionResult(Polynomial quotient, Polynomial remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }

    public Polynomial getQuotient() {
        return quotient;
    }

    public Polynomial getRemainder() {
        return remainder;
    }

    @Override
    public String toString() {
        return "Q: " + quotient + "\nR: " + remainder;
    }
}
