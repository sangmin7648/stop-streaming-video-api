package com.sangmin.stopstreamingvideo.watchverifier.domain;

import com.sangmin.stopstreamingvideo.common.Exceptions;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class WatchVerifier {

    private final UUID id;
    private final UUID userId;
    private final List<WatchVerifierItem> watchVerifierItems;

    public WatchVerifier(@NonNull UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.watchVerifierItems = initVerifiers();
    }

    private List<WatchVerifierItem> initVerifiers() {
        return Arrays.stream(VerifierMode.values())
                .map(WatchVerifierItem::new)
                .toList();
    }

    public boolean canWatch(@NonNull Video video, @NonNull VerifierMode mode) {
        return watchVerifierItems.stream()
                .filter(v -> v.supports(mode))
                .findAny()
                .map(v -> v.canWatch(video))
                .orElse(false);
    }

    public void addFilter(@NonNull VerifierMode mode, @NonNull Provider provider, @NonNull Property property) {
        WatchVerifierItem watchVerifierItem = watchVerifierItems.stream()
                .filter(v -> v.supports(mode))
                .findAny()
                .orElseThrow(verifierNotFound(mode));

        var newFilter = new WatchFilter(provider, property);
        watchVerifierItem.addFilter(newFilter);
    }

    private Supplier<Exceptions.VerifierNotFound> verifierNotFound(VerifierMode mode) {
        String message = "verifier for mode " + mode + " not found";
        return () -> new Exceptions.VerifierNotFound(message, this.id, this.userId);
    }

}
