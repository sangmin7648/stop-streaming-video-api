package com.sangmin.stopstreamingvideo.shared.event;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
