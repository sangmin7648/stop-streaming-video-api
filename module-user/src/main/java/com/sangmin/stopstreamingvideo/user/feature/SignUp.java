package com.sangmin.stopstreamingvideo.user.feature;

import an.awesome.pipelinr.Command;
import com.sangmin.stopstreamingvideo.user.domain.User;
import com.sangmin.stopstreamingvideo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class SignUp {

    public record Condition(String username, String password) implements Command<UUID> { }

    @Component
    @RequiredArgsConstructor
    static class Handler implements Command.Handler<Condition, UUID> {

        private final UserRepository userRepository;

        @Override
        public UUID handle(Condition condition) {
            var newUser = new User(condition.username, condition.password);
            User user = userRepository.save(newUser);
            return user.userId();
        }

    }

}
