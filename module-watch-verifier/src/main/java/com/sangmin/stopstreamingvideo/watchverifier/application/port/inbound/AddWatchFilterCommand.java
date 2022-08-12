package com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound;

import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.VerifierMode;

import java.util.UUID;

public record AddWatchFilterCommand(
        UUID userId,
        VerifierMode mode,
        Provider provider,
        Property property
) { }
