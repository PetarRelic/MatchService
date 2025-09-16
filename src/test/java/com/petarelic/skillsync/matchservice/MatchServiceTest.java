// src/test/java/com/petarelic/skillsync/matchservice/MatchServiceTest.java
package com.petarelic.skillsync.matchservice;

import com.petarelic.skillsync.matchservice.client.ProfileClient;
import com.petarelic.skillsync.matchservice.dto.UserProfileDTO;
import com.petarelic.skillsync.matchservice.service.MatchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @Mock
    private ProfileClient profileClient;

    @InjectMocks
    private MatchService matchService;

    @Test
    void shouldReturnTopMatchesSortedByScore() {
        UserProfileDTO user = new UserProfileDTO(1L, "Petar", "test@mail.com", "Loves architecture", "test-url");
        UserProfileDTO match1 = new UserProfileDTO(2L, "Ana", "test@mail.com", "Interested in architecture", "test-url");
        UserProfileDTO match2 = new UserProfileDTO(3L, "Marko", "test@mail.com", "No bio", "test-url");
        UserProfileDTO match3 = new UserProfileDTO(4L, "Jelena", "test@mail.com", "architecture enthusiast", "test-url");
        UserProfileDTO match4 = new UserProfileDTO(5L, "Ivan", "test@mail.com", "architecture", "test-url");
        UserProfileDTO match5 = new UserProfileDTO(6L, "Sara", "test@mail.com", "architecture and design", "test-url");
        UserProfileDTO match6 = new UserProfileDTO(7L, "Luka", "test@mail.com", "sports fan", "test-url");

        List<UserProfileDTO> allProfiles = List.of(user, match1, match2, match3, match4, match5, match6);

        when(profileClient.getProfiles()).thenReturn(allProfiles);

        List<UserProfileDTO> topMatches = matchService.getTopMatches(1L);

        assertEquals(5, topMatches.size());
        assertFalse(topMatches.contains(user));

        assertEquals(match1.getId(), topMatches.get(0).getId());
        assertEquals(match3.getId(), topMatches.get(1).getId());
        assertEquals(match4.getId(), topMatches.get(2).getId());
        assertEquals(match5.getId(), topMatches.get(3).getId());
        assertEquals(match2.getId(), topMatches.get(4).getId());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(profileClient.getProfiles()).thenReturn(List.of());

        assertThrows(RuntimeException.class, () -> matchService.getTopMatches(99L));
    }

    @Test
    void shouldReturnEmptyListWhenOnlyUserExists() {
        UserProfileDTO user = new UserProfileDTO(1L, "Petar", "test@mail.com", "Loves architecture", "test-url");
        when(profileClient.getProfiles()).thenReturn(List.of(user));

        List<UserProfileDTO> result = matchService.getTopMatches(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldHandleNullBiosInScore() {
        UserProfileDTO user = new UserProfileDTO(1L, "Petar", "test@mail.com", null, "test-url");
        UserProfileDTO match = new UserProfileDTO(2L, "Ana", "test@mail.com", null, "test-url");

        try {
            var method = MatchService.class.getDeclaredMethod("score", UserProfileDTO.class, UserProfileDTO.class);
            method.setAccessible(true);
            int score = (int) method.invoke(matchService, user, match);
            assertEquals(0, score);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    void shouldReturnZeroScoreWhenNoKeyword() {
        UserProfileDTO user = new UserProfileDTO(1L, "Petar", "test@mail.com", "No keyword here", "test-url");
        UserProfileDTO match = new UserProfileDTO(2L, "Ana", "test@mail.com", "Also no keyword", "test-url");

        try {
            var method = MatchService.class.getDeclaredMethod("score", UserProfileDTO.class, UserProfileDTO.class);
            method.setAccessible(true);
            int score = (int) method.invoke(matchService, user, match);
            assertEquals(0, score);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }

    @Test
    void shouldReturnScoreWhenBothBiosContainKeyword() {
        UserProfileDTO user = new UserProfileDTO(1L, "Petar", "test@mail.com", "Loves architecture", "test-url");
        UserProfileDTO match = new UserProfileDTO(2L, "Ana", "test@mail.com", "Interested in architecture", "test-url");

        try {
            var method = MatchService.class.getDeclaredMethod("score", UserProfileDTO.class, UserProfileDTO.class);
            method.setAccessible(true);
            int score = (int) method.invoke(matchService, user, match);
            assertEquals(10, score);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }
    }
}
