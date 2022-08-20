package com.sangmin.stopstreamingvideo.common;

public class Exceptions {

    public static class UsernameAlreadyExist extends RuntimeException {

        public UsernameAlreadyExist(String message) {
            super(message);
        }

    }

}
