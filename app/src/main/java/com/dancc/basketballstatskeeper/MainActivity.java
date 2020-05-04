package com.dancc.basketballstatskeeper;

import android.content.Intent;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.adapter.GameAdapter;
import com.dancc.basketballstatskeeper.model.Game;
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

  private MainPresenter mainPresenter;

  private GameAdapter gameAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);

    mainPresenter = new MainPresenter();
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
  }

  @Override
  public void displayGames(List<Game> games) {
    gameAdapter = new GameAdapter(games, this);

    pastGamesRecycler.setLayoutManager(new LinearLayoutManager(this));
    pastGamesRecycler.setAdapter(gameAdapter);
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
