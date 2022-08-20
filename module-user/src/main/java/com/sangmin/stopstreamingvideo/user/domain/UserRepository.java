package com.sangmin.stopstreamingvideo.user.domain;

import com.sangmin.stopstreamingvideo.user.domain.User;

public interface UserRepository {

    User save(User user);

    User getByUsername(String username);

}
