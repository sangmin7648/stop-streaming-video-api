package com.sangmin.stopstreamingvideo.watchverifier.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
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
