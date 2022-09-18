package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import com.sangmin.stopstreamingvideo.shared.componentannotation.UseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.VerifyWatchUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.WatchVerifierRepository;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Video;
import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
class VerifyWatchUseCaseImpl implements VerifyWatchUseCase {

    private final WatchVerifierRepository watchVerifierRepository;
    private final VideoService videoService;

    @Override
    public boolean canWatch(@NonNull UserWatchCommand command) {
        Video video = videoService.getVideo(command.videoId(), command.provider());
        WatchVerifier watchVerifier = watchVerifierRepository.getByUserId(command.userId());
        return watchVerifier.canWatch(video, command.mode());
    }

    @Override
    public boolean canWatch(@NonNull AnonWatchCommand command) {
        Video video = videoService.getVideo(command.videoId(), command.provider());
        WatchVerifier tmpWatchVerifier = createTmpWatchVerifier(command);
        return tmpWatchVerifier.canWatch(video, command.mode());
    }

    private WatchVerifier createTmpWatchVerifier(AnonWatchCommand command) {
        WatchVerifier tmpWatchVerifier = new WatchVerifier(UUID.randomUUID());
        command.properties().forEach(property
            -> tmpWatchVerifier.addFilter(command.mode(), command.provider(), property));
        return tmpWatchVerifier;
    }

}
