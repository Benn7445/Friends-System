package me.quartz.friends.commands;

import me.quartz.friends.commands.friends.*;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length > 0) {
                new ListCommand(commandSender, strings);
                new AddCommand(commandSender, strings);
                new AcceptCommand(commandSender, strings);
                new DenyCommand(commandSender, strings);
                new RemoveCommand(commandSender, strings);
                if (strings[0].equalsIgnoreCase("help")) helpMessage(commandSender);
            } else helpMessage(commandSender);
        }
        return true;
    }

    private void helpMessage(CommandSender commandSender) {
        commandSender.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-------------------");
        commandSender.sendMessage(ChatColor.YELLOW + "/f add <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Add a friend.");
        commandSender.sendMessage(ChatColor.YELLOW + "/f remove <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Remove a friend.");
        commandSender.sendMessage(ChatColor.YELLOW + "/f accept <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Accept a friend request.");
        commandSender.sendMessage(ChatColor.YELLOW + "/f deny <player>" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Accept a friend request.");
        commandSender.sendMessage(ChatColor.YELLOW + "/f list" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "List all of your friends.");
        commandSender.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "-------------------");
    }
}
