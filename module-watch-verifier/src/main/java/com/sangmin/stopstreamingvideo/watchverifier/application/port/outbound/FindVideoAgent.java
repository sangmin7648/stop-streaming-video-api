package com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound;

import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Video;
import java.util.Optional;

public interface FindVideoAgent {

    boolean supports(Provider provider);

    Optional<Video> findVideo(String videoId);

}
