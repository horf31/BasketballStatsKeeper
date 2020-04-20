package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.model.Player;

public class RecordPresenter {
  public interface Interface {
    void updateOperation();
  }

  private Interface page;

  private Player currentSelectedPlayer = null;

  public void onAttachPage(Interface page) {
    this.page = page;
  }

  public void onPlayerClicked(Player player) {
    currentSelectedPlayer = player;
  }
}
