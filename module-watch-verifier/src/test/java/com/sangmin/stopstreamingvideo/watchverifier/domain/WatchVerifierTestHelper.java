package com.sangmin.stopstreamingvideo.watchverifier.domain;

import java.util.List;
import java.util.Set;
import org.springframework.test.util.ReflectionTestUtils;

public class WatchVerifierTestHelper {

    public static Set<WatchFilter> getWatchFilters(VerifierMode mode, WatchVerifier sut) {
        return getWatchVerifierItems(sut).stream()
            .filter(wvi -> wvi.supports(mode))
            .map(WatchVerifierTestHelper::getWatchFilters)
            .findAny()
            .orElseThrow();
    }

    public static List<WatchVerifierItem> getWatchVerifierItems(WatchVerifier watchVerifier) {
        return (List<WatchVerifierItem>) ReflectionTestUtils.getField(watchVerifier, "watchVerifierItems");
    }

    public static WatchFilter createWatchFilter(Provider provider, Property property) {
        return new WatchFilter(provider, property);
    }

    static Set<WatchFilter> getWatchFilters(WatchVerifierItem watchVerifierItem) {
        return (Set<WatchFilter>) ReflectionTestUtils.getField(watchVerifierItem, "watchFilters");
    }

}
