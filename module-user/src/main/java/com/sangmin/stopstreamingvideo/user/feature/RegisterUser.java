package com.sangmin.stopstreamingvideo.user.feature;

import com.sangmin.stopstreamingvideo.common.Exceptions;
import com.sangmin.stopstreamingvideo.shared.event.UserSignedUpEvent;
import com.sangmin.stopstreamingvideo.user.domain.User;
import com.sangmin.stopstreamingvideo.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

public class RegisterUser {

    public record Command(String username) implements an.awesome.pipelinr.Command<UUID> { }

    @Component
    @RequiredArgsConstructor
    static class Handler implements an.awesome.pipelinr.Command.Handler<Command, UUID> {

        private final UserRepository userRepository;
        private final ApplicationEventPublisher eventPublisher;

        @Override
        public UUID handle(Command command) {
            User user = userRepository.getByUsername(command.username);
            if (user != null) {
                throw new Exceptions.UsernameAlreadyExist("username " + command.username + " already exists");
            }

            var newUser = new User(command.username);
            User savedUser = userRepository.save(newUser);

            eventPublisher.publishEvent(new UserSignedUpEvent(savedUser.id()));
            return savedUser.id();
        }

    }

}
