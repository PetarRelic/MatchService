package com.petarelic.skillsync.matchservice.client;

import com.petarelic.skillsync.matchservice.dto.UserProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "profile-service", url = "http://localhost:9090/profiles")
public interface ProfileClient {

    @GetMapping
    List<UserProfileDTO> getProfiles();
}
