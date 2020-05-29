package com.dancc.basketballstatskeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dancc.basketballstatskeeper.R.layout
import com.dancc.basketballstatskeeper.adapter.PlayerListAdapter
import com.dancc.basketballstatskeeper.adapter.StatsAdapter
import com.dancc.basketballstatskeeper.model.Game
import com.dancc.basketballstatskeeper.repository.GameViewModel
import kotlinx.android.synthetic.main.activity_display.playerRecycler
import kotlinx.android.synthetic.main.activity_display.statsRecycler
import java.util.ArrayList

class DisplayActivity : AppCompatActivity() {
  private var gameViewModel: GameViewModel? = null
  private lateinit var statsAdapter: StatsAdapter
  private lateinit var playerListAdapter: PlayerListAdapter
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_display)

    // Get the Intent that started this activity and extract the string
    val intent = intent
    val gameId = intent.getIntExtra(GAME_ID, -1)

    // Set up recyclers
    statsAdapter = StatsAdapter()
    statsRecycler.layoutManager = LinearLayoutManager(this)
    statsRecycler.adapter = statsAdapter
    playerListAdapter = PlayerListAdapter()
    playerRecycler.layoutManager = LinearLayoutManager(this)
    playerRecycler.adapter = playerListAdapter

    // Set up view model
    gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
    gameViewModel!!.getGameById(gameId).observe(this,
        Observer { game: Game ->
          statsAdapter.setGameStats(game.gameStatsList)
          val names = ArrayList<String>()
          for (player in game.players) {
            names.add(player.name)
          }
          playerListAdapter.setPlayers(names)
        })
  }

  override fun onDestroy() {
    super.onDestroy()
  }

  companion object {
    const val GAME_ID = "game_id"
  }
}