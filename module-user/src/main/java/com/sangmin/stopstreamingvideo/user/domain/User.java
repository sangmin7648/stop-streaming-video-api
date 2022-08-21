package com.sangmin.stopstreamingvideo.user.domain;

import java.util.UUID;

public class User {

    private final UUID id;
    private String username;

    public User(String username) {
        this.id = UUID.randomUUID();
        this.username = username;
    }

    public UUID id() {
        return id;
    }

    public String username() {
        return username;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

}
