package me.quartz.friends.profile;

import me.quartz.friends.Friends;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfileManager {

    private final List<Profile> profiles = new ArrayList<>();

    public void removeProfile(UUID uuid) {
        profiles.removeIf(profile -> profile.getUuid().toString().equals(uuid.toString()));
    }

    public Profile getProfile(UUID uuid) {
        Profile profile = null;
        for(Profile profileLoop : profiles)
            if (profileLoop.getUuid().toString().equals(uuid.toString())) profile = profileLoop;
        if(profile == null) {
            profile = Friends.getInstance().getMySQLManager().fetchProfile(uuid);
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if(player.isOnline() && profile == null) {
            player = Bukkit.getPlayer(uuid);
            profile = new Profile(player.getUniqueId(), player.getName());
            Friends.getInstance().getMySQLManager().saveLocalPlayer(profile);
            profiles.add(profile);
        }
        return profile;
    }

    public Profile getProfile(String name) {
        Profile profile = null;
        for(Profile profileLoop : profiles)
            if (profileLoop.getName().equals(name)) profile = profileLoop;
        if(profile == null) {
            profile = Friends.getInstance().getMySQLManager().fetchProfile(name);
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(name);
        if(player.isOnline() && profile == null) {
            player = Bukkit.getPlayer(name);
            profile = new Profile(player.getUniqueId(), player.getName());
            Friends.getInstance().getMySQLManager().saveLocalPlayer(profile);
            profiles.add(profile);
        }
        return profile;
    }
}
