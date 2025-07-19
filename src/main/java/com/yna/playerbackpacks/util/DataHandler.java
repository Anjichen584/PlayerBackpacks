package com.yna.playerbackpacks.util;

import com.yna.playerbackpacks.PlayerBackpacks;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataHandler {

    private static final Map<UUID, Integer> unlockedMap = new HashMap<>();
    private static final Map<UUID, Map<Integer, ItemStack[]>> backpackData = new HashMap<>();

    private static File dataFolder;

    public static void init(PlayerBackpacks plugin) {
        dataFolder = new File(plugin.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();
    }

    public static int getUnlocked(Player player) {
        return unlockedMap.getOrDefault(player.getUniqueId(), 0);
    }

    public static void setUnlocked(Player player, int count) {
        unlockedMap.put(player.getUniqueId(), count);
    }

    public static ItemStack[] getBackpack(Player player, int id) {
        return backpackData
                .computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .getOrDefault(id, new ItemStack[54]);
    }

    public static void setBackpack(Player player, int id, ItemStack[] contents) {
        backpackData
                .computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .put(id, contents);
    }

    public static void load(Player player) {
        File file = new File(dataFolder, player.getUniqueId() + ".yml");
        if (!file.exists()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        int unlocked = config.getInt("unlocked", 0);
        unlockedMap.put(player.getUniqueId(), unlocked);

        Map<Integer, ItemStack[]> backpacks = new HashMap<>();
        if (config.isConfigurationSection("backpacks")) {
            for (String key : config.getConfigurationSection("backpacks").getKeys(false)) {
                int id = Integer.parseInt(key);
                List<?> raw = config.getList("backpacks." + key);
                if (raw != null) {
                    ItemStack[] contents = raw.toArray(new ItemStack[0]);
                    backpacks.put(id, contents);
                }
            }
        }

        backpackData.put(player.getUniqueId(), backpacks);
    }

    public static void save(Player player) {
        File file = new File(dataFolder, player.getUniqueId() + ".yml");
        YamlConfiguration config = new YamlConfiguration();

        config.set("unlocked", getUnlocked(player));
        Map<Integer, ItemStack[]> contentsMap = backpackData.getOrDefault(player.getUniqueId(), new HashMap<>());
        for (Map.Entry<Integer, ItemStack[]> entry : contentsMap.entrySet()) {
            config.set("backpacks." + entry.getKey(), entry.getValue());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().warning("[PlayerBackpacks] 保存玩家数据失败: " + player.getName());
            e.printStackTrace();
        }
    }
}
