package com.otto15.client.gui;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localization {

    private final Locale currentLocale = Locale.getDefault();
    private final Locale enUsLocale = new Locale("en", "US");
    private final Locale ruRuLocale = new Locale("ru", "RU");
    private final Locale esHnLocale = new Locale("es", "HN");
    private final Locale trTrLocale = new Locale("tr", "TR");
    private final Locale ukUALocale = new Locale("uk", "UA");
    private ResourceBundle resourceBundle;

    public Localization() {
        try {
            resourceBundle = ResourceBundle.getBundle("bundles.text", ukUALocale);
        } catch (MissingResourceException e) {
            resourceBundle = ResourceBundle.getBundle("bundles.text", enUsLocale);
        }
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
