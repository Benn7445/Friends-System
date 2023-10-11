package me.quartz.friends.commands.friends;

import me.quartz.friends.Friends;
import me.quartz.friends.profile.Profile;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DenyCommand {

    public DenyCommand(CommandSender commandSender, String[] strings) {
        if (strings[0].equalsIgnoreCase("deny")) {
            if (strings.length > 1) {
                Player player = (Player) commandSender;
                if (!player.getName().equalsIgnoreCase(strings[1])) {
                    Profile profile = Friends.getInstance().getProfileManager().getProfile(player.getUniqueId());
                    Profile targetProfile = Friends.getInstance().getProfileManager().getProfile(strings[1]);
                    if (targetProfile != null) {
                        if (targetProfile.hasInvite(profile.getUuid())) {
                            profile.denyInvite(targetProfile.getUuid());
                            player.sendMessage(ChatColor.GREEN + "You've denied " + targetProfile.getName() + "'s friend request.");
                        } else player.sendMessage(ChatColor.RED + "This player didn't sent a friend request.");
                    } else player.sendMessage(ChatColor.RED + "User has never been on the server.");
                } else player.sendMessage(ChatColor.RED + "You cannot add yourself.");
            } else commandSender.sendMessage(ChatColor.RED + "Usage: /f add <name>");
        }
    }
}
