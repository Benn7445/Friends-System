package me.quartz.friends.commands;

import me.quartz.friends.Friends;
import me.quartz.friends.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MessageCommand implements CommandExecutor {

    public static HashMap<UUID, UUID> lastMessage = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length > 1) {
                StringBuilder message = new StringBuilder();
                for(int i = 1; i < strings.length; i++) message.append(strings[i]).append(" ");
                Profile target = Friends.getInstance().getProfileManager().getProfile(strings[0]);
                if(target != null && target.isFriends(player.getUniqueId()) && target.getServer() != null && !target.getServer().isEmpty()) {
                    lastMessage.put(player.getUniqueId(), target.getUuid());
                    lastMessage.put(target.getUuid(), player.getUniqueId());
                    OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(target.getName());
                    player.sendMessage(ChatColor.GRAY + "(me -> " + target.getName() + ") " + message);
                    if(targetPlayer != null && targetPlayer.isOnline() && targetPlayer.getPlayer() != null)
                        targetPlayer.getPlayer().sendMessage(ChatColor.GRAY + "(" + target.getName() + " -> me) " + message);
                    else Friends.getInstance().getBungeeManager().messageFriend(player, target.getName(), message.toString());
                } else player.sendMessage(ChatColor.RED + "This player is not online.");
            } else player.sendMessage(ChatColor.RED + "Usage: /message <player> <message>");
        }
        return true;
    }
}
