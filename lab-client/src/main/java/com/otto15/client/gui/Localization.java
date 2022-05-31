package com.otto15.client.gui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localization {

    private ResourceBundle resourceBundle;
    private Locales locale;

    private Localization() {
        try {
            Locale currentLocale = Locale.getDefault();
            resourceBundle = ResourceBundle.getBundle("bundles.text", currentLocale);
            locale = defineLocales(currentLocale);
        } catch (MissingResourceException e) {
            resourceBundle = ResourceBundle.getBundle("bundles.text", Locales.EN_US_LOCALE.getLocale());
            locale = Locales.EN_US_LOCALE;
        }
    }

    private static class LocalizationHolder {
        public static final Localization HOLDER_INSTANCE = new Localization();
    }

    public static Localization getInstance() {
        return LocalizationHolder.HOLDER_INSTANCE;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }


    public void setResourceBundle(Locales locale) {
        this.locale = locale;
        resourceBundle = ResourceBundle.getBundle("bundles.text", locale.getLocale());
    }

    public Locales getLocales() {
        return locale;
    }

    public Locales defineLocales(Locale locale) {
        for (Locales locales : Locales.values()) {
            if (locales.getLocale().equals(locale)) {
                return locales;
            }
        }
        return null;
    }

}
