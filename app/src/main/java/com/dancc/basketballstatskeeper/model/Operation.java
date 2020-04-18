package com.dancc.basketballstatskeeper.model;

public class Operation {
  public int playerId;
  public Action action;

  public Operation(int playerId, Action action) {
    this.playerId = playerId;
    this.action = action;
  }
}
