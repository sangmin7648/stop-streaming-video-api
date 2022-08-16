package com.sangmin.stopstreamingvideo.shared.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class UserSignedUpEvent extends IntegrationEvent {
    private final UUID userId;

    public UserSignedUpEvent(UUID workflowId, UUID userId) {
        super(workflowId);
        this.userId = userId;
    }

    public UUID userId() {
        return userId;
    }

}
