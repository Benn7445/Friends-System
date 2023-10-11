package me.quartz.friends.listeners;

import me.quartz.friends.Friends;
import me.quartz.friends.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Profile profile = Friends.getInstance().getProfileManager().getProfile(player.getUniqueId());
        profile.setLastOnline();
        profile.setServer(null);
        Friends.getInstance().getProfileManager().removeProfile(profile.getUuid());
    }
}
