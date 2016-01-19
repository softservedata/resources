package org.registrator.community.enumLoader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface MappedEnum {
    @SuppressWarnings("rawtypes")
    Class<? extends Enum> enumClass();
}