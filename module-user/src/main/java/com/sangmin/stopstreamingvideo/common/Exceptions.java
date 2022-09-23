package com.sangmin.stopstreamingvideo.common;

public class Exceptions {

    private Exceptions() {
        throw new UnsupportedOperationException("class should not be instantiated");
    }

    /**
     * base exception for user module
     * this exception should not be thrown raw, but rather extended
     */
    private static class UserException extends RuntimeException {

        private UserException(String message) {
            super(message);
        }

    }

    public static class UsernameAlreadyExist extends UserException {

        public UsernameAlreadyExist(String message) {
            super(message);
        }

    }

}
