package com.dancc.basketballstatskeeper;

import android.os.Bundle;
import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.Game;
import com.dancc.basketballstatskeeper.model.Player;
import com.dancc.basketballstatskeeper.util.MockData;
import com.google.firebase.analytics.FirebaseAnalytics;
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
  private FirebaseAnalytics firebaseAnalytics;

  private Interface page;

  private CompositeDisposable disposables = new CompositeDisposable();

  private boolean addingPlayer = false;

  MainPresenter(GameDatabase db, Scheduler ioScheduler, Scheduler uiScheduler, FirebaseAnalytics firebaseAnalytics) {
    this.db = db;
    this.ioScheduler = ioScheduler;
    this.uiScheduler = uiScheduler;
    this.firebaseAnalytics = firebaseAnalytics;
  }

  void onAttachPage(Interface page) {
    this.page = page;

    page.displayGames(MockData.getMockGames());

    logEvent();
  }

  private void logEvent() {
    Bundle bundle = new Bundle();
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle);
  }

  void onAddPlayerButtonClicked(int number, String name) {
    if (addingPlayer) return;

    Bundle bundle = new Bundle();
    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Add Player");
    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Button");
    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

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
