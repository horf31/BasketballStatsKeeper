package com.dancc.basketballstatskeeper.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.List;

@Entity
public class Game {
  @PrimaryKey(autoGenerate = true)
  public int gameId;

  public List<Player> players;

  public List<GameStats> gameStats;
}
