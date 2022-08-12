package com.sangmin.watchverifier.domain.model;

import com.sangmin.watchverifier.common.Exceptions;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

class WatchVerifierItem {

    private final VerifierMode mode;
    private final Set<WatchFilter> watchFilters;

    WatchVerifierItem(@NonNull VerifierMode mode) {
        this.mode = mode;
        this.watchFilters = new HashSet<>();
    }

    boolean supports(VerifierMode mode) {
        return this.mode == mode;
    }

    boolean canWatch(@NonNull Video video) {
        return switch (this.mode) {
            case BLACKLIST -> watchFilters
                    .stream()
                    .noneMatch(wf -> wf.matches(video));
            case WHITELIST -> watchFilters
                    .stream()
                    .allMatch(wf -> wf.matches(video));
        };
    }

    void addFilter(WatchFilter newFilter) {
        if (watchFilters.size() >= 1000) {
            throw new Exceptions.FilterMaxCapacityReached("new filter cannot be added " + newFilter.toString());
        }

        watchFilters.add(newFilter);
    }

    void removeFilter(WatchFilter filter) {
        watchFilters.remove(filter);
    }

}
