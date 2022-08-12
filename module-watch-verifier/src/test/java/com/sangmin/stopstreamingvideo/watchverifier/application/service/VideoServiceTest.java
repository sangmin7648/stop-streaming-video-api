package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.FindVideoAgent;
import com.sangmin.stopstreamingvideo.common.Exceptions;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Video;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VideoServiceTest {

    private VideoService sut;

    @BeforeEach
    void setup() {
        sut = new VideoService(List.of(new FakeFindYoutubeVideoAgent()));
    }

    @Test
    void can_find_video_from_provider() {
        String videoId = "test-id";
        var video = sut.getVideo(videoId, Provider.YOUTUBE);
        assertEquals(videoId, video.id());
    }

    @Test
    void throw_exception_when_video_not_found() {
        String videoId = "no-video";

        assertThatThrownBy(() -> sut.getVideo(videoId, Provider.YOUTUBE))
                .isInstanceOf(Exceptions.VideoNotFound.class);
    }

    @Test
    void throw_exception_when_video_agent_not_found() {
        String videoId = "test";

        assertThatThrownBy(() -> sut.getVideo(videoId, null))
                .isInstanceOf(Exceptions.ProviderVideoAgentNotFound.class);
    }

    static class FakeFindYoutubeVideoAgent implements FindVideoAgent {

        @Override
        public boolean supports(Provider provider) {
            return Provider.YOUTUBE == provider;
        }

        @Override
        public Optional<Video> findVideo(String videoId) {
            if ("no-video".equals(videoId)) {
                return Optional.empty();
            }

            List<Property> properties = List.of(
                    new Property.Category("gaming"),
                    new Property.Category("news"),
                    new Property.Category("test")
            );

            return Optional.of(new Video(videoId, Provider.YOUTUBE, properties));
        }

    }
}