package com.yna.playerbackpacks.listener;

import com.yna.playerbackpacks.util.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class BackpackCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        Inventory inventory = event.getInventory();
        String title = event.getView().getTitle();

        if (!title.startsWith("§9背包 #")) return;

        int backpackIndex = -1;
        try {
            backpackIndex = Integer.parseInt(title.replaceAll("§[0-9a-fk-or]", "").replace("背包 #", "")) - 1;
        } catch (NumberFormatException ignored) {}

        if (backpackIndex >= 0) {
            DataHandler.setBackpack(player, backpackIndex, inventory.getContents());
            DataHandler.save(player);
            Bukkit.getLogger().info("已保存玩家 " + player.getName() + " 的背包 #" + (backpackIndex + 1));
        }
    }
}
