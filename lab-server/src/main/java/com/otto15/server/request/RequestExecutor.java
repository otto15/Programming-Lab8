package com.otto15.server.request;

import com.otto15.common.commands.AddCommand;
import com.otto15.common.controllers.CommandManager;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.network.Serializer;

import java.util.Objects;


public class RequestExecutor {

    private final CommandManager commandManager;

    public RequestExecutor(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response execute(byte[] bytesOfRequest) {
        Serializer serializer = new Serializer();
        Request request = (Request) serializer.deserialize(bytesOfRequest);
        request.getCommand().setCommandManager(commandManager);
        if (Objects.equals(request.getCommand().getName(), "history") || Objects.equals(request.getCommand().getName(), "add")) {
            try {
                System.out.println("туктадык");
                Thread.sleep(14000);
            } catch (InterruptedException e) {

            }
        }
        if (commandManager.getCommandsWithoutAuth().containsKey(request.getCommand().getName())) {
            return commandManager.executeCommand(request.getCommand(), request.getArgs());
        }
        long checkUserResult = commandManager.getDBWorker().checkUser(request.getUser());
        if (checkUserResult < 0) {
            return new Response("DB problems, try again later.", false);
        }
        if (checkUserResult == 0) {
            return new Response("Sign in/up first, call \"help\" to see list of commands.", false);
        }

        return commandManager.executeCommand(request.getCommand(), request.getArgs());
    }

}
