package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.VerifyWatchUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.FindVideoAgent;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.WatchVerifierRepository;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.VerifierMode;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Video;
import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VerifyWatchUseCaseImplTest {

    private VerifyWatchUseCaseImpl sut;

    private WatchVerifierRepository watchVerifierRepository;

    private FindVideoAgent findVideoAgent;

    @BeforeEach
    void setup() {
        watchVerifierRepository = mock(WatchVerifierRepository.class);
        findVideoAgent = mock(FindVideoAgent.class);
        sut = new VerifyWatchUseCaseImpl(watchVerifierRepository, new VideoService(List.of(findVideoAgent)));
    }

    @ParameterizedTest
    @MethodSource
    void user_can_watch_depending_on_mode_when_attributes_registered(
        Provider provider,
        VerifierMode mode,
        Property property,
        boolean expected
    ) {
        // given
        var userId = UUID.randomUUID();
        var videoId = "test-id";
        var command = new VerifyWatchUseCase.UserWatchCommand(userId, videoId, provider, mode);
        var video = new Video(videoId, provider, List.of(property));
        var watchVerifier = new WatchVerifier(userId);
        watchVerifier.addFilter(mode, provider, property);
        setupMock(video, watchVerifier);

        // when
        boolean canWatch = sut.canWatch(command);

        // then
        assertEquals(expected, canWatch);
    }

    private static Stream<Arguments> user_can_watch_depending_on_mode_when_attributes_registered() {
        return Stream.of(
            Arguments.of(
                Provider.YOUTUBE,
                VerifierMode.WHITELIST,
                new Property.Category("test"),
                true
            ),
            Arguments.of(
                Provider.YOUTUBE,
                VerifierMode.BLACKLIST,
                new Property.Category("test"),
                false
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void user_can_always_watch_video_when_no_attributes_are_registered(
        Provider provider,
        VerifierMode mode,
        Property property
    ) {
        // given
        var userId = UUID.randomUUID();
        var videoId = "test-id";
        var command = new VerifyWatchUseCase.UserWatchCommand(userId, videoId, provider, mode);
        var video = new Video(videoId, provider, List.of(property));
        setupMock(video, new WatchVerifier(userId));

        // when
        boolean canWatch = sut.canWatch(command);

        // then
        assertTrue(canWatch);
    }

    private static Stream<Arguments> user_can_always_watch_video_when_no_attributes_are_registered() {
        return Stream.of(
            Arguments.of(
                Provider.YOUTUBE,
                VerifierMode.WHITELIST,
                new Property.Category("test")
            ),
            Arguments.of(
                Provider.YOUTUBE,
                VerifierMode.BLACKLIST,
                new Property.Category("test")
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void anon_can_watch_depending_on_mode_when_attributes_are_given(
        Provider provider,
        VerifierMode mode,
        List<Property> properties,
        boolean expected
    ) {
        // given
        var videoId = "test-id";
        var video = new Video(videoId, provider, properties);
        setupMockVideoAgent(video);
        var command = new VerifyWatchUseCase.AnonWatchCommand(properties, videoId, provider, mode);

        // when
        boolean canWatch = sut.canWatch(command);

        // then
        assertEquals(expected, canWatch);
    }

    private static Stream<Arguments> anon_can_watch_depending_on_mode_when_attributes_are_given() {
        return Stream.of(
            Arguments.of(
                Provider.YOUTUBE,
                VerifierMode.WHITELIST,
                List.of(new Property.Category("test")),
                true
            ),
            Arguments.of(
                Provider.YOUTUBE,
                VerifierMode.BLACKLIST,
                List.of(new Property.Category("test")),
                false
            )
        );
    }

    @ParameterizedTest
    @MethodSource
    void anon_can_always_watch_video_when_no_attributes_are_given(
        Provider provider,
        VerifierMode mode,
        List<Property> properties
    ) {
        // given
        var videoId = "test-id";
        var video = new Video(videoId, provider, properties);
        setupMockVideoAgent(video);
        var command = new VerifyWatchUseCase.AnonWatchCommand(Collections.emptyList(), videoId, provider, mode);

        // when
        boolean canWatch = sut.canWatch(command);

        // then
        assertTrue(canWatch);
    }

    private static Stream<Arguments> anon_can_always_watch_video_when_no_attributes_are_given() {
        return Stream.of(
            Arguments.of(
                Provider.YOUTUBE,
                VerifierMode.WHITELIST,
                List.of(new Property.Category("test"))
            ),
            Arguments.of(
                Provider.YOUTUBE,
                VerifierMode.BLACKLIST,
                List.of(new Property.Category("test"))
            )
        );
    }

    private void setupMockVideoAgent(Video video) {
        given(findVideoAgent.supports(any(Provider.class))).willReturn(true);
        given(findVideoAgent.findVideo(anyString())).willReturn(Optional.of(video));
    }

    private void setupMock(Video video, WatchVerifier watchVerifier) {
        setupMockVideoAgent(video);
        given(watchVerifierRepository.getByUserId(any(UUID.class))).willReturn(watchVerifier);
    }

}