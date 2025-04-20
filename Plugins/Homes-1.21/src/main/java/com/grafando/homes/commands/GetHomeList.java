package com.grafando.homes.commands;

import com.grafando.homes.data.LocationStorage;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetHomeList implements CommandExecutor {
    private Player player;
    private LocationStorage locStore = new LocationStorage();
    private Connection connection;

    public GetHomeList(Connection connectionInstance){
        this.connection = connectionInstance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        player = (Player) sender;
        if (sender instanceof Player){
            try {
                ResultSet rs = locStore.retrieveLocation(player, connection);
                if (rs.next()){
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&5>&d Your &lHomes &r&dare:"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&5>&d -&4 " + rs.getString("Homename")+"&r&d Loc: &f"
                                    +rs.getString("World")+"&d X: &f"+rs.getString("Axis_X")+"&d Y: &f"+rs.getString("Axis_Y")));
                    while (rs.next()){
                        player.sendMessage(ChatColor.translateAlternateColorCodes
                                ('&', "&5>&d -&4 " + rs.getString("Homename")+"&r&d Loc: &f"
                                        +rs.getString("World")+"&d X: &f"+rs.getString("Axis_X")+"&d Y: &f"+rs.getString("Axis_Y")));
                    }
                }else{
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&5>&d There are currently &lno homes&r&d stored in your name!"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
