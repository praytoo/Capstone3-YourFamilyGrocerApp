package org.yearup.data;


import org.springframework.stereotype.Component;
import org.yearup.models.Profile;

@Component
public interface ProfileDao
{
    Profile create(Profile profile);
    Profile getByUserId(Integer userId);
    void updateProfile(Integer userId, Profile profile);
}
