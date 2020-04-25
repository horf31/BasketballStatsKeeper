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

  public GameStats(int gameStatsId, int playerId, int gameId, int points, int rebounds, int assists,
      int steals, int blocks, int turnovers, Shooting threePtsShooting, Shooting twoPtsShooting,
      Shooting freeThrowShooting) {
    this.gameStatsId = gameStatsId;
    this.playerId = playerId;
    this.gameId = gameId;
    this.points = points;
    this.rebounds = rebounds;
    this.assists = assists;
    this.steals = steals;
    this.blocks = blocks;
    this.turnovers = turnovers;
    this.threePtsShooting = threePtsShooting;
    this.twoPtsShooting = twoPtsShooting;
    this.freeThrowShooting = freeThrowShooting;
  }
}
