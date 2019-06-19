package ua.igoodwill.polynomials.io.basic;

public class ConsoleWriter implements BasicWriter {

    @Override
    public void write(String line) {
        System.out.print(line);
    }

    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }
}
