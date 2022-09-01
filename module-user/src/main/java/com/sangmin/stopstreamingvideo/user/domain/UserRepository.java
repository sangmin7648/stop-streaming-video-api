package com.sangmin.stopstreamingvideo.user.domain;

public interface UserRepository {

    User save(User user);

    User getByUsername(String username);

}
