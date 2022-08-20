package com.sangmin.stopstreamingvideo.shared.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@ToString
@EqualsAndHashCode
public class IntegrationEvent {

    private final UUID messageId;

    public IntegrationEvent() {
        this.messageId = UUID.randomUUID();
    }

    public UUID messageId() {
        return messageId;
    }

}
