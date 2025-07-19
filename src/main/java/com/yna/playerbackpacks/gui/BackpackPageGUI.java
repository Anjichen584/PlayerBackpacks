package com.yna.playerbackpacks.gui;

import com.yna.playerbackpacks.util.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BackpackPageGUI {

    public static void open(Player player, int backpackId) {
        Inventory gui = Bukkit.createInventory(player, 54, "§9背包 #" + (backpackId + 1));
        gui.setContents(DataHandler.getBackpack(player, backpackId));
        player.openInventory(gui);
    }

    public static void save(Player player, int backpackId, Inventory inv) {
        DataHandler.setBackpack(player, backpackId, inv.getContents());
        DataHandler.save(player);
    }
}
