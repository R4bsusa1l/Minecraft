package com.grafando.homes.commands;

import com.grafando.homes.data.LocationStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;

public class DeleteSpecHome implements CommandExecutor {
    private Player player;
    private LocationStorage locStore = new LocationStorage();
    private Connection connection;

    public DeleteSpecHome(Connection connectionInstance){
        this.connection = connectionInstance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        player = (Player) sender;
        if (sender instanceof Player){
            if (args[0].length() > 0){
                if (locStore.DeleteSpecificHome(player, args[0], connection)){
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&5>&d Home was deleted successfully..."));
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&5>&d Home was &lnot&r&d deleted, pls try again:"));
                }
            }else {
                if (locStore.DeleteSpecificHome(player, "home", connection)){
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&5>&dHome was deleted successfully..."));
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&5>&d Home was &lnot&r&d deleted, pls try again:"));
                }
            }
        }
        return true;
    }
}

