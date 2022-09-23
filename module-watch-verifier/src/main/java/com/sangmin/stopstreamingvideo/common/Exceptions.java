package com.sangmin.stopstreamingvideo.common;

import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import java.util.UUID;
import lombok.Getter;

public class Exceptions {

    private Exceptions() {
        throw new UnsupportedOperationException("class should not be instantiated");
    }

    /**
     * base exception for watch-verifier module
     * this exception should not be thrown raw, but rather extended
     */
    private static class WatchVerifierException extends RuntimeException {

        private WatchVerifierException(String message) {
            super(message);
        }

    }

    public static class FilterMaxCapacityReached extends WatchVerifierException {

        public FilterMaxCapacityReached(String message) {
            super(message);
        }

    }

    @Getter
    public static class VerifierNotFound extends WatchVerifierException {

        private final UUID watchVerifierId;
        private final UUID userId;

        public VerifierNotFound(String message, UUID watchVerifierId, UUID userId) {
            super(message);
            this.watchVerifierId = watchVerifierId;
            this.userId = userId;
        }

    }

    @Getter
    public static class VideoNotFound extends WatchVerifierException {

        private final String videoId;

        public VideoNotFound(String message, String videoId) {
            super(message);
            this.videoId = videoId;
        }

    }

    @Getter
    public static class ProviderVideoAgentNotFound extends WatchVerifierException {

        private final Provider provider;

        public ProviderVideoAgentNotFound(String message, Provider provider) {
            super(message);
            this.provider = provider;
        }

    }
}
