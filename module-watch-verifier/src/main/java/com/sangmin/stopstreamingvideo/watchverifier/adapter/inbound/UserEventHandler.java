package com.sangmin.stopstreamingvideo.watchverifier.adapter.inbound;

import com.sangmin.stopstreamingvideo.shared.event.UserSignedUpEvent;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.RegisterWatchVerifierUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventHandler {

    private final RegisterWatchVerifierUseCase registerWatchVerifierUseCase;

    @EventListener
    public void handleUserSignedUp(UserSignedUpEvent event) {
        registerWatchVerifierUseCase.registerWatchVerifier(event.userId());
    }

}
