package me.quartz.friends.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.quartz.friends.Friends;
import me.quartz.friends.profile.Profile;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

public class BungeeManager implements PluginMessageListener {

    public BungeeManager() {
        Friends.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(Friends.getInstance(), "BungeeCord");
        Friends.getInstance().getServer().getMessenger().registerIncomingPluginChannel(Friends.getInstance(), "BungeeCord", this);
    }

    public void disableBungee() {
        Friends.getInstance().getServer().getMessenger().unregisterOutgoingPluginChannel(Friends.getInstance());
        Friends.getInstance().getServer().getMessenger().unregisterIncomingPluginChannel(Friends.getInstance());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("GetPlayerServer")) {
            String userName = in.readUTF();
            String serverName = in.readUTF();
            Profile profile = Friends.getInstance().getProfileManager().getProfile(userName);
            profile.setServer(serverName);
        }
        Bukkit.broadcastMessage(channel + ":" + subchannel);
    }

    public String fetchServer(String playerName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        try {
            out.writeUTF("GetPlayerServer");
            out.writeUTF(playerName);

            Bukkit.getServer().sendPluginMessage(Friends.getInstance(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String sendFriendRequest(Player player, String targetName) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();

            out.writeUTF("Message");
            out.writeUTF(targetName);

            out.writeUTF(ChatColor.GREEN + "You have received a friend request from " + player.getName() + "!");

            player.sendPluginMessage(Friends.getInstance(), "BungeeCord", out.toByteArray());

            ByteArrayDataOutput out2 = ByteStreams.newDataOutput();
            out2.writeUTF("MessageRaw");
            out2.writeUTF(targetName);

            out2.writeUTF("[\"\",{\"text\":\"ACCEPT\",\"bold\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/f accept " + player.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":[{\"text\":\"Accept " + player.getName() + "\",\"color\":\"green\"}]}},{\"text\":\" : \",\"bold\":true,\"color\":\"gray\"},{\"text\":\"DENY\",\"bold\":true,\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/f accept " + player.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":[{\"text\":\"Deny " + player.getName() + "\",\"color\":\"red\"}]}}]");

            player.sendPluginMessage(Friends.getInstance(), "BungeeCord", out2.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String acceptFriendRequest(Player player, String targetName) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();

            out.writeUTF("Message");
            out.writeUTF(targetName);

            out.writeUTF(ChatColor.GREEN + player.getName() + " accepted your friend request!");

            player.sendPluginMessage(Friends.getInstance(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String messageFriend(Player player, String targetName, String message) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();

            out.writeUTF("Message");
            out.writeUTF(targetName);

            out.writeUTF(ChatColor.GRAY + "(" + player.getName() + " -> me) " + message);

            player.sendPluginMessage(Friends.getInstance(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
