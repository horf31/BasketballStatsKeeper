package com.dancc.basketballstatskeeper.util;

import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Operation;
import com.dancc.basketballstatskeeper.model.Player;
import java.util.ArrayList;

public class MockData {
  public static ArrayList<Operation> getMockOperations() {
    return new ArrayList<Operation>() {
      {
        add(new Operation(4, Action.ASSIST));
        add(new Operation(9, Action.POINT1));
        add(new Operation(15, Action.POINT3));
        add(new Operation(12, Action.POINT2));
        add(new Operation(3, Action.ASSIST));
        add(new Operation(1, Action.ASSIST));
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
