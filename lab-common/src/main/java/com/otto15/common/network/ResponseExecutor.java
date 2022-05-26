package com.otto15.common.network;

import com.otto15.common.controllers.CommandListener;

public record ResponseExecutor(Response response,
                               CommandListener commandListener) {

    public void execute() {
        response.showResult();
        if (response.getUser() != null) {
            commandListener.setUser(response.getUser());
        }
    }

}
