package de.tageban.scoreboard.listener;

import de.tageban.scoreboard.ScoreboardPlugin;
import de.tageban.scoreboard.database.objects.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/******************************************************************
 *    Copyright © 30TageBan/Chris 2020
 *    Erstellt: 22.01.2020 / 18:14      
 *    Orginal Class: KDListener
 ******************************************************************/

public class KDListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Bukkit.getScheduler().runTaskAsynchronously(ScoreboardPlugin.getInstance(), () -> {
      PlayerData playerData = ScoreboardPlugin.getInstance().getScoreboardDatabase().getPlayerData(event.getPlayer());

      ScoreboardPlugin.getInstance().getScoreboardFactory()
          .setSidebar(playerData.getPlayer(), "&8⚔ &4&lTwerion.net &8⚔", ScoreboardPlugin.getInstance().getDefaultSidebar());

      ScoreboardPlugin.getInstance().getScoreboardFactory().update(playerData);
    });
  }


  @EventHandler
  public void onEvent(PlayerDeathEvent event) {
    Bukkit.getScheduler().runTaskAsynchronously(ScoreboardPlugin.getInstance(), () -> {
      if (event.getEntity().getKiller() != null) {
        onKill(event.getEntity().getKiller());
      }
      onDeath(event.getEntity());
    });
  }

  private void onDeath(Player player) {
    PlayerData playerData = ScoreboardPlugin.getInstance().getScoreboardDatabase().getPlayerData(player);

    playerData.addDeaths(1);

    ScoreboardPlugin.getInstance().getScoreboardFactory().update(playerData);
    ScoreboardPlugin.getInstance().getScoreboardDatabase().savePlayerData(playerData);
  }

  private void onKill(Player player) {
    PlayerData playerData = ScoreboardPlugin.getInstance().getScoreboardDatabase().getPlayerData(player);

    playerData.addKills(1);

    ScoreboardPlugin.getInstance().getScoreboardFactory().update(playerData);
    ScoreboardPlugin.getInstance().getScoreboardDatabase().savePlayerData(playerData);
  }

}
