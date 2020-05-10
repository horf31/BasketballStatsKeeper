package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.dancc.basketballstatskeeper.model.Player;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import java.util.ArrayList;
import java.util.List;

class DisplayPresenter {
  public interface Interface {
    void displayNames(List<String> names);
    void displayGameStats(List<GameStats> gameStats);
  }

  private GameDatabase db;
  private Scheduler ioScheduler;
  private Scheduler uiScheduler;
  private int gameId;

  private Interface page;

  private CompositeDisposable disposables = new CompositeDisposable();

  DisplayPresenter(GameDatabase db, Scheduler ioScheduler, Scheduler uiScheduler, int gameId) {
    this.db = db;
    this.ioScheduler = ioScheduler;
    this.uiScheduler = uiScheduler;
    this.gameId = gameId;
  }

  void onAttachPage(Interface page) {
    this.page = page;

    loadGameStatsFromGameId();
  }

  private void loadGameStatsFromGameId() {
    disposables.add(db.gameDao()
        .findById(gameId)
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(game -> {
              ArrayList<String> names = new ArrayList<>();
              for (Player player: game.players) {
                names.add(player.name);
              }
              page.displayNames(names);
              page.displayGameStats(game.gameStatsList);
            }
        ));
  }

  void onDetachPage() {
    disposables.clear();
  }
}
