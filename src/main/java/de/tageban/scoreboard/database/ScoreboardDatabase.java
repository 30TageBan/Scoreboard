package de.tageban.scoreboard.database;

import de.tageban.scoreboard.database.objects.PlayerData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import org.bukkit.entity.Player;

/******************************************************************
 *    Copyright © 30TageBan/Chris 2020
 *    Erstellt: 22.01.2020 / 18:20      
 *    Orginal Class: ScoreboardDatabase
 ******************************************************************/

public class ScoreboardDatabase {

  private final String schema = "CREATE TABLE IF NOT EXISTS kd (uuid VARCHAR(255), kills int, deaths int, PRIMARY KEY (`uuid`))";
  private final String load = "SELECT * FROM kd WHERE uuid = ? LIMIT 1";
  private final String save = "INSERT INTO kd (uuid, kills , deaths) VALUES (?,?,?) ON DUPLICATE KEY UPDATE kills = ?, deaths = ?";

  public PlayerData getPlayerData(Player player) {
    UUID uuid = player.getUniqueId();
    int kills = 0;
    int deaths = 0;
    try {
      MySQL mySQL = new MySQL();
      mySQL.connect();
      mySQL.update(schema);
      ResultSet loadResult = mySQL.query(load, uuid.toString());
      if (loadResult.next()) {
        kills = loadResult.getInt("kills");
        deaths = loadResult.getInt("deaths");
      }
      loadResult.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    PlayerData playerData = new PlayerData(uuid, kills, deaths);
    return playerData;
  }

  public void savePlayerData(PlayerData playerData) {
    MySQL mySQL = new MySQL();
    mySQL.connect();
    mySQL.update(schema);
    mySQL.update(save, playerData.getUuid().toString(), playerData.getKills(), playerData.getDeaths());
  }

}
