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
    public DivisionResult divide(HasMonomials dividend, HasMonomials... divisors) {
        if (Arrays.stream(divisors).anyMatch(HasMonomials::isZero)) {
            throw new IllegalArgumentException(MessageService.getUtil().wrongParam("divisors"));
        }

        Polynomial zero = Polynomial.zero();
        Polynomial[] quotients = IntStream
                .range(0, divisors.length)
                .mapToObj(index -> zero)
                .toArray(Polynomial[]::new);
        Polynomial remainder = new Polynomial(dividend);

        boolean stop = false;
        while (!remainder.isZero() && !stop) {
            int[] leadingRemainderMonomial = remainder.getLeadingMonomial().getDegrees();
            double leadingRemainderCoefficient = remainder.getCoefficient(leadingRemainderMonomial);

            stop = true;
            for (int i = 0; i < divisors.length; i++) {
                HasMonomials divisor = divisors[i];

                int[] leadingDivisorMonomial = divisor.getLeadingMonomial().getDegrees();
                double leadingDivisorCoefficient = divisor.getCoefficient(leadingDivisorMonomial);

                int[] degrees = IntStream
                        .range(0, NotationService.getNumberOfVariables())
                        .map(index -> leadingRemainderMonomial[index] - leadingDivisorMonomial[index])
                        .toArray();

                if (Arrays.stream(degrees).noneMatch(degree -> degree < 0)) {
                    Monomial tmp =
                            new Monomial(leadingRemainderCoefficient / leadingDivisorCoefficient, degrees);

                    quotients[i] = basicOperations.add(quotients[i], tmp);
                    remainder = basicOperations.subtract(remainder, basicOperations.multiply(divisor, tmp));

                    stop = false;
                    break;
                }
            }
        }

        return new DivisionResult(quotients, remainder);
    }
}
