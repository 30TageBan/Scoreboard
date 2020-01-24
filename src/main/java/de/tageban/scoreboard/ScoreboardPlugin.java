package de.tageban.scoreboard;

import de.tageban.scoreboard.database.ScoreboardDatabase;
import de.tageban.scoreboard.listener.KDListener;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;

/******************************************************************
 *    Copyright © 30TageBan/Chris 2020
 *    Erstellt: 22.01.2020 / 18:11      
 *    Orginal Class: ScoreboardPlugin
 ******************************************************************/

public class ScoreboardPlugin extends JavaPlugin {

  private static ScoreboardPlugin instance;
  private ScoreboardDatabase scoreboardDatabase;
  private ScoreboardFactory scoreboardFactory;

  @Override
  public void onEnable() {
    instance = this;
    saveDefaultConfig();
    reloadConfig();
    scoreboardDatabase = new ScoreboardDatabase();
    scoreboardFactory = new ScoreboardFactory();

    getServer().getPluginManager().registerEvents(new KDListener(), this);
  }

  public List<String> getDefaultSidebar() {
    List<String> sidebar = new ArrayList<>();
    sidebar.add("&1");
    sidebar.add("&8➤ &7Deadpool SkyPvP");
    sidebar.add("&2");
    sidebar.add("&4❉ &8| &4Stats");
    sidebar.add("&1 &8 ➥ &c");
    sidebar.add("&2 &8 ➥ &c");
    sidebar.add("&3 &8 ➥ &c");
    sidebar.add("&4 &8 ➥ &c%Honor%");
    sidebar.add("&3");
    sidebar.add("&4* &8| &4KillStreak");
    sidebar.add("&5 &8 ➥ &c%KillStreak%");
    sidebar.add("&4");
    sidebar.add("&4✪ &8| &4Money");
    sidebar.add("&6 &8 ➥ &c%Money%");
    return sidebar;
  }

  public ScoreboardFactory getScoreboardFactory() {
    return scoreboardFactory;
  }

  public ScoreboardDatabase getScoreboardDatabase() {
    return scoreboardDatabase;
  }

  public static ScoreboardPlugin getInstance() {
    return instance;
  }
}
