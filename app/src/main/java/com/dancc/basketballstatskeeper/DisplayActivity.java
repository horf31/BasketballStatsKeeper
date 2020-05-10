package com.dancc.basketballstatskeeper;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.adapter.PlayerListAdapter;
import com.dancc.basketballstatskeeper.adapter.StatsAdapter;
import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.dancc.basketballstatskeeper.model.Player;
import com.dancc.basketballstatskeeper.repository.GameViewModel;
import com.dancc.basketballstatskeeper.util.MockData;
import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity{

  @BindView(R.id.statsRecycler)
  RecyclerView statsRecycler;

  @BindView(R.id.playerRecycler)
  RecyclerView playerRecycler;

  private GameViewModel gameViewModel;

  private StatsAdapter statsAdapter;

  private PlayerListAdapter playerListAdapter;

  public static final String GAME_ID = "game_id";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display);

    ButterKnife.bind(this);

    // Get the Intent that started this activity and extract the string
    Intent intent = getIntent();
    int gameId = intent.getIntExtra(GAME_ID, -1);

    // Set up presenter
    CustomApplication application = (CustomApplication) getApplicationContext();

    // Set up recyclers
    statsAdapter = new StatsAdapter();
    statsRecycler.setLayoutManager(new LinearLayoutManager(this));
    statsRecycler.setAdapter(statsAdapter);

    playerListAdapter = new PlayerListAdapter();
    playerRecycler.setLayoutManager(new LinearLayoutManager(this));
    playerRecycler.setAdapter(playerListAdapter);

    // Set up view model
    gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);
    gameViewModel.getGameById(gameId).observe(this, game -> {
      statsAdapter.setGameStats(game.gameStatsList);
      ArrayList<String> names = new ArrayList<>();
      for (Player player: game.players) {
        names.add(player.name);
      }
      playerListAdapter.setPlayers(names);
    });

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
