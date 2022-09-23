package com.sangmin.stopstreamingvideo.watchverifier.adapter.outbound;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoCategory;
import com.google.api.services.youtube.model.VideoCategoryListResponse;
import com.google.api.services.youtube.model.VideoListResponse;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.FindVideoAgent;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Video;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FindYoutubeVideoAgent implements FindVideoAgent {

    private final String apiKey;
    private final YouTube youtube;
    private final Map<CategoryId, CategoryTitle> categoryMap;

    public FindYoutubeVideoAgent(@Value("${provider.youtube.api-key}") String apiKey) throws IOException {
        this.apiKey = apiKey;
        this.youtube = buildYoutube();
        this.categoryMap = retrieveAllCategory();
    }

    @Override
    public boolean supports(Provider provider) {
        return Provider.YOUTUBE == provider;
    }

    @Override
    public Optional<Video> findVideo(String videoId) {
        try {
            Optional<CategoryId> optCategoryId = this.retrieveVideoCategoryId(videoId);
            if (optCategoryId.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(createVideo(videoId, categoryMap.get(optCategoryId.get())));
        } catch (IOException e) {
            log.error("find youtube video failed message: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    // youtube api doc :
    // https://developers.google.com/youtube/v3/docs/videos/list
    private Optional<CategoryId> retrieveVideoCategoryId(String videoId) throws IOException {
        VideoListResponse videoResponse = youtube.videos()
            .list(List.of("snippet"))
            .setKey(apiKey)
            .setId(List.of(videoId))
            .execute();

        if (videoResponse.getItems().isEmpty()) {
            return Optional.empty();
        }

        var youtubeVideo = videoResponse.getItems().get(0);
        String categoryIdStr = youtubeVideo.getSnippet().getCategoryId();
        return Optional.of(new CategoryId(categoryIdStr));
    }

    private Video createVideo(String videoId, CategoryTitle categoryTitle) {
        List<Property> properties = List.of(
            new Property.Category(categoryTitle.title())
        );

        return new Video(videoId, Provider.YOUTUBE, properties);
    }

    private Map<CategoryId, CategoryTitle> retrieveAllCategory() throws IOException {
        VideoCategoryListResponse categoryResponse = youtube.videoCategories()
            .list(List.of("id", "snippet"))
            .setKey(apiKey)
            .setRegionCode("US")
            .execute();

        var categories = categoryResponse.getItems();
        return categories.stream().collect(videoCategoryMapCollector);
    }

    private final Collector<VideoCategory, ?, Map<CategoryId, CategoryTitle>> videoCategoryMapCollector =
        Collectors.toMap(
            videoCategory -> new CategoryId(videoCategory.getId()),
            videoCategory -> new CategoryTitle(videoCategory.getSnippet().getTitle())
        );

    // sdk sample : https://developers.google.com/youtube/v3/code_samples/java
    private YouTube buildYoutube() {
        return new YouTube.Builder(new NetHttpTransport(), new GsonFactory(), reqInit -> { /* no-op */ })
            .setApplicationName("stop-streaming-video")
            .build();
    }

    record CategoryId(String id) {
    }

    record CategoryTitle(String title) {
    }

}
