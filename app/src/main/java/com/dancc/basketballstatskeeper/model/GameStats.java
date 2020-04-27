package com.dancc.basketballstatskeeper.model;

import androidx.room.Entity;
import androidx.room.Ignore;
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
  public int threePtsMade;
  public int threePtsAttempted;
  public int twoPtsMade;
  public int twoPtsAttempted;
  public int freeThrowMade;
  public int freeThrowAttempted;

  public GameStats(int playerId) {
    this.playerId = playerId;
  }

  @Ignore
  public GameStats(int gameStatsId, int playerId, int gameId, int points, int rebounds, int assists,
      int steals, int blocks, int turnovers) {
    this.gameStatsId = gameStatsId;
    this.playerId = playerId;
    this.gameId = gameId;
    this.points = points;
    this.rebounds = rebounds;
    this.assists = assists;
    this.steals = steals;
    this.blocks = blocks;
    this.turnovers = turnovers;
  }

  public void changeStats(@org.jetbrains.annotations.NotNull Action action) {
    switch (action) {
      case POINT3: {
        threePtsAttempted++;
        threePtsMade++;
        points += 3;
        break;
      }
      case POINT2: {
        twoPtsAttempted++;
        twoPtsMade++;
        points += 2;
        break;
      }
      case POINT1: {
        freeThrowAttempted++;
        freeThrowMade++;
        points += 1;
        break;
      }
      case POINT3MISSED: {
        threePtsAttempted++;
        break;
      }
      case POINT2MISSED: {
        twoPtsAttempted++;
        break;
      }
      case POINT1MISSED: {
        freeThrowAttempted++;
        break;
      }
      case REBOUND: {
        rebounds++;
        break;
      }
      case ASSIST: {
        assists++;
        break;
      }
      case BLOCK: {
        blocks++;
        break;
      }
      case STEAL: {
        steals++;
        break;
      }
      case TURNOVER: {
        turnovers++;
        break;
      }
    }
  }

  private void revertStats(Action action) {
    switch (action) {
      case POINT3: {
        threePtsAttempted--;
        threePtsMade--;
        points -= 3;
        break;
      }
      case POINT2: {
        twoPtsAttempted--;
        twoPtsMade--;
        points -= 2;
        break;
      }
      case POINT1: {
        freeThrowAttempted--;
        freeThrowMade--;
        points -= 1;
        break;
      }
      case POINT3MISSED: {
        threePtsAttempted--;
        break;
      }
      case POINT2MISSED: {
        twoPtsAttempted--;
        break;
      }
      case POINT1MISSED: {
        freeThrowAttempted--;
        break;
      }
      case REBOUND: {
        rebounds--;
        break;
      }
      case ASSIST: {
        assists--;
        break;
      }
      case BLOCK: {
        blocks--;
        break;
      }
      case STEAL: {
        steals--;
        break;
      }
      case TURNOVER: {
        turnovers--;
        break;
      }
    }
  }
}
