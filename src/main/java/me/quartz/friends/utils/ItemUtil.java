package me.quartz.friends.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class ItemUtil {
    public static ItemStack createItemStack(Material material, short data, String name, String... lore) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.setDurability(data);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta != null) {
            itemMeta.setDisplayName(name);
            itemMeta.setLore(Arrays.asList(lore));
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }
    public static ItemStack createPlayerSkull(String owner, String name, String... lore) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, owner.isEmpty() ? (short) SkullType.SKELETON.ordinal() : (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
        if(!owner.isEmpty()) meta.setOwner(owner);
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        skull.setItemMeta(meta);
        return skull;
    }
}
