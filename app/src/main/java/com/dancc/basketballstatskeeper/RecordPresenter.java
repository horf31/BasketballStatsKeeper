package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Operation;
import com.dancc.basketballstatskeeper.model.Player;
import java.util.ArrayList;

public class RecordPresenter {
  public interface Interface {
    void updateOperation();
    void displayMissingPlayerToast();
  }

  private Interface page;

  private Player currentSelectedPlayer = null;

  private ArrayList<Operation> operations = new ArrayList<>();

  void onAttachPage(Interface page) {
    this.page = page;
  }

  void onPlayerClicked(Player player) {
    currentSelectedPlayer = player;
  }

  void onOperationActionClicked(Action action) {
    if (currentSelectedPlayer == null) {
      page.displayMissingPlayerToast();
    }
    operations.add(new Operation(currentSelectedPlayer, action));
  }

  void onUndoButtonClicked() {

  }

  void onEndGameButtonClicked() {

  }
}
