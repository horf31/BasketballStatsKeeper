package com.dancc.basketballstatskeeper.util;

import com.dancc.basketballstatskeeper.model.Action;
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

  public static ArrayList<Player> getMockPlayers(){
    return new ArrayList<Player>() {
      {
        add (new Player(21, "Kevin G"));
        add (new Player(3, "Dwayne W"));
        add (new Player(10, "Mike B"));
        add (new Player(4, "Chris W"));
        add (new Player(13, "Steve N"));
      }
    };
  }
}
