package ua.igoodwill.polynomials.algo.monomial.lcm;

import ua.igoodwill.polynomials.model.Monomial;
import ua.igoodwill.polynomials.service.locale.NotationService;

import java.util.stream.IntStream;

public class MonomialLcmImpl implements MonomialLcm {

    @Override
    public Monomial lcm(Monomial m1, Monomial m2) {
        return new Monomial(
                1,
                IntStream
                        .range(0, NotationService.getNumberOfVariables())
                        .map(index -> Math.max(m1.getDegree(index), m2.getDegree(index)))
                        .toArray()
        );
    }
}
