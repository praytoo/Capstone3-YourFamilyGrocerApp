package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Profile;
import org.yearup.service.ProfileService;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfileController {
    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<Profile> getByUserId(@PathVariable Integer userId){
        Profile profile = profileService.getByUserId(userId);
        if (profile == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    @PutMapping("{userId}")
    public void updateProfile(@PathVariable Integer userId, @RequestBody Profile profile){
        profileService.updateProfile(userId, profile);
    }
}
