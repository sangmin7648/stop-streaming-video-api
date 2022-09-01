package com.sangmin.stopstreamingvideo.common;

import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import java.util.UUID;
import lombok.Getter;

public class Exceptions {

    private Exceptions() {
        throw new UnsupportedOperationException("class should not be instantiated");
    }

    public static class FilterMaxCapacityReached extends RuntimeException {

        public FilterMaxCapacityReached(String message) {
            super(message);
        }

    }

    @Getter
    public static class VerifierNotFound extends RuntimeException {

        private final UUID watchVerifierId;
        private final UUID userId;

        public VerifierNotFound(String message, UUID watchVerifierId, UUID userId) {
            super(message);
            this.watchVerifierId = watchVerifierId;
            this.userId = userId;
        }

    }

    @Getter
    public static class VideoNotFound extends RuntimeException {

        private final String videoId;

        public VideoNotFound(String message, String videoId) {
            super(message);
            this.videoId = videoId;
        }

    }

    @Getter
    public static class ProviderVideoAgentNotFound extends RuntimeException {

        private final Provider provider;

        public ProviderVideoAgentNotFound(String message, Provider provider) {
            super(message);
            this.provider = provider;
        }

    }
}
