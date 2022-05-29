package com.otto15.common.entities.validators;

import com.otto15.common.entities.User;
import com.otto15.common.exceptions.ValidationException;
import com.otto15.common.utils.DataNormalizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class UserValidator {

    private static final short MIN_PASSWORD_LENGTH = 4;
    private static final short MAX_LOGIN_LENGTH = 200;

    private UserValidator() {

    }

    public static String getValidatedLogin(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Login can not be empty.");
        }
        if (args.length > 1) {
            throw new IllegalArgumentException("Spaces not allowed in login.");
        }
        if (args[0].length() < 1) {
            throw new IllegalArgumentException("Login must have at least 1 character.");
        }
        if (args[0].length() > MAX_LOGIN_LENGTH) {
            throw new IllegalArgumentException("Login must have at least 1 character.");
        }
        return args[0];
    }

    public static String getValidatedPassword(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Password can not be empty.");
        }
        if (args.length > 1) {
            throw new IllegalArgumentException("Spaces not allowed in password.");
        }
        if (args[0].length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must have at least 4 characters.");
        }
        return args[0];
    }

    public static User validateUser(String login, String password) throws ValidationException {
        User user = new User();
        List<String> validationErrorsList = new ArrayList<>();
        try {
            user.setLogin(getValidatedLogin(DataNormalizer.normalize(login)));
            validationErrorsList.add(null);
        } catch (IllegalArgumentException e) {
            validationErrorsList.add(e.getMessage());
        }
        try {
            user.setPassword(getValidatedPassword(DataNormalizer.normalize(password)));
            validationErrorsList.add(null);
        } catch (IllegalArgumentException e) {
            validationErrorsList.add(e.getMessage());
        }

        if (validationErrorsList.stream().anyMatch(Objects::nonNull)) {
            throw new ValidationException(validationErrorsList);
        }
        return user;
    }

    public static User validateUser(String login, String password, String repeatedPassword) throws ValidationException {
        User user = new User();
        List<String> validationErrorsList = new ArrayList<>();

        try {
            login = getValidatedLogin(DataNormalizer.normalize(login));
            validationErrorsList.add(null);
        } catch (IllegalArgumentException e) {
            validationErrorsList.add(e.getMessage());
        }

        try {
            password = getValidatedPassword(DataNormalizer.normalize(password));
            validationErrorsList.add(null);
        } catch (IllegalArgumentException e) {
            validationErrorsList.add(e.getMessage());
        }

        try {
            repeatedPassword = getValidatedPassword(DataNormalizer.normalize(repeatedPassword));
            validationErrorsList.add(null);
        } catch (IllegalArgumentException e) {
            validationErrorsList.add(e.getMessage());
        }

        if (!password.equals(repeatedPassword)) {
            validationErrorsList.set(1, "Passwords must be the same");
            validationErrorsList.set(2, "");
        }

        if (validationErrorsList.stream().anyMatch(Objects::nonNull)) {
            throw new ValidationException(validationErrorsList);
        }
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }
}
