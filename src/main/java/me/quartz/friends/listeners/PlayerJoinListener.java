package me.quartz.friends.listeners;

import me.quartz.friends.Friends;
import me.quartz.friends.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Friends.getInstance().getProfileManager().getProfile(player.getUniqueId());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Friends.getInstance(), () -> {
            Friends.getInstance().getBungeeManager().fetchServer(player.getName());
        }, 20L);
    }
}
