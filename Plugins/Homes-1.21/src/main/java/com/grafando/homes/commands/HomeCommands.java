package com.grafando.homes.commands;

import com.grafando.homes.data.LocationStorage;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.bukkit.Bukkit.getServer;

public class HomeCommands implements CommandExecutor {

    private Player player;
    public LocationStorage Ls = new LocationStorage();
    private ResultSet rs;
    private Location homeLocation;
    private World World;
    String homeName;
    private Connection connection;

    public HomeCommands(Connection conn){
        this.connection = conn;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        player = (Player) sender;
        if(sender instanceof Player){
            if (cmd.getName().equalsIgnoreCase("sethome")){
                if (args.length > 0){
                    try {
                        if (Ls.findSpecificPlayerHome(args[0], player, connection).next()){
                            Ls.storeLocation(player, args[0], connection);
                            player.sendMessage(ChatColor.translateAlternateColorCodes
                                    ('&', "&5>&d Home updated successfully..."));
                        }else {
                            if (Ls.returnMultipleHomePermission(player, connection) > Ls.getHomeCountFromPlayer(player, connection)) {
                                homeName = args[0];
                                Ls.storeLocation(player, homeName, connection);
                                player.sendMessage(ChatColor.translateAlternateColorCodes
                                        ('&', "&5>&d Home stored successfully..."));
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes
                                        ('&', "&5>&d You cannot store more than &l" +
                                                Ls.getHomeCountFromPlayer(player, connection) + "&r&d homes!"));
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    homeName = "home";
                    Ls.storeLocation(player, homeName, connection);
                    player.sendMessage(ChatColor.translateAlternateColorCodes
                            ('&', "&5>&d Home stored successfully..."));
                }
            }
            if (cmd.getName().equalsIgnoreCase("home")){
                if (args.length > 0){
                    try {
                        rs = Ls.findSpecificPlayerHome(args[0], player, connection);
                        if (!rs.next()){
                            player.sendMessage(ChatColor.translateAlternateColorCodes
                                    ('&', "&5>&d Home not set yet!"));
                        }else{
                            World = getServer().getWorld(rs.getString("World"));
                            homeLocation = new Location(World, rs.getInt("Axis_X"),
                                    rs.getInt("Axis_Y"), rs.getInt("Axis_Z"));
                            player.teleport(homeLocation);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else{
                    try {
                        rs = Ls.findSpecificPlayerHome("home", player, connection);
                        if (!rs.next()){
                            player.sendMessage(ChatColor.translateAlternateColorCodes
                                    ('&', "&5>&d Home not set yet!"));
                        }else{
                            World = getServer().getWorld(rs.getString("World"));
                            homeLocation = new Location(World, rs.getInt("Axis_X"),
                                    rs.getInt("Axis_Y"), rs.getInt("Axis_Z"));
                            player.teleport(homeLocation);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

}
