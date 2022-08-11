package com.sangmin.watchverifier.domain.model;

import com.sangmin.watchverifier.domain.model.property.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideoTest {

    @Test
    void can_verify_if_video_matches_given_attributes() {
        // given
        var videoId = "test";
        var provider = Provider.YOUTUBE;
        List<Property> properties = List.of(
                new Category("여행"),
                new Category("게임")
        );

        // when
        Video sut = new Video(videoId, provider, properties);
        boolean matches = sut.matches(provider, properties.get(0));

        // then
        assertTrue(matches);
    }

    @Test
    void can_verify_if_video_not_matches_given_attributes() {
        // given
        var videoId = "test";
        var provider = Provider.YOUTUBE;
        List<Property> properties = List.of(
                new Category("여행"),
                new Category("게임")
        );

        // when
        Video sut = new Video(videoId, provider, properties);
        boolean matches = sut.matches(provider, new Category("문학"));

        // then
        assertFalse(matches);
    }

}