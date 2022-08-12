package com.sangmin.watchverifier.domain.model;

import com.sangmin.watchverifier.common.Exceptions;
import com.sangmin.watchverifier.domain.model.property.Category;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class WatchVerifierItemTest {

    @Test
    void add_filter_to_verifier() {
        var filter = new WatchFilter(Provider.YOUTUBE, new Category("test"));
        var sut = new WatchVerifierItem(VerifierMode.WHITELIST);

        sut.addFilter(filter);

        assertThat(WatchVerifierTestHelper.getWatchFilters(sut)).hasSize(1);
    }

    @Test
    void cannot_add_filter_to_verifier_over_max_capacity() {
        var filter = new WatchFilter(Provider.YOUTUBE, new Category("test"));
        var sut = new WatchVerifierItem(VerifierMode.WHITELIST);
        addMaxFilter(sut, 1000);

        assertThatThrownBy(() -> sut.addFilter(filter))
                .isInstanceOf(Exceptions.FilterMaxCapacityReached.class);
    }

    @Test
    void delete_filter_from_verifier() {
        // given
        var filter1 = new WatchFilter(Provider.YOUTUBE, new Category("test1"));
        var filter2 = new WatchFilter(Provider.YOUTUBE, new Category("test2"));

        var sut = new WatchVerifierItem(VerifierMode.WHITELIST);
        sut.addFilter(filter1);
        sut.addFilter(filter2);

        // when
        sut.removeFilter(filter1);

        // then
        assertThat(WatchVerifierTestHelper.getWatchFilters(sut)).hasSize(1);
        assertThat(WatchVerifierTestHelper.getWatchFilters(sut)).contains(filter2);
    }

    @Test
    void can_watch_video_if_video_property_in_whitelist_verifier() {
        // given
        var provider = Provider.YOUTUBE;
        var property = new Category("test");
        var video = new Video("test-id", provider, List.of(property));
        var sut = randomUserWhitelistVerifierWithFilter(new WatchFilter(provider, property));

        // when
        boolean canWatch = sut.canWatch(video);

        // then
        assertTrue(canWatch);
    }

    @Test
    void cannot_watch_video_if_video_property_not_in_whitelist_verifier() {
        // given
        var provider = Provider.YOUTUBE;
        var property = new Category("test");
        var video = new Video("test-id", provider, List.of(new Category("test1")));
        var sut = randomUserWhitelistVerifierWithFilter(new WatchFilter(provider, property));

        // when
        boolean canWatch = sut.canWatch(video);

        // then
        assertFalse(canWatch);
    }

    @Test
    void cannot_watch_video_if_video_property_in_blacklist_verifier() {
        // given
        var provider = Provider.YOUTUBE;
        var property = new Category("test");
        var video = new Video("test-id", provider, List.of(property));
        var sut = randomUserBlacklistVerifierWithFilter(new WatchFilter(provider, property));

        // when
        boolean canWatch = sut.canWatch(video);

        // then
        assertFalse(canWatch);
    }

    @Test
    void can_watch_video_if_video_property_not_in_blacklist_verifier() {
        // given
        var provider = Provider.YOUTUBE;
        var property = new Category("test");
        var video = new Video("test-id", provider, List.of(new Category("test1")));
        var sut = randomUserBlacklistVerifierWithFilter(new WatchFilter(provider, property));

        // when
        boolean canWatch = sut.canWatch(video);

        // then
        assertTrue(canWatch);
    }

    private WatchVerifierItem randomUserWhitelistVerifierWithFilter(WatchFilter filter) {
        var watchVerifier = new WatchVerifierItem(VerifierMode.WHITELIST);
        watchVerifier.addFilter(filter);
        return watchVerifier;
    }

    private WatchVerifierItem randomUserBlacklistVerifierWithFilter(WatchFilter filter) {
        var watchVerifier = new WatchVerifierItem(VerifierMode.BLACKLIST);
        watchVerifier.addFilter(filter);
        return watchVerifier;
    }

    private void addMaxFilter(WatchVerifierItem sut, int maxCount) {
        for (int i = 0; i < maxCount; i++) {
            sut.addFilter(new WatchFilter(Provider.YOUTUBE, new Category(String.valueOf(i))));
        }
    }

}