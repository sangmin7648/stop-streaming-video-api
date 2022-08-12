package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.WatchVerifierRepository;
import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class DummyWatchVerifierRepository implements WatchVerifierRepository {

    private final Map<UUID, WatchVerifier> watchVerifierMap = new HashMap<>();

    @Override
    public Optional<WatchVerifier> findByUserId(UUID userId) {
        return watchVerifierMap.values().stream()
                .filter(wv -> wv.userId().equals(userId))
                .findAny();
    }

    @Override
    public WatchVerifier save(WatchVerifier watchVerifier) {
        watchVerifierMap.put(watchVerifier.id(), watchVerifier);
        return watchVerifier;
    }

    @Override
    public void delete(WatchVerifier watchVerifier) {
        watchVerifierMap.remove(watchVerifier.id());
    }

}
