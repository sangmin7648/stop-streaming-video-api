package com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound;

import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;

import java.util.Optional;
import java.util.UUID;

public interface WatchVerifierRepository {

    Optional<WatchVerifier> findByUserId(UUID userId);

    WatchVerifier save(WatchVerifier watchVerifier);

    void delete(WatchVerifier watchVerifier);

}
