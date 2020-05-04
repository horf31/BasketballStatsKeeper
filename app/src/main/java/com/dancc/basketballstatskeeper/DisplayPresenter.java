package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.dancc.basketballstatskeeper.model.Player;
import com.dancc.basketballstatskeeper.util.MockData;
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

  // DEBUG
  private void loadAnyGameStats() {
    disposables.add(db.gameDao()
        .getOne()
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

  private void loadGameStats() {
    disposables.add(db.gameStatsDao()
        .getAll()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(gameStats -> {
          page.displayGameStats(gameStats);
        }));
  }

  private void insertFakeGameStats() {
    disposables.add(db.gameStatsDao()
        .insertAll(MockData.getMockGameStats())
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe());
  }

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
}