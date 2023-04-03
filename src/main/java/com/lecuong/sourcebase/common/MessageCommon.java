package com.lecuong.sourcebase.common;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author CuongLM18
 * @created 03/04/2023 - 1:56 PM
 * @project source-base
 */
@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MessageCommon {

    /**
     * Source message using get message from message properties file.
     */
    private MessageSource messageSource;


    /**
     * Constructor create instance LoggerService.
     *
     * @param messageSource Source message
     */
    public MessageCommon(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * get information value by message code.
     *
     * @param messageCode message use to get message corresponding
     * @return message information corresponding to the message code
     */
    public String getValueByMessageCode(String messageCode) {
        return messageSource.getMessage(messageCode, null, new Locale("vi"));
    }

    /**
     * get information value by message code.
     *
     * @param messageCode message use to get message corresponding
     * @return message information corresponding to the message code
     */
    public String getValueByMessageCode(String messageCode, Locale locale) {
        return messageSource.getMessage(messageCode, null, locale);
    }

    /**
     * get information value by message code and format with params.
     *
     * @param messageCode message use to get message corresponding
     * @param params      parameter with format
     * @return message information corresponding to the message code
     */
    public String getMessage(String messageCode, Object... params) {
        try {
            if (params.length == 0) return getValueByMessageCode(messageCode);
            return String.format(getValueByMessageCode(messageCode), params);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
