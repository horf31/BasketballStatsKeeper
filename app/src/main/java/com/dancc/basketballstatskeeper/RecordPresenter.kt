package com.dancc.basketballstatskeeper

import com.dancc.basketballstatskeeper.db.GameDatabase
import com.dancc.basketballstatskeeper.model.Action
import com.dancc.basketballstatskeeper.model.Game
import com.dancc.basketballstatskeeper.model.GameStats
import com.dancc.basketballstatskeeper.model.Operation
import com.dancc.basketballstatskeeper.model.Player
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import java.util.ArrayList
import java.util.HashMap

internal class RecordPresenter(private val db: GameDatabase, private val ioScheduler: Scheduler,
    private val uiScheduler: Scheduler) {
  interface Interface {
    fun addOperation(operation: Operation)
    fun removeOperation(position: Int)
    fun removeLastOperation()
    fun displayMissingPlayerToast()
    fun displayLastOperationToast()
    fun displayPlayers(players: List<Player>?)
    fun goToDisplayActivity(gameId: Int)
    fun setUpOperationPanel()
  }

  private lateinit var page: Interface
  private var currentSelectedPlayer: Player? = null
  private val operations = ArrayList<Operation>()
  private val disposables = CompositeDisposable()
  private var players: List<Player>? = null

  // Key is player id
  private val gameStatsHashMap = HashMap<Int, GameStats>()
  fun onAttachPage(page: Interface) {
    this.page = page
    loadPlayers()
  }

  private fun loadPlayers() {
    disposables.add(db.playerDao()
        .all
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe { players: List<Player>? ->
          this.players = players
          initializePlayerStats()
          page.displayPlayers(players)
        })
  }

  private fun initializePlayerStats() {
    for (player in players!!) {
      val stats = GameStats(player.id)
      gameStatsHashMap[player.id] = stats
    }
  }

  fun onPlayerClicked(player: Player?) {
    if (currentSelectedPlayer == null) {
      page.setUpOperationPanel()
    }
    currentSelectedPlayer = player
  }

  fun onOperationActionClicked(action: Action?) {
    val currentPlayer = currentSelectedPlayer
    if (currentPlayer == null) {
      page.displayMissingPlayerToast()
    }
    val newOperation = Operation(
        currentPlayer, action)
    operations.add(newOperation)
    page.addOperation(newOperation)
  }

  fun onRemoveOperationClicked(position: Int) {
    page.removeOperation(position)
  }

  fun onUndoButtonClicked() {
    if (operations.isEmpty()) {
      page.displayLastOperationToast()
    } else {
      page.removeLastOperation()
    }
  }

  fun onEndGameButtonClicked() {
    for (op in operations) {
      val stats = gameStatsHashMap[op.player.id]
      stats?.changeStats(op.action)
    }
    val gameStatsArrayList = ArrayList<GameStats?>()
    for (player in players!!) {
      gameStatsArrayList.add(gameStatsHashMap[player.id])
    }
    val game = Game(players, gameStatsArrayList)
    disposables.add(
        db.gameDao().insert(game)
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe { gameId: Long -> page.goToDisplayActivity(gameId.toInt()) }
    )
  }

  fun onDetachPage() {
    disposables.clear()
  }

}