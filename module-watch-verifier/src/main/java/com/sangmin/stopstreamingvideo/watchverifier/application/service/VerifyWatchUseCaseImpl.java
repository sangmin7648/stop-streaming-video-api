package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.VerifyWatchUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.WatchVerifierRepository;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Video;
import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VerifyWatchUseCaseImpl implements VerifyWatchUseCase {

    private final WatchVerifierRepository watchVerifierRepository;
    private final VideoService videoService;

    public boolean canWatch(@NonNull Command command) {
        Video video = videoService.getVideo(command.videoId(), command.provider());
        WatchVerifier watchVerifier = watchVerifierRepository.getByUserId(command.userId());
        return watchVerifier.canWatch(video, command.mode());
    }

}
