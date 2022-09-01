package com.sangmin.stopstreamingvideo.watchverifier.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class VideoTest {

    @Test
    void can_verify_if_video_matches_given_attributes() {
        var videoId = "test";
        var provider = Provider.YOUTUBE;
        List<Property> properties = List.of(new Property.Category("여행"), new Property.Category("게임"));
        Video sut = new Video(videoId, provider, properties);

        boolean matches = sut.matches(provider, properties.get(0));

        assertTrue(matches);
    }

    @Test
    void can_verify_if_video_not_matches_given_attributes() {
        var videoId = "test";
        var provider = Provider.YOUTUBE;
        List<Property> properties = List.of(new Property.Category("여행"), new Property.Category("게임"));
        Video sut = new Video(videoId, provider, properties);

        boolean matches = sut.matches(provider, new Property.Category("문학"));

        assertFalse(matches);
    }

}