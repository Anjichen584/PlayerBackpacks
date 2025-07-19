package com.yna.playerbackpacks.util;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PointsUtil {

    private static PlayerPointsAPI api;

    public static void setup() {
        PlayerPoints plugin = (PlayerPoints) Bukkit.getPluginManager().getPlugin("PlayerPoints");
        if (plugin != null && plugin.isEnabled()) {
            api = plugin.getAPI();
        }
    }

    public static boolean take(Player player, int amount) {
        if (api == null) return false;
        int current = api.look(player.getUniqueId());
        if (current >= amount) {
            api.take(player.getUniqueId(), amount);
            return true;
        }
        return false;
    }

    public static void give(Player player, int amount) {
        if (api != null) {
            api.give(player.getUniqueId(), amount);
        }
    }

    public static int get(Player player) {
        return api != null ? api.look(player.getUniqueId()) : 0;
    }
}
