package com.yna.playerbackpacks.util;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class BackpackManager {

    private final File dataFolder;

    public BackpackManager(File pluginDataFolder) {
        this.dataFolder = new File(pluginDataFolder, "data");
        if (!dataFolder.exists()) dataFolder.mkdirs();
    }

    public Inventory getBackpack(UUID uuid, int index) {
        File file = new File(dataFolder, uuid.toString() + ".yml");
        if (!file.exists()) return Bukkit.createInventory(null, 54, "§b背包 " + index);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String path = "backpack." + index;
        Inventory inv = Bukkit.createInventory(null, 54, "§b背包 " + index);

        for (int i = 0; i < 54; i++) {
            if (config.contains(path + "." + i)) {
                ItemStack item = config.getItemStack(path + "." + i);
                if (item != null) {
                    inv.setItem(i, item);
                }
            }
        }
        return inv;
    }

    public void saveBackpack(UUID uuid, int index, Inventory inventory) {
        File file = new File(dataFolder, uuid.toString() + ".yml");
        YamlConfiguration config = file.exists() ? YamlConfiguration.loadConfiguration(file) : new YamlConfiguration();

        String path = "backpack." + index;
        for (int i = 0; i < 54; i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null) {
                config.set(path + "." + i, item);
            } else {
                config.set(path + "." + i, null);
            }
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
