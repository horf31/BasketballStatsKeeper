package com.dancc.basketballstatskeeper;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.adapter.GameAdapter;
import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.Game;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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

  private MainPresenter mainPresenter;

  private GameAdapter gameAdapter;

  private FirebaseAnalytics firebaseAnalytics;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    // Obtain the FirebaseAnalytics instance.
    firebaseAnalytics = FirebaseAnalytics.getInstance(this);

    // Set up presenter
    CustomApplication application = (CustomApplication) getApplicationContext();

    mainPresenter = new MainPresenter(
        GameDatabase.getInstance(this),
        application.ioScheduler,
        application.uiScheduler,
        firebaseAnalytics
    );
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

    setUpAdView();
  }

  private void setUpAdView() {
    if (adView != null) {
      // Create an ad request. Check logcat output for the hashed device ID to
      // get test ads on a physical device. e.g.
      // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
      AdRequest adRequest = new AdRequest.Builder()
          .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
          .build();
      adView.loadAd(adRequest);
    }
  }

  @Override
  public void displayGames(List<Game> games) {
    gameAdapter = new GameAdapter(games, this);

    pastGamesRecycler.setLayoutManager(new LinearLayoutManager(this));
    pastGamesRecycler.setAdapter(gameAdapter);
  }

  @Override
  public void showPlayerAdded() {
    numberEditText.setText("");
    nameEditText.setText("");
    Toast.makeText(getApplicationContext(), getText(R.string.player_added),
        Toast.LENGTH_SHORT).show();
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
