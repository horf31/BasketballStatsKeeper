package com.dancc.basketballstatskeeper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.dancc.basketballstatskeeper.adapter.OperationAdapter;
import com.dancc.basketballstatskeeper.adapter.PlayerIconAdapter;
import com.dancc.basketballstatskeeper.model.Player;
import com.dancc.basketballstatskeeper.util.MockData;

public class RecordActivity extends AppCompatActivity implements
    RecordPresenter.Interface,
    PlayerIconAdapter.PlayerIconAdapterCallback {

  @BindView(R.id.lastOperationsRecycler)
  RecyclerView lastOperationsRecycler;

  @BindView(R.id.playerRecycler)
  RecyclerView playerRecycler;

  private RecordPresenter recordPresenter = new RecordPresenter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_record);

    ButterKnife.bind(this);

    setUpLastOperationsRecycler();

    setUpPlayerRecycler();

    recordPresenter.onAttachPage(this);
  }

  private void setUpLastOperationsRecycler() {
    lastOperationsRecycler.setLayoutManager(new LinearLayoutManager(this));
    lastOperationsRecycler.setAdapter(new OperationAdapter(MockData.getMockOperations()));
  }

  private void setUpPlayerRecycler() {
    playerRecycler.setLayoutManager(new LinearLayoutManager(this));
    playerRecycler.setAdapter(new PlayerIconAdapter(MockData.getMockPlayers(), this));
  }

  // Adapter Callback
  @Override
  public void onPlayerClicked(Player player) {
    recordPresenter.onPlayerClicked(player);
  }

  // Presenter Callback
  @Override
  public void updateOperation() {

  }
}
