package me.quartz.friends;

import me.quartz.friends.profile.ProfileManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Friends extends JavaPlugin {

    private static Friends instance;
    private ProfileManager profileManager;

    public static Friends getInstance() {
        return instance;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() { }

    private void registerManager() {
        instance = this;
        profileManager = new ProfileManager();
    }
}
