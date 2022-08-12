package com.sangmin.watchverifier.common;

import lombok.Getter;

import java.util.UUID;

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

}
