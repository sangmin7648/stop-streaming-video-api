package com.sangmin.stopstreamingvideo.watchverifier.adapter.inbound;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.RegisterWatchVerifierUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.VerifyWatchUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.VerifierMode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
class WatchVerifierRestController {

    private final VerifyWatchUseCase verifyWatchUseCase;
    private final RegisterWatchVerifierUseCase registerWatchVerifierUseCase;

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

    @PostMapping("/v1/watch-verifier/user/{userId}/{mode}/add-watch-filter")
    ResponseEntity<Void> addWatchFilter(
            @PathVariable String userId,
            @PathVariable String mode,
            @RequestBody FilterRequest req
    ) {
        var command = new RegisterWatchVerifierUseCase.AddFilterCommand(
                UUID.fromString(userId),
                VerifierMode.valueOf(mode),
                Provider.valueOf(req.provider()),
                req.toProperty()
        );
        registerWatchVerifierUseCase.addWatchFilter(command);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private record FilterRequest(
            String provider,
            Type type,
            String value
    ) {
        enum Type {
            CATEGORY
        }

        Property toProperty() {
            return switch (type) {
                case CATEGORY -> new Property.Category(value);
            };
        }
    }
}
