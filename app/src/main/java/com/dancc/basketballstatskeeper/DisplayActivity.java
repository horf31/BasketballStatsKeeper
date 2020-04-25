package com.dancc.basketballstatskeeper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.adapter.PlayerListAdapter;
import com.dancc.basketballstatskeeper.adapter.StatsAdapter;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.dancc.basketballstatskeeper.util.MockData;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

  @BindView(R.id.statsRecycler)
  RecyclerView statsRecycler;

  @BindView(R.id.playerAdapter)
  RecyclerView playerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display);

    ButterKnife.bind(this);

    setUpNameRecycler();
    setUpStatsRecycler();

  }

  private void setUpStatsRecycler() {
    List<GameStats> mockData = MockData.getMockGameStats();

    statsRecycler.setLayoutManager(new LinearLayoutManager(this));
    statsRecycler.setAdapter(new StatsAdapter(mockData));
  }

  private void setUpNameRecycler() {
    playerAdapter.setLayoutManager(new LinearLayoutManager(this));
    playerAdapter.setAdapter(new PlayerListAdapter(MockData.getMockPlayerNames()));
  }
}
