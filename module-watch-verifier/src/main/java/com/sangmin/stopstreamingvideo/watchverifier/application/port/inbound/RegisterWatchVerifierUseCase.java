package com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound;

import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.VerifierMode;
import java.util.UUID;

public interface RegisterWatchVerifierUseCase {

    /**
     * create and save new user watch verifier.
     *
     * @param userId : user id
     * @return watch verifier id
     */
    UUID registerWatchVerifier(UUID userId);

    /**
     * add filter to verifier.
     *
     * @param command : params required for filter registration
     */
    void addWatchFilter(AddFilterCommand command);

    record AddFilterCommand(
        UUID userId,
        VerifierMode mode,
        Provider provider,
        Property property
    ) {
    }

}
