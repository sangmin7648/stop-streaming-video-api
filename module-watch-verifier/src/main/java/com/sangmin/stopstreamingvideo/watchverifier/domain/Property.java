package com.sangmin.stopstreamingvideo.watchverifier.domain;

public sealed interface Property {

    String value();

    record Category (String value) implements Property { }
}
