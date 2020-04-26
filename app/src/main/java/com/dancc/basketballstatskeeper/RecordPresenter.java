package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Operation;
import com.dancc.basketballstatskeeper.model.Player;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;

public class RecordPresenter {
  public interface Interface {
    void addOperation(Operation operation);

    void removeOperation(int position);

    void removeLastOperation();

    void displayMissingPlayerToast();

    void displayLastOperationToast();

    void displayPlayers(List<Player> players);
  }

  private GameDatabase db;
  private Scheduler ioScheduler;
  private Scheduler uiScheduler;

  private Interface page;

  private Player currentSelectedPlayer = null;

  private ArrayList<Operation> operations = new ArrayList<>();

  private CompositeDisposable disposables = new CompositeDisposable();

  RecordPresenter(
      GameDatabase db,
      Scheduler ioScheduler,
      Scheduler uiScheduler
  ) {
    this.db = db;
    this.ioScheduler = ioScheduler;
    this.uiScheduler = uiScheduler;
  }

  void onAttachPage(Interface page) {
    this.page = page;
    loadPlayers();
  }

  private void loadPlayers() {
    disposables.add(db.playerDao().getAll()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(players ->
            page.displayPlayers(players)
        )
    );

    //disposables.add(db.playerDao().insert(new Player(3, "Dan"))
    //    .subscribeOn(ioScheduler)
    //    .observeOn(uiScheduler)
    //    .subscribe(
    //
    //    )
    //);
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

  void onRemoveOperationClicked(int position) {
    page.removeOperation(position);
  }

  void onUndoButtonClicked() {
    if (operations.isEmpty()) {
      page.displayLastOperationToast();
    } else {
      page.removeLastOperation();
    }
  }

  void onEndGameButtonClicked() {

  }

  void onDetachPage() {
    disposables.clear();
  }
}
