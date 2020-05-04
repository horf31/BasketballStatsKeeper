package com.dancc.basketballstatskeeper;

import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.Game;
import com.dancc.basketballstatskeeper.model.Player;
import com.dancc.basketballstatskeeper.util.MockData;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import java.util.List;

class MainPresenter {
  public interface Interface {
    void displayGames(List<Game> games);

    void showPlayerAdded();
  }

  private GameDatabase db;
  private Scheduler ioScheduler;
  private Scheduler uiScheduler;

  private Interface page;

  private CompositeDisposable disposables = new CompositeDisposable();

  private boolean addingPlayer = false;

  MainPresenter(GameDatabase db, Scheduler ioScheduler, Scheduler uiScheduler) {
    this.db = db;
    this.ioScheduler = ioScheduler;
    this.uiScheduler = uiScheduler;
  }

  void onAttachPage(Interface page) {
    this.page = page;

    page.displayGames(MockData.getMockGames());
  }

  void onAddPlayerButtonClicked(int number, String name) {
    if (addingPlayer) return;

    addingPlayer = true;
    disposables.add(db.playerDao()
        .insert(new Player(number, name))
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(playerId -> {
          addingPlayer = false;
          page.showPlayerAdded();
        }));
  }

  void onDetachPage() {
    disposables.clear();
  }
}
