package com.sangmin.stopstreamingvideo.watchverifier.adapter.inbound;

import com.sangmin.stopstreamingvideo.watchverifier.adapter.inbound.dto.PropertyRequest;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.RegisterWatchVerifierUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.VerifyWatchUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.VerifierMode;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        var command = new VerifyWatchUseCase.UserWatchCommand(
            UUID.fromString(userId),
            videoId,
            Provider.valueOf(provider),
            VerifierMode.valueOf(mode)
        );
        boolean canWatch = verifyWatchUseCase.canWatch(command);

        return new ResponseEntity<>(canWatch, HttpStatus.OK);
    }

    @PostMapping("/v1/watch-verifier/verify-watch")
    ResponseEntity<AnonWatchResponse> verifyWatch(@RequestBody AnonWatchRequest req) {
        boolean canWatch = verifyWatchUseCase.canWatch(req.toCommand());
        return new ResponseEntity<>(new AnonWatchResponse(canWatch), HttpStatus.OK);
    }

    record AnonWatchRequest(
        List<PropertyRequest> properties,
        String videoId,
        String provider,
        String mode
    ) {
        private VerifyWatchUseCase.AnonWatchCommand toCommand() {
            List<Property> properties = this.properties().stream()
                .map(PropertyRequest::toProperty)
                .toList();

            return new VerifyWatchUseCase.AnonWatchCommand(
                properties,
                videoId(),
                Provider.valueOf(provider()),
                VerifierMode.valueOf(mode())
            );
        }
    }

    private record AnonWatchResponse(boolean canWatch) {
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
            req.property().toProperty()
        );
        registerWatchVerifierUseCase.addWatchFilter(command);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private record FilterRequest(String provider, PropertyRequest property) {
    }

}
