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
        Polynomial remainder = Polynomial.zero();

        while (!dividend.isZero()) {
            Monomial leadingDividendMonomial = dividend.getLeadingMonomial();
            int[] leadingDividendMonomialDegrees = leadingDividendMonomial.getDegrees();
            double leadingDividendCoefficient = dividend.getCoefficient(leadingDividendMonomialDegrees);

            boolean divisionOccurred = false;
            for (int i = 0; i < divisors.length; i++) {
                HasMonomials divisor = divisors[i];

                int[] leadingDivisorMonomial = divisor.getLeadingMonomial().getDegrees();
                double leadingDivisorCoefficient = divisor.getCoefficient(leadingDivisorMonomial);

                int[] degrees = IntStream
                        .range(0, NotationService.getNumberOfVariables())
                        .map(index -> leadingDividendMonomialDegrees[index] - leadingDivisorMonomial[index])
                        .toArray();

                if (Arrays.stream(degrees).noneMatch(degree -> degree < 0)) {
                    Monomial tmp =
                            new Monomial(leadingDividendCoefficient / leadingDivisorCoefficient, degrees);

                    quotients[i] = basicOperations.add(quotients[i], tmp);
                    dividend = basicOperations.subtract(dividend, basicOperations.multiply(divisor, tmp));

                    divisionOccurred = true;
                    break;
                }
            }

            if (!divisionOccurred) {
                remainder = basicOperations.add(remainder, leadingDividendMonomial);
                dividend = basicOperations.subtract(dividend, leadingDividendMonomial);
            }
        }

        return new DivisionResult(quotients, remainder);
    }
}
