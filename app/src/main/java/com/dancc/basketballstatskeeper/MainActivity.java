package com.dancc.basketballstatskeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.adapter.GameAdapter;
import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.Game;
import com.dancc.basketballstatskeeper.repository.GameViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements MainPresenter.Interface, GameAdapter.GameAdapterCallback {
  @BindView(R.id.addPlayerButton)
  AppCompatButton addPlayerButton;

  @BindView(R.id.startGameButton)
  AppCompatButton startGameButton;

  @BindView(R.id.numberEditText)
  EditText numberEditText;

  @BindView(R.id.nameEditText)
  EditText nameEditText;

  @BindView(R.id.pastGamesRecycler)
  RecyclerView pastGamesRecycler;

  @BindView(R.id.adView)
  AdView adView;

  @BindView(R.id.clearFakeGames)
  AppCompatButton clearFakeGamesButton;

  @BindView(R.id.clearPlayers)
  AppCompatButton clearPlayersButton;

  @BindView(R.id.addFakeGames)
  AppCompatButton addFakeGamesButton;

  @BindView(R.id.addFakePlayers)
  AppCompatButton addFakePlayersButton;

  private MainPresenter mainPresenter;

  private GameAdapter gameAdapter;

  private FirebaseAnalytics firebaseAnalytics;

  private GameViewModel gameViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    // Obtain the FirebaseAnalytics instance.
    firebaseAnalytics = FirebaseAnalytics.getInstance(this);

    // Set up ads
    MobileAds.initialize(this, new OnInitializationCompleteListener() {
      @Override
      public void onInitializationComplete(InitializationStatus initializationStatus) {
      }
    });

    setUpAdView();

    CustomApplication application = (CustomApplication) getApplicationContext();

    // Set up view model
    gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
    gameViewModel.getGames().observe(this, games -> gameAdapter.setGames(games));

    // Set up recycler
    gameAdapter = new GameAdapter(this);

    pastGamesRecycler.setLayoutManager(new LinearLayoutManager(this));
    pastGamesRecycler.setAdapter(gameAdapter);

    // Set up presenter
    mainPresenter = new MainPresenter(GameDatabase.getInstance(this), application.ioScheduler,
        application.uiScheduler, firebaseAnalytics);
    mainPresenter.onAttachPage(this);

    addPlayerButton.setOnClickListener(view -> {
      String numberText = numberEditText.getText().toString();
      int number = Integer.parseInt(numberText);

      String name = nameEditText.getText().toString();

      mainPresenter.onAddPlayerButtonClicked(number, name);
    });

    startGameButton.setOnClickListener(view -> {
      Intent intent = new Intent(this, RecordActivity.class);
      startActivity(intent);
    });

    if (application.debugMode) {
      showPlayModeButtons();
    }
  }

  private void setUpAdView() {
    if (adView != null) {
      // Create an ad request. Check logcat output for the hashed device ID to
      AdRequest adRequest = new AdRequest.Builder().build();
      adView.loadAd(adRequest);
    }
  }

  private void showPlayModeButtons() {
    clearFakeGamesButton.setOnClickListener(view -> mainPresenter.onClearAllGamesClicked());
    clearFakeGamesButton.setVisibility(View.VISIBLE);

    clearPlayersButton.setOnClickListener(view -> mainPresenter.onClearAllPlayersClicked());
    clearPlayersButton.setVisibility(View.VISIBLE);

    addFakeGamesButton.setOnClickListener(view -> mainPresenter.onAddMockedGamesClicked());
    addFakeGamesButton.setVisibility(View.VISIBLE);

    addFakePlayersButton.setOnClickListener(view -> mainPresenter.onAddMockedPlayersClicked());
    addFakePlayersButton.setVisibility(View.VISIBLE);
  }

  @Override
  public void displayGames(List<Game> games) {
    gameAdapter.setGames(games);
  }

  @Override
  public void showPlayerAdded() {
    numberEditText.setText("");
    nameEditText.setText("");
    Toast.makeText(getApplicationContext(), getText(R.string.player_added), Toast.LENGTH_SHORT)
        .show();
  }

  @Override
  public void onGameClicked(Game game) {
    Intent intent = new Intent(this, DisplayActivity.class);
    intent.putExtra(DisplayActivity.GAME_ID, game.gameId);
    startActivity(intent);
  }

  @Override
  protected void onDestroy() {
    mainPresenter.onDetachPage();
    super.onDestroy();
  }
}
