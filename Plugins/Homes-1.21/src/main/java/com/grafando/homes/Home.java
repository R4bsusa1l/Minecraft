package com.grafando.homes;

import com.grafando.homes.commands.DeleteSpecHome;
import com.grafando.homes.commands.GetHomeList;
import com.grafando.homes.commands.HomeCommands;
import com.grafando.homes.data.LocationStorage;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class Home extends JavaPlugin {
    private LocationStorage locStore = new LocationStorage();
    private final Connection locStoreConn = locStore.getConnection();

    @Override
    public void onEnable() {
        super.onEnable();
        this.getCommand("home").setExecutor(new HomeCommands(locStoreConn));
        this.getCommand("sethome").setExecutor(new HomeCommands(locStoreConn));
        this.getCommand("gethomelist").setExecutor(new GetHomeList(locStoreConn));
        this.getCommand("deletehome").setExecutor(new DeleteSpecHome(locStoreConn));
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[Home]:"+ ChatColor.LIGHT_PURPLE+" Plugin is enabled!");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        try {
            locStoreConn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[Home]:"+ ChatColor.LIGHT_PURPLE+" Plugin is disabled!");
    }
}
