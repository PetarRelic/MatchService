package com.petarelic.skillsync.matchservice.service;

import com.petarelic.skillsync.matchservice.client.ProfileClient;
import com.petarelic.skillsync.matchservice.dto.UserProfileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProfileClient profileClient;

    public MatchService(ProfileClient profileClient) {
        this.profileClient = profileClient;
    }

    public List<UserProfileDTO> getTopMatches(Long userId) {
        logger.info("Getting the top matches for user {}", userId);

        List<UserProfileDTO> allProfiles = profileClient.getProfiles();

        UserProfileDTO targetUser = allProfiles.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserProfileDTO> scoredMatches = allProfiles.stream()
                .filter(p -> !p.getId().equals(userId))
                .filter(p -> score(targetUser, p) == 10)
                .sorted((a, b) -> {
                    return Long.compare(b.getId(), a.getId());
                })
                .limit(5)
                .collect(Collectors.toList());

        return scoredMatches;
    }

    private int score(UserProfileDTO a, UserProfileDTO b) {
        int score = 0;
        String keyword = "architecture";

        if (a.getBio() != null && b.getBio() != null &&
                a.getBio().toLowerCase().contains(keyword) &&
                b.getBio().toLowerCase().contains(keyword)) {
            score += 10;
        }

        return score;
    }
}
