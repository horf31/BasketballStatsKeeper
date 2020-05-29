package com.dancc.basketballstatskeeper

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dancc.basketballstatskeeper.DisplayActivity
import com.dancc.basketballstatskeeper.MainPresenter.Interface
import com.dancc.basketballstatskeeper.R.layout
import com.dancc.basketballstatskeeper.R.string
import com.dancc.basketballstatskeeper.adapter.GameAdapter
import com.dancc.basketballstatskeeper.adapter.GameAdapter.GameAdapterCallback
import com.dancc.basketballstatskeeper.db.GameDatabase
import com.dancc.basketballstatskeeper.model.Game
import com.dancc.basketballstatskeeper.repository.GameViewModel
import com.google.android.gms.ads.AdRequest.Builder
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.adView
import kotlinx.android.synthetic.main.activity_main.addFakeGamesButton
import kotlinx.android.synthetic.main.activity_main.addFakePlayersButton
import kotlinx.android.synthetic.main.activity_main.addPlayerButton
import kotlinx.android.synthetic.main.activity_main.clearFakeGamesButton
import kotlinx.android.synthetic.main.activity_main.clearPlayersButton
import kotlinx.android.synthetic.main.activity_main.nameEditText
import kotlinx.android.synthetic.main.activity_main.numberEditText
import kotlinx.android.synthetic.main.activity_main.pastGamesRecycler
import kotlinx.android.synthetic.main.activity_main.startGameButton

class MainActivity() : AppCompatActivity(), Interface, GameAdapterCallback {

  private lateinit var mainPresenter: MainPresenter
  private lateinit var gameAdapter: GameAdapter
  private lateinit var firebaseAnalytics: FirebaseAnalytics
  private lateinit var gameViewModel: GameViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    // Obtain the FirebaseAnalytics instance.
    firebaseAnalytics = FirebaseAnalytics.getInstance(this)

    // Set up ads
    MobileAds.initialize(this)
    setUpAdView()
    val application = applicationContext as CustomApplication

    // Set up view model
    gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
    gameViewModel.games.observe(this,
        Observer { games: List<Game> -> gameAdapter.setGames(games) })

    // Set up recycler
    gameAdapter = GameAdapter(this)
    pastGamesRecycler.layoutManager = LinearLayoutManager(this)
    pastGamesRecycler.adapter = gameAdapter

    // Set up presenter
    mainPresenter = MainPresenter(GameDatabase.getInstance(this), application.ioScheduler,
        application.uiScheduler, firebaseAnalytics)
    mainPresenter.onAttachPage(this)
    addPlayerButton.setOnClickListener {
      val numberText: String = numberEditText!!.getText().toString()
      var number: Int
      try {
        number = numberText.toInt()
      } catch (e: NumberFormatException) {
        number = 0
      }
      val name: String = nameEditText.getText().toString()
      mainPresenter.onAddPlayerButtonClicked(number, name)
    }
    startGameButton.setOnClickListener {
      val intent = Intent(this, RecordActivity::class.java)
      startActivity(intent)
    }

    if (application.debugMode) {
      showPlayModeButtons()
    }
  }

  private fun setUpAdView() {
    // Create an ad request. Check logcat output for the hashed device ID to
    val adRequest = Builder().build()
    adView.loadAd(adRequest)
  }

  private fun showPlayModeButtons() {
    clearFakeGamesButton!!.setOnClickListener { mainPresenter.onClearAllGamesClicked() }
    clearFakeGamesButton!!.visibility = View.VISIBLE
    clearPlayersButton!!.setOnClickListener { mainPresenter.onClearAllPlayersClicked() }
    clearPlayersButton!!.visibility = View.VISIBLE
    addFakeGamesButton!!.setOnClickListener { mainPresenter.onAddMockedGamesClicked() }
    addFakeGamesButton!!.visibility = View.VISIBLE
    addFakePlayersButton!!.setOnClickListener { mainPresenter.onAddMockedPlayersClicked() }
    addFakePlayersButton!!.visibility = View.VISIBLE
  }

  override fun displayGames(games: List<Game>) {
    gameAdapter.setGames(games)
  }

  override fun showPlayerAdded() {
    numberEditText!!.setText("")
    nameEditText!!.setText("")
    Toast.makeText(applicationContext, getText(string.player_added), Toast.LENGTH_SHORT)
        .show()
  }

  override fun showEnterAName() {
    Toast.makeText(applicationContext, getText(string.player_enter_fail), Toast.LENGTH_SHORT)
        .show()
  }

  override fun onGameClicked(game: Game?) {
    val intent = Intent(this, DisplayActivity::class.java)
    intent.putExtra(DisplayActivity.GAME_ID, game!!.gameId)
    startActivity(intent)
  }

  override fun onDestroy() {
    mainPresenter.onDetachPage()
    super.onDestroy()
  }
}