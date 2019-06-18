package ua.igoodwill.polynomials.algo.division;

import ua.igoodwill.polynomials.algo.basic.BasicOperations;
import ua.igoodwill.polynomials.model.HasMonomials;
import ua.igoodwill.polynomials.model.Monomial;
import ua.igoodwill.polynomials.model.Polynomial;
import ua.igoodwill.polynomials.service.locale.MessageService;
import ua.igoodwill.polynomials.service.locale.NotationService;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DivisionImpl implements Division {

    private final BasicOperations basicOperations;

    public DivisionImpl(BasicOperations basicOperations) {
        this.basicOperations = basicOperations;
    }

    @Override
    public DivisionResult divide(HasMonomials dividend, HasMonomials divisor) {
        if (divisor.isZero()) {
            throw new IllegalArgumentException(MessageService.getUtil().wrongParam("divisor"));
        }

        Polynomial quotient = Polynomial.zero();
        Polynomial remainder = new Polynomial(dividend);

        int[] leadingDivisorMonomial = divisor.getLeadingMonomial().getDegrees();
        double leadingDivisorCoefficient = divisor.getCoefficient(leadingDivisorMonomial);
        while (!remainder.isZero()) {
            int[] leadingRemainderMonomial = remainder.getLeadingMonomial().getDegrees();
            double leadingRemainderCoefficient = remainder.getCoefficient(leadingRemainderMonomial);

            int[] degrees = IntStream
                    .range(0, NotationService.getNumberOfVariables())
                    .map(index -> leadingRemainderMonomial[index] - leadingDivisorMonomial[index])
                    .toArray();

            if (Arrays.stream(degrees)
                    .filter(degree -> degree < 0)
                    .count() > 0) {
                break;
            }

            Monomial tmp = new Monomial(leadingRemainderCoefficient / leadingDivisorCoefficient, degrees);

            quotient = basicOperations.add(quotient, tmp);
            remainder = basicOperations.subtract(remainder, basicOperations.multiply(divisor, tmp));
        }

        return new DivisionResult(quotient, remainder);
    }
}
