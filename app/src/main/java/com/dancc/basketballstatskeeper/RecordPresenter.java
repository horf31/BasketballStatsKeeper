package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.Action;
import com.dancc.basketballstatskeeper.model.Game;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.dancc.basketballstatskeeper.model.Operation;
import com.dancc.basketballstatskeeper.model.Player;
import com.dancc.basketballstatskeeper.util.MockData;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class RecordPresenter {
  public interface Interface {
    void addOperation(Operation operation);

    void removeOperation(int position);

    void removeLastOperation();

    void displayMissingPlayerToast();

    void displayLastOperationToast();

    void displayPlayers(List<Player> players);

    void goToDisplayActivity(int gameId);
  }

  private GameDatabase db;
  private Scheduler ioScheduler;
  private Scheduler uiScheduler;
  private boolean debugMode;

  private Interface page;

  private Player currentSelectedPlayer = null;

  private ArrayList<Operation> operations = new ArrayList<>();

  private CompositeDisposable disposables = new CompositeDisposable();

  private List<Player> players;

  // Key is player id
  private HashMap<Integer, GameStats> gameStatsHashMap = new HashMap<>();

  RecordPresenter(GameDatabase db, Scheduler ioScheduler, Scheduler uiScheduler, boolean debugMode) {
    this.db = db;
    this.ioScheduler = ioScheduler;
    this.uiScheduler = uiScheduler;
    this.debugMode = debugMode;
  }

  void onAttachPage(Interface page) {
    this.page = page;
    loadPlayers();
    //insertFakePlayers();
  }

  private void loadPlayers() {
    disposables.add(db.playerDao()
        .getAll()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(players -> {
          this.players = players;
          initializePlayerStats();
          page.displayPlayers(players);
        }));
  }

  private void insertFakePlayers() {
    disposables.add(db.playerDao()
        .insertAll(MockData.getMockPlayers())
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe());
  }

  private void initializePlayerStats() {
    for (Player player: players) {
      GameStats stats = new GameStats(player.id);

      gameStatsHashMap.put(player.id, stats);
    }
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
    if (debugMode) {
      return;
    }

    for (Operation op : operations) {
      GameStats stats = gameStatsHashMap.get(op.player.id);
      if (stats != null) {
        stats.changeStats(op.action);
      }
    }

    ArrayList<GameStats> gameStatsArrayList = new ArrayList<>();
    for (Player player: players) {
      gameStatsArrayList.add(gameStatsHashMap.get(player.id));
    }

    Game game = new Game(players, gameStatsArrayList);

    disposables.add(
        db.gameDao().insert(game)
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe( gameId -> page.goToDisplayActivity(gameId.intValue()) )
    );
  }

  void onDetachPage() {
    disposables.clear();
  }

  // DEBUG
  private void insertFakeGame() {
    disposables.add(db.gameDao()
        .insert(MockData.getMockGame())
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe());
  }

  private void clearGame() {
    disposables.add(db.gameDao()
        .nukeTable()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe());
  }

  private void deletePlayer(Player player) {
    disposables.add(db.playerDao().delete(player)
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe()
    );
  }
}
