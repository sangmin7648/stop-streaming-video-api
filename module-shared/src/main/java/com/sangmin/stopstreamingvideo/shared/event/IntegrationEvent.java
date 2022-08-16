package com.sangmin.stopstreamingvideo.shared.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode
public sealed class IntegrationEvent permits UserSignedUpEvent {

    private final UUID workflowId;
    private final UUID messageId;

    public IntegrationEvent(UUID workflowId) {
        this.workflowId = workflowId;
        this.messageId = UUID.randomUUID();
    }

    public UUID workflowId() {
        return workflowId;
    }

    public UUID messageId() {
        return messageId;
    }

}
