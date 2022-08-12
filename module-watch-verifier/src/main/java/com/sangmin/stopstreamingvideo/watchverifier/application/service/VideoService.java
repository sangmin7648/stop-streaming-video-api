package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.FindVideoAgent;
import com.sangmin.stopstreamingvideo.common.Exceptions;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Video;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class VideoService {

    private final List<FindVideoAgent> findVideoAgents;

    public Video getVideo(final String videoId, final Provider provider) {
        FindVideoAgent videoAgent = routeFindVideoAgent(provider);

        return videoAgent.findVideo(videoId)
                .orElseThrow(() -> new Exceptions.VideoNotFound("failed to get video", videoId));
    }

    private FindVideoAgent routeFindVideoAgent(final Provider provider) {
        return findVideoAgents.stream()
                .filter(agent -> agent.supports(provider))
                .findAny()
                .orElseThrow(providerVideoAgentNotFound(provider));
    }

    private Supplier<Exceptions.ProviderVideoAgentNotFound> providerVideoAgentNotFound(Provider provider) {
        return () -> new Exceptions.ProviderVideoAgentNotFound("failed to find video agent", provider);
    }

}
