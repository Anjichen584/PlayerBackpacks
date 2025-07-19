package com.yna.playerbackpacks.listener;

import com.yna.playerbackpacks.gui.BackpackMainGUI;
import com.yna.playerbackpacks.gui.BackpackPageGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        Inventory inv = event.getInventory();

        String title = event.getView().getTitle();
        if (title.equals("§8玩家背包管理")) {
            event.setCancelled(true);
            BackpackMainGUI.handleClick(player, event.getRawSlot(), event.getCurrentItem());
        }

        if (title.startsWith("§9背包 #")) {
            event.setCancelled(false); // 背包页允许操作
        }

    }
}
