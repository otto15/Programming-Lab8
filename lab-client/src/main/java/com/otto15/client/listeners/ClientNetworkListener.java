package com.otto15.client.listeners;

import com.otto15.client.Client;
import com.otto15.client.ClientDispatcher;
import com.otto15.client.ConnectionHandler;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.network.Serializer;
import com.otto15.common.state.PerformanceState;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public final class ClientNetworkListener implements NetworkListener {

    private static final int TIMEOUT = 10000;
    private final ConnectionHandler connectionHandler;
    private final Reader reader = new InputStreamReader(System.in);

    public ClientNetworkListener(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }


    public static class NetworkListenerHolder {
        public static final NetworkListener HOLDER_INSTANCE = new ClientNetworkListener(
                new ConnectionHandler(
                        new PerformanceState()
                ));
    }


    public static NetworkListener getInstance() {
        return NetworkListenerHolder.HOLDER_INSTANCE;
    }


    @Override
    public Response listen(Request request) throws IOException {
        if (!connectionHandler.isOpen()) {
            connectionHandler.openConnection(System.getenv("DB_HOST"),
                    Integer.parseInt(System.getenv("SERVER_PORT")));
        }

        try {
            Serializer serializer = new Serializer();
            ClientDispatcher clientDispatcher = new ClientDispatcher(serializer);
            clientDispatcher.send(request, connectionHandler.getOutputStream());
            connectionHandler.getSocket().setSoTimeout(TIMEOUT);
            return clientDispatcher.receive(connectionHandler.getInputStream(), connectionHandler.getSocket().getReceiveBufferSize());
        } catch (IOException e) {
            connectionHandler.close();
            throw e;
        }

    }

    @Override
    public PerformanceState getPerformanceState() {
        return connectionHandler.getPerformanceState();
    }
}
