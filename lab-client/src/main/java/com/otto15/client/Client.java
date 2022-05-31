package com.otto15.client;

import com.otto15.client.gui.App;


public final class Client {

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        App.main(args);
    }

}
