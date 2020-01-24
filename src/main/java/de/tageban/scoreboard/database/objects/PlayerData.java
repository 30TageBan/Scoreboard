package de.tageban.scoreboard.database.objects;

import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/******************************************************************
 *    Copyright © 30TageBan/Chris 2020
 *    Erstellt: 22.01.2020 / 20:40      
 *    Orginal Class: PlayerData
 ******************************************************************/

public class PlayerData {

  private final UUID uuid;
  private int kills;
  private int deaths;

  public PlayerData(UUID uuid, int kills, int deaths) {
    this.uuid = uuid;
    this.kills = kills;
    this.deaths = deaths;
  }

  public Player getPlayer() {
    return Bukkit.getPlayer(uuid);
  }

  public UUID getUuid() {
    return uuid;
  }

  public int getKills() {
    return kills;
  }

  public void setKills(int kills) {
    this.kills = kills;
  }

  public void addKills(int kills) {
    this.kills += kills;
  }

  public int getDeaths() {
    return deaths;
  }

  public void setDeaths(int deaths) {
    this.deaths = deaths;
  }

  public void addDeaths(int deaths) {
    this.deaths += deaths;
  }

  public double getKD() {
    double KD = (double) kills / (double) deaths;
    KD = Math.round(KD * 100) / 100.0;
    return Double.valueOf(KD);
  }
}
