package ua.igoodwill.polynomials.algo.basic;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.igoodwill.polynomials.algo.AlgorithmTest;

import static org.testng.Assert.assertEquals;
import static ua.igoodwill.polynomials.model.Polynomial.from;

public class BasicOperationsTest extends AlgorithmTest {

    @DataProvider
    public Object[][] dataForSum() {
        return new Object[][]{
                {"-x^2+4.2yz-1.1z^2+2.7", "9.34xy^3+3x^2-10.12yz", "9.34xy^3 + 2x^2 - 5.92yz - 1.1z^2 + 2.7"},
                {"-x^2", "x^2", "0"},
                {"-x^2+y", "2x^2-y", "x^2"},
                {"x^4-xyz", "y^4+xyz", "x^4 + y^4"}
        };
    }

    @DataProvider
    public Object[][] dataForSubtraction() {
        return new Object[][]{
                {"-x^2+4.2yz-1.1z^2+2.7", "9.34xy^3+3x^2-10.12yz", "-9.34xy^3 - 4x^2 + 14.32yz - 1.1z^2 + 2.7"},
                {"x^2", "x^2", "0"},
                {"-x^2-y", "2x^2-y", "-3x^2"},
                {"x^4-xyz", "y^4-xyz", "x^4 - y^4"}
        };
    }

    @DataProvider
    public Object[][] dataForMultiplication() {
        return new Object[][]{
                {
                        "-x^2+4.2yz-1.1z^2+2.7", "9.34xy^3+3x^2-10.12yz",
                        "-9.34x^3y^3 + 39.228xy^4z - 10.274xy^3z^2 " +
                                "- 3x^4 + 22.72x^2yz - 3.3x^2z^2 + 25.218xy^3" +
                                " - 42.504y^2z^2 + 11.132yz^3 + 8.1x^2 - 27.324yz"
                },
                {"x^2+y^2", "xy+yz", "x^3y + x^2yz + xy^3 + y^3z"},
                {"x+y+z", "x+y-z", "x^2 + 2xy + y^2 - z^2"},
                {"-x^2+4.2yz-1.1z^2+2.7", "x-x", "0"}
        };
    }

    @Test(dataProvider = "dataForSum")
    public void testAdd(String p1, String p2, String result) {
        assertEquals(basicOperations.add(from(p1), from(p2)).toString(), result);
    }

    @Test(dataProvider = "dataForSubtraction")
    public void testSubtract(String p1, String p2, String result) {
        assertEquals(basicOperations.subtract(from(p1), from(p2)).toString(), result);
    }

    @Test(dataProvider = "dataForMultiplication")
    public void testMultiply(String p1, String p2, String result) {
        assertEquals(basicOperations.multiply(from(p1), from(p2)).toString(), result);
    }
}