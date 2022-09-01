package com.sangmin.stopstreamingvideo.watchverifier.adapter.outbound;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.WatchVerifierRepository;
import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryWatchVerifierRepository implements WatchVerifierRepository {

    private final Map<UUID, WatchVerifier> watchVerifierMap = new HashMap<>();

    @NonNull
    @Override
    public WatchVerifier getByUserId(UUID userId) {
        return watchVerifierMap.values().stream()
            .filter(wv -> wv.userId().equals(userId))
            .findAny()
            .orElseThrow();
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
