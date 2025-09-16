package com.petarelic.skillsync.matchservice.resource;

import com.petarelic.skillsync.matchservice.dto.UserProfileDTO;
import com.petarelic.skillsync.matchservice.service.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public List<UserProfileDTO> getMatches(@RequestParam Long userId) {
        return matchService.getTopMatches(userId);
    }
}
