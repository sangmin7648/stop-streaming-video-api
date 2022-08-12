package com.sangmin.watchverifier.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchFilterTest {

    @Test
    void can_determine_video_matches_filter() {
        var video = new Video("test-id", Provider.YOUTUBE, List.of(new Property.Category("test")));
        var sut = new WatchFilter(Provider.YOUTUBE, new Property.Category("test"));

        var matches = sut.matches(video);

        assertTrue(matches);
    }

    @Test
    void can_determine_video_not_matches_filter() {
        var video = new Video("test-id", Provider.YOUTUBE, List.of(new Property.Category("other-category")));
        var sut = new WatchFilter(Provider.YOUTUBE, new Property.Category("test"));

        var matches = sut.matches(video);

        assertFalse(matches);
    }

}