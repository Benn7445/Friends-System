package me.quartz.friends;

import me.quartz.friends.bungee.BungeeManager;
import me.quartz.friends.commands.FriendsCommand;
import me.quartz.friends.commands.MessageCommand;
import me.quartz.friends.commands.ReplyCommand;
import me.quartz.friends.listeners.InventoryClickListener;
import me.quartz.friends.listeners.PlayerJoinListener;
import me.quartz.friends.listeners.PlayerQuitListener;
import me.quartz.friends.mysql.MySQLManager;
import me.quartz.friends.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Friends extends JavaPlugin {

    private static Friends instance;
    private MySQLManager mySQLManager;
    private ProfileManager profileManager;
    private BungeeManager bungeeManager;

    public static Friends getInstance() {
        return instance;
    }

    public MySQLManager getMySQLManager() {
        return mySQLManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public BungeeManager getBungeeManager() {
        return bungeeManager;
    }

    @Override
    public void onEnable() {
        registerManager();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerManager() {
        instance = this;
        mySQLManager = new MySQLManager();
        profileManager = new ProfileManager();
        bungeeManager = new BungeeManager();
    }

    private void registerCommands() {
        getCommand("friends").setExecutor(new FriendsCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }
}
