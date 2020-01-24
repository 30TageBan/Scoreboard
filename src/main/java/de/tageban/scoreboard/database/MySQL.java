package de.tageban.scoreboard.database;

import de.tageban.scoreboard.ScoreboardPlugin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/******************************************************************
 *    Copyright © 30TageBan/Chris 2020
 *    Erstellt: 22.01.2020 / 18:50      
 *    Orginal Class: MySQL
 ******************************************************************/

public class MySQL {

  private String host = ScoreboardPlugin.getInstance().getConfig().getString("database.host");
  private int port = ScoreboardPlugin.getInstance().getConfig().getInt("database.port");
  private String database = ScoreboardPlugin.getInstance().getConfig().getString("database.database");
  private String username = ScoreboardPlugin.getInstance().getConfig().getString("database.username");
  private String password = ScoreboardPlugin.getInstance().getConfig().getString("database.password");
  private Connection connection;


  public void connect() {
    if (isConnectet()) {
      disconnect();
    }
    try {
      connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public void disconnect() {
    if (isConnectet()) {
      try {
        connection.close();
        connection = null;
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
  }

  public void update(String qry) {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(qry);
      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update(String qry, String uuid, int kills, int deaths) {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(qry);
      preparedStatement.setString(1, uuid);
      preparedStatement.setInt(2, kills);
      preparedStatement.setInt(3, deaths);
      preparedStatement.setInt(4, kills);
      preparedStatement.setInt(5, deaths);
      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ResultSet query(String qry, Object... objects) {
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(qry);
      int x = 1;
      for (Object object : objects) {
        preparedStatement.setObject(x, object);
        x++;
      }
      return preparedStatement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }


  public boolean isConnectet() {
    return (connection == null ? false : true);
  }

}
