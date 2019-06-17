package ua.igoodwill.polynomials.algo.division;

import ua.igoodwill.polynomials.algo.basic.BasicOperations;
import ua.igoodwill.polynomials.model.Polynomial;
import ua.igoodwill.polynomials.util.locale.MessageUtil;

public class DivisionImpl implements Division {

    private final MessageUtil messageUtil;
    private final BasicOperations basicOperations;

    public DivisionImpl(MessageUtil messageUtil, BasicOperations basicOperations) {
        this.messageUtil = messageUtil;
        this.basicOperations = basicOperations;
    }

    @Override
    public DivisionResult divide(Polynomial dividend, Polynomial divisor) {
        if (divisor.isZero()) {
            throw new IllegalArgumentException(messageUtil.wrongParam("divisor"));
        }

        Polynomial quotient = Polynomial.zero(messageUtil);
        Polynomial remainder = new Polynomial(messageUtil, dividend);

        while (!remainder.isZero()) {
            int degreeDifference = remainder.getDegree() - divisor.getDegree();
            if (degreeDifference < 0) {
                break;
            }

            double[] monomial = new double[degreeDifference + 1];
            monomial[degreeDifference] = remainder.getLeadingCoefficient() / divisor.getLeadingCoefficient();

            Polynomial tmp = new Polynomial(messageUtil, monomial);
            quotient = basicOperations.add(quotient, tmp);
            remainder = basicOperations.subtract(remainder, basicOperations.multiply(divisor, tmp));
        }

        return new DivisionResult(quotient, remainder);
    }
}
