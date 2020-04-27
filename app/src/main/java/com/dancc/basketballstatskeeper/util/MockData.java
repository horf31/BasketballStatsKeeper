package com.dancc.basketballstatskeeper.util;

import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Game;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.dancc.basketballstatskeeper.model.Operation;
import com.dancc.basketballstatskeeper.model.Player;
import java.util.ArrayList;

public class MockData {
  public static ArrayList<Operation> getMockOperations() {
    return new ArrayList<Operation>() {
      {
        add(new Operation(new Player(4, "aaa"), Action.ASSIST));
        add(new Operation(new Player(9, "aaa"), Action.POINT1));
        add(new Operation(new Player(15, "aaa"), Action.POINT3));
        add(new Operation(new Player(12, "aaa"), Action.POINT2));
        add(new Operation(new Player(3, "aaa"), Action.ASSIST));
        add(new Operation(new Player(4, "aaa"), Action.ASSIST));
      }
    };
  }

  public static ArrayList<Player> getMockPlayers() {
    return new ArrayList<Player>() {
      {
        add(new Player(21, "Kevin G"));
        add(new Player(3, "Dwayne W"));
        add(new Player(10, "Mike B"));
        add(new Player(4, "Chris W"));
        add(new Player(13, "Steve N"));
        add(new Player(9, "Russel W"));
        add(new Player(2, "Devin B"));
        add(new Player(0, "Giannis A"));
      }
    };
  }

  public static ArrayList<String> getMockPlayerNames() {
    return new ArrayList<String>() {
      {
        add("");
        add("Kevin G");
        add("Dwayne W");
        add("Mike B");
        add("Chris W");
        add("Steve N");
        add("Russel W");
        add("Devin B");
        add("Giannis A");

      }
    };
  }

  public static ArrayList<GameStats> getMockGameStats() {
    return new ArrayList<GameStats>() {
      {
        add(new GameStats(0, 0, 0, 5, 2, 3, 1, 0, 0));
        add(new GameStats(0, 0, 0, 5, 2, 3, 1, 0, 0));
        add(new GameStats(0, 0, 0, 5, 2, 3, 1, 0, 0));
        add(new GameStats(0, 0, 0, 5, 2, 3, 1, 0, 0));
        add(new GameStats(0, 0, 0, 5, 2, 3, 1, 0, 0));
        add(new GameStats(0, 0, 0, 5, 2, 3, 1, 0, 0));
        add(new GameStats(0, 0, 0, 5, 2, 3, 1, 0, 0));
        add(new GameStats(0, 0, 0, 5, 2, 3, 1, 0, 0));
      }
    };
  }

  public static Game getMockGame() {
    return new Game(getMockPlayers(), getMockGameStats());
  }
}
