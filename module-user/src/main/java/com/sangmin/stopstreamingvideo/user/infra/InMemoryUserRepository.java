package com.sangmin.stopstreamingvideo.user.infra;

import com.sangmin.stopstreamingvideo.user.domain.User;
import com.sangmin.stopstreamingvideo.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<UUID, User> map = new HashMap<>();

    @Override
    public User save(User user) {
        map.put(user.id(), user);
        return user;
    }

    @Override
    public User getByUsername(String username) {
        return map.values().stream()
                .filter(user -> user.username().equals(username))
                .findAny()
                .orElse(null);
    }

}
