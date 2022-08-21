package com.sangmin.stopstreamingvideo.user.feature;

import an.awesome.pipelinr.Pipeline;
import an.awesome.pipelinr.Pipelinr;
import com.sangmin.stopstreamingvideo.common.Exceptions;
import com.sangmin.stopstreamingvideo.shared.event.UserSignedUpEvent;
import com.sangmin.stopstreamingvideo.user.infra.InMemoryUserRepository;
import com.sangmin.stopstreamingvideo.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserTest {

    private Pipeline pipeline;
    private UserRepository userRepository = new InMemoryUserRepository();
    private ApplicationEventPublisher eventPublisher;


    @BeforeEach
    void setup() {
        eventPublisher = mock(ApplicationEventPublisher.class);
        var handler = new RegisterUser.Handler(userRepository, eventPublisher);
        pipeline = new Pipelinr()
                .with(() -> Stream.of(handler));
    }

    @Test
    void user_sign_up() {
        String username = "test";
        var command = new RegisterUser.Command(username);

        UUID userId = command.execute(pipeline);

        assertThat(userId).isNotNull();
        assertThat(userRepository.getByUsername(username).username()).isEqualTo(username);
        verify(eventPublisher, times(1)).publishEvent(any(UserSignedUpEvent.class));
    }

    @Test
    void already_existing_username_cannot_signup() {
        var command1 = new RegisterUser.Command("test");
        command1.execute(pipeline);
        var command2 = new RegisterUser.Command("test");

        assertThatThrownBy(() -> command2.execute(pipeline))
                .isInstanceOf(Exceptions.UsernameAlreadyExist.class);
    }

}