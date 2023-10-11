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

public class AcceptCommand {

    public AcceptCommand(CommandSender commandSender, String[] strings) {
        if (strings[0].equalsIgnoreCase("accept")) {
            if(strings.length > 1) {
                Player player = (Player) commandSender;
                Profile profile = Friends.getInstance().getProfileManager().getProfile(player.getUniqueId());
                Profile targetProfile = Friends.getInstance().getProfileManager().getProfile(strings[1]);
                if(targetProfile != null) {
                    if(profile.hasInvite(targetProfile.getUuid())) {
                        profile.acceptInvite(targetProfile.getUuid());
                        targetProfile.acceptInvite(profile.getUuid());
                        player.sendMessage(ChatColor.GREEN + "You accepted " + targetProfile.getName() + "'s friend request.");
                        OfflinePlayer target = Bukkit.getOfflinePlayer(targetProfile.getUuid());
                        if(target != null && target.isOnline() && target.getPlayer() != null) {
                            target.getPlayer().sendMessage(ChatColor.GREEN + player.getName() + " accepted your friend request!");
                        } else {
                            Friends.getInstance().getBungeeManager().acceptFriendRequest(player, targetProfile.getName());
                        }
                    } else player.sendMessage(ChatColor.RED + "This player didn't invite you.");
                } else player.sendMessage(ChatColor.RED + "User has never been on the server.");
            } else commandSender.sendMessage(ChatColor.RED + "Usage: /f add <name>");
        }
    }
}
