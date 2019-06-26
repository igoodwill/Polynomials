package ua.igoodwill.polynomials.model;

public enum Order {

    LEX(false, false),
    INVLEX(true, false),
    GRLEX(false, true),
    GREVLEX(true, true);

    private final boolean inverted;
    private final boolean graded;

    Order(boolean inverted, boolean graded) {
        this.inverted = inverted;
        this.graded = graded;
    }

    public boolean isInverted() {
        return inverted;
    }

    public boolean isGraded() {
        return graded;
    }
}
