package com.sangmin.watchverifier.domain.model;

import com.sangmin.watchverifier.domain.model.property.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WatchVerifierTest {

    @ParameterizedTest
    @EnumSource(VerifierMode.class)
    void watchverifier_on_creation_contains_one_verifier_item_per_mode(VerifierMode mode) {
        var sut = new WatchVerifier(UUID.randomUUID());

        long watchVerifierItemCount = getWatchVerifierItems(sut).stream()
                .filter(v -> v.supports(mode))
                .count();

        assertEquals(1, watchVerifierItemCount);
    }

    @Test
    void can_watch_video_in_whitelist_mode_if_filter_exists() {
        // given
        var mode = VerifierMode.WHITELIST;
        var provider = Provider.YOUTUBE;
        var property = new Category("test");
        var video = new Video("test-id", provider, List.of(property));

        var sut = new WatchVerifier(UUID.randomUUID());
        sut.addFilter(mode, provider, property);

        // when
        boolean canWatch = sut.canWatch(video, mode);

        // then
        assertTrue(canWatch);
    }

    @Test
    void can_watch_video_in_blacklist_mode_if_filter_not_exists() {
        // given
        var mode = VerifierMode.BLACKLIST;
        var video = new Video("test-id", Provider.YOUTUBE, List.of(new Category("test")));

        var sut = new WatchVerifier(UUID.randomUUID());
        sut.addFilter(mode, Provider.YOUTUBE, new Category("blacklist"));

        // when
        boolean canWatch = sut.canWatch(video, mode);

        // then
        assertTrue(canWatch);
    }

    @Test
    void cannot_watch_video_in_whitelist_mode_if_filter_not_exists() {
        // given
        var mode = VerifierMode.WHITELIST;
        var video = new Video("test-id", Provider.YOUTUBE, List.of(new Category("test")));

        var sut = new WatchVerifier(UUID.randomUUID());
        sut.addFilter(mode, Provider.YOUTUBE, new Category("blacklist"));

        // when
        boolean canWatch = sut.canWatch(video, mode);

        // then
        assertFalse(canWatch);
    }

    @Test
    void cannot_watch_video_in_blacklist_mode_if_filter_exists() {
        // given
        var mode = VerifierMode.BLACKLIST;
        var provider = Provider.YOUTUBE;
        var property = new Category("test");
        var video = new Video("test-id", provider, List.of(property));

        var sut = new WatchVerifier(UUID.randomUUID());
        sut.addFilter(mode, provider, property);

        // when
        boolean canWatch = sut.canWatch(video, mode);

        // then
        assertFalse(canWatch);
    }

    @ParameterizedTest
    @EnumSource(VerifierMode.class)
    void add_filter_to_specific_mode(VerifierMode mode) {
        var provider = Provider.YOUTUBE;
        var property = new Category("test");

        var sut = new WatchVerifier(UUID.randomUUID());
        sut.addFilter(mode, provider, property);

        Set<WatchFilter> watchFilters = getWatchFilters(mode, sut);
        assertThat(watchFilters).hasSize(1);
    }

    private List<WatchVerifierItem> getWatchVerifierItems(WatchVerifier watchVerifier) {
        return (List<WatchVerifierItem>) ReflectionTestUtils.getField(watchVerifier, "watchVerifierItems");
    }

    private Set<WatchFilter> getWatchFilters(VerifierMode mode, WatchVerifier sut) {
        return getWatchVerifierItems(sut).stream()
                .filter(wvi -> wvi.supports(mode))
                .map(WatchVerifierTestHelper::getWatchFilters)
                .findAny()
                .orElseThrow(() -> new RuntimeException("watch filters does not exist"));
    }

}