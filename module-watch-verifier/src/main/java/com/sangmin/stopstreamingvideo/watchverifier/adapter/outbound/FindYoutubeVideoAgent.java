package com.sangmin.stopstreamingvideo.watchverifier.adapter.outbound;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoListResponse;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.FindVideoAgent;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Video;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindYoutubeVideoAgent implements FindVideoAgent {

    private final String apiKey;
    private YouTube youtube;

    public FindYoutubeVideoAgent(@Value("${provider.youtube.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    // sdk sample : https://developers.google.com/youtube/v3/code_samples/java
    @PostConstruct
    void setupYoutube() {
        this.youtube = new YouTube.Builder(new NetHttpTransport(), new GsonFactory(), reqInit -> { /* no-op */ })
            .setApplicationName("stop-streaming-video")
            .build();
    }

    @Override
    public boolean supports(Provider provider) {
        return Provider.YOUTUBE == provider;
    }

    @Override
    public Optional<Video> findVideo(String videoId) {
        try {
            String categoryId = this.retrieveVideoCategoryId(videoId);
            if (categoryId == null) {
                return Optional.empty();
            }

            return Optional.of(createVideo(videoId, categoryId));
        } catch (IOException e) {
            log.error("find youtube video failed message: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    // youtube api doc :
    // https://developers.google.com/youtube/v3/docs/videos/list
    @Nullable
    private String retrieveVideoCategoryId(String videoId) throws IOException {
        VideoListResponse videoResponse = youtube.videos()
            .list(List.of("snippet"))
            .setKey(apiKey)
            .setId(List.of(videoId))
            .execute();

        if (videoResponse.getItems().isEmpty()) {
            return null;
        }

        var youtubeVideo = videoResponse.getItems().get(0);
        return youtubeVideo.getSnippet().getCategoryId();
    }

    private Video createVideo(String videoId, String categoryTitle) {
        List<Property> properties = List.of(
            new Property.Category(categoryTitle)
        );

        return new Video(videoId, Provider.YOUTUBE, properties);
    }

}
