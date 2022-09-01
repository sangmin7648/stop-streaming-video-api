package com.sangmin.stopstreamingvideo.watchverifier.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sangmin.stopstreamingvideo.watchverifier.application.port.inbound.RegisterWatchVerifierUseCase;
import com.sangmin.stopstreamingvideo.watchverifier.application.port.outbound.WatchVerifierRepository;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Property;
import com.sangmin.stopstreamingvideo.watchverifier.domain.Provider;
import com.sangmin.stopstreamingvideo.watchverifier.domain.VerifierMode;
import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifier;
import com.sangmin.stopstreamingvideo.watchverifier.domain.WatchVerifierTestHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.lang.NonNull;

class RegisterWatchVerifierUseCaseImplTest {

    private RegisterWatchVerifierUseCaseImpl sut;
    private DummyWatchVerifierRepository repository;

    @BeforeEach
    void setup() {
        repository = new DummyWatchVerifierRepository();
        sut = new RegisterWatchVerifierUseCaseImpl(repository);
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
    void add_watch_filter() {
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
        assertEquals(2, repository.saveCount());
    }

    @Test
    void command_must_be_given_when_registering_filter() {
        assertThatThrownBy(() -> sut.addWatchFilter(null))
            .isInstanceOf(NullPointerException.class);
    }

    static class DummyWatchVerifierRepository implements WatchVerifierRepository {

        private final Map<UUID, WatchVerifier> watchVerifierMap = new HashMap<>();
        private int saveCount = 0;

        @NonNull
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
            saveCount++;
            return watchVerifier;
        }

        @Override
        public void delete(WatchVerifier watchVerifier) {
            watchVerifierMap.remove(watchVerifier.id());
        }

        int saveCount() {
            return saveCount;
        }
    }
}