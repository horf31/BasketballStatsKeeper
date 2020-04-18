package com.dancc.basketballstatskeeper.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GameStats {
  @PrimaryKey(autoGenerate = true)
  public int gameStatsId;
  public int playerId;
  public int gameId;

  public int points;
  public int rebounds;
  public int assists;
  public int steals;
  public int blocks;
  public int turnovers;
  public Shooting threePtsShooting;
  public Shooting twoPtsShooting;
  public Shooting freeThrowShooting;

  class Shooting {
    public int attempted;
    public int made;
  }
}
