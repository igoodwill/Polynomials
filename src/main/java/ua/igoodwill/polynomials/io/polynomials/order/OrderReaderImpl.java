package ua.igoodwill.polynomials.io.polynomials.order;

import ua.igoodwill.polynomials.io.basic.BasicReader;
import ua.igoodwill.polynomials.model.Order;

import java.io.IOException;

public class OrderReaderImpl implements OrderReader {

    private final BasicReader basicReader;

    public OrderReaderImpl(BasicReader basicReader) {
        this.basicReader = basicReader;
    }

    @Override
    public String[] readVariables() throws IOException {
        String line = basicReader.readLine();
        return line.trim().split(" ");
    }

    @Override
    public Order readOrder() throws IOException {
        String line = basicReader.readLine();
        return Order.valueOf(line.toUpperCase());
    }
}
