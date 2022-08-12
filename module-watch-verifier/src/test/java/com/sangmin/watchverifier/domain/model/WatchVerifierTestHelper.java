package com.sangmin.watchverifier.domain.model;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.Set;

class WatchVerifierTestHelper {

    static Set<WatchFilter> getWatchFilters(WatchVerifierItem watchVerifierItem) {
        return  (Set<WatchFilter>) ReflectionTestUtils.getField(watchVerifierItem, "watchFilters");
    }

}
