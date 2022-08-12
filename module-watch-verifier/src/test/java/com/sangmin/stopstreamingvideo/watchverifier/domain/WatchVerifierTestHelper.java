package com.sangmin.stopstreamingvideo.watchverifier.domain;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

public class WatchVerifierTestHelper {

    static Set<WatchFilter> getWatchFilters(WatchVerifierItem watchVerifierItem) {
        return (Set<WatchFilter>) ReflectionTestUtils.getField(watchVerifierItem, "watchFilters");
    }

}
