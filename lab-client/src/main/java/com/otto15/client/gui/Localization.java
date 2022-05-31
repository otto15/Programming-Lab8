package com.otto15.client.gui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localization {

    private ResourceBundle resourceBundle;

    public Localization() {
        try {
            Locale currentLocale = Locale.getDefault();
            resourceBundle = ResourceBundle.getBundle("bundles.text", currentLocale);
        } catch (MissingResourceException e) {
            resourceBundle = ResourceBundle.getBundle("bundles.text", Locales.EN_US_LOCALE.getLocale());
        }
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("bundles.text", locale);
    }

}
