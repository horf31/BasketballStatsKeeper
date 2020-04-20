package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Operation;
import com.dancc.basketballstatskeeper.model.Player;
import java.util.ArrayList;

public class RecordPresenter {
  public interface Interface {
    void addOperation(Operation operation);
    void removeOperation();
    void displayMissingPlayerToast();
    void displayLastOperationToast();
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
    final Player currentPlayer = currentSelectedPlayer;
    if (currentPlayer == null) {
      page.displayMissingPlayerToast();
    }

    Operation newOperation = new Operation(currentPlayer, action);
    operations.add(newOperation);
    page.addOperation(newOperation);
  }

  void onUndoButtonClicked() {
    if (operations.isEmpty()) {
      page.displayLastOperationToast();
    } else {
      page.removeOperation();
    }
  }

  void onEndGameButtonClicked() {

  }
}
