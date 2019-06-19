package ua.igoodwill.polynomials.io.polynomials.order;

import ua.igoodwill.polynomials.model.Order;

import java.io.IOException;

public interface OrderReader {

    String[] readVariables() throws IOException;

    Order readOrder() throws IOException;
}
