package com.sangmin.watchverifier.domain.model;

import com.sangmin.watchverifier.domain.model.property.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchFilterTest {

    @Test
    void can_determine_video_matches_filter() {
        // given
        var video = new Video(
                "test-id",
                Provider.YOUTUBE,
                List.of(new Category("test"))
        );

        var sut = new WatchFilter(
                Provider.YOUTUBE,
                new Category("test")
        );

        // when
        var matches = sut.matches(video);

        // then
        assertTrue(matches);
    }

    @Test
    void can_determine_video_not_matches_filter() {
        // given
        var video = new Video(
                "test-id",
                Provider.YOUTUBE,
                List.of(new Category("other-category"))
        );

        var sut = new WatchFilter(
                Provider.YOUTUBE,
                new Category("test")
        );

        // when
        var matches = sut.matches(video);

        // then
        assertFalse(matches);
    }

}