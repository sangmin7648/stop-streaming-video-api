package com.sangmin.stopstreamingvideo.watchverifier.adapter.inbound;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.VerifyWatchUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.VerifierMode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
class VerifyWatchRestController {

    private final VerifyWatchUseCase verifyWatchUseCase;

    @GetMapping("/v1/watch-verifier/user/{userId}/{mode}/verify-watch")
    ResponseEntity<Boolean> verifyWatch(
            @PathVariable String userId,
            @PathVariable String mode,
            @RequestParam String provider,
            @RequestParam String videoId
    ) {
        var command = new VerifyWatchUseCase.Command(
                UUID.fromString(userId),
                videoId,
                Provider.valueOf(provider),
                VerifierMode.valueOf(mode)
        );
        boolean canWatch = verifyWatchUseCase.canWatch(command);

        return new ResponseEntity<>(canWatch, HttpStatus.OK);
    }

}
