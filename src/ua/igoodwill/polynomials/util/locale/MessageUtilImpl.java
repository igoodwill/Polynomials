package ua.igoodwill.polynomials.util.locale;

import static java.lang.String.format;
import static ua.igoodwill.polynomials.util.locale.EnMessages.WRONG_PARAMETER;

public class MessageUtilImpl implements MessageUtil {

    private static MessageUtilImpl instance = new MessageUtilImpl();

    public static MessageUtilImpl getInstance() {
        return instance;
    }

    private MessageUtilImpl() {
    }

    @Override
    public String wrongParam(String param) {
        return format(WRONG_PARAMETER, param);
    }
}
