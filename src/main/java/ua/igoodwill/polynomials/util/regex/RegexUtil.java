package ua.igoodwill.polynomials.util.regex;

public class RegexUtil {

    public static String wrapWithAnchors(String regex) {
        return "^" + regex + "$";
    }

    public static boolean matchesFully(String regex, String line) {
        return line.matches(wrapWithAnchors(regex));
    }
}
