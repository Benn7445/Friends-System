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

import java.util.HashMap;
import java.util.UUID;

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (strings.length > 0) {
                StringBuilder message = new StringBuilder();
                for (int i = 0; i < strings.length; i++) message.append(strings[i]).append(" ");
                if (MessageCommand.lastMessage.containsKey(player.getUniqueId())) {
                    UUID uuid = MessageCommand.lastMessage.get(player.getUniqueId());
                    Profile target = Friends.getInstance().getProfileManager().getProfile(uuid);
                    if (target != null && target.isFriends(player.getUniqueId()) && target.getServer() != null && !target.getServer().isEmpty()) {
                        MessageCommand.lastMessage.put(player.getUniqueId(), target.getUuid());
                        MessageCommand.lastMessage.put(target.getUuid(), player.getUniqueId());
                        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(target.getName());
                        player.sendMessage(ChatColor.GRAY + "(me -> " + target.getName() + ") " + message);
                        if (targetPlayer != null && targetPlayer.isOnline() && targetPlayer.getPlayer() != null)
                            targetPlayer.getPlayer().sendMessage(ChatColor.GRAY + "(" + target.getName() + " -> me) " + message);
                        else
                            Friends.getInstance().getBungeeManager().messageFriend(player, target.getName(), message.toString());
                    } else player.sendMessage(ChatColor.RED + "This player is not online.");
                } else player.sendMessage(ChatColor.RED + "You didn't message any players");
            } else player.sendMessage(ChatColor.RED + "Usage: /reply <message>");
        }
        return true;
    }
}
