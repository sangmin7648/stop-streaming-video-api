package com.sangmin.watchverifier.domain.model;

public sealed interface Property {

    String value();

    record Category (String value) implements Property { }
}
