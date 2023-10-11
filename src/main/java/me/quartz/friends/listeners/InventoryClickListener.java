package me.quartz.friends.listeners;

import me.quartz.friends.Friends;
import me.quartz.friends.profile.Profile;
import me.quartz.friends.utils.ItemUtil;
import me.quartz.friends.utils.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryClickListener implements Listener {

    public static List<UUID> onlineFriendsOnly = new ArrayList<>();

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if(event.getView() != null && event.getView().getTitle().equals(ChatColor.DARK_GRAY + "Friends")) {
            ItemStack itemStack = event.getCurrentItem();
            if(itemStack != null && event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta != null && itemMeta.getDisplayName().equalsIgnoreCase(ChatColor.GRAY + "Online Friends Only")) {
                    if(onlineFriendsOnly.contains(player.getUniqueId())) onlineFriendsOnly.remove(player.getUniqueId());
                    else onlineFriendsOnly.add(player.getUniqueId());
                    Profile profile = Friends.getInstance().getProfileManager().getProfile(event.getWhoClicked().getUniqueId());
                    Inventory inventory = event.getClickedInventory();
                    for (int i = 0; i < 35; i++) inventory.setItem(18+i, null);
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
                } else if(itemMeta != null && itemStack.getType() == Material.SKULL_ITEM) {
                    String name = ChatColor.stripColor(itemMeta.getDisplayName());
                    Inventory inventory = Bukkit.createInventory(player, 27, ChatColor.DARK_GRAY + "Remove " + name);

                    inventory.setItem(12, ItemUtil.createItemStack(Material.WOOL, (short) 14, ChatColor.RED + "Remove " + name));
                    inventory.setItem(14, ItemUtil.createItemStack(Material.WOOL, (short) 13, ChatColor.GREEN + "Keep " + name));

                    player.openInventory(inventory);
                }
            }
            event.setCancelled(true);
        } else if(event.getView() != null && event.getView().getTitle().startsWith(ChatColor.DARK_GRAY + "Remove ")) {
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                String name = ChatColor.stripColor(event.getView().getTitle().replace(ChatColor.DARK_GRAY + "Remove ", ""));
                if (event.getSlot() == 12) {
                    Profile profile = Friends.getInstance().getProfileManager().getProfile(player.getUniqueId());
                    Profile friend = Friends.getInstance().getProfileManager().getProfile(name);
                    if(friend != null) {
                        profile.removeFriend(friend.getUuid());
                        friend.removeFriend(profile.getUuid());
                    }
                    player.closeInventory();
                    player.sendMessage(ChatColor.GREEN + "You removed " + name + " as a friend.");
                } else if (event.getSlot() == 14) {
                    player.closeInventory();
                    player.performCommand("f list");
                }
            }
            event.setCancelled(true);
        }
    }
}
