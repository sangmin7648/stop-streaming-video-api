package com.sangmin.stopstreamingvideo.user.feature;

import com.sangmin.stopstreamingvideo.shared.event.UserSignedUpEvent;
import com.sangmin.stopstreamingvideo.user.domain.User;
import com.sangmin.stopstreamingvideo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class SignUp {

    public record Command(String username, String password) implements an.awesome.pipelinr.Command<UUID> { }

    @Component
    @RequiredArgsConstructor
    static class Handler implements an.awesome.pipelinr.Command.Handler<Command, UUID> {

        private final UserRepository userRepository;
        private final ApplicationEventPublisher eventPublisher;

        @Override
        public UUID handle(Command command) {
            var newUser = new User(command.username, command.password);
            User user = userRepository.save(newUser);

            eventPublisher.publishEvent(new UserSignedUpEvent(user.id()));
            return user.id();
        }

    }

}
