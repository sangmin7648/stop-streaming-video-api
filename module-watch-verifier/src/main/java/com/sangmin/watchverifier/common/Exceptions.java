package com.sangmin.watchverifier.common;

public class Exceptions {

    public static class FilterMaxCapacityReached extends RuntimeException {

        public FilterMaxCapacityReached(String message) {
            super(message);
        }

    }

}
