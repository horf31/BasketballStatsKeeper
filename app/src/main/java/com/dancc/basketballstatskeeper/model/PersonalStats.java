package com.dancc.basketballstatskeeper.model;

import java.util.List;

public class PersonalStats {
  public int playerId;

  public int points;
  public int rebounds;
  public int assists;
  public int steals;
  public int blocks;
  public int turnovers;
  public int threePtsMade;
  public int threePtsMissed;
  public int twoPtsMade;
  public int twoPtsMissed;
  public int freeThrowMade;
  public int freeThrowMissed;

  public List<Action> actions;

  public void newAction(Action action) {
    changeStats(action);
    actions.add(action);
  }

  public void revertLastAction(Action action) {
    assert (actions.size() > 0);
    revertStats(action);
    actions.remove(actions.size() - 1);
  }

  private void changeStats(@org.jetbrains.annotations.NotNull Action action) {
    switch (action) {
      case POINT3: {
        threePtsMade++;
        points += 3;
        break;
      }
      case POINT2: {
        twoPtsMade++;
        points += 2;
        break;
      }
      case POINT1: {
        freeThrowMade++;
        points += 1;
        break;
      }
      case POINT3MISSED: {
        threePtsMissed++;
        break;
      }
      case POINT2MISSED: {
        twoPtsMissed++;
        break;
      }
      case POINT1MISSED: {
        freeThrowMissed++;
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
        threePtsMade--;
        points -= 3;
        break;
      }
      case POINT2: {
        twoPtsMade--;
        points -= 2;
        break;
      }
      case POINT1: {
        freeThrowMade--;
        points -= 1;
        break;
      }
      case POINT3MISSED: {
        threePtsMissed--;
        break;
      }
      case POINT2MISSED: {
        twoPtsMissed--;
        break;
      }
      case POINT1MISSED: {
        freeThrowMissed--;
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


