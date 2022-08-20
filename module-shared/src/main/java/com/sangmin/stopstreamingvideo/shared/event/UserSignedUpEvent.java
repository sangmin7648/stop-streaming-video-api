package com.sangmin.stopstreamingvideo.shared.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.MDC;

import java.util.UUID;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class UserSignedUpEvent extends IntegrationEvent {
    private final UUID userId;

    public UserSignedUpEvent(UUID userId) {
        super(UUID.fromString(MDC.get("workflowId")));
        this.userId = userId;
    }

    public UUID userId() {
        return userId;
    }

}
