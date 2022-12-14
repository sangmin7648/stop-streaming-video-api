package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import com.sangmin.stopstreamingvideo.shared.componentannotation.UseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.RegisterWatchVerifierUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.WatchVerifierRepository;
import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
class RegisterWatchVerifierUseCaseImpl implements RegisterWatchVerifierUseCase {

    private final WatchVerifierRepository watchVerifierRepository;

    @Override
    public UUID registerWatchVerifier(@NonNull UUID userId) {
        var newWatchVerifier = new WatchVerifier(userId);
        WatchVerifier watchVerifier = watchVerifierRepository.save(newWatchVerifier);

        return watchVerifier.id();
    }

    @Override
    public void addWatchFilter(@NonNull AddFilterCommand command) {
        WatchVerifier watchVerifier = watchVerifierRepository.getByUserId(command.userId());
        watchVerifier.addFilter(command.mode(), command.provider(), command.property());
        watchVerifierRepository.save(watchVerifier);
    }

}
