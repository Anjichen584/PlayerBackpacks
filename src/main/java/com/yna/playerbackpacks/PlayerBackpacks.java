package com.yna.playerbackpacks;

import com.yna.playerbackpacks.command.BackpackCommand;
import com.yna.playerbackpacks.listener.BackpackCloseListener;
import com.yna.playerbackpacks.listener.GUIListener;
import com.yna.playerbackpacks.listener.JoinQuitListener;
import com.yna.playerbackpacks.util.DataHandler;
import com.yna.playerbackpacks.util.LicenseValidator;
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

        // 在启动时验证许可证
        if (LicenseValidator.validateLicense()) {
            // 许可证验证成功，继续启动插件
            getLogger().info("许可证验证通过，插件已启动！");
        } else {
            // 许可证验证失败，禁用插件
            getLogger().warning("许可证验证失败，插件已禁用！");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerBackpacks 插件已卸载！");
    }
}
