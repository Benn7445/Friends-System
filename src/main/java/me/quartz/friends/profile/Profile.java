package me.quartz.friends.profile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Profile {

    private final UUID uuid;
    private final List<UUID> invites;
    private final List<UUID> sent;
    private final List<UUID> friends;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.invites = new ArrayList<>();
        this.sent = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public Profile(UUID uuid, List<UUID> invites, List<UUID> sent, List<UUID> friends) {
        this.uuid = uuid;
        this.invites = invites;
        this.sent = sent;
        this.friends = friends;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean hasInvited(UUID uuid) {
        return invites.contains(uuid);
    }

    public boolean hasSent(UUID uuid) {
        return sent.contains(uuid);
    }

    public List<UUID> getFriends() {
        return friends;
    }
}
