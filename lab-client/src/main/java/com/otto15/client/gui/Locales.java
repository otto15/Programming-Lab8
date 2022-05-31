package com.otto15.client.gui;

import java.util.Locale;

public enum Locales {
    EN_US_LOCALE(new Locale("en", "US"), "English"),
    RU_RU_LOCALE(new Locale("ru", "RU"), "Русский"),
    ES_HN_LOCALE(new Locale("es", "HN"), "Español"),
    TR_TR_LOCALE(new Locale("tr", "TR"), "Türk"),
    UK_UA_LOCALE(new Locale("uk", "UA"), "Український");

    private final Locale locale;
    private final String representation;

    Locales(Locale locale, String representation) {
        this.locale = locale;
        this.representation = representation;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getRepresentation() {
        return representation;
    }
}
