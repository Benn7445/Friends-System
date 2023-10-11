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

public class AddCommand {

    public AddCommand(CommandSender commandSender, String[] strings) {
        if (strings[0].equalsIgnoreCase("add")) {
            if(strings.length > 1) {
                Player player = (Player) commandSender;
                if(!player.getName().equalsIgnoreCase(strings[1])) {
                    Profile profile = Friends.getInstance().getProfileManager().getProfile(player.getUniqueId());
                    Profile targetProfile = Friends.getInstance().getProfileManager().getProfile(strings[1]);
                    if(targetProfile != null) {
                        if(!targetProfile.hasInvite(profile.getUuid())) {
                            if(!targetProfile.isFriends(profile.getUuid())) {
                                targetProfile.receiveInvite(profile.getUuid());
                                player.sendMessage(ChatColor.GREEN + "You've sent " + targetProfile.getName() + " a friend request.");
                                OfflinePlayer target = Bukkit.getOfflinePlayer(targetProfile.getUuid());
                                if(target != null && target.isOnline() && target.getPlayer() != null) {
                                    target.getPlayer().sendMessage(ChatColor.GREEN + "You have received a friend request from " + player.getName() + "!");
                                    TextComponent acceptComponent = new TextComponent(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "ACCEPT");
                                    acceptComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.GREEN + "Accept " + player.getName())));
                                    acceptComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f accept " + player.getName()));
                                    acceptComponent.addExtra(ChatColor.GRAY.toString() + ChatColor.BOLD + " : ");
                                    TextComponent denyComponent = new TextComponent(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "DENY");
                                    denyComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatColor.RED + "Deny " + player.getName())));
                                    denyComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f deny " + player.getName()));
                                    acceptComponent.addExtra(denyComponent);
                                    target.getPlayer().spigot().sendMessage(acceptComponent);
                                } else {
                                    Friends.getInstance().getBungeeManager().sendFriendRequest(player, targetProfile.getName());
                                }
                            } else player.sendMessage(ChatColor.RED + "You are already friends with this player.");
                        } else player.sendMessage(ChatColor.RED + "You already sent a friends request to this player.");
                    } else player.sendMessage(ChatColor.RED + "User has never been on the server.");
                } else player.sendMessage(ChatColor.RED + "You cannot add yourself.");
            } else commandSender.sendMessage(ChatColor.RED + "Usage: /f add <name>");
        }
    }
}
