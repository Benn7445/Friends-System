package me.quartz.friends.commands.friends;

import me.quartz.friends.Friends;
import me.quartz.friends.listeners.InventoryClickListener;
import me.quartz.friends.profile.Profile;
import me.quartz.friends.utils.ItemUtil;
import me.quartz.friends.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class ListCommand {

    public ListCommand(CommandSender commandSender, String[] strings) {
        if (strings[0].equalsIgnoreCase("list")) {
            Player player = (Player) commandSender;
            Profile profile = Friends.getInstance().getProfileManager().getProfile(player.getUniqueId());

            Inventory inventory = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "Friends");
            inventory.setItem(4, ItemUtil.createItemStack(Material.RED_ROSE, (short) 0, ChatColor.GRAY + "Online Friends Only"));

            if(!profile.getFriends().isEmpty()) {
                int i = 0;
                for(UUID uuid : profile.getFriends()) {
                    Profile friend = Friends.getInstance().getProfileManager().getProfile(uuid);
                    if(friend != null && i <= 35) {
                        if (friend.getServer() != null && !friend.getServer().isEmpty())
                            inventory.setItem(18 + i, ItemUtil.createPlayerSkull(friend.getName(), ChatColor.WHITE.toString() + ChatColor.BOLD + friend.getName(), ChatColor.GRAY + "Status: " + ChatColor.GREEN + "Online", ChatColor.GRAY + "Currently in " + friend.getServer()));
                        else if(!InventoryClickListener.onlineFriendsOnly.contains(player.getUniqueId()))
                            inventory.setItem(18 + i, ItemUtil.createPlayerSkull("", ChatColor.WHITE.toString() + ChatColor.BOLD + friend.getName(), ChatColor.GRAY + "Status: " + ChatColor.RED + "Offline", ChatColor.GRAY + "Last seen " + TimeUtil.findDifference(friend.getLastOnline()) + " ago."));
                        i++;
                    }
                }
            } else inventory.setItem(22, ItemUtil.createItemStack(Material.BARRIER, (short) 0, ChatColor.RED + "No friends found."));

            player.openInventory(inventory);
        }
    }
}
