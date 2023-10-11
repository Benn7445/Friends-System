package me.quartz.friends.mysql;

import me.quartz.friends.profile.Profile;
import me.quartz.friends.utils.ListUtil;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MySQLManager {

    final String username = "root";
    final String password = "";
    final String url = "jdbc:mysql://" +
            "localhost" + ":" +
            "3306" + "/" +
            "minecraft";

    static Connection connection;

    public MySQLManager() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            createDatabaseTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disableMySQL() {
        try {
            if (connection!=null && !connection.isClosed()) {
                connection.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Profile fetchProfile(String username) {
        String sql = "SELECT * FROM profiles WHERE username=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                SimpleDateFormat formatter6=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                Profile profile = new Profile(
                        UUID.fromString(results.getString("uuid")),
                        username,
                        ListUtil.stringToList(results.getString("invites")),
                        ListUtil.stringToList(results.getString("friends")),
                        results.getString("server"),
                        formatter6.parse(results.getString("lastOnline"))
                );
                return profile;
            }
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Profile fetchProfile(UUID uuid) {
        String sql = "SELECT * FROM profiles WHERE uuid=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, uuid.toString());
            ResultSet results = stmt.executeQuery();
            if (results.next()) {
                SimpleDateFormat formatter6=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                Profile profile = new Profile(
                        uuid,
                        results.getString("username"),
                        ListUtil.stringToList(results.getString("invites")),
                        ListUtil.stringToList(results.getString("friends")),
                        results.getString("server"),
                        formatter6.parse(results.getString("lastOnline"))
                );
                return profile;
            }
        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void saveLocalPlayer(Profile player) {
        SimpleDateFormat formatter6=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        if(fetchProfile(player.getUuid()) == null) {
            String sql = "INSERT INTO profiles(uuid, username, invites, friends, server, lastOnline) VALUES (?, ?, ?, ?, ?, ?);";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, player.getUuid().toString());
                stmt.setString(2, player.getName());
                stmt.setString(3, ListUtil.listToString(player.getInvites()));
                stmt.setString(4, ListUtil.listToString(player.getFriends()));
                stmt.setString(5, player.getServer());
                stmt.setString(6, formatter6.format(player.getLastOnline()));
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            String sql = "UPDATE profiles SET username=?, invites=?, friends=?, server=?, lastOnline=? WHERE uuid=?";
            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, player.getName());
                stmt.setString(2, ListUtil.listToString(player.getInvites()));
                stmt.setString(3, ListUtil.listToString(player.getFriends()));
                stmt.setString(4, player.getServer());
                stmt.setString(5, formatter6.format(player.getLastOnline()));
                stmt.setString(6, player.getUuid().toString());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createDatabaseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS profiles(uuid varchar(64), username varchar(64), invites LONGTEXT, friends LONGTEXT, server varchar(64), lastOnline varchar(64));";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
