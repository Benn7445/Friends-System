package me.quartz.friends.profile;

import me.quartz.friends.Friends;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Profile {

    private final UUID uuid;
    private String name;
    private final List<UUID> invites;
    private final List<UUID> friends;
    private Date lastOnline;
    private String server;

    public Profile(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.invites = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.lastOnline = new Date();
        this.server = "N/A";
    }

    public Profile(UUID uuid, String name, List<UUID> invites, List<UUID> friends, String server, Date lastOnline) {
        this.uuid = uuid;
        this.name = name;
        this.invites = invites;
        this.friends = friends;
        this.lastOnline = lastOnline;
        this.server = server;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public List<UUID> getInvites() {
        return invites;
    }

    public boolean hasInvite(UUID uuid) {
        return invites.contains(uuid);
    }

    public void receiveInvite(UUID uuid) {
        invites.add(uuid);
        Friends.getInstance().getMySQLManager().saveLocalPlayer(this);
    }

    public void acceptInvite(UUID uuid) {
        invites.remove(uuid);
        friends.add(uuid);
        Friends.getInstance().getMySQLManager().saveLocalPlayer(this);
    }

    public void denyInvite(UUID uuid) {
        invites.remove(uuid);
        Friends.getInstance().getMySQLManager().saveLocalPlayer(this);
    }

    public void removeFriend(UUID uuid) {
        friends.remove(uuid);
        Friends.getInstance().getMySQLManager().saveLocalPlayer(this);
    }

    public boolean isFriends(UUID uuid) {
        return friends.contains(uuid);
    }

    public List<UUID> getFriends() {
        return friends;
    }

    public Date getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline() {
        this.lastOnline = new Date();
        Friends.getInstance().getMySQLManager().saveLocalPlayer(this);
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
        Friends.getInstance().getMySQLManager().saveLocalPlayer(this);
    }
}
