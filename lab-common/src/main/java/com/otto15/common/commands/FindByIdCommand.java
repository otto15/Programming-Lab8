package com.otto15.common.commands;

import com.otto15.common.controllers.CommandManager;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.network.Response;

import java.util.Objects;

public class FindByIdCommand extends AbstractCommand {

    public FindByIdCommand(CommandManager commandManager) {
        super(commandManager, "", "", 0);
    }

    @Override
    public Response execute(Object[] args) {
        User user = (User) args[1];
        Person foundPerson = getCommandManager().getCollectionManager().findById((Long) args[0]);
        if (foundPerson.getId() == -1) {
            return new Response("Person was not found.", null, false);
        }
        if (!Objects.equals(foundPerson.getAuthor(), user.getLogin())) {
            return new Response("You do not have rights to the person with such id.", false);
        }
        return new Response(foundPerson.currentValues(), user, true);
    }

}
