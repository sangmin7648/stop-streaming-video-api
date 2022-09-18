package com.sangmin.stopstreamingvideo.watchverifier.adapter.inbound.dto;

import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;

public record PropertyRequest(Type type, String value) {
    enum Type {
        CATEGORY
    }

    public Property toProperty() {
        return switch (type) {
            case CATEGORY -> new Property.Category(value);
        };
    }
}
