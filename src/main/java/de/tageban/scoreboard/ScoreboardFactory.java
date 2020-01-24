package de.tageban.scoreboard;

import de.tageban.scoreboard.database.objects.PlayerData;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/******************************************************************
 *    Copyright © 30TageBan/Chris 2020
 *    Erstellt: 22.01.2020 / 22:41      
 *    Orginal Class: ScoreboardFactory
 ******************************************************************/

public class ScoreboardFactory {

  public void update(PlayerData playerData) {
    Player player = Bukkit.getPlayer(playerData.getUuid());
    createTeam(player, "kills", "&1 &8 ➥ &c", "", playerData.getKills() + " &7Kills");
    createTeam(player, "deaths", "&2 &8 ➥ &c", "", playerData.getDeaths() + " &7Deaths");
    createTeam(player, "kd", "&3 &8 ➥ &c", "", playerData.getKD() + " &7KD");
  }

  public Scoreboard getScoreboard(Player player) {
    if (player.hasMetadata("scoreboard")) {
      return (Scoreboard) player.getMetadata("scoreboard").get(0).value();
    }

    Future<Scoreboard> returnFuture = Bukkit.getScheduler()
        .callSyncMethod(ScoreboardPlugin.getInstance(), () -> Bukkit.getScoreboardManager().getNewScoreboard());

    Scoreboard scoreboard = null;
    try {
      scoreboard = returnFuture.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    player.setMetadata("scoreboard", new FixedMetadataValue(ScoreboardPlugin.getInstance(), scoreboard));
    return scoreboard;
  }

  public void setSidebar(Player player, String title, List<String> sidebar) {
    Scoreboard scoreboard = getScoreboard(player);
    Objective objective = scoreboard.getObjective(player.getName());
    if (objective != null) {
      objective.unregister();
    }
    objective = scoreboard.registerNewObjective(player.getName(), "dummy");
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    objective.setDisplayName(color(title));
    int x = sidebar.size();
    for (String score : sidebar) {
      objective.getScore(color(score)).setScore(x);
      x--;
    }
    player.setScoreboard(scoreboard);
  }

  public void createTeam(Player player, String name, String entry, String prefix, String suffix) {
    Scoreboard scoreboard = getScoreboard(player);
    Team team = scoreboard.getTeam(name);
    if (team == null) {
      team = scoreboard.registerNewTeam(name);
    }
    team.addEntry(color(entry));
    team.setPrefix(color(prefix));
    team.setSuffix(color(suffix));
  }

  private String color(String string) {
    return ChatColor.translateAlternateColorCodes('&', string);
  }
}
