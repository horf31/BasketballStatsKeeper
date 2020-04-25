package com.dancc.basketballstatskeeper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.adapter.StatsAdapter;
import com.dancc.basketballstatskeeper.model.GameStats;
import com.dancc.basketballstatskeeper.util.MockData;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

  @BindView(R.id.statsRecycler)
  RecyclerView statsRecycler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display);

    ButterKnife.bind(this);

    setUpRecycler();
  }

  private void setUpRecycler() {
    List<GameStats> mockData = MockData.getMockGameStats();

    statsRecycler.setLayoutManager(new LinearLayoutManager(this));
    statsRecycler.setAdapter(new StatsAdapter(mockData));
  }
}
