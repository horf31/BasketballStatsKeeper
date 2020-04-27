package com.dancc.basketballstatskeeper.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.dancc.basketballstatskeeper.db.converter.GameStatsListConverter;
import com.dancc.basketballstatskeeper.db.converter.PlayerListConverter;
import java.util.List;

@Entity
public class Game {
  @PrimaryKey(autoGenerate = true)
  public int gameId;

  @TypeConverters(PlayerListConverter.class)
  public List<Player> players;

  @TypeConverters(GameStatsListConverter.class)
  public List<GameStats> gameStatsList;

  public Game(List<Player> players, List<GameStats> gameStatsList) {
    this.players = players;
    this.gameStatsList = gameStatsList;
  }
}
