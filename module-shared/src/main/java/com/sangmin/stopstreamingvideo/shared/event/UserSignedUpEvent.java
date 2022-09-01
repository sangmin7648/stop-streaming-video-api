package com.sangmin.stopstreamingvideo.shared.event;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class UserSignedUpEvent extends IntegrationEvent {
    private final UUID userId;

    public UserSignedUpEvent(UUID userId) {
        super();
        this.userId = userId;
    }

    public UUID userId() {
        return userId;
    }

}
