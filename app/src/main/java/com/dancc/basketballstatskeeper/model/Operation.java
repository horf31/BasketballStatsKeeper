package com.dancc.basketballstatskeeper.model;

public class Operation {
  public Player player;
  public Action action;

  public Operation(Player player, Action action) {
    this.player = player;
    this.action = action;
  }
}
