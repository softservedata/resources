package org.registrator.community.exceptions;

import org.registrator.community.components.SpringApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Base exception class for specific to application exceptions
 */
public abstract class AbstractRegistratorException extends Exception  {

    public AbstractRegistratorException() {
        super();
    }
    
    public AbstractRegistratorException(String message) {
        super(message);
    }

    public AbstractRegistratorException(Throwable cause) {
        super(cause);
    }

    public AbstractRegistratorException(String message, Throwable cause) {
        super(message, cause);
    }

    protected String getLocalizedMessageKey() {
        return "";
    }

    protected Object[] getMessageParameters() {
        return new Object[0];
    }

    @Override
    public String getLocalizedMessage() {

        String messageKey = getLocalizedMessageKey();
        if ((messageKey == null) || (messageKey.isEmpty())) {
            return super.getLocalizedMessage();
        }

        MessageSource messageSource = SpringApplicationContext.getMessageSource();
        Locale locale = LocaleContextHolder.getLocale();
        if (messageSource != null) {
            return messageSource.getMessage(messageKey, getMessageParameters(), locale);
        } else {
            return super.getLocalizedMessage();
        }
    }
}
