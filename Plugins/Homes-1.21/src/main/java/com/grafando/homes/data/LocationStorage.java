package com.grafando.homes.data;

import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Properties;

public class LocationStorage {

    Connection conn = null;
    PreparedStatement ps = null;

    String username;
    String password;

    String serverName;
    String dbms;
    String SqlInsertQuery = "INSERT INTO homes(UUID, World, Axis_X, Axis_Y, Axis_Z, Homename, Username) VALUES(?,?,?,?,?,?,?);";
    String SqlUpdateQuery = "UPDATE homes SET World = ?, Axis_x = ?, Axis_Y = ?, Axis_Z = ?, Username = ? WHERE UUID = ? and Homename = ?;";
    String SqlSelectQuery = "SELECT World, Axis_X, Axis_Y, Axis_Z, Homename FROM homes WHERE UUID = ?;";
    String SqlCheckStatus = "SELECT NumberOfHomes FROM players WHERE UUID = ?;";
    String SqlSelectSpecificPlayerHome = "SELECT World, Axis_X, Axis_Y, Axis_Z FROM homes WHERE UUID like ? AND Homename like ?;";
    String SqlSpecSelectQuery = "SELECT * FROM homes WHERE UUID = ? AND Homename = ?;";
    String SqlDeleteSpecHome = "DELETE FROM homes WHERE UUID = ? AND Homename = ?;";
    ResultSet rs;
    int rowsAffected;


    public LocationStorage() {
        password = "";
        username = "";
        serverName = "";
        dbms = "mysql";
    }


    public Connection getConnection(){
        Connection conn = null;
        try {
            Properties connectionParms = new Properties();
            connectionParms.put("user", this.username);
            connectionParms.put("password", this.password);
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (this.dbms.equals("mysql")) {
                conn = DriverManager.getConnection(serverName, connectionParms);
            }
            assert conn != null;
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public void storeLocation(Player player, String homeName, Connection connection){
        try{
            conn = connection;
            ps= conn.prepareStatement(SqlSpecSelectQuery);
            ps.setString(1, String.valueOf(player.getUniqueId()));
            ps.setString(2, homeName);
            rs = ps.executeQuery();

            if(!rs.next()){
                ps = conn.prepareStatement(SqlInsertQuery);
                ps.setString(1, String.valueOf(player.getUniqueId()));
                ps.setString(2, player.getLocation().getWorld().getName());
                ps.setInt(3, (int) player.getLocation().getX());
                ps.setInt(4, (int) player.getLocation().getY());
                ps.setInt(5, (int) player.getLocation().getZ());
                ps.setString(6, homeName);
                ps.setString(7, player.getName());
                rowsAffected = ps.executeUpdate();
            }
            else{
                ps = conn.prepareStatement(SqlUpdateQuery);
                ps.setString(1, player.getLocation().getWorld().getName());
                ps.setInt(2, (int) player.getLocation().getX());
                ps.setInt(3, (int) player.getLocation().getY());
                ps.setInt(4, (int) player.getLocation().getZ());
                ps.setString(5, player.getName());
                ps.setString(6, String.valueOf(player.getUniqueId()));
                ps.setString(7, homeName);
                rowsAffected = ps.executeUpdate();
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet retrieveLocation(Player player, Connection connection){
        try{
            conn = connection;
            ps = conn.prepareStatement(SqlSelectQuery);
            ps.setString(1, String.valueOf(player.getUniqueId()));
            rs = ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public boolean DeleteSpecificHome(Player player, String Homename, Connection connection){
        boolean status = false;
        try {
            conn = connection;
            ps = conn.prepareStatement(SqlDeleteSpecHome);
            ps.setString(1, String.valueOf(player.getUniqueId()));
            ps.setString(2, Homename);
            int rowsAffected = ps.executeUpdate();
            status = rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    public int returnMultipleHomePermission(Player player, Connection connection){
        int sll = 0;
        try{
            conn = connection;
            ps = conn.prepareStatement(SqlCheckStatus);
            ps.setString(1, String.valueOf(player.getUniqueId()));
            rs=ps.executeQuery();
            if (rs.next()){
                sll = rs.getInt("NumberOfHomes");
            }else{
                sll = 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return sll;
    }

    public int getHomeCountFromPlayer(Player player, Connection connection){
        int sll = 0;
        try{
            conn = connection;
            ps = conn.prepareStatement("SELECT COUNT(*) FROM homes where UUID = ?");
            ps.setString(1, String.valueOf(player.getUniqueId()));
            rs=ps.executeQuery();
            if (rs.next()){
                sll = rs.getInt(1);
            }else{
                sll = 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return sll;
    }

    public ResultSet findSpecificPlayerHome(String Homename, Player player, Connection connection){
        try{
            conn = connection;
            ps = conn.prepareStatement(SqlSelectSpecificPlayerHome);
            ps.setString(1, String.valueOf(player.getUniqueId()));
            ps.setString(2, Homename);
            rs = ps.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

}

