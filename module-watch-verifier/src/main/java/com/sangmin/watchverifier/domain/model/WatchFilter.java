package com.sangmin.watchverifier.domain.model;

import lombok.ToString;

@ToString
class WatchFilter {

    private final Provider provider;
    private final Property property;

    public WatchFilter(Provider provider, Property property) {
        this.provider = provider;
        this.property = property;
    }

    boolean matches(Video video) {
        return video.matches(this.provider, this.property);
    }

}
