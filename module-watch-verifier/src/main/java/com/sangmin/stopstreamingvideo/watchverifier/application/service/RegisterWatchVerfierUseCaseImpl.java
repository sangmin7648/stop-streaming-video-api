package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import com.sangmin.stopstreamingvideo.common.Exceptions;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.AddWatchFilterCommand;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.RegisterWatchVerifierUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.WatchVerifierRepository;
import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RegisterWatchVerfierUseCaseImpl implements RegisterWatchVerifierUseCase {

    private final WatchVerifierRepository watchVerifierRepository;

    @Override
    public UUID registerWatchVerifier(@NonNull UUID userId) {
        var newWatchVerifier = new WatchVerifier(userId);
        WatchVerifier watchVerifier = watchVerifierRepository.save(newWatchVerifier);

        return watchVerifier.id();
    }

    @Override
    public void addWatchFilter(@NonNull AddWatchFilterCommand command) {
        WatchVerifier watchVerifier = watchVerifierRepository.getByUserId(command.userId());
        watchVerifier.addFilter(command.mode(), command.provider(), command.property());
    }

}
