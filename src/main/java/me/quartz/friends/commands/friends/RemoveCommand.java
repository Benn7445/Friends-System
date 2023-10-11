package me.quartz.friends.commands.friends;

import me.quartz.friends.Friends;
import me.quartz.friends.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveCommand {

    public RemoveCommand(CommandSender commandSender, String[] strings) {
        if (strings[0].equalsIgnoreCase("remove")) {
            if(strings.length > 1) {
                Player player = (Player) commandSender;
                Profile profile = Friends.getInstance().getProfileManager().getProfile(player.getUniqueId());
                Profile targetProfile = Friends.getInstance().getProfileManager().getProfile(strings[1]);
                if(targetProfile != null) {
                    if(profile.isFriends(targetProfile.getUuid())) {
                        profile.removeFriend(targetProfile.getUuid());
                        targetProfile.removeFriend(profile.getUuid());
                        player.sendMessage(ChatColor.GREEN + "You removed " + targetProfile.getName() + " as a friend.");
                    } else player.sendMessage(ChatColor.RED + "You are no friends with this player.");
                } else player.sendMessage(ChatColor.RED + "User has never been on the server.");
            } else commandSender.sendMessage(ChatColor.RED + "Usage: /f add <name>");
        }
    }
}
