package ua.igoodwill.polynomials.service.locale;

import ua.igoodwill.polynomials.model.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class NotationService {

    private static NotationService instance = new NotationService();

    private Order order;
    private String[] variableLetters; // [x, y] means x > y order
    private Map<String, Integer> variableLetterToIndex;
    private String powerSymbol;
    private String positiveSign;
    private String negativeSign;

    private static NotationService getInstance() {
        return instance;
    }

    private NotationService() {
    }

    public static Order getOrder() {
        return getInstance().order;
    }

    public static void setOrder(Order order) {
        getInstance().order = order;
    }

    public static String[] getVariableLetters() {
        return getInstance().variableLetters;
    }

    public static String getVariableLetter(int index) {
        return getInstance().variableLetters[index];
    }

    public static int getVariableIndex(String letter) {
        return getInstance().variableLetterToIndex.get(letter);
    }

    public static int getNumberOfVariables() {
        return getInstance().variableLetters.length;
    }

    public static void setVariableLetters(String... variableLetters) {
        NotationService instance = getInstance();

        instance.variableLetters = variableLetters;
        instance.variableLetterToIndex = new HashMap<>();

        IntStream
                .range(0, variableLetters.length)
                .forEach(index -> instance.variableLetterToIndex.put(variableLetters[index], index));
    }

    public static String getPowerSymbol() {
        return getInstance().powerSymbol;
    }

    public static void setPowerSymbol(String powerSymbol) {
        getInstance().powerSymbol = powerSymbol;
    }

    public static String getPositiveSign() {
        return getInstance().positiveSign;
    }

    public static void setPositiveSign(String positiveSign) {
        getInstance().positiveSign = positiveSign;
    }

    public static String getNegativeSign() {
        return getInstance().negativeSign;
    }

    public static void setNegativeSign(String negativeSign) {
        getInstance().negativeSign = negativeSign;
    }
}
