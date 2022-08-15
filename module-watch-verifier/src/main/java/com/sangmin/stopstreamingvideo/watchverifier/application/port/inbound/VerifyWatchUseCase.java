package com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound;

import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.VerifierMode;

import java.util.UUID;

public interface VerifyWatchUseCase {

    /**
     * check if video is watchable by user
     * @param command : params for watchable verification
     * @return if video is watchable
     */
    boolean canWatch(Command command);

    record Command(
            UUID userId,
            String videoId,
            Provider provider,
            VerifierMode mode
    ) { }

}
