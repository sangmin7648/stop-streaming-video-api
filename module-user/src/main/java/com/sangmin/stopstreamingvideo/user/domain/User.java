package com.sangmin.stopstreamingvideo.user.domain;

import java.util.UUID;

public class User {

    private final UUID userId;
    private String username;
    private String hashedPassword;

    public User(String username, String password) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.changePassword(password);
    }

    public UUID userId() {
        return userId;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changePassword(String password) {
        // TODO: hash password
        this.hashedPassword = password;
    }

}
