package com.sangmin.stopstreamingvideo.watchverifier.adapter.inbound;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sangmin.stopstreamingvideo.WatchVerifierConfig;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.FindVideoAgent;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Video;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = WatchVerifierConfig.class)
class WatchVerifierRestControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FindVideoAgent findVideoAgent;

    @Test
    void anon_can_verify_watchable() throws Exception {
        // given
        var video = new Video(
            "1234",
            Provider.YOUTUBE,
            List.of(new Property.Category("MOVIE"))
        );

        given(findVideoAgent.supports(Provider.YOUTUBE)).willReturn(true);
        given(findVideoAgent.findVideo(anyString())).willReturn(Optional.of(video));

        var endpoint = "/v1/watch-verifier/verify-watch";
        var request =
            """
            {
                "properties": [
                    {
                        "type": "CATEGORY",
                        "value": "MOVIE"
                    }
                ],
                "videoId": "1234",
                "provider": "YOUTUBE",
                "mode": "WHITELIST"
            }
            """;

        // when
        var resultActions = mockMvc.perform(post(endpoint)
            .contentType(MediaType.APPLICATION_JSON)
            .content(request)
            .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.canWatch").value(true));
    }

}