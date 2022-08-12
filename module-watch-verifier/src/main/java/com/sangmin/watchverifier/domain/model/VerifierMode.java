package com.sangmin.watchverifier.domain.model;

enum VerifierMode {
    BLACKLIST,  // can watch only if no filters match
    WHITELIST   // can watch only if all filters match
}
