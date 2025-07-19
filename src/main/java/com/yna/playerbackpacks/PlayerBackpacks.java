package com.yna.playerbackpacks;

import com.yna.playerbackpacks.command.BackpackCommand;
import com.yna.playerbackpacks.listener.BackpackCloseListener;
import com.yna.playerbackpacks.listener.GUIListener;
import com.yna.playerbackpacks.listener.JoinQuitListener;
import com.yna.playerbackpacks.util.DataHandler;
import com.yna.playerbackpacks.util.PointsUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerBackpacks extends JavaPlugin {

    @Override
    public void onEnable() {
        // 初始化点券系统
        PointsUtil.setup();

        // 初始化数据存储文件夹
        DataHandler.init(this);

        // 注册命令
        getCommand("backpack").setExecutor(new BackpackCommand());

        // 注册监听器
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getServer().getPluginManager().registerEvents(new BackpackCloseListener(), this);
        getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);

        getLogger().info("PlayerBackpacks 插件已启用！");
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerBackpacks 插件已卸载！");
    }
}
