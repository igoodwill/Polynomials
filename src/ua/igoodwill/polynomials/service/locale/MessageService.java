package ua.igoodwill.polynomials.service.locale;

import ua.igoodwill.polynomials.util.locale.MessageUtil;

public class MessageService {

    private static MessageService instance = new MessageService();

    private MessageUtil util;

    private static MessageService getInstance() {
        return instance;
    }

    private MessageService() {
    }

    public static void setUtil(MessageUtil util) {
        getInstance().util = util;
    }

    public static MessageUtil getUtil() {
        return getInstance().util;
    }
}
