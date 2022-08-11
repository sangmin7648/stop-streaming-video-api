package com.sangmin.watchverifier.domain.model;

import com.sangmin.watchverifier.common.Exceptions;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

class WatchVerifier {

    private final UUID id;
    private final Mode mode;
    private final Set<WatchFilter> watchFilters;

    enum Mode {
        BLACKLIST,  // can watch only if none filters match
        WHITELIST   // can watch only if all filters match
    }

    public WatchVerifier(Mode mode) {
        this.id = UUID.randomUUID();
        this.mode = mode;
        this.watchFilters = new HashSet<>();
    }

    public boolean canWatch(Video video) {
        return switch (this.mode) {
            case BLACKLIST -> watchFilters
                    .stream()
                    .noneMatch(wf -> wf.matches(video));
            case WHITELIST -> watchFilters
                    .stream()
                    .allMatch(wf -> wf.matches(video));
        };
    }

    public void addFilter(WatchFilter newFilter) {
        if (watchFilters.size() >= 1000) {
            throw new Exceptions.FilterMaxCapacityReached(
                    "new filter cannot be added " + newFilter.toString());
        }

        watchFilters.add(newFilter);
    }

    public void removeFilter(WatchFilter filter) {
        watchFilters.remove(filter);
    }

}
