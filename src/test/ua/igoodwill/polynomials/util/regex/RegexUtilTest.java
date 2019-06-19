package ua.igoodwill.polynomials.util.regex;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;
import static ua.igoodwill.polynomials.util.regex.RegexUtil.matchesFully;

public class RegexUtilTest {

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
                {"abc", "abc", true},
                {"[abc]+", "abc", true},
                {"\\d+(\\.\\d+)?", "1.42", true},
                {"\\d+(\\.\\d+)?", "1", true},

                {"abc", "abcd", false},
                {"[abc]+", "abcd", false},
                {"\\d+(\\.\\d+)?", "-1.5", false},
                {"\\d+(\\.\\d+)?", "-1", false}
        };
    }

    @Test(dataProvider = "data")
    public void testMatchesFully(String regex, String line, boolean expectedResult) {
        assertSame(matchesFully(regex, line), expectedResult);
    }
}