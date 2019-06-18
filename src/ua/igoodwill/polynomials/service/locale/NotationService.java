package ua.igoodwill.polynomials.service.locale;

public class NotationService {

    private static NotationService instance = new NotationService();

    private String variableLetter;
    private String powerSymbol;
    private String positiveSign;
    private String negativeSign;

    private static NotationService getInstance() {
        return instance;
    }

    private NotationService() {
    }

    public static String getVariableLetter() {
        return getInstance().variableLetter;
    }

    public static void setVariableLetter(String variableLetter) {
        getInstance().variableLetter = variableLetter;
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
