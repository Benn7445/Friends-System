package me.quartz.friends.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListUtil {

    public static String listToString(List<UUID> list) {
        StringBuilder s = new StringBuilder();
        for(UUID uuid : list) {
            s.append(uuid.toString()).append(",");
        }
        if(s.length() > 2) return s.substring(0, s.length()-1);
        return "";
    }

    public static List<UUID> stringToList(String string) {
        List<UUID> list = new ArrayList<>();
        if(!string.isEmpty()){
            for(String s : string.split(",")) {
                list.add(UUID.fromString(s));
            }
        }
        return list;
    }
}
