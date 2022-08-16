package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.RegisterWatchVerifierUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.WatchVerifierRepository;
import com.sangmin.stopstreamingvideo.watchverifier.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterWatchVerfierUseCaseImplTest {

    private RegisterWatchVerfierUseCaseImpl sut;
    private WatchVerifierRepository repository;

    @BeforeEach
    void setup() {
        repository = new DummyWatchVerifierRepository();
        sut = new RegisterWatchVerfierUseCaseImpl(repository);
    }

    @Test
    void register_watch_verifier_for_new_user() {
        var newUserId = UUID.randomUUID();

        UUID verifierId = sut.registerWatchVerifier(newUserId);

        WatchVerifier watchVerifier = repository.getByUserId(newUserId);
        assertEquals(newUserId, watchVerifier.userId());
    }

    @Test
    void user_id_must_be_given_when_registering_verifier() {
        assertThatThrownBy(() -> sut.registerWatchVerifier(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void register_watch_filter_for_mode() {
        // given
        var userId = UUID.randomUUID();
        sut.registerWatchVerifier(userId);

        var mode = VerifierMode.WHITELIST;
        var provider = Provider.YOUTUBE;
        var property = new Property.Category("test");
        var command = new RegisterWatchVerifierUseCase.AddFilterCommand(userId, mode, provider, property);

        // when
        sut.addWatchFilter(command);

        // then
        WatchVerifier watchVerifier = repository.getByUserId(userId);
        var expectedFilter = WatchVerifierTestHelper.createWatchFilter(provider, property);
        assertTrue(WatchVerifierTestHelper.getWatchFilters(mode, watchVerifier).contains(expectedFilter));
    }

    @Test
    void command_must_be_given_when_registering_filter() {
        assertThatThrownBy(() -> sut.addWatchFilter(null))
                .isInstanceOf(NullPointerException.class);
    }

    static class DummyWatchVerifierRepository implements WatchVerifierRepository {

        private final Map<UUID, WatchVerifier> watchVerifierMap = new HashMap<>();

        @Override
        public WatchVerifier getByUserId(UUID userId) {
            return watchVerifierMap.values().stream()
                    .filter(wv -> wv.userId().equals(userId))
                    .findAny()
                    .orElseThrow();
        }

        @Override
        public WatchVerifier save(WatchVerifier watchVerifier) {
            watchVerifierMap.put(watchVerifier.id(), watchVerifier);
            return watchVerifier;
        }

        @Override
        public void delete(WatchVerifier watchVerifier) {
            watchVerifierMap.remove(watchVerifier.id());
        }

    }
}