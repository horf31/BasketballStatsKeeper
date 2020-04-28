package com.dancc.basketballstatskeeper;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.adapter.PlayerListAdapter;
import com.dancc.basketballstatskeeper.adapter.StatsAdapter;
import com.dancc.basketballstatskeeper.db.GameDatabase;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.dancc.basketballstatskeeper.util.MockData;
import java.util.List;

public class DisplayActivity extends AppCompatActivity implements DisplayPresenter.Interface{

  @BindView(R.id.statsRecycler)
  RecyclerView statsRecycler;

  @BindView(R.id.playerAdapter)
  RecyclerView playerAdapter;

  private DisplayPresenter displayPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display);

    ButterKnife.bind(this);

    // Set up presenter
    CustomApplication application = (CustomApplication) getApplicationContext();

    displayPresenter = new DisplayPresenter(
        GameDatabase.getInstance(this),
        application.ioScheduler,
        application.uiScheduler,
        0
    );

    displayPresenter.onAttachPage(this);

  }

  // Presenter callbacks

  @Override
  public void displayGameStats(List<GameStats> gameStats) {
    statsRecycler.setLayoutManager(new LinearLayoutManager(this));
    statsRecycler.setAdapter(new StatsAdapter(gameStats));
  }

  @Override
  public void displayNames(List<String> names) {
    playerAdapter.setLayoutManager(new LinearLayoutManager(this));
    playerAdapter.setAdapter(new PlayerListAdapter(names));
  }

  @Override
  protected void onDestroy() {
    displayPresenter.onDetachPage();
    super.onDestroy();
  }
}
