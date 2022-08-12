package com.sangmin.watchverifier.application.port.outbound;

import com.sangmin.watchverifier.domain.model.Provider;
import com.sangmin.watchverifier.domain.model.Video;

import java.util.Optional;

public interface FindVideoAgent {

    boolean supports(Provider provider);

    Optional<Video> findVideo(String videoId);

}
