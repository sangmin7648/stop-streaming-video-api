package com.sangmin.stopstreamingvideo.watchverifier.domain;

enum VerifierMode {
    BLACKLIST,  // can watch only if no filters match
    WHITELIST   // can watch only if all filters match
}
