package com.dancc.basketballstatskeeper.util;

import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Operation;
import java.util.ArrayList;

public class MockData {
  public ArrayList<Operation> getMockOperations() {
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
}
