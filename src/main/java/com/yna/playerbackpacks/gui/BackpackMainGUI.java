package com.yna.playerbackpacks.gui;

import com.yna.playerbackpacks.util.DataHandler;
import com.yna.playerbackpacks.util.PointsUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BackpackMainGUI {

    public static final int BACKPACK_COST = 500; // 每个背包解锁花费点券
    public static final int MAX_BACKPACKS = 45;   // 最多支持 45 个背包（5 行 × 9）

    public static void open(Player player, int page) {
        Inventory gui = Bukkit.createInventory(null, 54, "§8玩家背包管理");

        int unlocked = DataHandler.getUnlocked(player);

        int startIndex = (page - 1) * 45;
        for (int i = 0; i < 45 && (startIndex + i) < MAX_BACKPACKS; i++) {
            int index = startIndex + i;

            ItemStack item;
            if (index < unlocked) {
                item = createItem(Material.CHEST, "§a背包 #" + (index + 1), "§7点击打开");
            } else {
                item = createItem(Material.WHITE_STAINED_GLASS_PANE,
                        "§c未解锁背包 #" + (index + 1),
                        "§7点击花费 §6500§7 点券解锁");
            }
            gui.setItem(i, item);
        }

        gui.setItem(49, createItem(Material.PAPER, "§e第 " + page + " 页"));
        gui.setItem(45, createItem(Material.ARROW, "§a上一页"));
        gui.setItem(53, createItem(Material.ARROW, "§a下一页"));

        for (int i = 45; i < 54; i++) {
            if (gui.getItem(i) == null) {
                gui.setItem(i, createItem(Material.GRAY_STAINED_GLASS_PANE, " "));
            }
        }

        player.openInventory(gui);
    }

    public static void handleClick(Player player, int slot, ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;

        int unlocked = DataHandler.getUnlocked(player);

        if (slot < 45) {
            int backpackId = slot;

            if (backpackId < unlocked) {
                BackpackPageGUI.open(player, backpackId);
            } else {
                if (PointsUtil.take(player, BACKPACK_COST)) {
                    int newCount = unlocked + 1;
                    DataHandler.setUnlocked(player, newCount);
                    DataHandler.save(player);

                    player.sendMessage("§a已解锁背包 #" + (backpackId + 1));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    open(player, 1);
                } else {
                    player.sendMessage("§c点券不足，解锁需要 §6500§c 点券！");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                }
            }
        } else if (slot == 45) {
            player.sendMessage("§e功能待完善：上一页");
        } else if (slot == 53) {
            player.sendMessage("§e功能待完善：下一页");
        }
    }

    private static ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
}
