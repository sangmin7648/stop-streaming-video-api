package com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound;

import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;
import java.util.UUID;
import org.springframework.lang.NonNull;

public interface WatchVerifierRepository {

    @NonNull
    WatchVerifier getByUserId(UUID userId);

    WatchVerifier save(WatchVerifier watchVerifier);

    void delete(WatchVerifier watchVerifier);

}
