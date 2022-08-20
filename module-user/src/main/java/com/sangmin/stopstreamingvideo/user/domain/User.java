package com.sangmin.stopstreamingvideo.user.domain;

import java.util.UUID;

public class User {

    private final UUID id;
    private String username;
    private String hashedPassword;

    public User(String username, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.changePassword(password);
    }

    public UUID id() {
        return id;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changePassword(String password) {
        // TODO: hash password
        this.hashedPassword = password;
    }

}
