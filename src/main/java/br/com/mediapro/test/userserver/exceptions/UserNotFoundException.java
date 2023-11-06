package br.com.mediapro.test.userserver.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super(String.format("User %s not found.", id));
    }
}