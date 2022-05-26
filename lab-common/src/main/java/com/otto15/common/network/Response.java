package com.otto15.common.network;

import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

public class Response implements Serializable {

    private final boolean status;
    private final String message;
    private Collection<Person> persons;
    private User user;

    public Response(String message, User user, boolean status) {
        this.message = message;
        this.user = user;
        this.status = status;
    }

    public Response(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public Response(Collection<Person> persons, String message, boolean status) {
        this.persons = persons;
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isStatus() {
        return status;
    }

    public void showResult() {
        if (persons != null) {
            if (persons.isEmpty()) {
                System.out.println("Collection is empty.");
                return;
            }
            System.out.println(persons.stream()
                    .map(Person::toString)
                    .collect(Collectors.joining("\n")));
            return;
        }
        System.out.println(message);
    }

    @Override
    public String toString() {
        return "Response{"
                + "message='" + message + '\''
                + '}';
    }
}
