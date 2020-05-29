package com.dancc.basketballstatskeeper

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.dancc.basketballstatskeeper.db.GameDatabase
import com.dancc.basketballstatskeeper.model.Game
import com.dancc.basketballstatskeeper.model.Player
import com.dancc.basketballstatskeeper.util.MockData
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.FirebaseAnalytics.Event
import com.google.firebase.analytics.FirebaseAnalytics.Param
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

internal class MainPresenter(private val db: GameDatabase,
    private val ioScheduler: Scheduler,
    private val uiScheduler: Scheduler,
    private val firebaseAnalytics: FirebaseAnalytics) {

  interface Interface {
    fun displayGames(games: List<Game>)
    fun showPlayerAdded()
    fun showEnterAName()
  }

  private lateinit var page: Interface
  private val disposables = CompositeDisposable()
  private var addingPlayer = false
  private val games: LiveData<List<Game>>? = null

  fun onAttachPage(page: Interface) {
    this.page = page

    //loadGames();
    logEvent()
  }

  private fun loadGames() {
    disposables.add(db.gameDao()
        .games
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe { games: List<Game> -> page.displayGames(games) })
  }

  private fun logEvent() {
    val bundle = Bundle()
    firebaseAnalytics.logEvent(Event.APP_OPEN, bundle)
  }

  fun onAddPlayerButtonClicked(number: Int, name: String?) {
    if (addingPlayer) return
    if (name == null || name.isEmpty()) {
      page.showEnterAName()
      return
    }
    val bundle = Bundle()
    bundle.putString(Param.ITEM_NAME, "Add Player")
    bundle.putString(Param.CONTENT_TYPE, "Button")
    firebaseAnalytics.logEvent(Event.SELECT_ITEM, bundle)
    addingPlayer = true
    disposables.add(db.playerDao()
        .insert(Player(number, name))
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe({
          addingPlayer = false
          page.showPlayerAdded()
        }, {}))
  }

  fun onDetachPage() {
    disposables.clear()
  }

  fun onAddMockedGamesClicked() {
    disposables.add(db.gameDao()
        .insertAll(MockData.getMockGames())
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe { loadGames() })
  }

  fun onClearAllGamesClicked() {
    disposables.add(db.gameDao()
        .nukeTable()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe { loadGames() })
  }

  fun onAddMockedPlayersClicked() {
    disposables.add(db.playerDao()
        .insertAll(MockData.getMockPlayers())
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe())
  }

  fun onClearAllPlayersClicked() {
    disposables.add(
        db.playerDao().nukeTable().subscribeOn(ioScheduler).observeOn(uiScheduler).subscribe())
  }

}