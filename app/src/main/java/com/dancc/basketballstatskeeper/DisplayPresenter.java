package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.dancc.basketballstatskeeper.util.MockData;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

public class DisplayPresenter {
  public interface Interface {
    void displayGameStats(List<GameStats> gameStats);
  }

  private GameDatabase db;
  private Scheduler ioScheduler;
  private Scheduler uiScheduler;

  private Interface page;

  private CompositeDisposable disposables = new CompositeDisposable();

  DisplayPresenter(GameDatabase db, Scheduler ioScheduler, Scheduler uiScheduler) {
    this.db = db;
    this.ioScheduler = ioScheduler;
    this.uiScheduler = uiScheduler;
  }

  void onAttachPage(Interface page) {
    this.page = page;

    loadGameStats();

    //insertFakeGameStats();
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

  void onDetachPage() {
    disposables.clear();
  }
}
