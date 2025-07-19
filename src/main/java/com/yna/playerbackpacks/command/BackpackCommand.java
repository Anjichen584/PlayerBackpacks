package com.yna.playerbackpacks.command;

import com.yna.playerbackpacks.gui.BackpackMainGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackpackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("只能玩家使用此命令！");
            return true;
        }

        BackpackMainGUI.open(player, 1);
        return true;
    }
}
