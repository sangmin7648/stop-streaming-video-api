package com.sangmin.watchverifier.domain.model;

import java.util.List;

public record Video(
        String id,
        Provider provider,
        List<Property> properties
) {

    public Video(String id, Provider provider, List<Property> properties) {
        this.id = id;
        this.provider = provider;
        this.properties = List.copyOf(properties);
    }

    public boolean matches(Provider provider, Property property) {
        return this.provider.equals(provider) && this.properties.contains(property);
    }

}
