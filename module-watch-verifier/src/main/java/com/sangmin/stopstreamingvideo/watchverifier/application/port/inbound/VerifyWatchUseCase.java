package com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound;

import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.VerifierMode;

import java.util.List;
import java.util.UUID;

public interface VerifyWatchUseCase {

    /**
     * check if video is watchable by user
     *
     * @param userWatchCommand : params for watchable verification
     * @return if video is watchable
     */
    boolean canWatch(UserWatchCommand userWatchCommand);

    record UserWatchCommand(
            UUID userId,
            String videoId,
            Provider provider,
            VerifierMode mode
    ) {
    }

    /**
     * check if video is watchable by anon user with given attributes
     *
     * @param anonWatchCommand : params for watchable verification
     * @return if video is watchable
     */
    boolean canWatch(AnonWatchCommand anonWatchCommand);

    record AnonWatchCommand(
            List<Property> properties,
            String videoId,
            Provider provider,
            VerifierMode mode
    ) {
    }
}
