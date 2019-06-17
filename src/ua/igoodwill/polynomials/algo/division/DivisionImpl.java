package ua.igoodwill.polynomials.algo.division;

import ua.igoodwill.polynomials.algo.basic.BasicOperations;
import ua.igoodwill.polynomials.model.Polynomial;
import ua.igoodwill.polynomials.service.locale.MessageService;

public class DivisionImpl implements Division {

    private final BasicOperations basicOperations;

    public DivisionImpl(BasicOperations basicOperations) {
        this.basicOperations = basicOperations;
    }

    @Override
    public DivisionResult divide(Polynomial dividend, Polynomial divisor) {
        if (divisor.isZero()) {
            throw new IllegalArgumentException(MessageService.getUtil().wrongParam("divisor"));
        }

        Polynomial quotient = Polynomial.zero();
        Polynomial remainder = new Polynomial(dividend);

        while (!remainder.isZero()) {
            int degreeDifference = remainder.getDegree() - divisor.getDegree();
            if (degreeDifference < 0) {
                break;
            }

            double[] monomial = new double[degreeDifference + 1];
            monomial[degreeDifference] = remainder.getLeadingCoefficient() / divisor.getLeadingCoefficient();

            Polynomial tmp = new Polynomial(monomial);
            quotient = basicOperations.add(quotient, tmp);
            remainder = basicOperations.subtract(remainder, basicOperations.multiply(divisor, tmp));
        }

        return new DivisionResult(quotient, remainder);
    }
}
