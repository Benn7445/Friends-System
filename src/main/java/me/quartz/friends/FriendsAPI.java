package me.quartz.friends;

import me.quartz.friends.profile.Profile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FriendsAPI {

    public boolean isFriends(UUID player1, UUID player2) {
        Profile profile1 = Friends.getInstance().getProfileManager().getProfile(player1);
        return profile1 != null && profile1.isFriends(player2);
    }

    public boolean hasInvited(UUID player1, UUID player2) {
        Profile profile1 = Friends.getInstance().getProfileManager().getProfile(player1);
        return profile1 != null && profile1.hasInvite(player2);
    }

    public List<UUID> getFriends(UUID player) {
        Profile profile1 = Friends.getInstance().getProfileManager().getProfile(player);
        return profile1.getFriends();
    }

    public List<UUID> getInvites(UUID player) {
        Profile profile1 = Friends.getInstance().getProfileManager().getProfile(player);
        return profile1.getInvites();
    }

    public Date getLastOnline(UUID player) {
        Profile profile1 = Friends.getInstance().getProfileManager().getProfile(player);
        return profile1.getLastOnline();
    }

    public String getServer(UUID player) {
        Profile profile1 = Friends.getInstance().getProfileManager().getProfile(player);
        return profile1.getServer();
    }
}
