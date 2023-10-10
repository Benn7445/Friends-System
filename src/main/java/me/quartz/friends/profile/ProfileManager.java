package me.quartz.friends.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProfileManager {

    private List<Profile> profiles = new ArrayList<>();

    public Profile getProfile(UUID uuid) {
        Profile profile;
        List<Profile> filtered = profiles.stream().filter(profile1 -> profile1.getUuid().toString().equals(uuid.toString())).collect(Collectors.toList());
        if(!filtered.isEmpty()) profile = profiles.get(0);
        else {
            profile = new Profile(uuid);
            profiles.add(profile);
        }
        return profile;
    }
}
